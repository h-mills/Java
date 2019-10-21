package project.pc.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import project.common.config.MsgConstants;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.pc.service.StatsService;

@Controller
@RequestMapping("/stats/")
public class StatsController extends CommonController {

	@Autowired StatsService statsService; 

	@RequestMapping("stats_scan")
	public ModelAndView stats_scan(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/pc/stats/stats_scan");
		HashMap<String, Object> paramMap = null;
		List<HashMap<String, Object>> statsConfig = null;
		List<HashMap<String, Object>> monthData = null;
		List<HashMap<String, Object>> dailyData = null;
		List<HashMap<String, Object>> hourData = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			int scanTotalCount = statsService.getScanTotalCount(paramMap);
			int scanTodayCount = statsService.getScanTodayCount(paramMap);
			statsConfig = statsService.getScanData(paramMap);
			monthData = statsService.getMonthData(paramMap);
			dailyData = statsService.getDailyData(paramMap);
			hourData = statsService.getHourData(paramMap);

			request.getSession().setAttribute("param", paramMap);
			request.getSession().setAttribute("scanTotalCount", scanTotalCount);
			request.getSession().setAttribute("scanTodayCount", scanTodayCount);
			request.getSession().setAttribute("statsConfig", statsConfig);
			request.getSession().setAttribute("monthData", monthData);
			request.getSession().setAttribute("dailyData", dailyData);
			request.getSession().setAttribute("hourData", hourData);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.STATS_SCAN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("stats_local")
	public ModelAndView stats_local(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/stats/stats_local");
		Map<String, Object> paramMap = null;
		Map<String, Object> countMap = null;
		List<HashMap<String, Object>> nationRack = null;
		List<HashMap<String, Object>> nationData = null;
		List<HashMap<String, Object>> cityData = null;
		List<HashMap<String, Object>> gpsList = null;
		try
		{
			paramMap = this.makeRequestMap(request);

			countMap = statsService.getLocalCnt(paramMap);
			nationRack = statsService.getNationRank(paramMap);
			nationData = statsService.getNationData(paramMap);
			cityData = statsService.getCityData(paramMap);
			gpsList = statsService.getGpsList(paramMap);
			
			mav.addObject("gpsList", gpsList);
			
			request.getSession().setAttribute("param", paramMap);
			request.getSession().setAttribute("countMap", countMap);
			request.getSession().setAttribute("nationRack", nationRack);
			request.getSession().setAttribute("nationData", nationData);
			request.getSession().setAttribute("cityData", cityData);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.STATS_LOCAL_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("scanexcel")
	public ModelAndView scanexcel(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		Map<String, Object> paramMap = null;
		mav.setViewName("/pc/stats/excel_stats_scan");
		try
		{
			paramMap = this.makeRequestMap(request);
			mav.addObject("paramMap", paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.SCANEXCEL_ERROR + "/" + e.getMessage());
			return null;
		}
		return mav;
	}

	@RequestMapping("localexcel")
	public ModelAndView localexcel(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/stats/excel_stats_local");
		Map<String, Object> paramMap = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			mav.addObject("paramMap", paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.LOCALEXCEL_ERROR + "/" + e.getMessage());
		}
		return mav;
	}
	@RequestMapping("qr_stats_scan")
	public ModelAndView qr_stats_scan(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/pc/qr/stats_scan");
		HashMap<String, Object> paramMap = null;
		List<HashMap<String, Object>> monthData = null;
		List<HashMap<String, Object>> dailyData = null;
		List<HashMap<String, Object>> hourData = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			int totalCnt = statsService.getQRScanTotalCount(paramMap);
			monthData = statsService.getQRMonthData(paramMap);
			dailyData = statsService.getQRDailyData(paramMap);
			hourData = statsService.getQRHourData(paramMap);
			
			request.getSession().setAttribute("totalCnt", totalCnt);
			request.getSession().setAttribute("param", paramMap);
			request.getSession().setAttribute("monthData", monthData);
			request.getSession().setAttribute("dailyData", dailyData);
			request.getSession().setAttribute("hourData", hourData);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.STATS_SCAN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("qr_stats_local")
	public ModelAndView qr_stats_local(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/qr/stats_local");
		Map<String, Object> paramMap = null;
		Map<String, Object> countMap = null;
		List<HashMap<String, Object>> nationRack = null;
		List<HashMap<String, Object>> nationData = null;
		List<HashMap<String, Object>> cityData = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			countMap = statsService.getQRLocalCnt(paramMap);
			nationRack = statsService.getQRNationRank(paramMap);
			nationData = statsService.getQRNationData(paramMap);
			cityData = statsService.getQRCityData(paramMap);

			request.getSession().setAttribute("param", paramMap);
			request.getSession().setAttribute("countMap", countMap);
			request.getSession().setAttribute("nationRack", nationRack);
			request.getSession().setAttribute("nationData", nationData);
			request.getSession().setAttribute("cityData", cityData);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.STATS_LOCAL_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}
	@RequestMapping("qr_scanexcel")
	public ModelAndView qr_scanexcel(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		Map<String, Object> paramMap = null;
		mav.setViewName("/pc/qr/excel_stats_scan");
		try
		{
			paramMap = this.makeRequestMap(request);
			mav.addObject("paramMap", paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.SCANEXCEL_ERROR + "/" + e.getMessage());
			return null;
		}
		return mav;
	}

	@RequestMapping("qr_localexcel")
	public ModelAndView qr_localexcel(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/qr/excel_stats_local");
		Map<String, Object> paramMap = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			mav.addObject("paramMap", paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.STATS_SYSTEM, MsgConstants.LOCALEXCEL_ERROR + "/" + e.getMessage());
		}
		return mav;
	}
}