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
import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.common.util.StringUtil;
import project.common.util.WebUtil;
import project.pc.model.memberModel;
import project.pc.service.MemberService;

@Controller
@RequestMapping("/pc/member/")
public class MemberController extends CommonController {

	@Autowired ServiceConfig serviceConfig;
	@Autowired MemberService memberService;

	@RequestMapping("main")
	@HPNullCheck(parameters={"pageNum"})
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/member/main");
		Map<String, Object> paramMap = null;
		List<memberModel> memberList = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			String listcount = memberService.getMemberList_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			memberList = memberService.getMemberList(paramMap);
			mav.addObject("memberList", memberList);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.MAIN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("umain")
	@HPNullCheck(parameters={"pageNum"})
	public ModelAndView umain(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/pc/member/umain");
		Map<String, Object> paramMap = null;
		List<HashMap<String, Object>> userList = null;
		List<HashMap<String, Object>> companyList = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			companyList = memberService.getCompany();
			String listcount = memberService.getUserList_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			userList = memberService.getUserList(paramMap);
			
			if(userList != null)
			{
				for (int i=0; i<userList.size(); i++) 
				{
					int level = (int)userList.get(i).get("level");
					userList.get(i).put("mDept", (level & 0x10) > 0 ? "1" : "0");
					userList.get(i).put("mStats", (level & 0x08) > 0 ? "1" : "0");
					userList.get(i).put("mCard", (level & 0x04) > 0 ? "1" : "0");
					userList.get(i).put("mNotice", (level & 0x02) > 0 ? "1" : "0");
					userList.get(i).put("mOrder", (level & 0x01) > 0 ? "1" : "0");
				}
			}

			mav.addObject("companyList", companyList);
			mav.addObject("userList", userList);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.UMAIN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("addmain")
	public ModelAndView addmain(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/member/add");

		try
		{
			String pageNum = StringUtil.replaceNull(request.getParameter("pageNum"), "0");
			mav.addObject("pageNum", pageNum);
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.ADDMAIN_ERROR + "/" + e.getMessage());
		}
		finally {
			
		}
		return mav;
	}

	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/member/add");
		Map<String, Object> paramMap = new HashMap<>();

		try
		{
			paramMap = this.makeRequestMap(request);
			String[] levelArr = request.getParameterValues("level");
			int level = 0;
			for (int i=0; i < levelArr.length; i++) {
				
				level += Integer.parseInt(levelArr[i]);
			}

			paramMap.put("level", level);

			String res = "success";
			int ret = memberService.insertMember(paramMap);

			if(ret != 1)
			{
				res = "fail";
			}
			mav.addObject("res", res);
			mav.addObject("pageNum", paramMap.get("pageNum"));
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.ADD_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("modmain")
	public ModelAndView modmain(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/member/mod");
		Map<String, Object> paramMap = new HashMap<>();
		memberModel member = new memberModel();

		try
		{
			paramMap = this.makeRequestMap(request);
			member = memberService.getMember(paramMap);
			mav.addObject("member", member);
			mav.addObject("pageNum", paramMap.get("pageNum"));
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.MODMAIN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("mod")
	public ModelAndView mod(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/member/mod");
		Map<String, Object> paramMap = new HashMap<>();

		try
		{
			paramMap = this.makeRequestMap(request);

			String[] levelArr = request.getParameterValues("level");

			int level = 0;
			for (int i=0; i < levelArr.length; i++) {
				level += Integer.parseInt(levelArr[i]);
			}
			paramMap.put("level", level);
			
			String res = "success";
			int ret = memberService.updateMember(paramMap);
			if(ret != 1)
			{
				res = "fail";
			}
			mav.addObject("res", res);
			mav.addObject("no", paramMap.get("no"));
			mav.addObject("pageNum", paramMap.get("pageNum"));
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.MOD_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("del")
	public ModelAndView del(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pc/member/main");

		try
		{
			String[] no = request.getParameterValues("box");
			String pageNum = StringUtil.replaceNull(request.getParameter("pageNum"), "0");
			memberService.deleteMember(no);
			no = null;
			mav.addObject("pageNum", pageNum);
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.DEL_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("usermodify")
	public void usermodify(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		try
		{
			paramMap = this.makeRequestMap(request);

			int result = memberService.updateUserModify(paramMap);

			jsonObj.put("result", result);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.USERMODIFY_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}
}