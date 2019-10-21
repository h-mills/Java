package project.pc.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

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
import project.pc.dao.OrderDao;

@Service
@Transactional
public class OrderServiceImpl implements OrderService
{	
	@Autowired OrderDao 	dao;
	@Autowired ServiceConfig serviceConfig;
	
	@Override
	public List<HashMap<String, Object>> getorderlist(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.getlist(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETLISTCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getlistcount(String user_no)
	{
		try
		{
			return dao.getlistcount(user_no);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETLISTCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return "0";
		}
	}
		
	@Override
	public HashMap<String, Object> orderregist(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.orderregist(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.REGISTORDER_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public int insertorder(MultipartHttpServletRequest request)
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		int result = 1;
		try
		{
			int companyNo = 0;
			if(request.getSession().getAttribute("COMPANY_NO") != null)
			{
				// insert to order_master
				companyNo = Integer.parseInt((String)request.getSession().getAttribute("COMPANY_NO"));
				paramMap.put("title", (String)request.getParameter("title").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("tel", (String)request.getParameter("tel").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("email", (String)request.getParameter("email").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("address", (String)request.getParameter("address").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("recvdate", (String)request.getParameter("recvdate").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
				paramMap.put("content", (String)request.getParameter("content"));
				paramMap.put("language", (String)request.getParameter("language"));
				paramMap.put("company_no", request.getSession().getAttribute("COMPANY_NO"));
				paramMap.put("user_no", request.getSession().getAttribute("USER_NO"));
				paramMap.put("orderth", (String)request.getParameter("orderth"));
				paramMap.put("category", Integer.parseInt((String)request.getParameter("category")));
				MultipartFile design_file = request.getFile("dsfile");
				
				if (design_file != null) {
					String filePath_ds = serviceConfig.getRootPath() + String.format("%05d", companyNo) + "/" + serviceConfig.getOrderPath() + WebUtil.getDate();
					String dsext = FileUtil.getFileExtension(design_file.getOriginalFilename());
					String fileName = "/design."+dsext;
					File f = new File(filePath_ds);
					if (f.exists() == false) {
						f.mkdirs();
					}
					f = null;
					f = new File(filePath_ds + fileName);
					design_file.transferTo(f);
					f = null;
					filePath_ds = filePath_ds + fileName; 
					filePath_ds = filePath_ds.replace(serviceConfig.getRootPath(), "");
					paramMap.put("dsfile", filePath_ds);
				}
				
				dao.insertorder(paramMap);
				
				// insert to order_data
				String order_no = paramMap.get("id").toString();
				paramMap.put("order_no", order_no);
				String[] cd = request.getParameterValues("cd");
				String[] pcd = request.getParameterValues("pcd");
				String[] name = request.getParameterValues("name");
				String[] dept_no = request.getParameterValues("dept_no");
				for(int i=0; i<cd.length; i++){
					paramMap.put("cd", cd[i]);
					paramMap.put("pcd", pcd[i]);
					paramMap.put("name", name[i]);
					paramMap.put("dept_no", dept_no[i]);
					dao.insertorderdata(paramMap);
				}
				
				// file upload & order_data 'file' update
				List<MultipartFile> exfiles = request.getFiles("excelfile");
				if(!exfiles.isEmpty()){
					String isfiles = request.getParameter("isfile");
					String[] isfile = isfiles.split(",");
					for(int i=0; i<exfiles.size(); i++){
						boolean check = true;
						int filecount = 1;
						String filePath = serviceConfig.getRootPath() + String.format("%05d", companyNo) + "/" + serviceConfig.getOrderPath() + WebUtil.getDate();
						String realPath = null;
						String ext =  FileUtil.getFileExtension(exfiles.get(i).getOriginalFilename());
						File f = new File(filePath);
						if(f.exists() == false){
							f.mkdirs();
						}
						f = null;
						while(check){
							File f1 = null;
							realPath = filePath + "/" + String.format("%05d", filecount) + "." + ext;
							f1 = new File(realPath);
							if(f1.exists()){
								filecount++;
							}
							else{
								check = false;
							}
							f1 = null;
						}
						f = new File(realPath);
						exfiles.get(i).transferTo(f);
						f = null;
						realPath = realPath.replace(serviceConfig.getRootPath(), "");
						paramMap.put("file", realPath);
						paramMap.put("file_cd", isfile[i]);
						dao.updatefilepath(paramMap);
					}
				}
			}
			else
			{
				result = -1;
			}
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.INSERTORDER_ERROR_SERVICE + "/" + e.getMessage());
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
	public HashMap<String, Object> getorderinfo(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.getorderinfo(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETORDERINFO_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public void updateorderconfirm(HashMap<String, Object> requestMap)
	{
		try
		{
			dao.updateorderconfirm(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDERANS_ERROR_SERVICE + "/" + e.getMessage());
		}
	}
	
	@Override
	public void updatereplyconfirm(HashMap<String, Object> requestMap)
	{
		try
		{
			dao.updatereplyconfirm(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEREPLYANS_ERROR_SERVICE + "/" + e.getMessage());
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getreplylist(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.getreplylist(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETREPLY_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public int updateorder(MultipartHttpServletRequest request)
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		int result = 1;
		
		try
		{
			int companyNo = 0;
			if(request.getSession().getAttribute("COMPANY_NO") != null)
			{
				companyNo = Integer.parseInt((String)request.getSession().getAttribute("COMPANY_NO"));
				if(companyNo != 0)
				{
					paramMap.put("title", (String)request.getParameter("title").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("tel", (String)request.getParameter("tel").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("email", (String)request.getParameter("email").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("address", (String)request.getParameter("address").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("recvdate", (String)request.getParameter("recvdate").replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
					paramMap.put("content", (String)request.getParameter("content"));
					paramMap.put("language", (String)request.getParameter("language"));
					paramMap.put("orderno", (String)request.getParameter("orderno"));
					paramMap.put("company_no", request.getSession().getAttribute("COMPANY_NO"));
					paramMap.put("user_no", request.getSession().getAttribute("USER_NO"));
					paramMap.put("category", Integer.parseInt((String)request.getParameter("category")));
					MultipartFile design_file = request.getFile("dsfile");
					
					if (design_file != null) {
						String filePath_ds = serviceConfig.getRootPath() + String.format("%05d", companyNo) + "/" + serviceConfig.getOrderPath() + WebUtil.getDate();
						String dsext = FileUtil.getFileExtension(design_file.getOriginalFilename());
						String fileName = "/design."+dsext;
						File f = new File(filePath_ds);
						if (f.exists() == false) {
							f.mkdirs();
						}
						f = null;
						f = new File(filePath_ds + fileName);
						design_file.transferTo(f);
						f = null;
						filePath_ds = filePath_ds + fileName; 
						filePath_ds = filePath_ds.replace(serviceConfig.getRootPath(), "");
						paramMap.put("dsfile", filePath_ds);
					}
					dao.updateorder(paramMap);
				}
				else
				{
					result = -1;
				}
			}
			else
			{
				result = -1;
			}
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDER_ERROR_SERVICE + "/" + e.getMessage());
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
	public int cancelorder(String orderno)
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		int result = 1;
		
		try
		{	
			paramMap.put("orderno", orderno);
			dao.cancelorder(paramMap);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDER_ERROR_SERVICE + "/" + e.getMessage());
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
	public HashMap<String, Object> insertreply(HashMap<String, Object> requestMap)
	{
		HashMap<String, Object> result = null;
		try
		{	
			String no = dao.insertreply(requestMap);
			result = dao.getreplyinfo(no);
			return result;
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.INSERTREPLY_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public void updateordercount(MultipartHttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<>();
		try
		{
			int companyNo = 0;
			if(request.getSession().getAttribute("COMPANY_NO") != null)
			{
				companyNo = Integer.parseInt((String)request.getSession().getAttribute("COMPANY_NO"));
				paramMap.put("company_no", request.getSession().getAttribute("COMPANY_NO"));
				
				dao.updateordercount(paramMap);
			}
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.INSERTORDER_ERROR_SERVICE + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}

	@Override
	public void updateordernew(HashMap<String, Object> requestMap) {
		try
		{
			dao.updateordernew(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEREPLYANS_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public List<HashMap<String, Object>> getcompanyorgchart(HashMap<String, Object> requestMap) {
		try
		{
			return dao.getcompanyorgchart(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETLISTCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getorderorgchart(HashMap<String, Object> requestMap) {
		try
		{
			return dao.getorderorgchart(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETLISTCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public HashMap<String, Object> xlsupdate(MultipartHttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> requestMap = new HashMap<>();
		int companyNo = 0;
		try{
			MultipartFile file = request.getFile("updatefile");
			String cd = request.getParameter("cd");
			String orderno = request.getParameter("orderno");
			boolean check = true;
			companyNo = Integer.parseInt((String)request.getSession().getAttribute("COMPANY_NO"));
			
			int filecount = 1;
			String filePath = serviceConfig.getRootPath() + String.format("%05d", companyNo) + "/" + serviceConfig.getOrderPath() + WebUtil.getDate();
			String realPath = null;
			String ext =  FileUtil.getFileExtension(file.getOriginalFilename());
			File f = new File(filePath);
			if(f.exists() == false){
				f.mkdirs();
			}
			f = null;
			while(check){
				File f1 = null;
				realPath = filePath + "/" + String.format("%05d", filecount) + "." + ext;
				f1 = new File(realPath);
				if(f1.exists()){
					filecount++;
				}
				else{
					check = false;
				}
				f1 = null;
			}
			f = new File(realPath);
			file.transferTo(f);
			f = null;
			realPath = realPath.replace(serviceConfig.getRootPath(), "");
			requestMap.put("file", realPath);
			requestMap.put("cd", cd);
			requestMap.put("orderno", orderno);
			dao.xlsupdate(requestMap);
			
			resultMap.put("realpath", realPath);
		}catch(Exception e){
			return null;
		}
		return resultMap;
	}

	@Override
	public int getdeptcount(String company_no) {
		try{
			return dao.getdeptcount(company_no);
		}catch(Exception e){
			return 0;
		}
	}
}
