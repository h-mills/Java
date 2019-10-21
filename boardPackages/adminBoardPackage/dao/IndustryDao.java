package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IndustryDao {
	public List<HashMap<String, Object>> getIndustryListST();
	public String getIndustryListCount(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getIndustryList(Map<String, Object> paramMap);
	public int getCdCheck(Map<String, Object> paramMap);
	public int insertIndustry(Map<String, Object> paramMap);
	public int updateIndustry(Map<String, Object> paramMap);
	public int deleteIndustry(String[] cdList);
}
