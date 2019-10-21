package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<HashMap<String, Object>> getDeptListST()
	{
		try
		{
			return dao.getDeptListST();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.GETDEPTLISTST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getDeptListCount(Map<String, Object> paramMap)
	{
		try
		{
			return dao.getDeptListCount(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.GETDEPTLISTCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap)
	{
		try
		{
			return dao.getDeptList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.GETDEPTLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertDept(Map<String, Object> paramMap)
	{
		try
		{
			return dao.insertDept(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.INSERTDEPT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateDept(Map<String, Object> paramMap)
	{
		try
		{
			return dao.updateDept(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.UPDATEDEPT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteDept(String[] noList)
	{
		try
		{
			return dao.deleteDept(noList);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.DELETEDEPT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}
}