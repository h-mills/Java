package project.pc.service;

import java.util.List;
import java.util.Map;

import project.pc.model.companyModel;
import project.pc.model.orderModel;
import project.pc.model.statsConfig;
import project.pc.model.statsModel;

public interface StatsService {
	public List<companyModel> getStatsCompanyList(Map<String, Object> paramMap);
	public List<orderModel> getStatsOrderList(Map<String, Object> paramMap);
	public List<statsConfig> getScanData(Map<String, Object> paramMap);
	public int getTotalCnt(Map<String, Object> paramMap);
	public List<statsModel> getMonthData(Map<String, Object> paramMap);
	public List<statsModel> getDailyData(Map<String, Object> paramMap);
	public List<statsModel> getHourData(Map<String, Object> paramMap);
	public Map<String, Object> getLocalCnt(Map<String, Object> paramMap);
	public List<statsConfig> getNationRank(Map<String, Object> paramMap);
	public List<statsModel> getNationData(Map<String, Object> paramMap);
	public List<statsModel> getCityData(Map<String, Object> paramMap);
	public List<statsModel> getAddressData(Map<String, Object> paramMap);
	public String getAddressData_Cnt(Map<String, Object> paramMap);
	public Map<String, Object> getExcelLocalCnt(Map<String, Object> paramMap);
	public List<statsConfig> getExcelNationRank(Map<String, Object> paramMap);
	public List<statsModel> getExcelNationData(Map<String, Object> paramMap);
	public List<statsModel> getExcelCityData(Map<String, Object> paramMap);
	public List<statsModel> getExcelAddressData(Map<String, Object> paramMap);
	public List<statsModel> getCompanyRank(Map<String, Object> paramMap);
	public int getTodayCount(Map<String, Object> paramMap);
	public List<Map<String, Object>> getCompanyCityRank(Map<String, Object> paramMap);
	public List<Map<String, Object>> getExcelCompanyCityRank(Map<String, Object> paramMap);
	public List<Map<String, Object>> getMapData(Map<String, Object> paramMap);
}
