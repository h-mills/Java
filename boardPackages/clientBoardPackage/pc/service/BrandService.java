package project.pc.service;

import java.util.HashMap;
import java.util.List;

public interface BrandService {
	public String getlistcount();
	public List<HashMap<String, Object>> getlist(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getMainNotice();
	public HashMap<String, Object> getinfo(HashMap<String, Object> requestMap);
	public void updatecount(HashMap<String, Object> requestMap);
	public String getfilepath(HashMap<String, Object> requestMap);
}
