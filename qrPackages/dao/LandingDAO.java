package project.pc.dao;

import java.util.HashMap;

public interface LandingDAO {
	public int insertQrDet(HashMap<String, Object> paramMap);
	public int updateQrCount(HashMap<String, Object> paramMap);
	public HashMap<String, Object> getLandingData(HashMap<String, Object> paramMap);
}
