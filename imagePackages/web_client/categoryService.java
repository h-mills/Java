package service;

import java.util.HashMap;
import dao.categoryDao;

public class categoryService {
	HashMap<String, Object> result = null;
	public HashMap<String, Object> getCategory(String filepath){
		try {
			categoryDao dao = new categoryDao();
			result = dao.getCategory(filepath);
			 if(result != null)
			 {
				 System.out.println("reuslt is not null in service");
			 }
			 return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public HashMap<String, Object> getCategoryData(HashMap<String, Object> paramMap) {
		try {
			categoryDao dao = new categoryDao();
			return dao.getCategoryData(paramMap);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}