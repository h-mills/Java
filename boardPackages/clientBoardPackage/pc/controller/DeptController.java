package project.pc.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

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
import project.pc.service.DeptService;

@Controller
@RequestMapping("/pc/dept/")
public class DeptController extends CommonController {
	@Autowired ServiceConfig serviceConfig;
	@Autowired DeptService service;
	
	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/pc/dept/main");
		HashMap<String, Object> requestMap = new HashMap<>();
		List<HashMap<String, Object>> deptList = null;
		List<HashMap<String, Object>> Stdept = null;

		try
		{
			String company_no = (String) request.getSession().getAttribute("COMPANY_NO");
			if (!company_no.equals("") && company_no != null) 
			{
				requestMap.put("company_no", company_no);
				deptList = service.getDeptList(requestMap);
				Stdept = service.getStDept();
				mav.addObject("deptList", deptList);
				mav.addObject("Stdept", Stdept);
				mav.addObject("company_no", company_no);
			}
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("deptsubmit")
	public void deptsubmit(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> requestMap = null;

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
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR + "/" + be.getMessage());
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
		HashMap<String, Object> requestMap = null;

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
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR + "/" + be.getMessage());
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
		HashMap<String, Object> requestMap = null;

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
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR + "/" + be.getMessage());
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
