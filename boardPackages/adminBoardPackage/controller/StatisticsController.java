package project.pc.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.common.util.WebUtil;
import project.pc.model.companyModel;
import project.pc.model.orderModel;
import project.pc.model.statsConfig;
import project.pc.model.statsModel;
import project.pc.service.StatsService;

@Controller
@RequestMapping("/pc/statistics/")
public class StatisticsController extends CommonController {

	@Autowired ServiceConfig serviceConfig;
	@Autowired StatsService statsService; 

	@RequestMapping("stats_scan")
	public ModelAndView stats_scan(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/statistics/stats_scan");
		int todayCount = 0;
		Map<String, Object> paramMap = null;
		List<statsModel> monthData = null;
		List<statsModel> dailyData = null;
		List<statsModel> hourData = null;
		List<statsConfig> statsConfig = null;
		List<statsModel> rankData = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			int count = statsService.getTotalCnt(paramMap);
			monthData = statsService.getMonthData(paramMap);
			dailyData = statsService.getDailyData(paramMap);
			hourData = statsService.getHourData(paramMap);
			statsConfig = statsService.getScanData(paramMap);
			todayCount = statsService.getTodayCount(paramMap);
			rankData = statsService.getCompanyRank(paramMap);
			request.getSession().setAttribute("count", count);
			request.getSession().setAttribute("statsConfig", statsConfig);
			request.getSession().setAttribute("monthData", monthData);
			request.getSession().setAttribute("dailyData", dailyData);
			request.getSession().setAttribute("hourData", hourData);
			request.getSession().setAttribute("rankData", rankData);
			request.getSession().setAttribute("paramMap", paramMap);
			mav.addObject("todayCount",todayCount);
			mav.addObject("rankData",rankData);
			mav.addObject("statsConfig",statsConfig);
			mav.addObject("st_company",paramMap.get("st_company"));
			mav.addObject("st_category",paramMap.get("st_category"));
			mav.addObject("st_order",paramMap.get("st_order"));
			mav.addObject("startDate",paramMap.get("startDate"));
			mav.addObject("endDate",paramMap.get("endDate"));
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.STATS_SCAN_ERROR + "/" + e.getMessage());
		}finally{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("stats_local")
	public ModelAndView stats_local(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/statistics/stats_local");
		Map<String, Object> paramMap = null;
		List<statsConfig> nationRank = null;
		List<statsModel> nationData = null;
		List<statsModel> cityData = null;
		List<statsModel> addressData = null;
		Map<String, Object> countMap = null;
		List<Map<String, Object>> companyCityRank = null;
		List<Map<String, Object>> mapData = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			String listcount = statsService.getAddressData_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			
			nationRank = statsService.getNationRank(paramMap);
			countMap = statsService.getLocalCnt(paramMap);
			nationData = statsService.getNationData(paramMap);
			cityData = statsService.getCityData(paramMap);
			addressData = statsService.getAddressData(paramMap);
			companyCityRank = statsService.getCompanyCityRank(paramMap);
			mapData = statsService.getMapData(paramMap);
			mav.addObject("countMap", countMap);
			mav.addObject("nationRank", nationRank);
			mav.addObject("nationData", nationData);
			mav.addObject("cityData", cityData);
			mav.addObject("addressData", addressData);
			mav.addObject("companyCityRank", companyCityRank);
			mav.addObject("mapData", mapData);
			mav.addObject("st_company", paramMap.get("st_company"));
			mav.addObject("st_category", paramMap.get("st_category"));
			mav.addObject("st_order", paramMap.get("st_order"));
			mav.addObject("startDate", paramMap.get("startDate"));
			mav.addObject("endDate", paramMap.get("endDate"));
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.STATS_LOCAL_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("companylist")
	public ModelAndView stats_companylist(HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		List<companyModel> companyList = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			companyList = statsService.getStatsCompanyList(paramMap);

			if(companyList.size() <= 0)
			{
				jsonObj.put("result", "0");
			}
			else
			{
				jsonObj.put("result", "1");
				jsonObj.put("companyList", companyList);
			}

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.STATS_COMPANYLIST_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
			companyList.clear();
			companyList = null;
			paramMap.clear();
			paramMap = null;
		}
		
		return null;
	}

	@RequestMapping("orderlist")
	public ModelAndView stats_orderlist(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		List<orderModel> orderList = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			orderList = statsService.getStatsOrderList(paramMap);

			if(orderList.size() <= 0)
			{
				jsonObj.put("result", "0");
			}
			else
			{
				jsonObj.put("result", "1");
				jsonObj.put("orderList", orderList);
			}

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.STATS_ORDERLIST_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
			orderList.clear();
			orderList = null;
			paramMap.clear();
			paramMap = null;
		}
		
		return null;
	}

	@RequestMapping("excel")
	public ModelAndView excelDown(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		Map<String, Object> paramMap = null;
		mav.setViewName("/pc/statistics/excel_stats_scan");
		try
		{
			paramMap = this.makeRequestMap(request);
			mav.addObject("paramMap",paramMap);
			return mav;
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.EXCELDOWN_ERROR + "/" + e.getMessage());
			return null;
		}
	}

	@RequestMapping("stats_excel_local")
	public ModelAndView stats_excel_local(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/statistics/excel_stats_local");
		Map<String, Object> paramMap = null;
		List<statsConfig> nationRack = null;
		List<statsModel> nationData = null;
		List<statsModel> cityData = null;
		List<statsModel> addressData = null;
		Map<String, Object> countMap = null;
		List<Map<String, Object>> companyCityRank = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			nationRack = statsService.getExcelNationRank(paramMap);
			countMap = statsService.getExcelLocalCnt(paramMap);
			nationData = statsService.getExcelNationData(paramMap);
			cityData = statsService.getExcelCityData(paramMap);
			addressData = statsService.getExcelAddressData(paramMap);
			companyCityRank = statsService.getExcelCompanyCityRank(paramMap);
			mav.addObject("countMap", countMap);
			mav.addObject("nationRack", nationRack);
			mav.addObject("nationData", nationData);
			mav.addObject("cityData", cityData);
			mav.addObject("addressData", addressData);
			mav.addObject("companyCityRank", companyCityRank);
			mav.addObject("paramMap", paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.STATS_EXCEL_LOCAL_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("addrmore")
	public ModelAndView addrmore(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Map<String, Object> paramMap = null;
		List<statsModel> addrList = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			String listcount = statsService.getAddressData_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());

			addrList = statsService.getAddressData(paramMap);

			jsonObj.put("addrList", addrList);
			jsonObj.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.ADDRMORE_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return null;
	}
}