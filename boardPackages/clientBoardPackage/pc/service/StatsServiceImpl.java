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
import project.pc.model.statsConfig;
import project.pc.model.statsModel;

@Service
@Transactional
public class StatsServiceImpl implements StatsService
{
	@Autowired StatsDao statsDao;


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
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETCOMPANY_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getDeptRank(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getDeptRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETDEPTRANKDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getLocalRank(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getLocalRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETLOCALRANKDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getLocalDeptRank(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getLocalDeptRank(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETLOCALDEPTRANKDATA_ERROR_SERVICE + "/" + e.getMessage());
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
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETTODAYCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap) {
		try{
			return statsDao.getDeptList(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETDEPTLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getAddressDataCnt(Map<String, Object> paramMap) {
		try{
			return statsDao.getAddresDataCnt(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETADDRESSDATACOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getNameData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getNameData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNAMEDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getNameDataCnt(Map<String, Object> paramMap) {
		try{
			return statsDao.getNameDataCnt(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNAMEDATACOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getNameLocalData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getNameLocalData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNAMELOCALDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getNameLocalDataCnt(Map<String, Object> paramMap) {
		try{
			return statsDao.getNameLocalDataCnt(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNAMELOCALDATACOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getExcelNameLocalData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getExcelNameLocalData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELNAMELOCALDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getExcelNameRankData(Map<String, Object> paramMap) {
		try
		{
			return statsDao.getExcelNameRankData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELNAMERANKDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getGpsData(Map<String, Object> paramMap) {
		try{
			return statsDao.getGpsData(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETGPSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
}