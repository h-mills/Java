package project.pc.dao;

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
public class OrderDaoImpl implements OrderDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public String getlistcount(String user_no)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("order.getlistcount", user_no);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETLISTCOUNT_ERROR_DAO + "/" + e.getMessage());
			return "0";
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getlist(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("order.getlist", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
			
	@Override
	public HashMap<String, Object> orderregist(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("order.orderregist", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.REGISTORDER_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public int insertorder(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.insert("order.insertorder", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.INSERTORDER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public HashMap<String, Object> getorderinfo(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("order.getorderinfo", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETORDERINFO_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public void updateorderconfirm(HashMap<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.update("order.updateorderconfirm", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDERANS_ERROR_DAO + "/" + e.getMessage());
		}
	}
	
	@Override
	public void updatereplyconfirm(HashMap<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.update("order.updatereplyconfirm", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEREPLYANS_ERROR_DAO + "/" + e.getMessage());
		}
	}
	
	@Override
	public List<HashMap<String, Object>> getreplylist(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("order.getreplylist", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETREPLY_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public int updateorder(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("order.updateorder", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int cancelorder(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("order.cancelorder", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public String insertreply(HashMap<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("order.insertreply", requestMap);
			return requestMap.get("no").toString();
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDER_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public HashMap<String, Object> getreplyinfo(String no)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("order.getreplyinfo", no);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDER_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public void updateordercount(HashMap<String, Object> requestMap) {
		try
		{
			masterSqlSessionTemplate.update("order.updateOrderCount", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDER_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void updateordernew(HashMap<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.update("order.updateordernew", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDERANS_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public List<HashMap<String, Object>> getcompanyorgchart(HashMap<String, Object> requestMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("order.getcompanyorgchart", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public int insertorderdata(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.insert("order.insertorderdata", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.INSERTORDER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	@Override
	public void updatefilepath(HashMap<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.update("order.updatefilepath", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDERANS_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public List<HashMap<String, Object>> getorderorgchart(HashMap<String, Object> requestMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("order.getorderorgchart", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public void xlsupdate(HashMap<String, Object> requestMap) {
		try
		{
			masterSqlSessionTemplate.update("order.xlsupdate", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEREPLYANS_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public int getdeptcount(String company_no) {
		try{
			return masterSqlSessionTemplate.selectOne("order.getdeptcount",company_no);
		}catch(Exception e){
			return 0;
		}
	}
}