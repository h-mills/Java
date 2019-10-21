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
public class DeptDaoImpl implements DeptDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;

	@Override
	public List<HashMap<String, Object>> getDeptListST()
	{
		try
		{
			return masterSqlSessionTemplate.selectList("deptMapper.getDeptListST");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.GETDEPTLISTST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getDeptListCount(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("deptMapper.getDeptListCount", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.GETDEPTLISTCOUNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getDeptList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("deptMapper.getDeptList", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.GETDEPTLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertDept(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.insert("deptMapper.insertDept", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.INSERTDEPT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateDept(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.insert("deptMapper.updateDept", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.UPDATEDEPT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteDept(String[] noList)
	{
		try
		{
			return masterSqlSessionTemplate.insert("deptMapper.deleteDept", noList);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.DELETEDEPT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}
}
