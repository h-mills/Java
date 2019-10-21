package project.pc.service;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import project.pc.model.statsConfig;
import project.pc.model.statsModel;

public interface StatsService {
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
	public List<statsModel> getExcelNameRankData(Map<String, Object> paramMap);
	public Map<String, Object> getExcelLocalCnt(Map<String, Object> paramMap);
	public List<statsConfig> getExcelNationRank(Map<String, Object> paramMap);
	public List<statsModel> getExcelNationData(Map<String, Object> paramMap);
	public List<statsModel> getExcelCityData(Map<String, Object> paramMap);
	public List<statsModel> getExcelAddressData(Map<String, Object> paramMap);
	public List<statsModel> getExcelNameLocalData(Map<String, Object> paramMap);
	public List<statsModel> getCompanyRank(Map<String, Object> paramMap);
	public List<statsModel> getDeptRank(Map<String, Object> paramMap);
	public List<statsModel> getLocalRank(Map<String, Object> paramMap);
	public List<statsModel> getLocalDeptRank(Map<String, Object> paramMap);
	public int getTodayCount(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap);
	public String getAddressDataCnt(Map<String, Object> paramMap);
	public List<statsModel> getNameData(Map<String, Object> paramMap);
	public String getNameDataCnt(Map<String, Object> paramMap);
	public List<statsModel> getNameLocalData(Map<String, Object> paramMap);
	public String getNameLocalDataCnt(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getGpsData(Map<String, Object> paramMap);
}
