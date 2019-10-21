package project.pc.service;

import java.util.HashMap;

public interface LandingService {
	public HashMap<String, Object> detinfo(HashMap<String, Object> requestMap);
	public int insertQrDet(HashMap<String, Object> paramMap);
	public int updateQrCount(HashMap<String, Object> paramMap);
	public HashMap<String, Object> getLandingData(HashMap<String, Object> paramMap);
}
