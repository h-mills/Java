package project.common.aspect;

import net.sf.json.JSONObject;

import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import project.common.annotation.HPNullCheckImpl;
import project.common.logging.BizLogger;
import project.common.util.SequenceUtil;
import project.common.util.StopWatch;
import project.common.exception.*;
import project.common.config.*;

@Aspect
@Component
public class CommonAspect {
	private static Log logger = BizLogger.getInfo();
	
	public Object loggingExceptionHandler(ProceedingJoinPoint joinpoint) throws Throwable {

		String sessionKey = SequenceUtil.getInstance().getSessionKey();
		
		StopWatch stopWatch = new StopWatch(joinpoint.getSignature().getName());		
		stopWatch.start(sessionKey);
		
		ModelAndView response = null;
		
		Object[] args = joinpoint.getArgs();	

		HPNullCheckImpl hpNullCheck = new HPNullCheckImpl(joinpoint.getSignature().getDeclaringType(), joinpoint.getSignature().getName(), args);

		try 
		{
			for(Object obj:args )
			{
	        	if(obj.getClass().getSimpleName().equals("RequestFacade")) 
	        	{
	        		RequestFacade requestFacade = (RequestFacade) obj;      
	        		logger.info(LoggingUtil.makeRequestFacadeLog(requestFacade, sessionKey));
	        		hpNullCheck.requestFacadeNullCheck(requestFacade);
	        	} 
	        	else if(obj.getClass().getSimpleName().equals("DefaultMultipartHttpServletRequest")) 
	        	{        		
	        		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) obj;	
	        		logger.info(LoggingUtil.makeMultipartHttpServletRequestLog(multi, sessionKey));
	        		hpNullCheck.defaultMultipartHttpServletRequestNullCheck(multi);
	        	}        	
	        }
			
			response = (ModelAndView) joinpoint.proceed(args);
			stopWatch.stop();
			
			if(response != null && !response.isEmpty() && !response.getModel().isEmpty() ) 
			{
				if(response.getModel().get("json") != null) 
				{
					((JSONObject) response.getModel().get("json")).getJSONObject("header").put("sessionKey", sessionKey);
					((JSONObject) response.getModel().get("json")).getJSONObject("header").put("runningTime", stopWatch.getTimeString());		
				}
			}
			
			logger.info(LoggingUtil.makeResultModelLog(response));
			return response;
		} 
		catch(BizException be) 
		{
			stopWatch.stop();
			be.setSessionKey(sessionKey);
			be.setRunningTime(stopWatch.getTimeString());
			throw be;
		}
		catch (Throwable e) 
		{
			stopWatch.stop();
			BizException be = new BizException(MsgConstants.ERR_SYSTEM, MsgConstants.ERR_SYSTEM_MSG + " / " + e.getMessage());
			be.setSessionKey(sessionKey);
			be.setRunningTime(stopWatch.getTimeString());
			throw be;
		} 
		finally
		{
			hpNullCheck = null;
			stopWatch = null;
		}
	}
}
