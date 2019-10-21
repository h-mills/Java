package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;

@Service
public class StatsDaoImpl implements StatsDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;

	@Override
	public int getScanTotalCount(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("statsMapper.getScanTotalCount",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANTOTALCOUNT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public int getQRScanTotalCount(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("statsMapper.getQRScanTotalCount",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANTOTALCOUNT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int getScanTodayCount(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("statsMapper.getScanTodayCount",paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANTODAYCOUNT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getScanData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getScanData",paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getMonthData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getMonthData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETMONTHDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getDailyData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getDailyData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETDAILYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getHourData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getHourData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETHOURDATA_ERROR_DAO + "/" + e.getMessage());
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
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETLOCALCNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getNationRank(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getNationRank", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETNATIONRANK_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getNationData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getNationData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETNATIONDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCityData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getCityData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETCITYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRMonthData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getQRMonthData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETMONTHDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRDailyData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getQRDailyData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETDAILYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRHourData(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getQRHourData",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETHOURDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public Map<String, Object> getQRLocalCnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("statsMapper.getQRLocalCnt", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETLOCALCNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRNationRank(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getQRNationRank", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETNATIONRANK_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRNationData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getQRNationData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETNATIONDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQRCityData(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getQRCityData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETCITYDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public List<HashMap<String, Object>> getGpsList(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("statsMapper.getGpsList",paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.GETSCANDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
}