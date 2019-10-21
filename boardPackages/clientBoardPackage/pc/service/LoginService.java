package project.pc.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface LoginService {
	public int idcheck(String user_id);
	public String biznumbercheck(String biznumber);
	public HashMap<String, Object> getcompanyinfo(String no);
	public int insertRegist(MultipartHttpServletRequest request);
	public HashMap<String, Object> checkUserInfo(HashMap<String, Object> param);
	public String getid(HttpServletRequest request);
	public String getpwd(HttpServletRequest request);
	public HashMap<String, Object> getuserinfo(HashMap<String, Object> param);
	public int updateuserinfo(HashMap<String, Object> param);
	public int updatecompanyinfo(MultipartHttpServletRequest request);
	public int updateuserdelete(String no);
	public List<HashMap<String, Object>> getIndustryList();
	public List<HashMap<String, Object>> getIndustryInfo(String no);
}
