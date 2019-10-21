package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.pc.model.cardStatsModel;
import project.pc.model.companyModel;
import project.pc.model.orderModel;

public interface CardService {
	public List<companyModel> getCardCompanyList(Map<String, Object> paramMap);
	public List<orderModel> getCardOrderList(Map<String, Object> paramMap);
	public List<orderModel> getCardOrderListMake(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getCardViewList(Map<String, Object> paramMap);
	public String getCardViewList_Cnt(Map<String, Object> paramMap);
	public int updateIsdelte(Map<String, Object> paramMap);
	public int updateIsdelte_normal(Map<String, Object> paramMap);
	public Map<String, Object> getOrderData(Map<String, Object> paramMap);
	public int insertCardConfig(Map<String, Object> paramMap);
	public int insertCardData_Master(Map<String, Object> data_master);
	public void insertCardData(Map<String, Object> insertMap);
	public List<Map<String, Object>> getCardDown(Map<String, Object> paramMap);
	public Map<String, Object> getCardOrderInfo(Map<String, Object> paramMap);
	public Map<String, Object> getStatsOrderData(Map<String, Object> paramMap);
	public int getStatsTotalCnt(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsMonthData(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsDailyData(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsHourData(Map<String, Object> paramMap);
	public Map<String, Object> getStatsLocalCnt(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsNationData(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsCityData(Map<String, Object> paramMap);
	public List<cardStatsModel> getStatsAddressData(Map<String, Object> paramMap);
	public int updateOrderInfo(Map<String, Object> paramMap);
	public void updateCompanyInfo(Map<String, Object> paramMap);
	public Map<String, Object> getCardData(Map<String, Object> paraMap);
	public int updateCardData(Map<String, Object> paramMap);
	public int updateImage(Map<String, Object> paramMap);
	public String getCardViewList_Cnt2(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getCardViewList2(Map<String, Object> paramMap);
	public String getCardViewList_Cnt3(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getCardViewList3(Map<String, Object> paramMap);
	public String getStatsAddressData_Cnt(Map<String, Object> paramMap);
	public List<cardStatsModel> getExcelStatsAddressData(Map<String, Object> paramMap);
	public List<HashMap<String, Object>> getOrderDataList(Map<String, Object> paramMap);
	public HashMap<String, Object> cardxlsdowntozip(Map<String, Object> paramMap);
}
