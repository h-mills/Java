package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.dao.IndustryDao;

@Service
@Transactional
public class IndustryServiceImpl implements IndustryService 
{
	@Autowired IndustryDao dao;

	
	@Override
	public List<HashMap<String, Object>> getIndustryListST()
	{
		try
		{
			return dao.getIndustryListST();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.GETINDUSTRYLISTST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getIndustryListCount(Map<String, Object> paramMap)
	{
		try
		{
			return dao.getIndustryListCount(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.GETINDUSTRYLISTCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getIndustryList(Map<String, Object> paramMap)
	{
		try
		{
			return dao.getIndustryList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.GETINDUSTRYLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getCdCheck(Map<String, Object> paramMap)
	{
		try
		{
			return dao.getCdCheck(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.GETCDCHECK_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int insertIndustry(Map<String, Object> paramMap)
	{
		try
		{
			return dao.insertIndustry(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.INSERTINDUSTRY_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateIndustry(Map<String, Object> paramMap)
	{
		try
		{
			return dao.updateIndustry(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.UPDATEINDUSTRY_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteIndustry(String[] cdList)
	{
		try
		{
			return dao.deleteIndustry(cdList);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.INDUSTRY_SYSTEM, MsgConstants.DELETEINDUSTRY_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}
}