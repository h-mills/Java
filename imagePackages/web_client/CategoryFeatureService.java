package service;

import java.util.HashMap;

import dao.CategoryFeatureDao;

public class CategoryFeatureService
{
	public HashMap<String, Object> getCategory(String filepath)
	{
		HashMap<String, Object> result = null;
		CategoryFeatureDao dao = new CategoryFeatureDao();

		try
		{
			result = dao.getCategory(filepath);
		}
		catch (Exception e)
		{
			System.out.println("CategoryFeatureService.getCategory() error = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally 
		{
			dao = null;
		}
		return result;
	}

	public HashMap<String, Object> getCategoryData(HashMap<String, Object> paramMap)
	{
		CategoryFeatureDao dao = new CategoryFeatureDao();
		try
		{
			return dao.getCategoryData(paramMap);
		}
		catch (Exception e)
		{
			System.out.println("CategoryFeatureService.getCategoryData() error = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally 
		{
			dao = null;
		}
	}
}