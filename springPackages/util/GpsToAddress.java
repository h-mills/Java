package project.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import project.common.config.MsgConstants;
import project.common.exception.BizException;

/**
 * 본 class는 appstore에서 위치정보 사용 문제가 자주 reject 사유가 되어, 위치정보를 특정 랜딩페이지에 보여주기 위해 만든 것임
 * 실제, utility로 gps를 주소로 변경해주는 로직이 필요하다면, 활용하기 좋게 수정해야 함.
 * @author 배경애
 * @date 2016.11.10
 */
public class GpsToAddress {
	public String [] convertGeo(String geo)
	{
		String geoTmp = null;
		String []m_oritext = {"a","b","c","d","e","f","g","h","i","j","k","l","m"
                ,"n","o","p","q","r","s","t","u","v","w","x","y","z"
                ,"A","B","C","D","E","F","G","H","I","J","K","L","M"
                ,"N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
                ,"0","1","2","3","4","5","6","7","8","9"
                ,"@","_","-","."};
		String []m_outtext = {"z","y","x","w","v","u","t","s","r","q","p","o","n"
                ,"m","l","k","j","i","h","g","f","e","d","c","b","a"
                ,"N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
                ,"A","B","C","D","E","F","G","H","I","J","K","L","M"
                ,"9","8","7","6","5","4","3","2","1","0"
                ,".","-","_","@"};
		String [] tmp = null;
		String [] out = new String [2];
		out[0] = new String();
		out[1] = new String();
		out[0] = ""; out[1] = "";
		
		try
		{
			if(geo == null || geo.equals("") || geo.equals("null") || geo.equals("0@0,0@0") || geo.equals("9@9,9@9"))
			{
				out[0] = "null";
				out[1] = "null";
			}
			else
			{
				tmp = geo.split(",");
				String latstr = tmp[0];
				String lodstr = tmp[1];	
				for(int i=0; i<latstr.length() ; i++)
				{
					geoTmp = latstr.substring(i, i+1);
					for(int j=0; j<m_outtext.length; j++)
					{
						String text = m_outtext[j];
						if(geoTmp.equals(text))
						{
							out[0] += m_oritext[j];
							break;
						}
					}
				}
							
				for(int i=0; i<lodstr.length() ; i++)
				{
					geoTmp = lodstr.substring(i, i+1);
					for(int j=0; j<m_outtext.length; j++)
					{
						String text = m_outtext[j];
						if(geoTmp.equals(text))
						{
							out[1] +=  m_oritext[j];
						}
					}
				}
			}
			return out;
		}
		catch(Exception ex)
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.CONVERTGEO_ERROR + "/" + ex.getMessage());
			return null;
		}
	}
	public HashMap<String, Object> reverseGeo(String[] gpsdata) throws UnsupportedEncodingException, IOException {
		JSONArray List = new JSONArray();
		HashMap<String, Object> map = new HashMap<String, Object>();
		URL req = new URL("https://maps.google.com/maps/api/geocode/json?latlng=" + gpsdata[0] + ","
		        + gpsdata[1] + "&key=AIzaSyBSjrzTYips1MZ0vrodUtiuTIexkxeDiLY&language=en&location_type=ROOFTOP");
		HttpURLConnection con  = (HttpURLConnection)req.openConnection();
		Object            obj  = JSONValue.parse(new InputStreamReader(con.getInputStream(), "UTF-8"));
		JSONObject        jObj = (JSONObject)obj;

		if(con.getResponseCode() == 200) {
			String status = (String)jObj.get("status");
			List = (JSONArray)jObj.get("results");
			
			if(status.equals("OK")) {
				map.put("result", "1");
				if(List != null) {
					jObj = (JSONObject)List.get(0);
					JSONArray List1 = new JSONArray();
					
					List1 = (JSONArray)jObj.get("address_components");
				
					if(List1.size() > 0) {
						int lSize = List1.size();
						String country = null;
						String city = null;
						String local = null;
						
						for(int i = 0; i < lSize; i ++ ) {
							Object obj1 = JSONValue.parse(new InputStreamReader(con.getInputStream(), "UTF-8"));
							JSONObject jObj1 = (JSONObject)obj1;
							jObj1 = (JSONObject)List1.get(i);
							
							JSONArray List2 = new JSONArray();
							List2 = (JSONArray)jObj1.get("types");
							
							if(List2.size() > 0) {
								int nSize = List2.size();
								for(int j = 0; j < nSize; j ++ ) {
									String name = (String)List2.get(j);
									if(name.equals("country")) {
										country = (String)jObj1.get("long_name");
										map.put("country", country);
									}else if(name.equals("administrative_area_level_1")) {
										city = (String)jObj1.get("long_name");
										map.put("city", city);
									}else if(name.equals("locality")) {
										local = (String)jObj1.get("long_name");
										map.put("local", local);
									}
								}
								for(int j = nSize - 1; j >= 0; j -- ) {
									List2.remove(j);
								}
								List2 = null;
							}
							obj1 = null;
						}
						
						for(int i = lSize - 1; i >= 0; i -- ) {
							List1.remove(i);
						}
						List1 = null;
						
						if((String)jObj.get("formatted_address") != null) {
							String address = (String)jObj.get("formatted_address");
							map.put("address", address);
						}
					}
					int Size = List.size();
					for(int i = Size - 1; i >= 0; i -- ) {
						List.remove(i);
					}
					List = null;
				}
			}
			else if(status.equals("OVER_QUERY_LIMIT")) 
			{
				System.out.println("할당량이 초과되었습니다.");
				map.put("result", "-1");
			}
			else if(status.equals("REQUEST_DENIED")) 
			{
				System.out.println("요청이 거부되었습니다.대부분의 경우 sensor 매개변수가 없기 때문입니다.");
				map.put("result", "-2");
			}
			else if(status.equals("INVALID_REQUEST")) 
			{
				System.out.println("일반적으로 쿼리(address 또는 latlng)가 누락되었음을 나타냅니다.");
				map.put("result", "-3");
			}
			con.disconnect();
		}
		con = null;
		req = null;
						
		return map;
	}
}
