package project.common.util;

import java.util.HashMap;

public class MsgUtil {

	public static HashMap<String, Object> makeHeader(String rt, String rtMsg) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rt", rt);
		map.put("rtMsg", rtMsg);
		
		return map;
	}
	
	public static HashMap<String, Object> makeHeader(String rt, String rtMsg, String sessionKey, String runningTime) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rt", rt);
		map.put("rtMsg", rtMsg);
		map.put("sessionKey", sessionKey);
		map.put("runningTime", runningTime);
		
		return map;
	}

}
