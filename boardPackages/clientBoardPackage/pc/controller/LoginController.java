package project.pc.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.pc.service.LoginService;

@Controller
@RequestMapping("/pc/login/")
public class LoginController extends CommonController 
{
	@Autowired LoginService  loginService;
	
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		return new ModelAndView("/pc/login/login");
	}
	
	@RequestMapping("submit")
	@HPNullCheck(parameters={"user_id", "user_pwd"})
	public ModelAndView submit(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		try
		{
			HttpSession session = request.getSession();
			Object USER_ID = session.getAttribute("USER_ID");

			ModelAndView mav = new ModelAndView();
			//session check
			if (USER_ID == null || USER_ID == "")
			{
				HashMap<String, Object> requestMap = null;
				HashMap<String, Object> result = null;
				requestMap = this.makeRequestMap(request);
				result = loginService.checkUserInfo(requestMap);
				
				if(result != null)
				{
					if(result.get("id") != null)
					{
						request.getSession().setAttribute("USER_ID", result.get("id").toString());
						request.getSession().setAttribute("USER_NO", result.get("no").toString());
						request.getSession().setAttribute("COMPANY_NO", result.get("company_no").toString());
						if (result.get("level") != null)
						{
							HashMap<String, Object> levelMap = new HashMap<>();
							int level = (int) result.get("level");
							levelMap.put("m_order", (level & 0x01) > 0 ? "1" : "0");
							levelMap.put("m_notice", (level & 0x02) > 0 ? "1" : "0");
							levelMap.put("m_card", (level & 0x04) > 0 ? "1" : "0");
							levelMap.put("m_stats", (level & 0x08) > 0 ? "1" : "0");
							levelMap.put("m_dept", (level & 0x16) > 0 ? "1" : "0");
							request.getSession().setAttribute("LEVEL", levelMap);
						}
						mav.setViewName("redirect:/pc/main/frame");
					}
					result.clear();
					result = null;
				}
				else
				{
					mav.setViewName("/pc/login/login");
					mav.addObject("session", "nomember");
				}
			}
			else
			{
				//회원 확인후 세션 부여
				request.getSession().setAttribute("USER_ID", (String)USER_ID);
				mav.setViewName("redirect:/pc/main/frame");
			}
			
			return mav;
		} 
		catch(Exception be) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.LOGIN_ERROR + "/" + be.getMessage());
		}
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		try
		{
			request.getSession().setAttribute("USER_ID", null);
			request.getSession().invalidate();
			ModelAndView mav = new ModelAndView();
			mav.setViewName("/pc/login/session");
			mav.addObject("session", "logout");
			return mav;
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.LOGOUT_ERROR + "/" + e.getMessage());
		}
	}

	@RequestMapping("sessionclose")
	public ModelAndView sessionclose(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		try
		{
			request.getSession().setAttribute("USER_ID", null);
			request.getSession().invalidate();
			ModelAndView mav = new ModelAndView();
			mav.setViewName("/pc/login/session");
			mav.addObject("session", "sessionclose");
			return mav;
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.SESSIONCLOSE_ERROR + "/" + e.getMessage());
		}
	}
	
	@RequestMapping("findid")
	public ModelAndView findid(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		return new ModelAndView("/pc/login/findid");
	}
	
	@RequestMapping("findidsubmit")
	public ModelAndView findidsubmit(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject jsonObj = new JSONObject();
		
		try
		{
			String user_id = null;
			user_id = loginService.getid(request);
			
			jsonObj.put("user_id", user_id);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.FINDUSERID_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
		}
		
		return null;
	}
	
	@RequestMapping("findpw")
	public ModelAndView findpw(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		return new ModelAndView("/pc/login/findpw");
	}
	
	@RequestMapping("findpwsubmit")
	public ModelAndView findpwsubmit(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject jsonObj = new JSONObject();
		
		try
		{
			String passwd = null;
			passwd = loginService.getpwd(request);
			
			if(passwd != null)
			{
				jsonObj.put("result", "1");
				jsonObj.put("passwd", passwd);
			}
			else
			{
				jsonObj.put("result", "0");
			}
						
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.FINDUSERID_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
		}
		
		return null;
	}

	@RequestMapping("regist")
	public ModelAndView regist(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		List<HashMap<String, Object>> industryList = null;
		try
		{
			industryList = loginService.getIndustryList();
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.FINDUSERID_ERROR + "/" + e.getMessage());
		}

		return new ModelAndView("/pc/login/regist", "industryList", industryList);
	}
	
	@RequestMapping("idcheck")
	@HPNullCheck(parameters={"user_id"})
	public ModelAndView idcheck(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			String user_id = request.getParameter("user_id");
			
			if(user_id == null)
			{
				jsonObj.put("user_id", "-1");
			}
			else
			{
				int check = loginService.idcheck(user_id);
				if(check == 0)
				{
					jsonObj.put("user_id", "1");//ok
				}
				else
				{
					jsonObj.put("user_id", "0");//현재 사용중인 id
				}
			}
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.IDCHECK_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
		}
		
		return null;
	}
	
	@RequestMapping("biznumbercheck")
	@HPNullCheck(parameters={"biznumber"})
	public ModelAndView biznumbercheck(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> info = null;
		List<HashMap<String, Object>> industryInfo = null;
		try
		{
			String biznumber = request.getParameter("biznumber");
			
			if(biznumber == null)
			{
				jsonObj.put("biznumber", "-1");
			}
			else
			{
				String no = loginService.biznumbercheck(biznumber);
				if(no == null)
				{
					jsonObj.put("biznumber", "1");
				}
				else if (no.equals("-1"))
				{
					jsonObj.put("biznumber", "-1");
				}
				else
				{
					String [] splTel = null;
					String [] splFax = null;
					jsonObj.put("biznumber", "0");
					info = loginService.getcompanyinfo(no);
					
					String tel = (String)info.get("tel");
					if(tel != null && tel.length() > 0)
					{
						splTel = tel.split("-");
					}
					String fax = (String)info.get("fax");
					if(fax != null && fax.length() > 0)
					{
						splFax = fax.split("-");
					}
					
					jsonObj.put("company", info.get("name"));
					jsonObj.put("ceo", info.get("ceo"));
					if(tel != null)
					{
						jsonObj.put("number_first", splTel[0]);
						jsonObj.put("number_middle", splTel[1]);
						jsonObj.put("number_last", splTel[2]);
					}
					if(fax != null)
					{
						jsonObj.put("fax_first", splFax[0]);
						jsonObj.put("fax_middle", splFax[1]);
						jsonObj.put("fax_last", splFax[2]);
					}
					
					jsonObj.put("address", info.get("address"));

					industryInfo = loginService.getIndustryInfo(no);
					if (industryInfo != null) 
					{
						jsonObj.put("industryInfo", industryInfo);
					}
				}
			}
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.BIZNUMBERCHECK_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
		}
		
		return null;
	}
	
	@RequestMapping("registsubmit")
	public ModelAndView registsubmit(MultipartHttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			int result = -1;
			result = loginService.insertRegist(request);
			
			if(result == 1)
			{
				jsonObj.put("result", "1");
			}
			else
			{
				jsonObj.put("result", "-1");
			}
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.BIZNUMBERCHECK_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
		}
		
		return null;
	}
	
	@RequestMapping("usermodify")
	public ModelAndView usermodify(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		HashMap<String, Object> info = null;
		
		try
		{
			String userno = (String)request.getSession().getAttribute("USER_NO");
			requestMap.put("user_no", userno);
			info = loginService.getuserinfo(requestMap);
			if(info != null)
			{
				try
				{
					String mobile = info.get("mobile").toString();
					String []mbspt = mobile.split("-");
					String tel = info.get("tel").toString();
					String []telspt = tel.split("-");
					info.put("mobile_first", mbspt[0]);
					info.put("mobile_middle", mbspt[1]);
					info.put("mobile_last", mbspt[2]);
					info.put("tel_first", telspt[0]);
					info.put("tel_middle", telspt[1]);
					info.put("tel_last", telspt[2]);
				}
				catch (Exception e)
				{
					new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETUSERINFO_ERROR + "/" + e.getMessage());
				}
				
			}
			result.put("info", info);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETUSERINFO_ERROR + "/" + e.getMessage());
		}
		finally
		{
			requestMap.clear();
			requestMap = null;
		}
		
		return new ModelAndView("/pc/login/usermodify", "data", result);
	}
	
	@RequestMapping("usermodifysubmit")
	@HPNullCheck(parameters={"passwd", "name", "mobile", "tel", "email"})
	public void usermodifysubmit(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> requestMap = null;
				
		try
		{
			String userno = (String)request.getSession().getAttribute("USER_NO");
			requestMap = this.makeRequestMap(request);
			requestMap.put("no", userno);
			int result = loginService.updateuserinfo(requestMap);
			if(result == 1)
			{
				jsonObj.put("result", "1");
			}
			else
			{
				jsonObj.put("result", "-1");
			}
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		    
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.UPDATEUSER_ERROR + "/" + e.getMessage());
		}
		finally
		{
			requestMap.clear();
			requestMap = null;
		}
	}
	
	@RequestMapping("companymodify")
	public ModelAndView companymodify(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> info = null;
		List<HashMap<String, Object>> industryList = null;
		List<HashMap<String, Object>> industryInfo = null;

		try
		{
			String companyno = (String)request.getSession().getAttribute("COMPANY_NO");
			info = loginService.getcompanyinfo(companyno);
			industryList = loginService.getIndustryList();
			industryInfo = loginService.getIndustryInfo(companyno);

			try
			{
				String biznumber = info.get("biznumber").toString();
				String []bizspt = biznumber.split("-");
				String tel = info.get("tel").toString();
				String []telspt = tel.split("-");
				String fax = info.get("fax").toString();
				String []faxspt = fax.split("-");
				
				info.put("biz_first", bizspt[0]);
				info.put("biz_middle", bizspt[1]);
				info.put("biz_last", bizspt[2]);
				info.put("tel_first", telspt[0]);
				info.put("tel_middle", telspt[1]);
				info.put("tel_last", telspt[2]);
				info.put("fax_first", faxspt[0]);
				info.put("fax_middle", faxspt[1]);
				info.put("fax_last", faxspt[2]);
				
				if (industryInfo != null && industryList != null) 
				{
					for (int i=0; i<industryList.size(); i++) 
					{
						for (int j=0; j<industryInfo.size(); j++) 
						{
							if (industryList.get(i).get("cd").toString().equals(industryInfo.get(j).get("industry_cd").toString())) {
								industryList.get(i).put("select", 1);
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETCOMPANYINFO_ERROR + "/" + e.getMessage());
			}
			result.put("info", info);
			result.put("industryList", industryList);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETCOMPANYINFO_ERROR + "/" + e.getMessage());
		}
		
		return new ModelAndView("/pc/login/companymodify", "data", result);
	}
	
	@RequestMapping("companymodifysubmit")
	public void companymodifysubmit(MultipartHttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject jsonObj = new JSONObject();
						
		try
		{
			int result = loginService.updatecompanyinfo(request);
			if(result == 1)
			{
				jsonObj.put("result", "1");
			}
			else
			{
				jsonObj.put("result", "-1");
			}
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		    
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.UPDATECOMPANY_ERROR + "/" + e.getMessage());
		}
	}
	
	@RequestMapping("deleteuser")
	public ModelAndView deleteuser(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		try
		{
			String no = (String)request.getSession().getAttribute("USER_NO");
			loginService.updateuserdelete(no);
			request.getSession().setAttribute("USER_ID", null);
			request.getSession().invalidate();
			ModelAndView mav = new ModelAndView();
			mav.setViewName("/pc/login/session");
			mav.addObject("session", "logout");
			return mav;
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR + "/" + e.getMessage());
		}
	}
	@RequestMapping("jusopop")
	public ModelAndView jusopop(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		try
		{
			return new ModelAndView("/pc/address/jusoPopup");
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR + "/" + e.getMessage());
		}
	}
}
