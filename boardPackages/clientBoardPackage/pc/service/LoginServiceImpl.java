package project.pc.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.common.util.FileUtil;
import project.common.util.WebUtil;
import project.pc.dao.LoginDao;


@Service
@Transactional
public class LoginServiceImpl implements LoginService 
{
	@Autowired LoginDao 	loginDao;
	@Autowired ServiceConfig serviceConfig;
	
	@Override
	public int idcheck(String user_id)
	{
		try
		{
			return loginDao.idcheck(user_id.replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
		}
		catch (Exception ex)
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.IDCHECK_ERROR_SERVICE + "/" + ex.getMessage());
			return -1;
		}
	}
	
	@Override
	public String biznumbercheck(String biznumber)
	{
		try
		{
			return loginDao.biznumbercheck(biznumber.replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
		}
		catch (Exception ex)
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.BIZNUMBERCHECK_ERROR_SERVICE + "/" + ex.getMessage());
			return "-1";
		}
	}
	
	@Override
	public HashMap<String, Object> getcompanyinfo(String no)
	{
		try
		{
			return loginDao.getcompanyinfo(no.replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
		}
		catch (Exception ex)
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETCOMPANYINFO_ERROR_SERVICE + "/" + ex.getMessage());
			return null;
		}
	}
	
	@Override
	public int insertRegist(MultipartHttpServletRequest request)
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		int result = 1;
		
		try
		{
			MultipartFile file = request.getFile("bizfile");
			String existcompany = request.getParameter("existcompany");
			int companyNo = 0;
			if (existcompany.equals("1")) {
				String biznumber = (String)request.getParameter("biznumber").replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
				paramMap.put("onenumber", (String)request.getParameter("onenumber").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("fax", (String)request.getParameter("fax").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("address", (String)request.getParameter("address").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				
				companyNo = Integer.parseInt(loginDao.biznumbercheck(biznumber));
				if (companyNo > 0) {
					//회사 정보 갱신
					paramMap.put("companyno", String.format("%d", companyNo));
					companyNo = loginDao.updateCompany(paramMap);
				}
			}
			else
			{
				if(file.isEmpty() == false)
				{
					paramMap.put("biznumber", (String)request.getParameter("biznumber").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("company", (String)request.getParameter("company").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("ceo", (String)request.getParameter("ceo").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("onenumber", (String)request.getParameter("onenumber").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("fax", (String)request.getParameter("fax").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("address", (String)request.getParameter("address").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					//회사 정보 등록
					companyNo = loginDao.insertCompany(paramMap);

					if (companyNo > 0) {
						//사업자등록증 저장
						String filePath = serviceConfig.getRootPath() + String.format("%05d", companyNo) + "/" + serviceConfig.getBusinessPath();
						File f = new File(filePath);
						if(f.exists() == false)
						{
							f.mkdirs();
						}
						f = null;				
						
						String ext = FileUtil.getFileExtension(file.getOriginalFilename());
						filePath = serviceConfig.getRootPath() + String.format("%05d", companyNo) + "/" + serviceConfig.getBusinessPath() + WebUtil.getDate() + "." + ext;
						
						f = new File(filePath);
						file.transferTo(f);
						f = null;
						String newPath = "";
						filePath = filePath.replace(serviceConfig.getRootPath(), "");
						newPath = filePath.replace("\\", "/");
						paramMap.put("filepath", newPath);
						paramMap.put("companyno", String.format("%d", companyNo));
						//사업자등록증 경로 업데이트
						loginDao.updateCompanyFilepath(paramMap);
					}
				}
			}
			if(companyNo > 0)
			{
				paramMap.put("id", (String)request.getParameter("user_id").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("passwd", (String)request.getParameter("user_pwd").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("name", (String)request.getParameter("user_name").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("mobile", (String)request.getParameter("mobile").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("tel", (String)request.getParameter("tel").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("email", (String)request.getParameter("email").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				//회원 정보 등록
				loginDao.insertUser(paramMap);

				String st_industrys = request.getParameter("st_industry");
				if (st_industrys != null)
				{
					String[] st_industry = st_industrys.split(",");
					paramMap.put("st_industry", st_industry);
					//서비스업종 등록
					loginDao.deleteIndustry(paramMap);
					loginDao.insertIndustry(paramMap);
				}
			}
			else
			{
				result = -1;
			}
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.REGIST_ERROR_SERVICE + "/" + e.getMessage());
			result = -1;
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return result;
	}
	
	@Override
	public HashMap<String, Object> checkUserInfo(HashMap<String, Object> param)
	{
		try
		{
			return loginDao.checkUserInfo(param);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.LOGIN_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getid(HttpServletRequest request)
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		try
		{
			paramMap.put("name", (String)request.getParameter("name").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("mobile", (String)request.getParameter("mobile").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("tel", (String)request.getParameter("tel").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("email", (String)request.getParameter("email").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			return loginDao.getid(paramMap);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETID_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}
	
	@Override
	public String getpwd(HttpServletRequest request)
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		try
		{
			paramMap.put("id", (String)request.getParameter("user_id").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("name", (String)request.getParameter("user_name").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("mobile", (String)request.getParameter("mobile").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("tel", (String)request.getParameter("tel").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("email", (String)request.getParameter("email").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("biznumber", (String)request.getParameter("biznumber").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("ceo", (String)request.getParameter("ceo").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("onenumber", (String)request.getParameter("onenumber").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			paramMap.put("fax", (String)request.getParameter("fax").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
			return loginDao.getpwd(paramMap);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETPW_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}
	
	@Override
	public HashMap<String, Object> getuserinfo(HashMap<String, Object> param)
	{
		try
		{
			return loginDao.getuserinfo(param);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETUSERINFO_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public int updateuserinfo(HashMap<String, Object> param)
	{
		try
		{
			loginDao.updateuserinfo(param);
			return 1;
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.UPDATEUSER_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int updatecompanyinfo(MultipartHttpServletRequest request)
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		int result = 1;
		
		try
		{
			MultipartFile file = request.getFile("bizfile");
			if(file.isEmpty() == false)
			{
				String company = (String)request.getSession().getAttribute("COMPANY_NO");
				int companyNo = Integer.parseInt(company);
				paramMap.put("no", company);
				paramMap.put("companyno", company);
				paramMap.put("ceo", (String)request.getParameter("ceo").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("tel", (String)request.getParameter("tel").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("fax", (String)request.getParameter("fax").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("address", (String)request.getParameter("address").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				
				//사업자등록증 저장
				String filePath = serviceConfig.getRootPath() + String.format("%05d", companyNo) + "/" + serviceConfig.getBusinessPath();
				File f = new File(filePath);
				if(f.exists() == false)
				{
					f.mkdirs();
				}
				f = null;				
				
				String ext = FileUtil.getFileExtension(file.getOriginalFilename());
				filePath = serviceConfig.getRootPath() + String.format("%05d", companyNo) + "/" + serviceConfig.getBusinessPath() + WebUtil.getDate() + "." + ext;
				
				f = new File(filePath);
				file.transferTo(f);
				f = null;
				String newPath = "";
				filePath = filePath.replace(serviceConfig.getRootPath(), "");
				newPath = filePath.replace("\\", "/");
				paramMap.put("file", newPath);
				//사업자등록증 경로 업데이트
				loginDao.updatecompanyinfo(paramMap);
				
				String st_industrys = request.getParameter("st_industry");
				if (st_industrys != null)
				{
					String[] st_industry = st_industrys.split(",");
					paramMap.put("st_industry", st_industry);
					//서비스업종 등록
					loginDao.deleteIndustry(paramMap);
					loginDao.insertIndustry(paramMap);
				}
			}
			else return -1;
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.UPDATECOMPANY_ERROR_SERVICE + "/" + e.getMessage());
			result = -1;
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return result;
	}
	
	@Override
	public int updateuserdelete(String no)
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		try
		{
			paramMap.put("no", no);
			return loginDao.updateuserdelete(paramMap);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getIndustryList()
	{
		try
		{
			return loginDao.getIndustryList();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getIndustryInfo(String no)
	{
		try
		{
			return loginDao.getIndustryInfo(no);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
}