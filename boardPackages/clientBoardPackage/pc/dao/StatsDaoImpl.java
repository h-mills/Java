package project.pc.dao;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.model.statsConfig;
import project.pc.model.statsModel;

@Service
public class StatsDaoImpl implements StatsDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;

	@Override
	public List<statsConfig> getScanData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getScanData",paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETSCANDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getTotalCnt(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectOne("statsMapper.getTotalCnt",paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETTOTALCNT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<statsModel> getMonthData(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectList("statsMapper.getMonthData",paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETMONTHDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getDailyData(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectList("statsMapper.getDailyData",paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETDAILYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getHourData(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectList("statsMapper.getHourData",paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETHOURDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getLocalCnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("statsMapper.getLocalCnt", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETLOCALCNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsConfig> getNationRank(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getNationRank", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNATIONRANK_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getNationData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getNationData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNATIONDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getCityData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getCityData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETCITYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getAddressData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getAddressData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETADDRESSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getExcelLocalCnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("statsMapper.getExcelLocalCnt", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELLOCALCNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsConfig> getExcelNationRank(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getExcelNationRank", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELNATIONRANK_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getExcelNationData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getExcelNationData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELNATIONDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getExcelCityData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getExcelCityData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELCITYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getExcelAddressData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getExcelAddressData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELADDRESSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<statsModel> getCompanyRank(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getCompanyRank", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETCOMPANY_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getDeptRank(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getDeptRank", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETDEPTRANKDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getLocalRank(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getLocalRank", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETLOCALRANKDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getLocalDeptRank(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getLocalDeptRank", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETLOCALDEPTRANKDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getTodayCount(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("statsMapper.getTodayCount",paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETTODAYCOUNT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectList("statsMapper.getDeptList", paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETDEPTLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getAddresDataCnt(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectOne("statsMapper.getAddressDataCnt", paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETADDRESSDATACOUNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getNameData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getNameData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNAMEDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getNameDataCnt(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectOne("statsMapper.getNameDataCnt", paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNAMEDATACOUNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getNameLocalData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getNameLocalData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNAMELOCALDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getNameLocalDataCnt(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectOne("statsMapper.getNameLocalDataCnt", paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETNAMELOCALDATACOUNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getExcelNameLocalData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getExcelNameLocalData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELNAMELOCALDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<statsModel> getExcelNameRankData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getExcelNameRankData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETEXCELNAMERANKDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getGpsData(Map<String, Object> paramMap) {
		try{
			return masterSqlSessionTemplate.selectList("statsMapper.getGpsData", paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.GETGPSDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
}