package service;

import classify.classifyClass;
import dao.categoryDao;

public class categoryService {
	classifyClass classify = new classifyClass();
	categoryDao dao = new categoryDao();
	public int getCategory(String imgpath){
		String result = "";
		int categoryno = -1;
		int categorycount = 0;
		try{
			categorycount = dao.getCategoryCount();
			result = classify.getCategoryNo(imgpath,categorycount);
			String[] top = result.split(",");
			for(int i=0; i<top.length; i++){
				System.out.println(top[i]);
			}
			if(Float.parseFloat(top[1]) >= 0.7){
				categoryno = Integer.parseInt(top[0]);
			}
			return categoryno;
		}catch(Exception e){
			e.printStackTrace();
			return categoryno;
		}
	}
	public String getIsCategory(String imgpath){
		String result = "";
		int categorycount = 0;
		try{
			categorycount = dao.getCategoryCount();
			result = classify.getCategoryNo(imgpath,categorycount);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
