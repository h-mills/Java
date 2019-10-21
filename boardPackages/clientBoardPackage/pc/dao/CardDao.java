package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.pc.model.cardStatsModel;

public interface CardDao {
	public List<HashMap<String, Object>> getCardViewList2(Map<String, Object> paramMap);
	public String getCardViewList_Cnt2(Map<String,Object> paramMap);
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap);
	public String getCompanyName(String company_no);
	public Map<String, Object> getStatsOrderData(Map<String, Object> paramMap);
	public int getStatsTotalCnt(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsMonthData(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsDailyData(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsHourData(Map<String, Object> paramMap);
	public Map<String, Object> getStatsLocalCnt(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsNationData(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsAddressData(Map<String, Object> paramMap);
	public List<cardStatsModel> getCityData(Map<String, Object> paramMap);
	public List<cardStatsModel> getExcelCityData(Map<String, Object> paramMap);
	public String getStatsAddressDataCnt(Map<String,Object> paramMap);
	public List<HashMap<String, Object>> getGpsData(Map<String, Object> paramMap);
}
