package project.mobile.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.common.util.GpsToAddress;
import project.mobile.dao.mobileDao;

@Service
@Transactional
public class mobileServiceImpl implements mobileService
{	
	@Autowired ServiceConfig serviceConfig;
	@Autowired mobileDao 	 dao;
	
//	index = 검출번호
//	type = ( iOS HiddenTag = 3 , iOS HiddenTag &  Client = 2 , Android HiddenTag C.O.P. = 1 , Android HiddenTag & Client = 0 )
//	version = 서버통신버전 
//	app_gubun = (HUROM = 8, 360 = 7, YG = 6,Skinion = 5, Claires = 4, Aboutme = 3, HiddenTagC.O.P. = 2, HiddenTag = 1) 
//	lang = 언어
//	iqrcategory = (Micro wide = 3, Micro = 2, wide = 1 , normal = 0)
//	g = GPS
//	uniq = 단말기 고유번호 
//	uniqtime = 검출시간
//	server_gubun = (아마존 = 2 , 내부서버 = 1)
	@Override
	public HashMap<String, Object> detinfo(String decript, String gubun)
	{
		HashMap<String, Object> info = null;
		HashMap<String, Object> geodata = null;
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		String []paramName = decript.split("\\&");
		String imagePath = "/" + serviceConfig.getCompanyPath();
		
		paramMap.put("gubun", gubun);
		try
		{
			if(paramName != null)
			{
				//gps, card_data_no
				String []gps = null;
				paramMap.put("nation", "없음");
				paramMap.put("city", "없음");
				paramMap.put("address", "없음");
				paramMap.put("lang", "ko");
				GpsToAddress gta = new GpsToAddress();
				for(int i = 0; i < paramName.length; i++)
				{
					int teal = (paramName[i]).indexOf("=");
					int size = paramName[i].length();
					if(teal >= 0)
					{
						String name = paramName[i].substring(0, teal);
						String value = paramName[i].substring(teal + 1, size);
						if(name.equals("index"))
						{
							paramMap.put("card_data_no", value);
						}
						else if(name.equals("g"))
						{
							gps = gta.convertGeo(value);
							paramMap.put("gps", gps[0] + "," + gps[1]);
						}
						else if(name.equals("lang"))
						{
							paramMap.put("lang", value);
						}
						name  = null;
						value = null;
					}
				}
				
				geodata = gta.reverseGeo(gps);
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
				
				paramMap.put("company_no", getcompanyNo(paramMap));
				paramMap.put("config_no", getconfigNo(paramMap));
				paramMap.put("order_no", getorderNo(paramMap));
				insertdetinfo(paramMap);
				updatedetcnt(paramMap);
				updateDetCityCnt(paramMap);
				info = getdata(paramMap);
				int language = 0;
				if(info.get("language") != null)
				{
					language = Integer.parseInt(info.get("language").toString());
					info.put("ko", "0");
					info.put("en", "0");
					info.put("cn", "0");
					info.put("jp", "0");
					if((language & 8) > 0)
					{
						info.put("ko", "1");
					}
					if((language & 4) > 0)
					{
						info.put("en", "1");
					}
					if((language & 2) > 0)
					{
						info.put("cn", "1");
					}
					if((language & 1) > 0)
					{
						info.put("jp", "1");
					}
				}
				if(info.get("image") != null)
				{
					imagePath = imagePath + info.get("image").toString();
					info.put("imagepath", imagePath);
				}
				info.put("lang", paramMap.get("lang").toString());
			}
			return info;
		}
		catch (Exception ex)
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.DETINFO_ERROR_SERVICE + "/" + ex.getMessage());
			return null;
		}
		finally
		{
			if(paramMap != null)
			{
				paramMap.clear();
				paramMap = null;
			}
			if(paramName != null)
			{
				paramName = null;
			}
		}
	}
	
	@Override
	public String getcompanyNo(HashMap<String, Object> paraMap)
	{
		try
		{
			return dao.getcompanyNo(paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETCOMPANY_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getconfigNo(HashMap<String, Object> paraMap)
	{
		try
		{
			return dao.getconfigNo(paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETCONFIG_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getorderNo(HashMap<String, Object> paraMap)
	{
		try
		{
			return dao.getorderNo(paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETORDER_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public int insertdetinfo(HashMap<String, Object> paraMap)
	{
		try
		{
			return dao.insertdetinfo(paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.INSERTDET_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int updatedetcnt(HashMap<String, Object> paraMap)
	{
		try
		{
			return dao.updatedetcnt(paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.UPDATEDET_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public int updateDetCityCnt(HashMap<String, Object> paraMap)
	{
		try
		{
			return dao.updateDetCityCnt(paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.UPDATEDET_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public HashMap<String, Object> getdata(HashMap<String, Object> paraMap)
	{
		try
		{
			return dao.getdata(paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public HashMap<String, Object> pclanding(HashMap<String, Object> paraMap)
	{
		try
		{
			return dao.getdata(paraMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertCarddataReply(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return dao.insertCarddataReply(paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public List<HashMap<String, Object>> getCardDataReply(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return dao.getCardDataReply(paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public int getDelteCheck(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return dao.getDelteCheck(paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public int deleteCarddataReply(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return dao.deleteCarddataReply(paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public String getReplyListCount(HashMap<String, Object> paraMap) throws BizException
	{
		try
		{
			return dao.getReplyListCount(paraMap);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.GETDATA_ERROR_SERVICE + "/" + e.getMessage());
		}
	}
}
