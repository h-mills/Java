package project.pc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.springframework.web.servlet.ModelAndView;

import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.common.util.WebUtil;
import project.pc.model.orderModel;
import project.pc.model.replyModel;
import project.pc.service.OrderService;

@Controller
@RequestMapping("/pc/order/")
public class OrderController extends CommonController{
	@Autowired ServiceConfig serviceConfig;
	@Autowired OrderService orderService;
	
	@RequestMapping("main")
	@HPNullCheck(parameters={"pageNum"})
	public ModelAndView orderMain(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/order/main");
		String optNum = request.getParameter("optNum");
		String searchType = request.getParameter("searchType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String keyword = request.getParameter("keyword");
		int status = 0;
		String[] option = {"주문","접수","제작중","배송중","완료","취소"};
		List<orderModel> list = null;
		Map<String, Object> paramMap = null;

		try 
		{
			paramMap = this.makeRequestMap(request);
		} 
		catch(BizException be) 
		{
			new BizException(be.getErrCode(), be.getMessage());
		}
		try
		{
			if(optNum == null)
				optNum = "6";
			status = Integer.parseInt(optNum);
			paramMap.put("status", status);
			paramMap.put("searchType", searchType);

			String listcount = orderService.getOrderCnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			list = orderService.getOrderList(paramMap);

			mav.addObject("keyword",keyword);
			mav.addObject("startDate",startDate);
			mav.addObject("endDate",endDate);
			mav.addObject("searchType",searchType);
			mav.addObject("checkNum",optNum);
			mav.addObject("option",option);
			mav.addObject("paramMap",paramMap);
			mav.addObject("status",status);
			mav.addObject("list",list);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
		}
		catch(Exception be)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.ORDER_MAIN_ERROR + "/" + be.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}
	@RequestMapping("detail")
	public ModelAndView detail(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		int no = Integer.parseInt(request.getParameter("no"));
		orderModel orderModel = new orderModel();
		List<replyModel> replyList = null;
		String showPath = "";
		Map<String, Object> paramMap = null;
		mav.setViewName("/pc/order/detail");

		try
		{
			paramMap = this.makeRequestMap(request);
			orderService.updateReplyConfirm(no);
			orderService.updateClientNew(no);
			orderModel = orderService.getDetailInfo(no);
			replyList = orderService.getReplyInfo(no);
			mav.addObject("searchType",paramMap.get("searchType"));
			mav.addObject("endDate",paramMap.get("endDate"));
			mav.addObject("startDate",paramMap.get("startDate"));
			mav.addObject("optNum",paramMap.get("optNum"));
			mav.addObject("keyword",paramMap.get("keyword"));
			mav.addObject("pageNum",paramMap.get("pageNum"));
			mav.addObject("orderModel",orderModel);
			mav.addObject("replyList",replyList);
		}
		catch(Exception be)
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.ORDER_DETAIL_ERROR + "/" + be.getMessage());
		}
		return mav;
	}
	@RequestMapping("mod")
	public ModelAndView mod(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		int no = Integer.parseInt(request.getParameter("no"));
		orderModel orderModel = new orderModel();
		List<replyModel> replyList = null;
		String showPath = "";
		mav.setViewName("/pc/order/mod");
		try{
			replyList = orderService.getReplyInfo(no);
			orderModel = orderService.getDetailInfo(no);
			mav.addObject("replyList",replyList);
			mav.addObject("orderModel",orderModel);
		}catch(Exception be){
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.ORDER_MOD_ERROR + "/" + be.getMessage());
		}
		return mav;
	}
	@RequestMapping("modOk")
	public ModelAndView modOk(HttpServletRequest request , HttpServletResponse response) {
		orderModel orderModel = new orderModel();
		int no = Integer.parseInt(request.getParameter("no"));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pc/order/detail?no="+no);
		try{
			request.setCharacterEncoding("UTF-8");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String recvdate = request.getParameter("startDate");
			short status = Short.parseShort(request.getParameter("status"));
			orderModel.setNo(no);
			orderModel.setTel(tel);
			orderModel.setEmail(email);
			orderModel.setAddress(address);
			orderModel.setRecvdate(recvdate);
			orderModel.setStatus(status);
			orderService.updateReplyConfirm(no);
			orderService.updateOrder(orderModel);
		}catch(Exception be){
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.ORDER_MODOK_ERROR + "/" + be.getMessage());
		}
		return mav;
	}
	@RequestMapping("cancel")
	public ModelAndView cancel(HttpServletRequest request , HttpServletResponse response){
		int no = 0;
		try{
			no = Integer.parseInt(request.getParameter("no"));
			orderService.updateToCancleOrder(no);
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('주문이 취소 되었습니다.');");
			out.println("window.close(); </script>");
			out.flush();
		}catch(Exception be){
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.ORDER_CANCEL_ERROR + "/" + be.getMessage());
		}
		return null;
	}
	@RequestMapping("reply")
	public ModelAndView reply(HttpServletRequest request , HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		int orderno = 0;
		String content = "";
		replyModel replyModel = new replyModel();
		try{
			orderno = Integer.parseInt(request.getParameter("orderno"));
			content = request.getParameter("reply");
			replyModel.setOrderno(orderno);
			replyModel.setContent(content);
			orderService.insertReply(replyModel);
			orderService.updateNewCount(orderno);
			orderService.updateReplyConfirm(orderno);
			mav.setViewName("redirect:/pc/order/detail?no="+orderno);
		}catch(Exception be){
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.ORDER_REPLY_ERROR + "/" + be.getMessage());
		}
		return mav;
	}
	@RequestMapping("download")
	public void download(HttpServletRequest request , HttpServletResponse response) throws IOException{
		Map<String, Object> paramMap = null;
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		byte[] outByte = new byte[4096];
		HashMap<String, Object> filepath = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			filepath = orderService.xlsdowntozip(paramMap);
			if(filepath != null && filepath.get("zipFile") != null){
				File downfile = new File(filepath.get("zipFile").toString());
	        	outStream = response.getOutputStream();
	        	inputStream = new FileInputStream(downfile);
	        	response.setContentType("application/octet-stream; charset=UTF-8");
	        	response.setCharacterEncoding("utf-8");
	        	response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode("namecard.zip", "utf-8") + "\"");
	              
	        	while(inputStream.read(outByte, 0, 4096) != -1)
	        	{
	        		outStream.write(outByte, 0, 4096);
	        	}
	        	outByte = null;
				inputStream.close();
		    	outStream.flush();
		    	outStream.close();
			}else {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html; charset=UTF-8");
				out = response.getWriter();
				out.println("<script>alert('다운로드 할 파일이 없습니다.');");
				out.println("history.back(); </script>");
				out.flush();
				out.close();
				out = null;
			}
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.EXCELDOWN_ERROR , MsgConstants.EXCELDOWN_ERROR + "/" + e.getMessage());
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
			}
		}
	}
	@RequestMapping("jusopop")
	public ModelAndView jusopop (HttpServletRequest request, HttpServletResponse response){
		try{
			return new ModelAndView("/pc/address/jusoPopup");
		}catch(Exception e){
			return null;
		}
	}
	@RequestMapping("dsfiledown")
	@HPNullCheck(parameters={"path"})
	public void dsfiledown(HttpServletRequest request, HttpServletResponse response){
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		byte[] outByte = new byte[4096];
		try{
			String dsfilepath = request.getParameter("path");
			if(dsfilepath != null){
				String dsfilename = dsfilepath.substring(dsfilepath.lastIndexOf("/")+1,dsfilepath.length());
				dsfilepath = serviceConfig.getRootPath() + dsfilepath;
				File downfile = new File(dsfilepath);
	        	outStream = response.getOutputStream();
	        	inputStream = new FileInputStream(downfile);
	        	response.setContentType("application/octet-stream; charset=UTF-8");
	        	response.setCharacterEncoding("utf-8");
	        	response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode(dsfilename, "utf-8") + "\"");
	        	while(inputStream.read(outByte, 0, 4096) != -1)
	        	{
	        		outStream.write(outByte, 0, 4096);
	        	}
	        	outByte = null;
				inputStream.close();
		    	outStream.flush();
		    	outStream.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
