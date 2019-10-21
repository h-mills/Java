package project.pc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.pc.service.LoginService;

@Controller
@RequestMapping("/pc/login/")
public class LoginController extends CommonController 
{
	@Autowired ServiceConfig serviceConfig;
	@Autowired LoginService  loginService;
	
	@RequestMapping("login")	
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		return new ModelAndView("/pc/login/login");
	}
	
	@RequestMapping("submit")
	public ModelAndView submit(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");
		Map<String, Object> member= null;

		try
		{
			HttpSession session = request.getSession(true);
			Object USER_ID = session.getAttribute("user_id");

			ModelAndView mav = new ModelAndView();
			//session check
			if (USER_ID == null || USER_ID == "")
			{
				member = loginService.getUserNickname(user_id, user_pwd);
				//아이디가 없으면 login page 호출
				if(member == null)
				{
					mav.setViewName("/pc/login/login");
					mav.addObject("session", "nomember");
				}
				else
				{
					//회원 확인후 세션 부여
					String nickname = (String) member.get("name");
					//회원 메뉴 레벨
					if(member.get("level") != null)
					{
						int level = (int) member.get("level");
						member.put("m_order", (level & 0x01) > 0 ? "1" : "0");
						member.put("m_company", (level & 0x02) > 0 ? "1" : "0");
						member.put("m_card", (level & 0x04) > 0 ? "1" : "0");
						member.put("m_stats", (level & 0x08) > 0 ? "1" : "0");
						member.put("m_notice", (level & 0x10) > 0 ? "1" : "0");
						member.put("m_member", (level & 0x20) > 0 ? "1" : "0");
						member.put("m_industry", (level & 0x40) > 0 ? "1" : "0");
						member.put("m_dept", (level & 0x80) > 0 ? "1" : "0");
					}
					request.getSession().setAttribute("USER_ID", user_id);
					request.getSession().setAttribute("USER_NICKNAME", nickname);
					request.getSession().setAttribute("USER_INFO", member);
					mav.setViewName("redirect:/pc/main/frame");
				}
			}
			
			return mav;
		} 
		catch(BizException be) 
		{
			throw new BizException(be.getErrCode(), be.getMessage());
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.LOGIN_ERROR + "/" + e.getMessage());
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
}
