package project.pc.controller;

import java.io.PrintWriter;
import java.util.HashMap;
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
import project.common.util.StringUtil;
import project.common.util.WebUtil;
import project.pc.service.DeptService;

@Controller
@RequestMapping("/pc/dept/")
public class DeptController extends CommonController
{
	@Autowired ServiceConfig serviceConfig;
	@Autowired DeptService service;

	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		ModelAndView mav = new ModelAndView("/pc/dept/dept");
		Map<String, Object> paramMap = null;
		List<HashMap<String, Object>> deptList = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			String listcount = service.getDeptListCount(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			deptList = service.getDeptList(paramMap);

			mav.addObject("deptList", deptList);
			mav.addObject("param", paramMap);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.DEPT_MAIN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("add")
	public void add(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		try
		{
			paramMap = this.makeRequestMap(request);

			int result = service.insertDept(paramMap);

			jsonObj.put("result", result);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.DEPT_ADD_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}

	@RequestMapping("modify")
	public void modify(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		try
		{
			paramMap = this.makeRequestMap(request);

			int result = service.updateDept(paramMap);

			jsonObj.put("result", result);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.DEPT_MODIFY_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}

	@RequestMapping("del")
	public ModelAndView del(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		ModelAndView mav = new ModelAndView("redirect:/pc/dept/main");
		try
		{
			String[] noList = request.getParameterValues("box");
			String pageNum = StringUtil.replaceNull(request.getParameter("pageNum"), "0");
			service.deleteDept(noList);
			noList = null;
			mav.addObject("pageNum", pageNum);
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.DEPT_SYSTEM, MsgConstants.DEPT_DEL_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

}
