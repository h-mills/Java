package project.mobile.service;

import java.util.HashMap;
import java.util.List;

import project.common.exception.BizException;


public interface mobileService {
	public HashMap<String, Object> detinfo(String decript, String gubun);
	public String getcompanyNo(HashMap<String, Object> paraMap);
	public String getconfigNo(HashMap<String, Object> paraMap);
	public String getorderNo(HashMap<String, Object> paraMap);
	public int insertdetinfo(HashMap<String, Object> paraMap);
	public int updatedetcnt(HashMap<String, Object> paraMap);
	public int updateDetCityCnt(HashMap<String, Object> paraMap);
	public HashMap<String, Object> getdata(HashMap<String, Object> paraMap);
	public HashMap<String, Object> pclanding(HashMap<String, Object> paraMap);
	public int insertCarddataReply(HashMap<String, Object> paraMap) throws BizException;
	public List<HashMap<String, Object>> getCardDataReply(HashMap<String, Object> paraMap) throws BizException;
	public int getDelteCheck(HashMap<String, Object> paraMap) throws BizException;
	public int deleteCarddataReply(HashMap<String, Object> paraMap) throws BizException;
	public String getReplyListCount(HashMap<String, Object> paraMap) throws BizException;
}
