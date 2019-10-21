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
import project.pc.model.companyModel;
import project.pc.model.orderModel;

@Service
public class CardDaoImpl implements CardDao
{

	@Autowired
	private SqlSessionTemplate masterSqlSessionTemplate;

	@Override
	public List<companyModel> getCardCompanyList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getCardCompanyList", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDCOMPANYLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<orderModel> getCardOrderList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getCardOrderList", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDORDERLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<orderModel> getCardOrderListMake(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getCardOrderListMake", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDORDERLISTMAKE_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCardViewList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getCardViewList", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCardViewList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getCardViewList_Cnt", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int updateIsdelte(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("cardMapper.updateIsdelte", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEISDELTE_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateIsdelte_normal(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("cardMapper.updateIsdelte_normal", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEISDELTE_NORMAL_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Map<String, Object> getOrderData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getOrderData", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETORDERDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertCardConfig(Map<String, Object> paramMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("cardMapper.insertCardConfig", paramMap);
			int card_config_no = (Integer) paramMap.get("no");
			return card_config_no;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.INSERTCARDCONFIG_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public int insertCardData_Master(Map<String, Object> data_master)
	{
		try
		{
			masterSqlSessionTemplate.insert("cardMapper.insertCardData_Master", data_master);
			int carddata_master_no = (Integer) data_master.get("no");
			return carddata_master_no;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.INSERTCARDDATA_MASTER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public void insertCardData(Map<String, Object> insertMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("cardMapper.insertCardData", insertMap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.INSERTCARDDATA_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> getCardDown(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getCardDown", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDDOWN_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getCardOrderInfo(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getCardOrderInfo", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDORDERINFO_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getStatsOrderData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getStatsOrderData", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSORDERDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getStatsTotalCnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getStatsTotalCnt", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSTOTALCNT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<cardStatsModel> getStatsMonthData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsMonthData", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSMONTHDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsDailyData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsDailyData", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSDAILYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsHourData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsHourData", paramMap);
		}
		catch (Exception e)
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
		catch (Exception e)
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
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSNATIONDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsCityData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsCityData", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSCITYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsAddressData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getStatsAddressData", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int updateOrderInfo(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("cardMapper.updateOrderInfo", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEORDERINFO_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}

	}

	@Override
	public void updateCompanyInfo(Map<String, Object> paramMap)
	{
		try
		{
			masterSqlSessionTemplate.update("cardMapper.updateCompanyInfo", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATECOMPANYINFO_ERROR_DAO + "/" + e.getMessage());
		}

	}

	@Override
	public Map<String, Object> getCardData(Map<String, Object> paraMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getCardData", paraMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int updateCardData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("cardMapper.updateCardData", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATECARDDATA_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}

	}

	@Override
	public int updateImage(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("cardMapper.updateImage", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEIMAGE_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCardViewList2(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getCardViewList2", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST2_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCardViewList_Cnt2(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getCardViewList_Cnt2", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT2_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCardViewList3(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getCardViewList3", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST3_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCardViewList_Cnt3(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getCardViewList_Cnt3", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT3_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getStatsAddressData_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("cardMapper.getStatsAddressData_Cnt", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_CNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getExcelStatsAddressData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getExcelStatsAddressData", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETEXCELSTATSADDRESSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getOrderDataList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getOrderDataList", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETEXCELSTATSADDRESSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getcardxlsfiles(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("cardMapper.getcardxlsfiles", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.EXCELDOWN_ERROR + "/" + e.getMessage());
			return null;
		}
		finally 
		{
			paramMap.clear();
			paramMap = null;
		}
	}
}