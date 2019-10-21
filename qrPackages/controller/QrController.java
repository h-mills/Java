package project.pc.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.common.util.WebUtil;
import project.pc.generator.QRGenerator;
import project.pc.service.QrService;

@Controller
@RequestMapping("/qr/")
public class QrController extends CommonController{
	@Autowired ServiceConfig serviceConfig;
	@Autowired QrService qrService; 
	
	@RequestMapping("qr_view")
	public ModelAndView qr_view(HttpServletRequest request, HttpServletResponse response) {
		List<HashMap<String, Object>> list = null;
		JSONObject result = new JSONObject();
		Map<String, Object> paramMap = null;
		String landing = "";
		try
		{
			landing = serviceConfig.getDomainInfo()+serviceConfig.getLandingUrl();
			paramMap = this.makeRequestMap(request);
			String listcount = qrService.getQRListCount(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			list = qrService.getQRList(paramMap);
			result.put("list", list);
			result.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			result.put("listcount", listcount);
			result.put("title", paramMap.get("title"));
			result.put("category", paramMap.get("qr_category"));
			result.put("startDate", paramMap.get("startDate"));
			result.put("endDate", paramMap.get("endDate"));
			result.put("isdelete", paramMap.get("rd_isdelete"));
			result.put("landing", landing);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_VIEW_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return new ModelAndView("/pc/qr/qr_view","data",result);
	}

	@RequestMapping("qr_make")
	public ModelAndView qr_make(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/qr/qr_make");
		Map<String, Object> paramMap = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			mav.addObject("masterno", paramMap.get("masterno"));
			mav.addObject("pageNum", paramMap.get("pageNum"));
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_MAKE_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}


	@RequestMapping("del")
	public ModelAndView qr_del(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/qr/qr_view");
		Map<String, Object> noMap = new HashMap<>();

		try
		{
			String[] no = request.getParameterValues("box");
			String category = request.getParameter("st_category");
			noMap.put("category", category);
			noMap.put("noList", no);
			qrService.updateIsdelte(noMap);

			mav.addObject("rd_isdelete", request.getParameter("rd_isdelete"));
			mav.addObject("listSize", request.getParameter("listSize"));
			mav.addObject("category", request.getParameter("category"));
			mav.addObject("pageNum", request.getParameter("pageNum"));
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_DEL_ERROR + "/" + e.getMessage());
		}
		finally 
		{
			noMap.clear();
			noMap = null;
		}
		return mav;
	}
	@RequestMapping("normal")
	public ModelAndView qr_normal(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/qr/qr_view");
		Map<String, Object> noMap = new HashMap<>();

		try
		{
			String[] no = request.getParameterValues("box");
			String category = request.getParameter("st_category");
			noMap.put("category", category);
			noMap.put("noList", no);
			qrService.updateIsdelte_normal(noMap);

			mav.addObject("rd_isdelete", request.getParameter("rd_isdelete"));
			mav.addObject("listSize", request.getParameter("listSize"));
			mav.addObject("category", request.getParameter("category"));
			mav.addObject("pageNum", request.getParameter("pageNum"));
		}
		catch (Exception e) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_NORMAL_ERROR + "/" + e.getMessage());
		}
		finally 
		{
			noMap.clear();
			noMap = null;
		}
		return mav;
	}
	@RequestMapping("detail")
	@HPNullCheck(parameters={"masterno"})
	public ModelAndView qr_detail (HttpServletRequest request , HttpServletResponse response){
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		String[] data = null;
		try{
			requestMap = this.makeRequestMap(request);
			info = qrService.getQRInfo(requestMap);
			data = info.get("data").toString().split(";");
			result.put("pageNum", requestMap.get("pageNum").toString());
			result.put("info", info);
			result.put("data", data);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_DETAIL_ERROR + "/" + e.getMessage());
		}finally{
			if(requestMap != null){
				requestMap.clear();
				requestMap = null;
			}
		}
		return new ModelAndView("/pc/qr/qr_detail", "data", result);
	}
	@RequestMapping("updateImage")
	public ModelAndView qr_updateImage(HttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObj = new JSONObject();
		int qr_master_no = 0;
		String pageNum = "";
		HashMap<String, Object> requestMap = null;
		try{
			requestMap = this.makeRequestMap(request);
			qr_master_no = Integer.parseInt(requestMap.get("no").toString());
			pageNum = requestMap.get("pageNum").toString();
			String imgPath = requestMap.get("imagePath").toString();
			
			requestMap.put("saveDir", serviceConfig.getRootPath() + serviceConfig.getImgSavePath() + WebUtil.getDate());
			requestMap.put("qr_master_no", qr_master_no);
			requestMap.put("imgPath", serviceConfig.getRootPath() + imgPath);
			requestMap.put("workDir", serviceConfig.getRootPath()+serviceConfig.getJNIDir());
			requestMap.put("landingUrl", serviceConfig.getDomainInfo() + serviceConfig.getLandingUrl());
			QRGenerator qrgen = new QRGenerator();
			qrgen.createQR(requestMap);
			
			jsonObj.put("masterno", qr_master_no);
			jsonObj.put("pageNum", pageNum);
			jsonObj.put("result", "success");
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_UPDATEIMAGE_ERROR + "/" + e.getMessage());
		}finally{
			if(requestMap != null){
				requestMap.clear();
				requestMap = null;
			}
		}
		return null;
	}
	@RequestMapping("update")
	public ModelAndView qr_update (HttpServletRequest request , HttpServletResponse response){
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> requestMap = null;
		String result ="fail";
		try{
			requestMap = this.makeRequestMap(request);
			int res = qrService.updateQRData(requestMap);
			if(res != -1) result ="success";
			jsonObj.put("result", result);
			jsonObj.put("masterno", Integer.parseInt(requestMap.get("no").toString()));
			jsonObj.put("pageNum", Integer.parseInt(requestMap.get("pageNum").toString()));
			jsonObj.put("ca",requestMap.get("ca").toString());
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_UPDATE_ERROR + "/" + e.getMessage());
		}finally{
			if(requestMap != null){
				requestMap.clear();
				requestMap = null;
			}
		}
		return null;
	}
	@RequestMapping("gen")
	public void qr_gen(MultipartHttpServletRequest request , HttpServletResponse response){
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> paramMap = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			jsonObj = qrService.gen(request, paramMap);
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM , MsgConstants.QR_GEN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
		}
	}
	@RequestMapping("download")
	public void qr_download(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> noMap = new HashMap<>();
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		byte[] outByte = new byte[4096];
		HashMap<String, Object> filepath = null;
		PrintWriter out = null;
		try
		{
			String[] no = request.getParameterValues("box");
			noMap.put("noList", no);
			filepath = qrService.getImageList(noMap);
			File downfile = new File(filepath.get("zipFile").toString());
			if(downfile.exists()){
				outStream = response.getOutputStream();
		        inputStream = new FileInputStream(downfile);
		        response.setContentType("application/octet-stream; charset=UTF-8");
		        response.setCharacterEncoding("utf-8");
		        response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode("QrImages.zip", "utf-8") + "\"");
		              
		        while(inputStream.read(outByte, 0, 4096) != -1)
		        {
		        	outStream.write(outByte, 0, 4096);
		        }

		        outByte = null;
				inputStream.close();
			    outStream.flush();
			    outStream.close();
			}
		}
		catch (Exception be) 
		{
			try{
				response.setContentType("text/html; charset=UTF-8");
				out = response.getWriter();
				out.println("<script>alert('다운로드에 실패하였습니다.');");
				out.println("javascript:history.back();");
				out.print("</script>");
				out.flush();
			}catch(Exception e){
				new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_DOWNLOAD_ERROR + "/" + be.getMessage());
			}
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_DOWNLOAD_ERROR + "/" + be.getMessage());
		}
		finally 
		{
			if (filepath != null) {
				//서버에 압축파일 삭제
			    File f = new File(filepath.get("zipFile").toString());
			    f.delete();
			    f = null;
			    f = new File(filepath.get("excelFile").toString());
			    f.delete();
			    f = null;
			}
		}
	}
	@RequestMapping("filedown")
	@HPNullCheck(parameters={"no"})
	public void qr_filedown(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, Object> requestMap = null;
		byte[] outByte = new byte[4096];
		String filepath = null;		
		PrintWriter out = null;
		FileInputStream inputStream = null;
		ServletOutputStream outStream = null;
		try 
		{
			requestMap = this.makeRequestMap(request);
			filepath = qrService.getfilepath(requestMap);
			filepath = serviceConfig.getRootPath() + filepath;
			File downfile = new File(filepath);
				inputStream = new FileInputStream(downfile);
				response.setContentType("application/octet-stream; charset=UTF-8");
		        response.setCharacterEncoding("utf-8");
		        response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode(downfile.getName(), "utf-8") + "\"");
		        
		        outStream = response.getOutputStream();
		        while(inputStream.read(outByte, 0, 4096) != -1)
		        {
		        	outStream.write(outByte, 0, 4096);
		        }
		        outByte = null;
				inputStream.close();
				outStream.flush();
				outStream.close();
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
				new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_FILEDOWN_ERROR + "/" + be.getMessage());
			}
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.QR_FILEDOWN_ERROR + "/" + be.getMessage());
		}
	}

	@RequestMapping("largemake")
	public ModelAndView largemake(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/pc/qr/largeMake");
	}

	@RequestMapping("template")
	public void template(HttpServletRequest request, HttpServletResponse response)
	{
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		byte[] outByte = new byte[4096];
		try 
		{
			String filepath = serviceConfig.getRootPath() + serviceConfig.getTemplatePath() + "template.xls";
			
			File downfile = new File(filepath);
	        outStream = response.getOutputStream();
	        inputStream = new FileInputStream(downfile);
	        response.setContentType("application/octet-stream; charset=UTF-8");
	        response.setCharacterEncoding("utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode("template.xls", "utf-8") + "\"");
	              
	        while(inputStream.read(outByte, 0, 4096) != -1)
	        {
	        	outStream.write(outByte, 0, 4096);
	        }
	        
	        outByte = null;
			inputStream.close();
		    outStream.flush();
		    outStream.close();
		}
		catch(Exception be) 
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.TEMPLATE_ERROR + "/" + be.getMessage());
		}
	}

	@RequestMapping("largegen")
	public void largegen(MultipartHttpServletRequest request , HttpServletResponse response){
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> paramMap = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			jsonObj = qrService.largegen(request, paramMap);
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM , MsgConstants.LARGEGEN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
		}
	}

	@RequestMapping("largedown")
	public void largedown(HttpServletRequest request , HttpServletResponse response) {
		HashMap<String, Object> paramMap = null;
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		byte[] outByte = new byte[4096];
		HashMap<String, Object> filepath = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			filepath = qrService.largedown(paramMap);
			File downfile = new File(filepath.get("zipFile").toString());
	        outStream = response.getOutputStream();
	        inputStream = new FileInputStream(downfile);
	        response.setContentType("application/octet-stream; charset=UTF-8");
	        response.setCharacterEncoding("utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode("QrImages.zip", "utf-8") + "\"");
	              
	        while(inputStream.read(outByte, 0, 4096) != -1)
	        {
	        	outStream.write(outByte, 0, 4096);
	        }

	        outByte = null;
			inputStream.close();
		    outStream.flush();
		    outStream.close();
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM , MsgConstants.LARGEDOWN_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;

			if (filepath != null) {
			    //서버에 압축파일 삭제
			    File f = new File(filepath.get("zipFile").toString());
			    f.delete();
			    f = null;
			    f = new File(filepath.get("excelFile").toString());
			    f.delete();
			    f = null;
			}
		}
	}
}
