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
public class IndustryDaoImpl implements IndustryDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;

	@Override
	public List<HashMap<String, Object>> getIndustryListST()
	{
		try
		{
			return masterSqlSessionTemplate.selectList("industryMapper.getIndustryListST");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.GETINDUSTRYLISTST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getIndustryListCount(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("industryMapper.getIndustryListCount", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.GETINDUSTRYLISTCOUNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getIndustryList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("industryMapper.getIndustryList", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.GETINDUSTRYLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getCdCheck(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("industryMapper.getCdCheck", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.GETCDCHECK_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int insertIndustry(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.insert("industryMapper.insertIndustry", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.INSERTINDUSTRY_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateIndustry(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.insert("industryMapper.updateIndustry", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.UPDATEINDUSTRY_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteIndustry(String[] cdList)
	{
		try
		{
			return masterSqlSessionTemplate.insert("industryMapper.deleteIndustry", cdList);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.DELETEINDUSTRY_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}
}
