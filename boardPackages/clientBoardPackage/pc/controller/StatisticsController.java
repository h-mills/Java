package project.pc.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
		List<statsModel> deptRank = null;
		List<statsModel> nameData = null;
		List<HashMap<String, Object>> deptList = null;
		HttpSession session = request.getSession();
		String company_no = (String) session.getAttribute("COMPANY_NO");
		String st_dept = "-2";
		String namesearch = "0";
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
			
			if(paramMap.get("namesearch") != null)
				namesearch = paramMap.get("namesearch").toString();
			if(namesearch.equals("1")){
				String listcount = statsService.getNameDataCnt(paramMap);
				String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
				paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
				paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
				mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			}
			deptList = statsService.getDeptList(paramMap);
			
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

			int count = statsService.getTotalCnt(paramMap);
			if(namesearch.equals("0")){
				monthData = statsService.getMonthData(paramMap);
				dailyData = statsService.getDailyData(paramMap);
				hourData = statsService.getHourData(paramMap);
				deptRank = statsService.getDeptRank(paramMap);
			}else if(namesearch.equals("1"))
				nameData = statsService.getNameData(paramMap);
			statsConfig = statsService.getScanData(paramMap);
			todayCount = statsService.getTodayCount(paramMap);
			rankData = statsService.getCompanyRank(paramMap);
			request.getSession().setAttribute("count", count);
			request.getSession().setAttribute("statsConfig", statsConfig);
			if(namesearch.equals("0")){
				request.getSession().setAttribute("monthData", monthData);
				request.getSession().setAttribute("dailyData", dailyData);
				request.getSession().setAttribute("hourData", hourData);
				request.getSession().setAttribute("deptRank", deptRank);
			}else if(namesearch.equals("1"))
				request.getSession().setAttribute("nameData", nameData);
			request.getSession().setAttribute("paramMap", paramMap);
			
			mav.addObject("namesearch",namesearch);
			mav.addObject("todayCount",todayCount);
			mav.addObject("rankData",rankData);
			mav.addObject("statsConfig",statsConfig);
			mav.addObject("st_dept",st_dept);
			mav.addObject("startDate",paramMap.get("startDate"));
			mav.addObject("endDate",paramMap.get("endDate"));
			mav.addObject("deptList",deptList);
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
	@HPNullCheck(parameters={"pageNum"})
	public ModelAndView stats_local(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/statistics/stats_local");
		Map<String, Object> paramMap = null;
		List<statsConfig> nationRack = null;
		List<statsModel> nationData = null;
		List<statsModel> deptRank = null;
		List<statsModel> localRank = null;
		List<statsModel> cityData = null;
		List<statsModel> addressData = null;
		List<HashMap<String, Object>> deptList = null;
		List<HashMap<String, Object>> gpsList = null;
		List<statsModel> nameData = null;
		Map<String, Object> countMap = null;
		HttpSession session = request.getSession();
		String company_no = (String) session.getAttribute("COMPANY_NO");
		String st_dept = "-1";
		String namesearch = "0";
		try
		{
			paramMap = this.makeRequestMap(request);
			paramMap.put("company_no", company_no);
			paramMap.put("st_dept", st_dept);
			String[] stDept = request.getParameterValues("st_dept[]");
			if(stDept != null)
			{
				paramMap.put("stDept", stDept);
				st_dept = "0";
				paramMap.put("st_dept", "0");
			}
			
			if(paramMap.get("namesearch") != null)
				namesearch = paramMap.get("namesearch").toString();
			
			int count = statsService.getTotalCnt(paramMap);
			deptList = statsService.getDeptList(paramMap);
			nationRack = statsService.getNationRank(paramMap);
			countMap = statsService.getLocalCnt(paramMap);
			deptRank = statsService.getLocalDeptRank(paramMap);

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

			if(namesearch.equals("0")){
				gpsList = statsService.getGpsData(paramMap);
				localRank = statsService.getLocalRank(paramMap);
				nationData = statsService.getNationData(paramMap);
				cityData = statsService.getCityData(paramMap);
				String listcount = statsService.getAddressDataCnt(paramMap);
				String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
				paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
				paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
				addressData = statsService.getAddressData(paramMap);
				mav.addObject("localRank",localRank);
				mav.addObject("listcount",listcount);
				mav.addObject("nationData", nationData);
				mav.addObject("cityData", cityData);
				mav.addObject("addressData", addressData);
				mav.addObject("gpsList",gpsList);
				mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			}else if(namesearch.equals("1")){
				String listcount = statsService.getNameLocalDataCnt(paramMap);
				count = Integer.parseInt(statsService.getNameLocalDataCnt(paramMap));
				String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
				paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
				paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
				nameData = statsService.getNameLocalData(paramMap);
				mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			}
			mav.addObject("deptRank",deptRank);
			mav.addObject("nameData",nameData);
			mav.addObject("count",count);
			mav.addObject("namesearch",namesearch);
			mav.addObject("deptList",deptList);
			mav.addObject("countMap", countMap);
			mav.addObject("nationRack", nationRack);
			mav.addObject("st_dept",st_dept);
			mav.addObject("startDate",paramMap.get("startDate"));
			mav.addObject("endDate",paramMap.get("endDate"));
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.STATS_LOCAL_ERROR + "/" + e.getMessage());
		}finally{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}
	@RequestMapping("stats_more")
	@HPNullCheck(parameters={"pageNum2"})
	public void statsMore(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> paramMap = null;
		List<statsModel> addressData = null;
		PrintWriter writer = response.getWriter();
		JSONObject result = new JSONObject();
		JSONArray para = new JSONArray();

		try{
			paramMap = this.makeRequestMap(request);
			String[] stDept = request.getParameterValues("st_dept");
			if(stDept != null)
			{
				paramMap.put("stDept", stDept);
				paramMap.put("st_dept", "0");
			} else {
				paramMap.put("st_dept", "-1");
			}
			String listcount = statsService.getAddressDataCnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum2"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			addressData = statsService.getAddressData(paramMap);
			JSONObject json = new JSONObject();
			for(int i=0; i<addressData.size();i++){
				json.put("getNation", addressData.get(i).getNation());
				json.put("getCity", addressData.get(i).getCity());
				json.put("getName", addressData.get(i).getName());
				json.put("getDate", addressData.get(i).getDate());
				json.put("getAddress", addressData.get(i).getAddress());
				json.put("getRownum", addressData.get(i).getRownum());
				json.put("getCategory", addressData.get(i).getCategory());
				json.put("getCard_data_no", addressData.get(i).getCard_data_no());
				json.put("getOrder_no", addressData.get(i).getOrder_no());
				
				para.add(json);
			}
			result.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.ADDRESS_MORE_ERROR + "/" + e.getMessage());
		}
		result.put("list", para);
		writer.println(result);
		writer.flush();
		writer.close();
		writer = null;
	}
	@RequestMapping("excel")
	public ModelAndView excelDown(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		Map<String, Object> paramMap = null;
		List<statsModel> nameRankData = null;
		mav.setViewName("/pc/statistics/excel_stats_scan");
		try
		{
			paramMap = this.makeRequestMap(request);
			System.out.println("paramMap = " + paramMap.toString());
			nameRankData = statsService.getExcelNameRankData(paramMap);
			
			mav.addObject("nameRankData",nameRankData);
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
		List<statsModel> nameData = null;
		List<statsModel> deptRank = null;
		Map<String, Object> countMap = null;
		HttpSession session = request.getSession();
		String company_no = (String) session.getAttribute("COMPANY_NO");
		String namesearch = "0";
		try
		{
			paramMap = this.makeRequestMap(request);
			paramMap.put("company_no", company_no);
			if(paramMap.get("namesearch") != null)
				namesearch = (String) paramMap.get("namesearch");
			nationRack = statsService.getExcelNationRank(paramMap);
			countMap = statsService.getExcelLocalCnt(paramMap);
			if(namesearch.equals("0")){
				deptRank = statsService.getLocalRank(paramMap);
				nationData = statsService.getExcelNationData(paramMap);
				cityData = statsService.getExcelCityData(paramMap);
				addressData = statsService.getExcelAddressData(paramMap);
				mav.addObject("deptRank", deptRank);
				mav.addObject("nationData", nationData);
				mav.addObject("cityData", cityData);
				mav.addObject("addressData", addressData);
			}else if(namesearch.equals("1")){
				nameData = statsService.getExcelNameLocalData(paramMap);
				mav.addObject("nameData",nameData);
			}
			mav.addObject("namesearch",paramMap.get("namesearch"));
			mav.addObject("countMap", countMap);
			mav.addObject("nationRack", nationRack);
			mav.addObject("paramMap", paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.STATS_EXCEL_LOCAL_ERROR + "/" + e.getMessage());
		}
		return mav;
	}
	@RequestMapping("stats_scan_more")
	@HPNullCheck(parameters={"pageNum"})
	public void statsScanMore(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> paramMap = null;
		List<statsModel> nameData = null;
		PrintWriter writer = response.getWriter();
		JSONObject result = new JSONObject();
		JSONArray para = new JSONArray();
		try{
			paramMap = this.makeRequestMap(request);
			String[] stDept = request.getParameterValues("st_dept");
			if(stDept != null)
			{
				paramMap.put("stDept", stDept);
				paramMap.put("st_dept", "0");
			} else {
				paramMap.put("st_dept", "-1");
			}
			String listcount = statsService.getNameDataCnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			nameData = statsService.getNameData(paramMap);
			JSONObject json = new JSONObject();
			for(int i=0; i<nameData.size();i++){
				json.put("getName", nameData.get(i).getName());
				json.put("getCount", nameData.get(i).getCount());
				json.put("getRownum", nameData.get(i).getRownum());
				json.put("getDept_name", nameData.get(i).getDept_name());
				json.put("getOrderth", nameData.get(i).getOrderth());
				json.put("getCategory", nameData.get(i).getCategory());
				json.put("getCard_data_no", nameData.get(i).getCard_data_no());
				json.put("getOrder_no", nameData.get(i).getOrder_no());
				
				para.add(json);
			}
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.SCAN_NAMECARD_MORE_ERROR + "/" + e.getMessage());
		}
		result.put("list", para);
		writer.println(result);
		writer.flush();
		writer.close();
		writer = null;
	}
	@RequestMapping("stats_local_more")
	@HPNullCheck(parameters={"pageNum2"})
	public void statsLocalMore(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> paramMap = null;
		List<statsModel> nameData = null;
		PrintWriter writer = response.getWriter();
		JSONObject result = new JSONObject();
		JSONArray para = new JSONArray();
		try{
			paramMap = this.makeRequestMap(request);
			String[] stDept = request.getParameterValues("st_dept");
			if(stDept != null)
			{
				paramMap.put("stDept", stDept);
				paramMap.put("st_dept", "0");
			} else {
				paramMap.put("st_dept", "-1");
			}
			String listcount = statsService.getNameLocalDataCnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum2"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			nameData = statsService.getNameLocalData(paramMap);
			JSONObject json = new JSONObject();
			for(int i=0; i<nameData.size();i++){
				json.put("getName", nameData.get(i).getName());
				json.put("getCitycount", nameData.get(i).getCitycount());
				json.put("getRownum", nameData.get(i).getRownum());
				json.put("getDept_name", nameData.get(i).getDept_name());
				json.put("getOrderth", nameData.get(i).getOrderth());
				json.put("getCategory", nameData.get(i).getCategory());
				json.put("getCard_data_no", nameData.get(i).getCard_data_no());
				json.put("getOrder_no", nameData.get(i).getOrder_no());
				
				para.add(json);
			}
		}catch(Exception e){
			new BizException(MsgConstants.STATISTIC_SYSTEM, MsgConstants.LOCAL_NAMECARD_MORE_ERROR + "/" + e.getMessage());
		}
		result.put("list", para);
		writer.println(result);
		writer.flush();
		writer.close();
		writer = null;
	}
}