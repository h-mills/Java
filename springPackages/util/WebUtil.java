package project.common.util;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import project.common.controller.CommonController;

public class WebUtil extends CommonController {

	/**
	 * LIST Total value 구하기 
	 * @param totalSize
	 * @param pageSize
	 * @return
	 */
	public static int makeTotalPage(int totalSize, int pageSize) {
		if(totalSize == 0) return 0;
	
		return (totalSize - 1) / pageSize + 1;
	}
	
	/**
	 * LIST Paging 처리
	 * @param pagingSize
	 * @param pageNum
	 * @param totalListCount
	 * @param listViewSize
	 * @param pagingURL
	 * @return
	 */
	public static HashMap<String, Object> makePagingDataMap(int pagingSize, String pageNum, String totalListCount, int listViewSize, String pagingURL) {

		// 페이징에 필요한 데이터 Start.
		HashMap<String, Object> pagingValues = new HashMap<String, Object>();
		pagingValues.put("pagingSize", pagingSize);
		pagingValues.put("startPageNum", (Integer.parseInt(pageNum)/pagingSize) * pagingSize);
		pagingValues.put("pageNum", pageNum);
		pagingValues.put("totalPageSize", makeTotalPage(Integer.parseInt(totalListCount), listViewSize));
		pagingValues.put("totalListCount", totalListCount);
		pagingValues.put("pagingURL", pagingURL);
		
		return pagingValues;
	}

	
	/**
	 * 사이트 공통 정보
	 * @param domainURL
	 * @param nasImageURL
	 * @return
	 */
	public static HashMap<String, Object> makePublicDataMap(String domainURL, String nasInfo, HttpServletRequest request) {

		HttpSession session = request.getSession(true);			
		Object USER_ID = session.getAttribute("USER_ID");
		
		// 공통 프로퍼티 정보
		HashMap<String, Object> publicValues = new HashMap<String, Object>();
		publicValues.put("domainURL", domainURL);
		publicValues.put("nasInfo", nasInfo);
		publicValues.put("USER_ID", USER_ID);
		publicValues.put("boardCode", request.getParameter("boardCode"));

		return publicValues;
	}
	
	/*
	 * date 정보
	 */
	public static String getDate()
	{
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
		String str = dayTime.format(new Date(time));
		return str;
	}
	
	/*
	 * date 정보
	 */
	public static String getDateTime()
	{
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dayTime.format(new Date(time));
		return str;
	}

	public static String getPageNum(String pageNum, String totalListCount, int listViewSize)
	{
		if(pageNum == null) return "0";
		else
		{
			int page = Integer.parseInt(pageNum);
			if(Integer.parseInt(pageNum) < 0 ) pageNum = "0";
			else if(page > makeTotalPage(Integer.parseInt(totalListCount), listViewSize))
			{
				pageNum = String.format("%d", makeTotalPage(Integer.parseInt(totalListCount), listViewSize)-1);
			}
			return pageNum;
		}
	}

	public static String getBrowser(HttpServletRequest request) 
	{
		String header = request.getHeader("User-Agent"); 

		if (header.indexOf("MSIE") > -1) { 
			return "MSIE"; 
		} else if (header.indexOf("Chrome") > -1) { 
			return "Chrome"; 
		} else if (header.indexOf("Opera") > -1) {
			return "Opera"; 
		} else if (header.indexOf("Trident/7.0") > -1) {
			//IE 11 이상 //IE 버전 별 체크 >> Trident/6.0(IE 10) , Trident/5.0(IE 9) , Trident/4.0(IE 8) 
			return "MSIE"; 
		}
		return "Firefox"; 
	}

	public static String getDisposition(String filename, String browser) throws Exception { 
		String encodedFilename = null;

		if (browser.equals("MSIE")) { 
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) { 
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\""; 
		} else if (browser.equals("Opera")) { 
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\""; 
		} else if (browser.equals("Chrome")) { 
			StringBuffer sb = new StringBuffer(); 
			for (int i = 0; i < filename.length(); i++) { 
				char c = filename.charAt(i); 
				if (c > '~') { 
					sb.append(URLEncoder.encode("" + c, "UTF-8")); 
				} else { 
					sb.append(c); 
				} 
			} 
			encodedFilename = sb.toString(); 
		} else { 
			throw new RuntimeException("Not supported browser"); 
		} 
		return encodedFilename; 
	}

}