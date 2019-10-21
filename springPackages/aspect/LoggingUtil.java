package project.common.aspect;

import java.util.Enumeration;

import net.sf.json.JSONObject;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public class LoggingUtil {

	public static String makeRequestFacadeLog(RequestFacade request, String sessionKey) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("****** START REQUEST=> ");
		buffer.append(request.getRequestURI());
		buffer.append(" : ");
		
		for(Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();){
			String _paramName = (String) params.nextElement();
			buffer.append(_paramName);
			buffer.append("=");
			buffer.append(request.getParameter(_paramName));
			buffer.append("/");
		}
		buffer.append("sessionKey");
		buffer.append("=");
		buffer.append(sessionKey);

		return buffer.toString();
	}
	
	public static String makeMultipartHttpServletRequestLog(MultipartHttpServletRequest request, String sessionKey) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("****** START REQUEST=> ");
		buffer.append(request.getRequestURI());
		buffer.append(" : ");
		
		for(Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();){
			String _paramName = (String) params.nextElement();
			buffer.append(_paramName);
			buffer.append("=");
			buffer.append(request.getParameter(_paramName));
			buffer.append("/");
		}
		buffer.append("sessionKey");
		buffer.append("=");
		buffer.append(sessionKey);

		return buffer.toString();
	}
	
	public static String makeResultModelLog(ModelAndView model) {
		
		StringBuffer buffer = new StringBuffer("****** END RESPONSE=> ");
		try {
			buffer.append(model.getViewName());
			buffer.append(" : ");
			buffer.append(JSONObject.fromObject(model.getModel()).toString() + " END\n");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
