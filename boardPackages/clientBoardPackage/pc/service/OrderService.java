package project.pc.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import project.common.exception.BizException;

public interface OrderService {
	public List<HashMap<String, Object>> getorderlist(HashMap<String, Object> requestMap);
	public String getlistcount(String user_no);
	public HashMap<String, Object> orderregist(HashMap<String, Object> requestMap);
	public int insertorder(MultipartHttpServletRequest request);
	public HashMap<String, Object> getorderinfo(HashMap<String, Object> requestMap);
	public void updateorderconfirm(HashMap<String, Object> requestMap);
	public void updatereplyconfirm(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getreplylist(HashMap<String, Object> requestMap);
	public int updateorder(MultipartHttpServletRequest request);
	public int cancelorder(String orderno);
	public HashMap<String, Object> insertreply(HashMap<String, Object> requestMap);
	public void updateordercount(MultipartHttpServletRequest request);
	public void updateordernew(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getcompanyorgchart(HashMap<String, Object> requestMap);
	public List<HashMap<String, Object>> getorderorgchart(HashMap<String, Object> requestMap);
	public HashMap<String, Object> xlsupdate(MultipartHttpServletRequest request);
	public int getdeptcount(String company_no);
}
