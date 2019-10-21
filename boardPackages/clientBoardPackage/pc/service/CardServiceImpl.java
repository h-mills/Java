package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.dao.CardDao;
import project.pc.model.cardStatsModel;

@Service
@Transactional
public class CardServiceImpl implements CardService {
	
	@Autowired CardDao cardDao;
	
	@Override
	public List<HashMap<String, Object>> getCardViewList2(Map<String, Object> paramMap) {
		try{
			return cardDao.getCardViewList2(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST2_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCardViewList_Cnt2(Map<String, Object> paramMap) {
		try{
			return cardDao.getCardViewList_Cnt2(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT2_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap) {
		try{
			return cardDao.getDeptList(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST2_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCompanyName(String company_no) {
		try{
			return cardDao.getCompanyName(company_no);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCOMPANYNAME_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public Map<String, Object> getStatsOrderData(Map<String, Object> paramMap) {
		try
		{
			return cardDao.getStatsOrderData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSORDERDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getStatsTotalCnt(Map<String, Object> paramMap) {
		try{
			return cardDao.getStatsTotalCnt(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSTOTALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<cardStatsModel> getStatsMonthData(Map<String, Object> paramMap) {
		try{
			return cardDao.getStatsMonthData(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSMONTHDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsDailyData(Map<String, Object> paramMap) {
		try{
			return cardDao.getStatsDailyData(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSDAILYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsHourData(Map<String, Object> paramMap) {
		try{
			return cardDao.getStatsHourData(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSHOURDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getStatsLocalCnt(Map<String, Object> paramMap) {
		try
		{
			return cardDao.getStatsLocalCnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSLOCALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsNationData(Map<String, Object> paramMap) {
		try
		{
			return cardDao.getStatsNationData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSNATIONDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsAddressData(Map<String, Object> paramMap) {
		try
		{
			return cardDao.getStatsAddressData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<cardStatsModel> getCityData(Map<String, Object> paramMap) {
		try
		{
			return cardDao.getCityData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<cardStatsModel> getExcelCityData(Map<String, Object> paramMap) {
		try
		{
			return cardDao.getExcelCityData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getStatsAddressDataCnt(Map<String, Object> paramMap) {
		try{
			return cardDao.getStatsAddressDataCnt(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT2_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getGpsData(Map<String, Object> paramMap) {
		try{
			return cardDao.getGpsData(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETGPSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
}
