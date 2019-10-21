package project.pc.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.exception.BizException;
import project.pc.dao.CategoryFeatureDao;

@Service("categoryfeatureservice")
@Transactional
public class CategoryFeatureServiceImpl implements CategoryFeatureService
{
	@Autowired
	CategoryFeatureDao dao;

	@Override
	public List<HashMap<String, Object>> getCategory(String filepath) throws BizException
	{
		List<HashMap<String, Object>> result = null;
		try
		{
			result = dao.getCategory(filepath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<HashMap<String, Object>> getCategoryData(List<HashMap<String, Object>> paramMap) throws BizException
	{
		List<HashMap<String, Object>> result = null;
		try
		{
			result = dao.getCategoryData(paramMap);
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}