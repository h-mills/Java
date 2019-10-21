package project.pc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.common.controller.CommonController;
import project.common.util.StringUtil;
import project.common.util.WebUtil;
import project.pc.model.noticeModel;
import project.pc.service.NoticeService;

@Controller
@RequestMapping("/pc/notice/")
public class NoticeController extends CommonController {
	@Autowired
	ServiceConfig serviceConfig;
	@Autowired
	NoticeService noticeService;

	@RequestMapping("noticelist")
	@HPNullCheck(parameters = { "pageNum" })
	public ModelAndView noticeList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pc/notice/noticelist");
		Map<String, Object> paramMap = null;
		List<noticeModel> noticeList = null;
		try {
			paramMap = this.makeRequestMap(request);
		} catch (BizException be) {
			new BizException(be.getErrCode(), be.getMessage());
		}
		try {
			String listcount = noticeService.getNoticeList_Cnt();
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount,
					serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());
			noticeList = noticeService.getNoticeList(paramMap);
			mav.addObject("noticeList", noticeList);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum,
					listcount, serviceConfig.getDefaultListViewSize(), null));

		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICE_MAIN_ERROR + "/" + be.getMessage());
		} finally {
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("notice_add")
	public ModelAndView noticeAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/notice/notice_add");
		try {
			String pageNum = StringUtil.replaceNull(request.getParameter("pageNum"), "0");
			mav.addObject("pageNum", pageNum);
		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICE_INSERT_ERROR + "/" + be.getMessage());
		}
		return mav;
	}

	@RequestMapping("insert")
	public ModelAndView noticeInsert(MultipartHttpServletRequest request, HttpServletResponse response) {
		MultipartFile file = request.getFile("file");
		Map<String, Object> paramMap = null;
		String filePath = serviceConfig.getNoticePath() + "/" + WebUtil.getDate();
		String imagepath = null;
		try {
			if (file.isEmpty() == false) {
				imagepath = WebUtil.getDate();
				String fileName = file.getOriginalFilename();
				response.setCharacterEncoding("utf-8");
				File f = new File(filePath);
				if (f.exists() == false) {
					f.mkdirs();
				}
				f = null;
				f = new File(filePath + "/" + fileName);
				file.transferTo(f);
				f = null;
				imagepath = imagepath + "/" + fileName;
			}
			paramMap = this.makeRequestMap(request);
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			paramMap.put("title", title);
			paramMap.put("content", content);
			paramMap.put("filepath", imagepath);
			noticeService.insertNotice(paramMap);

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('등록 되었습니다.');</script>");
			out.flush();
		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICE_INSERT_ERROR + "/" + be.getMessage());
		} finally {
			paramMap.clear();
			paramMap = null;
		}
		return new ModelAndView("/pc/notice/notice_add");
	}
	@RequestMapping("mod")
	public ModelAndView noticeMod(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/notice/noticeMod");
		noticeModel model = new noticeModel();
		String showPath = "";
		String pageNum = StringUtil.replaceNull(request.getParameter("pageNum"), "0");
		int no = Integer.parseInt(request.getParameter("no"));
		try {
			model = noticeService.getDetailData(no);
			showPath = model.getFilepath();
			if(showPath != null)
				showPath = showPath.substring(showPath.lastIndexOf("/") + 1, showPath.length());
			mav.addObject("model", model);
			mav.addObject("showPath", showPath);
			mav.addObject("pageNum", pageNum);
		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICE_MOD_ERROR + "/" + be.getMessage());
		}
		return mav;
	}

	@RequestMapping("modOk")
	public ModelAndView noticeModOk(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		noticeModel model = new noticeModel();
		int no = Integer.parseInt(request.getParameter("no"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		model.setNo(no);
		model.setTitle(title);
		model.setContent(content);
		mav.setViewName("redirect:/pc/notice/mod?no="+no);
		try {
			noticeService.updateNotice(model);
		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICE_MODOK_ERROR + "/" + be.getMessage());
		}
		return mav;
	}

	@RequestMapping("del")
	public ModelAndView del(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pc/notice/noticelist");
		try {
			String[] no = request.getParameterValues("box");
			noticeService.deleteNotice(no);
			no = null;
		} catch (Exception be) {
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICE_DELETE_ERROR + "/" + be.getMessage());
		}
		return mav;
	}

	@RequestMapping("download")
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) {
		String no = request.getParameter("no");
		String noticePath = serviceConfig.getNoticePath();
		PrintWriter out = null;

		if (no != null) {
			ServletOutputStream outStream = null;
			FileInputStream inputStream = null;
			byte[] outByte = new byte[4096];
			String filepath = null;

			try {
				filepath = noticeService.getNoticeFile(no);
				
				if (filepath != null) {
					filepath = noticePath + filepath;
					File downfile = new File(filepath);
					inputStream = new FileInputStream(downfile);

					String name = downfile.getName();
					name = WebUtil.getDisposition(name, WebUtil.getBrowser(request));

					response.setContentType("application/octet-stream; charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + name + "\";");
					response.setHeader("Content-Transfer-Encoding", "binary");
					outStream = response.getOutputStream();
					while (inputStream.read(outByte, 0, 4096) != -1) {
						outStream.write(outByte, 0, 4096);
					}
					outByte = null;
					inputStream.close();
					outStream.flush();
					outStream.close();
				}
			} catch (Exception be) {
				try{
					response.setContentType("text/html; charset=UTF-8");
					out = response.getWriter();
					out.println("<script>alert('다운로드 할 파일이 없습니다.');");
					out.println("window.close(); </script>");
					out.flush();
				}catch(Exception e){
					new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICE_DOWNLOAD_ERROR + "/" + e.getMessage());
				}
				new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.NOTICE_DOWNLOAD_ERROR + "/" + be.getMessage());
			}
		}
		return null;
	}
}
