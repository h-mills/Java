package project.pc.dao;


import java.util.HashMap;
import java.util.List;

import project.common.exception.BizException;

public interface BrandDao {
	public String getlistcount() throws BizException;
	public List<HashMap<String, Object>> getlist(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getMainNotice();
	public HashMap<String, Object> getinfo(HashMap<String, Object> requestMap);
	public void updatecount(HashMap<String, Object> requestMap);
	public String getfilepath(HashMap<String, Object> requestMap);
}
