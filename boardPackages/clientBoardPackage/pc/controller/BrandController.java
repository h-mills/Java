package project.pc.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.*;

import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.common.util.WebUtil;
import project.pc.service.BrandService;

@Controller
@RequestMapping("/brand/")
public class BrandController extends CommonController {

	@Autowired ServiceConfig serviceConfig;
	@Autowired BrandService service;

	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		List<HashMap<String,Object>> list = null;
		try{
			list = service.getMainNotice();
		}catch(Exception e){
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.LOGOUT_ERROR + "/" + e.getMessage());
		}
		return new ModelAndView("/brand/main","list", list);
	}
	//명함소개
	@RequestMapping("about")
	public ModelAndView about(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		String menu = null;
		try
		{
			menu = request.getParameter("menu");
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.LOGOUT_ERROR + "/" + e.getMessage());
		}
		return new ModelAndView("/brand/about", "result", menu);
	}
	
	//명함종류
	@RequestMapping("namecard")
	public ModelAndView namecard(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		String menu = null;
		try
		{
			menu = request.getParameter("menu");
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.LOGOUT_ERROR + "/" + e.getMessage());
		}
		return new ModelAndView("/brand/namecard", "result", menu);
	}
	
	//회사소개
	@RequestMapping("company")
	public ModelAndView company(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/company");
	}
	//회사소개-파트너사
	@RequestMapping("partner")
	public ModelAndView partner(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/partner");
	}
	//회사소개-컨택트어즈
	@RequestMapping("contact")
	public ModelAndView contact(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/contact");
	}
	//일반명함 사진1 클릭
	@RequestMapping("namecardtype01")
	public ModelAndView namecardtype01(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype01");
	}
	//일반명함 사진2 클릭
	@RequestMapping("namecardtype02")
	public ModelAndView namecardtype02(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype02");
	}
	//일반명함 사진3 클릭
	@RequestMapping("namecardtype03")
	public ModelAndView namecardtype03(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype03");
	}
	//일반명함 사진4 클릭
	@RequestMapping("namecardtype04")
	public ModelAndView namecardtype04(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype04");
	}

	//디자인 명함 사진1
	@RequestMapping("namecardtype_ds_01")
	public ModelAndView namecardtype_ds_01(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype_ds_01");
	}
	//디자인 명함 사진2
	@RequestMapping("namecardtype_ds_02")
	public ModelAndView namecardtype_ds_02(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype_ds_02");
	}
	//디자인 명함 사진3
	@RequestMapping("namecardtype_ds_03")
	public ModelAndView namecardtype_ds_03(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype_ds_03");
	}
	//디자인 명함 사진4
	@RequestMapping("namecardtype_ds_04")
	public ModelAndView namecardtype_ds_04(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype_ds_04");
	}
	//스페셜 명함 사진1
	@RequestMapping("namecardtype_sp_01")
	public ModelAndView namecardtype_sp_01(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype_sp_01");
	}
	//스페셜 명함 사진2
	@RequestMapping("namecardtype_sp_02")
	public ModelAndView namecardtype_sp_02(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype_sp_01");
	}
	//스페셜 명함 사진3
	@RequestMapping("namecardtype_sp_03")
	public ModelAndView namecardtype_sp_03(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype_sp_01");
	}
	//스페셜 명함 사진4
	@RequestMapping("namecardtype_sp_04")
	public ModelAndView namecardtype_sp_04(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/brand/namecardtype_sp_01");
	}
	//공지사항
	@RequestMapping("notice")
	public ModelAndView notice(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = null;
		String pagenum = request.getParameter("pagenum");
		try{
			String listcount = service.getlistcount();
			if(pagenum == null || pagenum.equals("")) pagenum = "0";
			pagenum = WebUtil.getPageNum(pagenum, listcount, serviceConfig.getDefaultListViewSize());
			requestMap.put("pageNum", (Integer.parseInt(pagenum) * serviceConfig.getDefaultListViewSize()));
			requestMap.put("listSize", serviceConfig.getDefaultListViewSize());
			list = service.getlist(requestMap);
			int start = Integer.parseInt(listcount)-(Integer.parseInt(pagenum)*serviceConfig.getDefaultListViewSize());
			result.put("startno", start);
			result.put("list", list);
			result.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pagenum, listcount, serviceConfig.getDefaultListViewSize(), null));
		}catch(Exception be){
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR + "/" + be.getMessage());
		}finally{
			if(requestMap != null){
				requestMap.clear();
				requestMap = null;
			}
		}
		return new ModelAndView("/brand/notice","data", result);
	}
	//공지사항 상세보기
	@RequestMapping("noticedetail")
	@HPNullCheck(parameters={"no"})
	public ModelAndView noticedetail(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		try{
			requestMap = this.makeRequestMap(request);
			service.updatecount(requestMap);
			info = service.getinfo(requestMap);
			info.put("content", info.get("content").toString().replace("\n", "<br>"));
			if(info.get("filepath") != null){
				String viewpath = info.get("filepath").toString();
				viewpath = viewpath.substring(viewpath.indexOf("/")+1, viewpath.length());
				result.put("viewpath", viewpath);
			}
			result.put("pagenum", requestMap.get("pagenum"));
			result.put("info", info);
		}catch(Exception be){
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICEINFO_ERROR + "/" + be.getMessage());
		}finally{
			if(requestMap != null){
				requestMap.clear();
				requestMap = null;
			}
		}
		return new ModelAndView("/brand/noticedetail","data",result);
	}
	//공지사항 파일 다운로드
	@RequestMapping("filedown")
	@HPNullCheck(parameters={"no"})
	public void filedown(HttpServletRequest request, HttpServletResponse response)
	{
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		HashMap<String, Object> requestMap = null;
		byte[] outByte = new byte[4096];
		PrintWriter out = null;
		try 
		{
			String filepath = null;			
			requestMap = this.makeRequestMap(request);
			filepath = service.getfilepath(requestMap);
			filepath = serviceConfig.getNoticePath() + filepath;
			File downfile = new File(filepath);
			if(downfile.exists()){
	        outStream = response.getOutputStream();
	        inputStream = new FileInputStream(downfile);
	        response.setContentType("application/octet-stream; charset=UTF-8");
	        response.setCharacterEncoding("utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode(downfile.getName(), "utf-8") + "\"");
	              
	        while(inputStream.read(outByte, 0, 4096) != -1)
	        {
	        	outStream.write(outByte, 0, 4096);
	        }
	        
	        outByte = null;
			inputStream.close();
		    outStream.flush();
		    outStream.close();
			}else {
				throw new Exception();
			}
		}
		catch(Exception be) 
		{
			try{
				response.setContentType("text/html; charset=UTF-8");
				out = response.getWriter();
				out.println("<script>alert('다운로드에 실패하였습니다.');");
				out.println("javascript:history.back();");
				out.print("</script>");
				out.flush();
			}catch(Exception e){
				new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICEDOWN_ERROR + "/" + e.getMessage());
			}
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICEDOWN_ERROR + "/" + be.getMessage());
		}
	}
}
