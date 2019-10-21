package project.common.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author  0216
 */
public class BizException extends Exception{
	private static final long serialVersionUID = -6048580843364126395L;

	private String errCode;
	private String sessionKey;
	private Object data;
	private String runningTime;
	
	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public BizException() {
		super();
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(String errCode, String message, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
	}
	
	public BizException(String errCode, String message, String sessionkey) {
		super(message);
		this.errCode = errCode;
		this.sessionKey = sessionkey;
	}

	public BizException(String message) {
		super(message);
	}
	
	public BizException(String errCode, String message) {
		super(message);
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dayTime.format(new Date(time));
		this.errCode = "[" + str + "] " + errCode;
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public BizException(String errCode, String message, Object data, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
		this.data = data;
	}
	
	public BizException(String errCode, String message, Object data){
		super(message);
		this.errCode = errCode;
		this.data = data;
	}

}
