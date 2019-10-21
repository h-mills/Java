package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.model.cardStatsModel;

@Service
public class CardDaoImpl implements CardDao {
	
	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public List<HashMap<String, Object>> getCardViewList2(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectList("cardMapper.getCardViewList2", paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST2_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCardViewList_Cnt2(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getCardViewList_Cnt2",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT2_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectList("cardMapper.getDeptList", paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST2_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getCompanyName(String company_no) {
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getCompanyName",company_no);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCOMPANYNAME_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public Map<String, Object> getStatsOrderData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getStatsOrderData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSORDERDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getStatsTotalCnt(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getStatsTotalCnt",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSTOTALCNT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<cardStatsModel> getStatsMonthData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsMonthData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSMONTHDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsDailyData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsDailyData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSDAILYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsHourData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsHourData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSHOURDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getStatsLocalCnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getStatsLocalCnt", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSLOCALCNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsNationData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsNationData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSNATIONDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsAddressData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getCityData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<cardStatsModel> getCityData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsAddressData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<cardStatsModel> getExcelCityData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getExcelCityData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getStatsAddressDataCnt(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getStatsAddressDataCnt",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT2_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getGpsData(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectList("cardMapper.getGpsData", paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETGPSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
}
