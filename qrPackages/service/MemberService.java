package project.pc.service;

import java.util.HashMap;

import java.util.List;
import java.util.Map;


public interface MemberService {
	public String getMemberList_Cnt();
	public List<HashMap<String,Object>> getMemberList(Map<String, Object> paramMap);
	public int insertMember(Map<String, Object> paramMap);
	public int updateMember(Map<String, Object> paramMap);
	public int deleteMember(String [] no);
	public HashMap<String,Object> getMember(Map<String, Object> paramMap);
	public int getIdCount(String id);
}
