package project.pc.dao;


import java.util.HashMap;
import java.util.List;

import project.common.exception.BizException;

public interface LoginDao {
	public int idcheck(String user_id);
	public String biznumbercheck(String biznumber);
	public HashMap<String, Object> getcompanyinfo(String no);
	public int insertCompany(HashMap<String, Object> param);
	public void updateCompanyFilepath(HashMap<String, Object> param) throws BizException;
	public void insertUser(HashMap<String, Object> param) throws BizException;
	public HashMap<String, Object> checkUserInfo(HashMap<String, Object> param);
	public String getid(HashMap<String, Object> param);
	public String getpwd(HashMap<String, Object> param);
	public HashMap<String, Object> getuserinfo(HashMap<String, Object> param);
	public int updateuserinfo(HashMap<String, Object> param);
	public int updatecompanyinfo(HashMap<String, Object> param);
	public int updateuserdelete(HashMap<String, Object> param);
	public int updateCompany(HashMap<String, Object> param);
	public List<HashMap<String, Object>> getIndustryList();
	public int deleteIndustry(HashMap<String, Object> param);
	public int insertIndustry(HashMap<String, Object> param);
	public List<HashMap<String, Object>> getIndustryInfo(String no);
}
