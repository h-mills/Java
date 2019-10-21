package project.pc.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.common.util.AES256Cipher;
import project.common.util.GpsToAddress;
import project.pc.dao.LandingDAO;

@Service
@Transactional
public class LandingServiceImpl implements LandingService {

	@Autowired LandingDAO dao;

	@Override
	public HashMap<String, Object> detinfo(HashMap<String, Object> requestMap) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		try
		{
			paramMap.put("no", requestMap.get("idx").toString());
			paramMap.put("nation", "없음");
			paramMap.put("city", "없음");
			paramMap.put("address", "없음");
			paramMap.put("gps", "없음, 없음");

			if(requestMap.get("gen") != null)
			{
				String decript = null;
				AES256Cipher cipher = new AES256Cipher();
				try
				{
					//decript = "index=44&type=2&version=04.04.03&app_gubun=1&lang=ko&iqrcategory=1&g=62@4533556,872@9486946&uniq=2_ETamuUksMK6YoFCBsp4ThIbY6kauMHTOHmwuQG9RNxfnYlogW0mAk7og7pBiPtdl_NULL&uniqtime=1499127667340&server_gubun=2";
					decript = cipher.AES_Decode(requestMap.get("gen").toString());
					System.out.println("decript ==> " + decript);
				}
				catch(Exception be)
				{
					new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.DECRIPT_ERROR + "/" + be.getMessage());
				}
				if(decript != null)
				{
					HashMap<String, Object> geodata = null;
					String []paramName = decript.split("\\&");

					if(paramName != null)
					{
						//gps
						String []gps = null;
						GpsToAddress gta = new GpsToAddress();

						for(int i = 0; i < paramName.length; i++)
						{
							int teal = (paramName[i]).indexOf("=");
							int size = paramName[i].length();
							if(teal >= 0)
							{
								String name = paramName[i].substring(0, teal);
								String value = paramName[i].substring(teal + 1, size);
								if(name.equals("gps"))
								{
									gps = gta.convertGeo(value);
									paramMap.put("gps", gps[0] + "," + gps[1]);
								}
								name  = null;
								value = null;
							}
						}
						
						if (gps != null) 
						{
							geodata = gta.reverseGeo(gps);
						}

						if(geodata != null)
						{
							if(geodata.get("result") != null)
							{
								if(geodata.get("result").toString() == "1")
								{
									if(geodata.get("country") != null)
									{
										paramMap.put("nation", geodata.get("country").toString());
									}
									if(geodata.get("city") != null)
									{
										paramMap.put("city", geodata.get("city").toString());
									}
									else if(geodata.get("local") != null)
									{
										paramMap.put("city", geodata.get("local").toString());
									}
									if(geodata.get("address") != null)
									{
										paramMap.put("address", geodata.get("address").toString());
									}
								}
							}
							geodata.clear();
						}
						geodata = null;
						gta = null;
					}
				}
			}

			int ret = updateQrCount(paramMap);
			if (ret >= 1) {
				insertQrDet(paramMap);
			}

			return getLandingData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.DETINFO_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
		finally
		{
			if (paramMap != null)
			{
				paramMap.clear();
				paramMap = null;
			}
		}
	}

	@Override
	public int insertQrDet(HashMap<String, Object> paramMap) {
		try
		{
			return dao.insertQrDet(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.INSERTQRDET_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateQrCount(HashMap<String, Object> paramMap) {
		try
		{
			return dao.updateQrCount(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.UPDATEQRCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public HashMap<String, Object> getLandingData(HashMap<String, Object> paramMap) {
		try
		{
			return dao.getLandingData(paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.GETLANDINGDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

}
