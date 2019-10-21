package project.pc.service;

import java.util.HashMap;

import net.sf.json.JSONObject;

public interface APIService {
	public JSONObject gen(HashMap<String, Object> paramMap) throws Exception;
}
