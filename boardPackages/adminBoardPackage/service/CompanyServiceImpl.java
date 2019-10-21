package project.pc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.dao.CompanyDao;
import project.pc.model.companyModel;
import project.pc.model.orderModel;


@Service
@Transactional
public class CompanyServiceImpl implements CompanyService
{
	@Autowired CompanyDao companyDao;

	@Override
	public String getCompanyList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return companyDao.getCompanyList_Cnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETCOMPANYLIST_CNT_ERROR_SERVICE + "/" + e.getMessage());
			return "0";
		}
	}

	@Override
	public List<companyModel> getCompanyList(Map<String, Object> paramMap) 
	{
		try
		{
			return companyDao.getCompanyList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETCOMPANYLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getCompanyDetail(Map<String, Object> paramMap) 
	{
		try
		{
			return companyDao.getCompanyDetail(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETCOMPANYDETAIL_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getOrderList_Cnt(Map<String, Object> paramMap) 
	{
		try
		{
			return companyDao.getOrderList_Cnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETORDERLIST_CNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<orderModel> getOrderList(Map<String, Object> paramMap) 
	{
		try
		{
			return companyDao.getOrderList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETORDERLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getOrderDetail(Map<String, Object> paramMap) 
	{
		try
		{
			return companyDao.getOrderDetail(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETORDERDETAIL_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getIndustryInfo(Map<String, Object> paramMap)
	{
		try
		{
			return companyDao.getIndustryInfo(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETINDUSTRYINFO_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getcompanyorg(Map<String, Object> paramMap) {

		try
		{
			return companyDao.getcompanyorg(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETCOMPANYLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertDept(Map<String, Object> requestMap)
	{
		try
		{
			return companyDao.insertDept(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateDept(Map<String, Object> requestMap)
	{
		try
		{
			companyDao.updateDept(requestMap);
			return 1;
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteDept(Map<String, Object> requestMap)
	{
		try
		{
			return companyDao.deleteDept(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getStDept()
	{
		try
		{
			return companyDao.getStDept();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getMaxCd() {
		try{
			return companyDao.getMaxCd();
		}catch(Exception e){
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}
}