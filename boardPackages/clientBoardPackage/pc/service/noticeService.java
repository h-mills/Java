package project.pc.service;

import java.util.HashMap;
import java.util.List;

public interface noticeService {
	public String getlistcount();
	public List<HashMap<String, Object>> getlist(HashMap<String, Object> requestMap);
	public HashMap<String, Object> getinfo(HashMap<String, Object> requestMap);
	public void updatecount(HashMap<String, Object> requestMap);
	public String getfilepath(HashMap<String, Object> requestMap);
}
