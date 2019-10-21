package project.pc.service;

import java.util.HashMap;
import java.util.List;

public interface DeptService {
	public List<HashMap<String, Object>> getDeptList(HashMap<String, Object> requestMap);
	public int insertDept(HashMap<String, Object> requestMap);
	public int updateDept(HashMap<String, Object> requestMap);
	public int deleteDept(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getStDept();
	public int getMaxCd();
}
