package project.pc.dao;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import project.common.exception.BizException;

public interface OrderDao {
	public String getlistcount(String user_no);
	public List<HashMap<String, Object>> getlist(HashMap<String, Object> requestMap);
	public HashMap<String, Object> orderregist(HashMap<String, Object> requestMap);
	public int insertorder(HashMap<String, Object> requestMap);
	public HashMap<String, Object> getorderinfo(HashMap<String, Object> requestMap);
	public void updateorderconfirm(HashMap<String, Object> requestMap);
	public void updatereplyconfirm(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getreplylist(HashMap<String, Object> requestMap);
	public int updateorder(HashMap<String, Object> requestMap);
	public int cancelorder(HashMap<String, Object> requestMap);
	public String insertreply(HashMap<String, Object> requestMap);
	public HashMap<String, Object> getreplyinfo(String no);
	public void updateordercount(HashMap<String, Object> requestMap);
	public void updateordernew(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getcompanyorgchart(HashMap<String, Object> requestMap);
	public int insertorderdata(HashMap<String, Object> requestMap);
	public void updatefilepath(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getorderorgchart(HashMap<String, Object> requestMap);
	public void xlsupdate(HashMap<String, Object> requestMap);
	public int getdeptcount(String company_no);
}
