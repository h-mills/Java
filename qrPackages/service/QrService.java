package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.sf.json.JSONObject;

public interface QrService {
	public List<HashMap<String, Object>> getQRList(Map<String,Object> paramMap);
	public String getQRListCount(Map<String, Object> paramMap);
	public int updateIsdelte(Map<String, Object> paramMap);
	public int updateIsdelte_normal(Map<String, Object> paramMap);
	public HashMap<String, Object> getQRInfo(HashMap<String,Object> requestMap);
	public int updateQRData(HashMap<String, Object> requestMap);
	public HashMap<String, Object> getImageList(HashMap<String, Object> noMap);
	public JSONObject gen(MultipartHttpServletRequest request, HashMap<String, Object> paramMap);
	public String getfilepath(HashMap<String, Object> requestMap);
	public JSONObject largegen(MultipartHttpServletRequest request, HashMap<String, Object> paramMap);
	public HashMap<String, Object> largedown(HashMap<String, Object> paramMap);
}
