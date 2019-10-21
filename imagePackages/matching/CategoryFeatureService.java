package service;

import dao.CategoryFeatureDao;
import model.matchModel;

public class CategoryFeatureService
{
	public matchModel [] match(String gipaddress, String imgPath)
	{
		CategoryFeatureDao dao = new CategoryFeatureDao();
		
		try
		{
			return dao.match(gipaddress, imgPath);
		}
		catch (Exception e) 
		{
			System.out.println("CategoryFeatureService.match() error : " + e.getMessage());
			e.printStackTrace();
			return null;
		} 
		finally
		{
			dao = null;
		}
	}
}
