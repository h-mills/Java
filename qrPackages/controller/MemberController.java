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
import project.pc.service.MemberService;

@Controller
@RequestMapping("/member/")
public class MemberController extends CommonController {

	@Autowired ServiceConfig serviceConfig;
	@Autowired MemberService memberService;

	@RequestMapping("main")
	@HPNullCheck(parameters={"pageNum"})
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		Map<String, Object> paramMap = null;
		List<HashMap<String, Object>> list = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			String listcount = memberService.getMemberList_Cnt();
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			list = memberService.getMemberList(paramMap);
			if (list != null) {
				for(int i=0;i<list.size();i++){
					if(((int)list.get(i).get("level") & 0x01) > 0 ) {list.get(i).put("qr", 1);} else{list.get(i).put("qr", 0);}
					if(((int)list.get(i).get("level") & 0x02) > 0 ) {list.get(i).put("est", 1);} else{list.get(i).put("est", 0);}
					if(((int)list.get(i).get("level") & 0x04) > 0 ) {list.get(i).put("mng", 1);} else{list.get(i).put("mng", 0);}
				}
				result.put("list", list);
				result.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			}
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
		return new ModelAndView("/pc/member/main", "data", result);
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
	@RequestMapping("idcheck")
	@HPNullCheck(parameters={"id"})
	public ModelAndView idcheck(HttpServletRequest request, HttpServletResponse response) throws BizException 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			String id = request.getParameter("id");
			
			if(id == null)
			{
				jsonObj.put("id", "-1");
			}
			else
			{
				int check = memberService.getIdCount(id);
				if(check == 0)
				{
					jsonObj.put("id", "1");//ok
				}
				else
				{
					jsonObj.put("id", "0");//현재 사용중인 id
				}
			}
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.IDCHECK_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
		}
		
		return null;
	}
	@RequestMapping("modmain")
	public ModelAndView modmain(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		Map<String, Object> paramMap = null;
		HashMap<String, Object> info = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			info = memberService.getMember(paramMap);
			if(((int)info.get("level") & 0x01) > 0 ) {info.put("qr", 1);} else{info.put("qr", 0);}
			if(((int)info.get("level") & 0x02) > 0 ) {info.put("est", 1);} else{info.put("est", 0);}
			if(((int)info.get("level") & 0x04) > 0 ) {info.put("mng", 1);} else{info.put("mng", 0);}
			result.put("info", info);
			result.put("pageNum", paramMap.get("pageNum").toString());
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.MODMAIN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			if(paramMap != null){
				paramMap.clear();
				paramMap = null;
			}
		}
		return new ModelAndView("/pc/member/mod", "data", result);
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
		mav.setViewName("redirect:/member/main");

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
}