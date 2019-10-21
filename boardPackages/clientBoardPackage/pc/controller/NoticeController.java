package project.pc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import project.pc.service.noticeService;

@Controller
@RequestMapping("/pc/notice/")
public class NoticeController extends CommonController {

	@Autowired
	ServiceConfig serviceConfig;
	@Autowired
	noticeService Service;

	/**
	 * MYFEED VER 1.0 泥섏쓬 �떎�뻾�릺�뒗 �솕硫�
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("notice")
	public ModelAndView notice(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = null;

		try {
			String listcount = Service.getlistcount();
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount,
					serviceConfig.getDefaultListViewSize());
			requestMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			requestMap.put("listSize", serviceConfig.getDefaultListViewSize());
			requestMap.put("user_no", (String) request.getSession().getAttribute("USER_NO"));
			list = Service.getlist(requestMap);
			int start = Integer.parseInt(listcount)
					- (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize());
			result.put("startno", start);
			result.put("list", list);
			result.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum,
					listcount, serviceConfig.getDefaultListViewSize(), null));
		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR + "/" + be.getMessage());
		} finally {

			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
		}

		return new ModelAndView("/pc/notice/notice", "data", result);
	}

	@RequestMapping("noticedetail")
	@HPNullCheck(parameters = { "no" })
	public ModelAndView noticedetail(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		String file = null;
		try {
			requestMap = this.makeRequestMap(request);
			Service.updatecount(requestMap);
			info = Service.getinfo(requestMap);
			if (info.get("filepath") != null) {
				file = info.get("filepath").toString();
				file = file.substring(file.lastIndexOf("/") + 1, file.length());
			}
			result.put("file", file);
			result.put("info", info);
		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICEINFO_ERROR + "/" + be.getMessage());
		} finally {

			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
		}

		return new ModelAndView("/pc/notice/noticedetail", "data", result);
	}

	@RequestMapping("filedown")
	@HPNullCheck(parameters = { "no" })
	public void filedown(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, Object> requestMap = null;
		PrintWriter out = null;
		try {
			String filepath = null;
			requestMap = this.makeRequestMap(request);
			filepath = Service.getfilepath(requestMap);
			filepath = serviceConfig.getNoticePath() + filepath;
			File downfile = new File(filepath);
			if (downfile.exists()) {
				byte[] outByte = new byte[4096];
				ServletOutputStream outStream = null;
				FileInputStream inputStream = null;
				outStream = response.getOutputStream();
				inputStream = new FileInputStream(downfile);
				response.setContentType("application/octet-stream; charset=UTF-8");
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + URLEncoder.encode(downfile.getName(), "utf-8") + "\"");

				while (inputStream.read(outByte, 0, 4096) != -1) {
					outStream.write(outByte, 0, 4096);
				}

				outByte = null;
				inputStream.close();
				outStream.flush();
				outStream.close();
			} else{
				response.setContentType("text/html; charset=UTF-8");
				out = response.getWriter();
				out.println("<script>alert('다운로드 할 파일이 없습니다.');");
				out.println("history.back();</script>");
				out.flush();
			}
		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICEDOWN_ERROR + "/" + be.getMessage());
		}
	}
}
