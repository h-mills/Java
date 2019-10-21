package dao;

import java.io.File;
import java.util.Random;

import DNA.CategoryClass;
import DNA.ResultList;
import init.setProvider;
import model.matchModel;

public class CategoryFeatureDao
{
	public matchModel[] match(String gipaddress, String imgPath)
	{
		CategoryClass Matcher = new CategoryClass();
		Random random = new Random();
		ResultList Result = null;

		try
		{
			int Ret = 0;
			String dnaPath = setProvider.getWorkDir() + String.format("query/%03d%d%d.dna", setProvider.getNode(), System.currentTimeMillis(), random.nextInt(10));
			String newPath = setProvider.getWorkDir() + imgPath;
			Ret = Matcher.Extract(newPath, dnaPath, 1);
			if (Ret == 1)
			{
				Result = Matcher.Match(dnaPath);
				if (Result != null)
				{
					int nSize = Result.getCount();
					matchModel[] model = new matchModel[nSize];
					try
					{
						String dnaNo = null;
						for (int iy = 0; iy < nSize; iy++)
						{
							model[iy] = new matchModel();
							dnaNo = String.format("%d", Result.getResult(iy).getM_Idx());
							model[iy].setDnaIdx(dnaNo);
							model[iy].setScore(String.format("%d", Result.getResult(iy).getM_Score()));
						}

						return model;
					}
					catch (Exception e)
					{
						System.out.println("CategoryFeatureDao.match() error : " + e.getMessage());
						e.printStackTrace();
						return null;
					}
					finally
					{
						File f = new File(dnaPath);
						try
						{
							if (f.exists())
							{
								f.delete();
							}
							f = new File(newPath);
							if (f.exists())
							{
								f.delete();
							}
						}
						catch (Exception ex)
						{
						}
						finally
						{
							f = null;
						}
						model = null;
					}
				}
			}
			return null;
		}
		catch (Exception e)
		{
			System.out.println("CategoryFeatureDao.match() error : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally
		{
			if (Result != null) 
			{
				Result.reset();
				Result = null;
			}
			Matcher = null;
			random = null;
		}
	}
}