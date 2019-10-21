package project.pc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.common.util.WebUtil;
import project.pc.model.cardStatsModel;
import project.pc.service.CardService;

@Controller
@RequestMapping("/pc/card/")
public class CardContorller extends CommonController{
	@Autowired ServiceConfig serviceConfig;
	@Autowired CardService cardService; 
	@RequestMapping("card_view")
	@HPNullCheck(parameters={"pageNum"})
	public ModelAndView card_view_search(HttpServletRequest request, HttpServletResponse response) 
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_view");
		Map<String, Object> paramMap = null;
		List<HashMap<String, Object>> cardList = null;
		List<HashMap<String,Object>> deptList = null; 
		String companyPath = serviceConfig.getCompanyPath();
		String landingInfo = serviceConfig.getLandingInfo();
		HttpSession session = request.getSession();
		String company_no = (String) session.getAttribute("COMPANY_NO");
		String s_name = request.getParameter("s_name");
		if(s_name != null)
			s_name = s_name.trim();
		String company_name = "";
		String st_dept = "-2";
		try
		{
			paramMap = this.makeRequestMap(request);
			String[] stDept = request.getParameterValues("st_dept[]");
			paramMap.put("company_no", company_no);
			paramMap.put("st_dept", st_dept);
			if(stDept != null)
			{
				paramMap.put("stDept", stDept);
				st_dept = "0";
				paramMap.put("st_dept", "0");
			}
			if(s_name != null && s_name.length() >= 1)
				paramMap.put("st_dept", "-1");
			paramMap.put("company_no", company_no);
			paramMap.put("name", s_name);
			
			
			String listcount = cardService.getCardViewList_Cnt2(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			cardList = cardService.getCardViewList2(paramMap);
			company_name = cardService.getCompanyName(company_no);
			if (cardList != null) {
				for (int i=0; i<cardList.size(); i++) {
					int language = Integer.parseInt(cardList.get(i).get("language").toString());
					String first_lang = "";
					int lang_count = 0;

					if ((language & 0x08) > 0) {
						cardList.get(i).put("ko_lang", "1");
						first_lang = (first_lang.equals("") ? "ko" : first_lang);
						++lang_count;
					} else {
						cardList.get(i).put("ko_lang", "0");
					}
					if ((language & 0x04) > 0) {
						cardList.get(i).put("en_lang", "1");
						first_lang = (first_lang.equals("") ? "en" : first_lang);
						++lang_count;
					} else {
						cardList.get(i).put("en_lang", "0");
					}
					if ((language & 0x02) > 0) {
						cardList.get(i).put("cn_lang", "1");
						first_lang = (first_lang.equals("") ? "cn" : first_lang);
						++lang_count;
					} else {
						cardList.get(i).put("cn_lang", "0");
					}
					if ((language & 0x01) > 0) {
						cardList.get(i).put("jp_lang", "1");
						first_lang = (first_lang.equals("") ? "jp" : first_lang);
						++lang_count;
					} else {
						cardList.get(i).put("jp_lang", "0");
					}

					cardList.get(i).put("lang_count", lang_count);
					cardList.get(i).put("first_lang", first_lang);
					cardList.get(i).put("landinginfo", landingInfo);
					cardList.get(i).put("imageViewPath", "/" + companyPath + cardList.get(i).get("image"));
				}
			}
			deptList = cardService.getDeptList(paramMap);
			
			if (stDept != null && deptList != null) 
			{
				for (int i=0; i<deptList.size(); i++) 
				{
					for (int j=0; j<stDept.length; j++) 
					{
						if (deptList.get(i).get("dept_no").toString().equals(stDept[j])) {
							deptList.get(i).put("isselect", 1);
						}
					}
				}
			}
			
			if(paramMap.get("st_dept") != null)
				st_dept = paramMap.get("st_dept").toString();
			mav.addObject("deptList",deptList);
			mav.addObject("listcount", listcount);
			
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			mav.addObject("cardList", cardList);
			mav.addObject("st_dept", st_dept);
			mav.addObject("company_name",company_name);
			mav.addObject("rd_isdelete", paramMap.get("rd_isdelete"));
			mav.addObject("search", "2");
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_VIEW2_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}
	@RequestMapping("card_stats_scan")
	public ModelAndView card_stats_scan(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_stats_scan");
		Map<String, Object> paramMap = null;
		List<cardStatsModel> monthData = null;
		List<cardStatsModel> dailyData = null;
		List<cardStatsModel> hourData = null;
		Map<String, Object> orderData = null;
		String name = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
			paramMap.put("name", name);
			System.out.println(paramMap.get("name").toString());
			orderData = cardService.getStatsOrderData(paramMap);
			int count = cardService.getStatsTotalCnt(paramMap);
			monthData = cardService.getStatsMonthData(paramMap);
			dailyData = cardService.getStatsDailyData(paramMap);
			hourData = cardService.getStatsHourData(paramMap);
			request.getSession().setAttribute("orderData", orderData);
			request.getSession().setAttribute("count", count);
			request.getSession().setAttribute("monthData", monthData);
			request.getSession().setAttribute("dailyData", dailyData);
			request.getSession().setAttribute("hourData", hourData);
			request.getSession().setAttribute("paramMap", paramMap);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_STATS_SCAN_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("card_stats_local")
	public ModelAndView stats_local(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_stats_local");
		Map<String, Object> paramMap = null;
		List<cardStatsModel> nationData = null;
		List<cardStatsModel> addressData = null;
		Map<String, Object> countMap = null;
		Map<String, Object> orderData = null;
		List<cardStatsModel> cityData = null;
		List<cardStatsModel> excelCityData = null;
		List<HashMap<String, Object>> gpsList = null;
		String name = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			String listcount = cardService.getStatsAddressDataCnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
			paramMap.put("name", name);
			System.out.println(paramMap.toString());
			orderData = cardService.getStatsOrderData(paramMap);
			countMap = cardService.getStatsLocalCnt(paramMap);
			nationData = cardService.getStatsNationData(paramMap);
			addressData = cardService.getStatsAddressData(paramMap);
			cityData = cardService.getCityData(paramMap);
			excelCityData = cardService.getExcelCityData(paramMap);
			gpsList = cardService.getGpsData(paramMap);
			
			request.getSession().setAttribute("cityData", cityData);
			request.getSession().setAttribute("excelCityData", excelCityData);
			request.getSession().setAttribute("orderData", orderData);
			request.getSession().setAttribute("countMap", countMap);
			request.getSession().setAttribute("nationData", nationData);
			request.getSession().setAttribute("addressData", addressData);
			request.getSession().setAttribute("gpsList", gpsList);
			request.getSession().setAttribute("paramMap", paramMap);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_STATS_LOCAL_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("excel_card_stats_scan")
	public ModelAndView excel_card_stats_scan(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/pc/card/excel_card_stats_scan");
	}

	@RequestMapping("excel_card_stats_local")
	public ModelAndView excel_card_stats_local(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/pc/card/excel_card_stats_local");
	}
	@RequestMapping("card_stats_local_more")
	@HPNullCheck(parameters={"pageNum"})
	public void card_stats_local_more(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> paramMap = null;
		List<cardStatsModel> cityData = null;
		PrintWriter writer = response.getWriter();
		JSONObject result = new JSONObject();
		JSONArray para = new JSONArray();
		try{
			paramMap = this.makeRequestMap(request);
			String listcount = cardService.getStatsAddressDataCnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			System.out.println("paramMap = " + paramMap.toString());
			cityData = cardService.getCityData(paramMap);
			JSONObject json = new JSONObject();
			for(int i=0; i<cityData.size();i++){
				json.put("getNation", cityData.get(i).getNation());
				json.put("getCity", cityData.get(i).getCity());
				json.put("getDate", cityData.get(i).getDate());
				json.put("getAddress", cityData.get(i).getAddress());
				json.put("getRownum", cityData.get(i).getRownum());
				
				para.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		result.put("list", para);
		writer.println(result);
		writer.flush();
		writer.close();
		writer = null;
	}
}
