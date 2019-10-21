package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DeptService {
	public List<HashMap<String, Object>> getDeptListST();
	public String getDeptListCount(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap);
	public int insertDept(Map<String, Object> paramMap);
	public int updateDept(Map<String, Object> paramMap);
	public int deleteDept(String[] noList);
}
