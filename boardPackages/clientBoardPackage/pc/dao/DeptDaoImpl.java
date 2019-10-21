package project.pc.dao;

import java.util.HashMap;
import java.util.List;

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
	public List<HashMap<String, Object>> getDeptList(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("dept.getDeptList", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertDept(HashMap<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("dept.insertDept", requestMap);
			int no = (Integer) requestMap.get("no");
			return no;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateDept(HashMap<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("dept.updateDept", requestMap);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteDept(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.delete("dept.deleteDept", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getStDept()
	{
		try
		{
			return masterSqlSessionTemplate.selectList("dept.getStDept");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getMaxCd() {
		try
		{
			return masterSqlSessionTemplate.selectOne("dept.getMaxCd");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

}