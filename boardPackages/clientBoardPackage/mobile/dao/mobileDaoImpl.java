package project.mobile.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;

@Service
@Transactional
public class mobileDaoImpl implements mobileDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public String getcompanyNo(HashMap<String, Object> paraMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("mobile.getcompanyNo", paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETCOMPANY_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getconfigNo(HashMap<String, Object> paraMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("mobile.getconfigNo", paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETCONFIG_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getorderNo(HashMap<String, Object> paraMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("mobile.getorderNo", paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETORDER_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public int insertdetinfo(HashMap<String, Object> paraMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("mobile.insertdetinfo", paraMap);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.INSERTDET_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int updatedetcnt(HashMap<String, Object> paraMap)
	{
		try
		{
			masterSqlSessionTemplate.update("mobile.updatedetcnt", paraMap);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.UPDATEDET_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int updateDetCityCnt(HashMap<String, Object> paraMap)
	{
		try
		{
			masterSqlSessionTemplate.update("mobile.updateDetCityCnt", paraMap);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.UPDATEDET_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public HashMap<String, Object> getdata(HashMap<String, Object> paraMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("mobile.getdata", paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertCarddataReply(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return masterSqlSessionTemplate.insert("mobile.insertCarddataReply", paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public List<HashMap<String, Object>> getCardDataReply(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return masterSqlSessionTemplate.selectList("mobile.getCardDataReply", paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public int getDelteCheck(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("mobile.getDelteCheck", paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public int deleteCarddataReply(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return masterSqlSessionTemplate.delete("mobile.deleteCarddataReply", paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public String getReplyListCount(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("mobile.getReplyListCount", paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_DAO + "/" + e.getMessage());
		}
	}
}