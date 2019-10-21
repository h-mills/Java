package project.pc.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.dao.noticeDao;

@Service
@Transactional
public class noticeServiceImpl implements noticeService
{	
	@Autowired noticeDao 	 dao;
	
	@Override
	public String getlistcount()
	{
		try
		{
			return dao.getlistcount();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELISTCNT_ERROR_SERVICE + "/" + e.getMessage());
			return "0";
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getlist(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.getlist(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public HashMap<String, Object> getinfo(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.getinfo(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICEINFO_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public void updatecount(HashMap<String, Object> requestMap)
	{
		try
		{
			dao.updatecount(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.UPDATENOTICECNT_ERROR_SERVICE + "/" + e.getMessage());
		}
	}
	
	@Override
	public String getfilepath(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.getfilepath(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICEDOWN_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
}
