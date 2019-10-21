package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.pc.model.companyModel;
import project.pc.model.orderModel;

public interface CompanyDao {
	public String getCompanyList_Cnt(Map<String, Object> paramMap);
	public List<companyModel> getCompanyList(Map<String, Object> paramMap);
	public Map<String, Object> getCompanyDetail(Map<String, Object> paramMap);
	public String getOrderList_Cnt(Map<String, Object> paramMap);
	public List<orderModel> getOrderList(Map<String, Object> paramMap);
	public Map<String, Object> getOrderDetail(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getIndustryInfo(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getcompanyorg(Map<String, Object> paramMap);
	public int insertDept(Map<String, Object> requestMap);
	public int updateDept(Map<String, Object> requestMap);
	public int deleteDept(Map<String, Object> requestMap);
	public List<HashMap<String, Object>> getStDept();
	public int getMaxCd();
}
