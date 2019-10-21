package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.model.companyModel;
import project.pc.model.orderModel;

@Service
public class CompanyDaoImpl implements CompanyDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public String getCompanyList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("companyMapper.getCompanyList_Cnt", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETCOMPANYLIST_CNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<companyModel> getCompanyList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("companyMapper.getCompanyList", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETCOMPANYLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getCompanyDetail(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("companyMapper.getCompanyDetail", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETCOMPANYDETAIL_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getOrderList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("companyMapper.getOrderList_Cnt", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETORDERLIST_CNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<orderModel> getOrderList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("companyMapper.getOrderList", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETORDERLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getOrderDetail(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("companyMapper.getOrderDetail", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETORDERDETAIL_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getIndustryInfo(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("companyMapper.getIndustryInfo", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETINDUSTRYINFO_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getcompanyorg(Map<String, Object> paramMap) {

		try
		{
			return masterSqlSessionTemplate.selectList("companyMapper.getcompanyorg", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETCOMPANYLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertDept(Map<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("companyMapper.insertDept", requestMap);
			int no = (Integer) requestMap.get("no");
			return no;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateDept(Map<String, Object> requestMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("companyMapper.updateDept", requestMap);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteDept(Map<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.delete("companyMapper.deleteDept", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getStDept()
	{
		try
		{
			return masterSqlSessionTemplate.selectList("companyMapper.getStDept");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getMaxCd() {
		try {
			return masterSqlSessionTemplate.selectOne("companyMapper.getMaxCd");
		} catch (Exception e) {
			return 0;
		}
	}
}