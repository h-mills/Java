package project.common.logging;

import org.apache.commons.logging.Log;


import org.apache.commons.logging.LogFactory;

/**
 * �α׼���
 * 
 * @author  �����
 */
public class BizLogger {

	final static String INFO_INFO 		= "business.info";
	
	final static Log info = LogFactory.getLog(INFO_INFO);
	
	
	public static Log getInfo() {
		return info;
	}

}
