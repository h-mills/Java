package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.common.util.WebUtil;

@Service
public class QrDAOImpl implements QrDAO {

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public List<HashMap<String, Object>> getQRList(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("qrMapper.getQRList",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETQRLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getQRListCount(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("qrMapper.getQRListCount",paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETQRLISTCOUNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public int updateIsdelte(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("qrMapper.updateIsdelete", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.UPDATEISDELTE_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public int updateIsdelte_normal(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("qrMapper.updateIsdelete_normal", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.UPDATEISDELTE_NORMAL_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public HashMap<String, Object> getQRInfo(HashMap<String, Object> requestMap) {
		try{
			return masterSqlSessionTemplate.selectOne("qrMapper.getQRInfo",requestMap);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETQRINFO_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public int updateQRData(HashMap<String, Object> requestMap) {
		try{
			return masterSqlSessionTemplate.update("qrMapper.updateQRData",requestMap);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.UPDATEQRDATA_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public int insertQRConfig(String title, String gen_name) {
		HashMap<String, Object> paramMap = new HashMap<>();
		try
		{
			paramMap.put("title", title);
			paramMap.put("gen_name", gen_name);
			masterSqlSessionTemplate.insert("qrMapper.insertQRConfig", paramMap);
			int qr_config_no = (Integer)paramMap.get("no");
			return qr_config_no;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.INSERTQRCONFIG_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
		finally 
		{
			paramMap.clear();
			paramMap = null;
		}
	}
	@Override
	public int insertQRMaster(HashMap<String, Object> requestMap) {
		int qr_master_no = 0;
		try{
			if(masterSqlSessionTemplate.insert("qrMapper.insertQRMaster",requestMap) ==1){
				qr_master_no = Integer.parseInt(requestMap.get("no").toString());
			}
			return qr_master_no;
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.INSERTQRMASTER_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public List<HashMap<String, Object>> getImageList(HashMap<String, Object> noMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("qrMapper.getImageList", noMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETIMAGELIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
		finally 
		{
			noMap.clear();
			noMap = null;
		}
	}
	@Override
	public void updateImgPath(int no,String imagePath) {
		HashMap<String, Object> paramMap = new HashMap<>();
		try{
			paramMap.put("no", no);
			paramMap.put("imgPath", imagePath);
			masterSqlSessionTemplate.update("qrMapper.updateImagePath",paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.UPDATEIMGPATH_ERROR_DAO + "/" + e.getMessage());
		}finally{
			if(paramMap != null){
				paramMap.clear();
				paramMap = null;
			}
		}
	}
	@Override
	public String getfilepath(HashMap<String, Object> requestMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("qrMapper.getfilepath", requestMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETFILEPATH_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertLargeConfig(String title, String gen_name, String fileName) {
		HashMap<String, Object> paramMap = new HashMap<>();
		try
		{
			paramMap.put("title", title);
			paramMap.put("gen_name", gen_name);
			paramMap.put("file", WebUtil.getDate() + "/" + fileName);
			masterSqlSessionTemplate.insert("qrMapper.insertLargeConfig", paramMap);
			int qr_config_no = (Integer)paramMap.get("no");
			return qr_config_no;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.INSERTLARGECONFIG_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
		finally 
		{
			paramMap.clear();
			paramMap = null;
		}
	}

	@Override
	public int insertQr(HashMap<String, Object> dataMap) {
		try
		{
			masterSqlSessionTemplate.insert("qrMapper.insertQr", dataMap);
			int qr_master_no = (Integer)dataMap.get("no");
			return qr_master_no;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.INSERTQR_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
		finally 
		{
			dataMap.clear();
			dataMap = null;
		}
	}

	@Override
	public void updateImage(int qr_master_no , String imgPath) {
		HashMap<String, Object> paramMap = new HashMap<>();
		try
		{
			paramMap.put("qr_master_no", qr_master_no);
			paramMap.put("image", imgPath);
			masterSqlSessionTemplate.update("qrMapper.updateImage", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.UPDATEIMAGE_ERROR_DAO + "/" + e.getMessage());
		}
		finally 
		{
			paramMap.clear();
			paramMap = null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getImages(HashMap<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("qrMapper.getImages", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETIMAGES_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
		finally 
		{
			paramMap.clear();
			paramMap = null;
		}
	}
}
