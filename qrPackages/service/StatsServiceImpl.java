package project.pc.service;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.dao.StatsDao;

@Service
@Transactional
public class StatsServiceImpl implements StatsService
{
	@Autowired StatsDao statsDao;

	@Override
	public int getScanTotalCount(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getScanTotalCount(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANTOTALCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public int getQRScanTotalCount(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getQRScanTotalCount(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANTOTALCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int getScanTodayCount(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getScanTodayCount(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANTODAYCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getScanData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getScanData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getMonthData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getMonthData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETMONTHDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getDailyData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getDailyData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETDAILYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getHourData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getHourData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETHOURDATA_ERROR_SERVICE + "/" + e.getMessage());
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
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETLOCALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getNationRank(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getNationRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETNATIONRANK_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getNationData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getNationData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETNATIONDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCityData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getCityData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETCITYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getQRMonthData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getQRMonthData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETMONTHDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRDailyData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getQRDailyData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETDAILYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRHourData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getQRHourData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETHOURDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public Map<String, Object> getQRLocalCnt(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getQRLocalCnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETLOCALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRNationRank(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getQRNationRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETNATIONRANK_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRNationData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getQRNationData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETNATIONDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRCityData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getQRCityData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETCITYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getGpsList(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getGpsList(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETMONTHDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
}