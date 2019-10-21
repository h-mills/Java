package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface QrDAO {
	public List<HashMap<String, Object>> getQRList(Map<String,Object> paramMap);
	public String getQRListCount(Map<String,Object> paramMap);
	public int updateIsdelte(Map<String, Object> paramMap);
	public int updateIsdelte_normal(Map<String, Object> paramMap);
	public HashMap<String, Object> getQRInfo(HashMap<String, Object> requestMap);
	public int updateQRData(HashMap<String, Object> requestMap);
	public int insertQRConfig(String title, String gen_name);
	public int insertQRMaster(HashMap<String,Object> requestMap);
	public List<HashMap<String, Object>> getImageList(HashMap<String, Object> noMap);
	public void updateImgPath(int no,String imagePath);
	public String getfilepath(HashMap<String, Object> requestMap);
	public int insertLargeConfig(String title, String gen_name, String fileName);
	public int insertQr(HashMap<String, Object> dataMap);
	public void updateImage(int qr_master_no, String imgPath);
	public List<HashMap<String, Object>> getImages(HashMap<String, Object> paramMap);
}
