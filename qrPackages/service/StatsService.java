package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StatsService {
	public int getScanTotalCount(Map<String, Object> paramMap);
	public int getScanTodayCount(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getScanData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getMonthData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getDailyData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getHourData(Map<String, Object> paramMap);
	public Map<String, Object> getLocalCnt(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getNationRank(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getNationData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getCityData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getQRMonthData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getQRDailyData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getQRHourData(Map<String, Object> paramMap);
	public Map<String, Object> getQRLocalCnt(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getQRNationRank(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getQRNationData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getQRCityData(Map<String, Object> paramMap);
	public int getQRScanTotalCount(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getGpsList(Map<String, Object> paramMap);
}
