package project.pc.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.dao.DeptDao;


@Service
@Transactional
public class DeptServiceImpl implements DeptService 
{
	@Autowired DeptDao dao;
	
	@Override
	public List<HashMap<String, Object>> getDeptList(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.getDeptList(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertDept(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.insertDept(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateDept(HashMap<String, Object> requestMap)
	{
		try
		{
			dao.updateDept(requestMap);
			return 1;
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteDept(HashMap<String, Object> requestMap)
	{
		try
		{
			return dao.deleteDept(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getStDept()
	{
		try
		{
			return dao.getStDept();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getMaxCd() {
		try
		{
			return dao.getMaxCd();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

}
	