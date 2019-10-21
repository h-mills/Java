package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.pc.model.memberModel;

public interface MemberService {
	public String getMemberList_Cnt(Map<String, Object> paramMap);
	public List<memberModel> getMemberList(Map<String, Object> paramMap);
	public String getUserList_Cnt(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getUserList(Map<String, Object> paramMap);
	public int insertMember(Map<String, Object> paramMap);
	public int updateMember(Map<String, Object> paramMap);
	public int deleteMember(String [] no);
	public memberModel getMember(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getCompany();
	public int updateUserModify(Map<String, Object> paramMap);
}
