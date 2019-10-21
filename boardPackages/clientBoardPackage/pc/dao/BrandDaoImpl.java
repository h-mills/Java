package project.pc.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;

@Service
public class BrandDaoImpl implements BrandDao 
{
	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public String getlistcount() throws BizException
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("brand.getlistcount");
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELISTCNT_ERROR_DAO + "/" + e.getMessage());
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getlist(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("brand.getlist", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public HashMap<String, Object> getinfo(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("brand.getinfo", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICEINFO_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getMainNotice() {
		try
		{
			return masterSqlSessionTemplate.selectList("brand.getMainNotice");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public void updatecount(HashMap<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.update("brand.updatecount", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.UPDATENOTICECNT_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public String getfilepath(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("brand.getfilepath", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICEDOWN_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
}