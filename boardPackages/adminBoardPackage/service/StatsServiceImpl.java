package project.pc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.dao.StatsDao;
import project.pc.model.companyModel;
import project.pc.model.orderModel;
import project.pc.model.statsConfig;
import project.pc.model.statsModel;

@Service
@Transactional
public class StatsServiceImpl implements StatsService
{
	@Autowired StatsDao statsDao;

	@Override
	public List<companyModel> getStatsCompanyList(Map<String, Object> paramMap)
	{
		try
		{
			return statsDao.getStatsCompanyList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETSTATSCOMPANYLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<orderModel> getStatsOrderList(Map<String, Object> paramMap)
	{
		try
		{
			return statsDao.getStatsOrderList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETSTATSORDERLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsConfig> getScanData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getScanData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETSCANDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getTotalCnt(Map<String, Object> paramMap) {
		try{
			return statsDao.getTotalCnt(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETTOTALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<statsModel> getMonthData(Map<String, Object> paramMap) {
		try{
			return statsDao.getMonthData(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETMONTHDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getDailyData(Map<String, Object> paramMap) {
		try{
			return statsDao.getDailyData(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETDAILYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getHourData(Map<String, Object> paramMap) {
		try{
			return statsDao.getHourData(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETHOURDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getLocalCnt(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getLocalCnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETLOCALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsConfig> getNationRank(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getNationRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNATIONRANK_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getNationData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getNationData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNATIONDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getCityData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getCityData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETCITYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getAddressData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getAddressData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getExcelLocalCnt(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getExcelLocalCnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELLOCALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsConfig> getExcelNationRank(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getExcelNationRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELNATIONRANK_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getExcelNationData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getExcelNationData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELNATIONDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getExcelCityData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getExcelCityData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELCITYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getExcelAddressData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getExcelAddressData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getCompanyRank(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getCompanyRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getTodayCount(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getTodayCount(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public String getAddressData_Cnt(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getAddressData_Cnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETADDRESSDATA_CNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getCompanyCityRank(Map<String, Object> paramMap)
	{
		try
		{
			return statsDao.getCompanyCityRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETCOMPANYCITYRANK_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getExcelCompanyCityRank(Map<String, Object> paramMap)
	{
		try
		{
			return statsDao.getExcelCompanyCityRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELCOMPANYCITYRANK_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getMapData(Map<String, Object> paramMap)
	{
		try
		{
			return statsDao.getMapData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELCOMPANYCITYRANK_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
}