package project.pc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.common.util.WebUtil;
import project.pc.model.companyModel;
import project.pc.model.orderModel;
import project.pc.service.CompanyService;
import project.pc.service.IndustryService;

@Controller
@RequestMapping("/pc/company/")
public class CompanyController extends CommonController {

	@Autowired ServiceConfig serviceConfig;
	@Autowired CompanyService service;
	@Autowired IndustryService industryService;

	@RequestMapping("companylist")
	@HPNullCheck(parameters={"pageNum"})
	public ModelAndView companylist(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/company/companylist");
		Map<String, Object> paramMap = null;
		List<companyModel> companyList = null;
		List<HashMap<String, Object>> industryList = null;

		try
		{
			String[] st_industry = request.getParameterValues("st_industry");
			paramMap = this.makeRequestMap(request);
			if (st_industry != null) {
				paramMap.put("st_industry", st_industry);
			}

			String listcount = service.getCompanyList_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());

			companyList = service.getCompanyList(paramMap);

			industryList = industryService.getIndustryListST();

			if (st_industry != null && industryList != null) 
			{
				for (int i=0; i<industryList.size(); i++) 
				{
					for (int j=0; j<st_industry.length; j++) 
					{
						if (industryList.get(i).get("cd").toString().equals(st_industry[j])) {
							industryList.get(i).put("select", 1);
						}
					}
				}
			}

			mav.addObject("companyList", companyList);
			mav.addObject("industryList", industryList);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			mav.addObject("startDate", paramMap.get("startDate").toString());
			mav.addObject("endDate", paramMap.get("endDate"));
			mav.addObject("keyword", paramMap.get("keyword"));
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.COMPANYLIST_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("companydetail")
	public ModelAndView companydetail(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/company/companydetail");
		Map<String, Object> paramMap = null;
		Map<String, Object> company = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			company = service.getCompanyDetail(paramMap);
			String fileName = (String) company.get("file");
			fileName = fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
			company.put("fileName", fileName);
			mav.addObject("company", company);
		} catch (Exception e) {
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.COMPANYDETAIL_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("orderlist")
	@HPNullCheck(parameters={"pageNum"})
	public ModelAndView orderlist(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/company/orderlist");
		Map<String, Object> paramMap = null;
		List<orderModel> orderList = null;
		Map<String, Object> companyInfo = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			String listcount = service.getOrderList_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			orderList = service.getOrderList(paramMap);
			companyInfo = service.getCompanyDetail(paramMap);
			mav.addObject("orderList", orderList);
			mav.addObject("companyInfo", companyInfo);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			mav.addObject("parentStartDate", paramMap.get("parentStartDate"));
			mav.addObject("parentEndDate", paramMap.get("parentEndDate"));
			mav.addObject("parentKeyword", paramMap.get("parentKeyword"));
			mav.addObject("startDate", paramMap.get("startDate"));
			mav.addObject("endDate", paramMap.get("endDate"));
		} catch (Exception e) {
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.ORDERLIST_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("orderdetail")
	public ModelAndView orderdetail(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/company/orderdetail");
		Map<String, Object> paramMap = null;
		Map<String, Object> order = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			order = service.getOrderDetail(paramMap);
			mav.addObject("order", order);
		} catch (Exception e) {
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.ORDERDETAIL_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("bizdownload")
	public void bizdownload(HttpServletRequest request, HttpServletResponse response) {
		try 
		{
			String filepath = request.getParameter("filepath");

			if(filepath.length() > 0)
			{
				ServletOutputStream outStream = null;
				FileInputStream inputStream = null;
				byte[] outByte = new byte[4096];
				filepath = request.getParameter("filepath");
				filepath = filepath.replace("\\", "/");
				String str = serviceConfig.getRootPath();
				filepath = str + filepath;
				File downfile = new File(filepath);
		        outStream = response.getOutputStream();
		        inputStream = new FileInputStream(downfile);               
		        response.setContentType("application/octet-stream; charset=UTF-8");
		        response.setCharacterEncoding("utf-8");
		        response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode(downfile.getName(), "utf-8") + "\"");
		              
		        //Writing InputStream to OutputStream
		        while(inputStream.read(outByte, 0, 4096) != -1)
		        {
		        	outStream.write(outByte, 0, 4096);
		        }
		        downfile = null;
		        outByte = null;
				inputStream.close();
			    outStream.flush();
			    outStream.close();
			}
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM , MsgConstants.BIZDOWNLOAD_ERROR + "/" + e.getMessage());
		}
	}

	@RequestMapping("industryinfo")
	public void industryinfo(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		List<HashMap<String, Object>> industryInfo = new ArrayList<>();
		try
		{
			paramMap = this.makeRequestMap(request);

			industryInfo = service.getIndustryInfo(paramMap);
			jsonObj.put("industryInfo", industryInfo);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.INDUSTRYINFO_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
			industryInfo.clear();
			industryInfo = null;
			jsonObj.clear();
			jsonObj = null;
		}
	}
	@RequestMapping("orginfo")
	@HPNullCheck(parameters={"company_no"})
	public ModelAndView orginfo(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/pc/company/orginfo");
		HashMap<String, Object> paramMap = new HashMap<>();
		List<HashMap<String, Object>> deptList = null;
		List<HashMap<String, Object>> Stdept = null;
		try{
			String company_no = request.getParameter("company_no");
			if(!company_no.equals("") && company_no != null){
				paramMap.put("company_no", company_no);
				deptList = service.getcompanyorg(paramMap);
				Stdept = service.getStDept();
				mav.addObject("company_no",company_no);
				mav.addObject("deptList",deptList);
				mav.addObject("Stdept",Stdept);
			}
		}catch(Exception e){
			return null;
		}finally{
			if(paramMap != null){
				paramMap.clear();
				paramMap = null;
			}
		}
		return mav;
	}
	@RequestMapping("deptsubmit")
	public void deptsubmit(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Map<String, Object> requestMap = null;

		try 
		{
			requestMap = this.makeRequestMap(request);
			int maxcd = service.getMaxCd();
			maxcd ++;
			requestMap.put("cd", maxcd);
			int no = service.insertDept(requestMap);
			
			jsonObj.put("no", no);
			jsonObj.put("maxcd", maxcd);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception be) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETDEPTLIST_ERROR_DAO + "/" + be.getMessage());
		}
		finally 
		{
			if (requestMap != null) 
			{
				requestMap.clear();
				requestMap = null;
			}
		}
	}

	@RequestMapping("deptmodify")
	public void deptmodify(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Map<String, Object> requestMap = null;

		try 
		{
			requestMap = this.makeRequestMap(request);
			int result = service.updateDept(requestMap);

			jsonObj.put("result", result);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception be) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETDEPTLIST_ERROR_DAO + "/" + be.getMessage());
		}
		finally 
		{
			if (requestMap != null) 
			{
				requestMap.clear();
				requestMap = null;
			}
		}
	}

	@RequestMapping("del")
	public void del(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Map<String, Object> requestMap = null;

		try 
		{
			requestMap = this.makeRequestMap(request);

			int result = service.deleteDept(requestMap);

			jsonObj.put("result", result);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception be) 
		{
			new BizException(MsgConstants.COMPANY_SYSTEM, MsgConstants.GETDEPTLIST_ERROR_DAO + "/" + be.getMessage());
		}
		finally 
		{
			if (requestMap != null) 
			{
				requestMap.clear();
				requestMap = null;
			}
		}
	}
}