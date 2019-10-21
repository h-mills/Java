package project.pc.dao;


import java.util.HashMap;

import java.util.List;
import java.util.Map;


public interface MemberDao {
	public String getMemberList_Cnt();
	public List<HashMap<String, Object>> getMemberList(Map<String, Object> paramMap);
	public int insertMember(Map<String, Object> paramMap);
	public int updateMember(Map<String, Object> paramMap);
	public int deleteMember(List<String> noList);
	public HashMap<String,Object> getMember(Map<String, Object> paramMap);
	public int getIdCount(String id);
}
