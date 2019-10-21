package project.common.annotation;


import java.lang.reflect.Method;

import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import project.common.exception.*;
import project.common.config.*;

/**
 * Http �Ķ����?�� üũ
 * 
 * @author �����?
 *
 */
public class HPNullCheckImpl {

	String methodName;
	@SuppressWarnings("rawtypes")
	Class delcaringClass;
	Object[] args;
	
	public HPNullCheckImpl(@SuppressWarnings("rawtypes") Class delcaringClass, String methodName, Object[] args) {
		this.delcaringClass = delcaringClass;
		this.args = args;
		this.methodName = methodName;
	}
	
	public void requestFacadeNullCheck(RequestFacade request) throws BizException {
		Method[] methods = delcaringClass.getMethods();
		
		for(Method method : methods) {
			
			if(method.getName().equals(methodName)) {
				HPNullCheck hpNullCheck = (HPNullCheck) method.getAnnotation(HPNullCheck.class);
				
				if(hpNullCheck != null) {
	
					String[] parameters = hpNullCheck.parameters();        		
	        		if(parameters == null) return;
	        		
	        		for(int i = 0;i<parameters.length;i++) {
	        			if(!parameters[i].equals("")) {
		        			String value = request.getParameter(parameters[i]);			        		
		        			
		        			if(StringUtils.isEmpty(value)) {	
		        				throw new BizException(MsgConstants.CP00, "["+parameters[i]+"] is required.");
		        			}
	        			}
	    			}
				}
			}
		}
	}	
	
	public void defaultMultipartHttpServletRequestNullCheck(MultipartHttpServletRequest request) throws BizException {
		Method[] methods = delcaringClass.getMethods();
		
		for(Method method : methods) {
			
			if(method.getName().equals(methodName)) {
				HPNullCheck hpNullCheck = (HPNullCheck) method.getAnnotation(HPNullCheck.class);
				
				if(hpNullCheck != null) {
	
					String[] parameters = hpNullCheck.parameters();        		
	        		if(parameters == null) return;
	        		
	        		for(int i = 0;i<parameters.length;i++) {
	        			if(!parameters[i].equals("")) {
		        			String value = request.getParameter(parameters[i]);			        		
		        			
		        			if(StringUtils.isEmpty(value)) {	
		        				throw new BizException(MsgConstants.CP00, "["+parameters[i]+"] is required.");
		        			}
	        			}
	    			}
				}
			}
		}
	}
	
}
