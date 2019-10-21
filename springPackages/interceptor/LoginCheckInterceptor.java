package project.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//System.out.println("login check start");
		
		HttpSession session = request.getSession(true);
		
		String userId = (String) session.getAttribute("USER_ID");
		
		
		//System.out.println("session user : " + userId);
		
		if(userId==null || userId.equals("")) {
			
			response.sendRedirect("/login/sessionclose");
			return false;	
			
		}
		
		//System.out.println("login check end");

		return true;
	}
}

