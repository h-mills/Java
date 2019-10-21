package project.common.controller;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import project.common.config.MsgConstants;
import project.common.exception.BizException;

public abstract class CommonController {

	public HashMap<String, Object> makeRequestMap(HttpServletRequest request) throws BizException {

		HashMap<String, Object> reqeustMap = new HashMap<String, Object>();
		try {
			for(Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();){
				String paramName = (String) params.nextElement();
				if(paramName != null) paramName.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
				reqeustMap.put(paramName, request.getParameter(paramName));
			}		
		} catch(Exception e) {
			new BizException(MsgConstants.CP01, MsgConstants.CP01_MSG);
		}
		return reqeustMap;
	}
}

