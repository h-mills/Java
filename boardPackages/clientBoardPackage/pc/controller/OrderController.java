package project.pc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
import project.pc.service.OrderService;

@Controller
@RequestMapping("/pc/order/")
public class OrderController extends CommonController {

	@Autowired
	ServiceConfig serviceConfig;
	@Autowired
	OrderService Service;

	@RequestMapping("order")
	public ModelAndView order(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = null;

		try {
			String user_no = (String) request.getSession().getAttribute("USER_NO");
			String company_no = (String) request.getSession().getAttribute("COMPANY_NO");
			int isdept = Service.getdeptcount(company_no);
			String listcount = Service.getlistcount(user_no);
			String pageNum = WebUtil.getPageNum(request.getParameter("pagenum"), listcount,
					serviceConfig.getDefaultListViewSize());
			requestMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			requestMap.put("listSize", serviceConfig.getDefaultListViewSize());
			requestMap.put("user_no", (String) request.getSession().getAttribute("USER_NO"));
			list = Service.getorderlist(requestMap);
			result.put("isdept", isdept);
			result.put("list", list);
			result.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum,
					listcount, serviceConfig.getDefaultListViewSize(), null));
		} catch (Exception be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.ORDERLIST_ERROR + "/" + be.getMessage());
		} finally {

			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
		}

		return new ModelAndView("/pc/order/order", "data", result);
	}

	@RequestMapping("templete")
	public void templete(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			String filepath = serviceConfig.getTempletePath() + "templete.xls";

			File downfile = new File(filepath);
			if (downfile.exists()) {
				ServletOutputStream outStream = null;
				FileInputStream inputStream = null;
				byte[] outByte = new byte[4096];
				outStream = response.getOutputStream();
				inputStream = new FileInputStream(downfile);
				response.setContentType("application/octet-stream; charset=UTF-8");
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + URLEncoder.encode("templete.xls", "utf-8") + "\"");

				while (inputStream.read(outByte, 0, 4096) != -1) {
					outStream.write(outByte, 0, 4096);
				}
				outByte = null;
				inputStream.close();
				outStream.flush();
				outStream.close();
			} else {
				response.setContentType("text/html; charset=UTF-8");
				out = response.getWriter();
				out.println("<script>alert('다운로드 할 파일이 없습니다.');");
				out.println("history.back();</script>");
				out.flush();
			}
		} catch (Exception be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.TEMPLETEDOWNLOAD_ERROR + "/" + be.getMessage());
		}
	}

	@RequestMapping("orderregist")
	@HPNullCheck(parameters = { "pagenum" })
	public ModelAndView orderregist(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		List<HashMap<String,Object>> deptList = null;
		HashMap<String, Object> info = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			requestMap = this.makeRequestMap(request);
			requestMap.put("company_no", (String) request.getSession().getAttribute("COMPANY_NO"));
			requestMap.put("user_no", (String) request.getSession().getAttribute("USER_NO"));
			deptList = Service.getcompanyorgchart(requestMap);
			info = Service.orderregist(requestMap);
			if (info.get("tel") != null) {
				String tel = info.get("tel").toString();
				String[] telsub = tel.split("-");
				info.put("tel_first", telsub[0]);
				info.put("tel_middle", telsub[1]);
				info.put("tel_last", telsub[2]);
			}
			result.put("user_id", (String) request.getSession().getAttribute("USER_ID"));
			result.put("info", info);
			result.put("pagenum", requestMap.get("pagenum").toString());
			result.put("today", sdf.format(cal.getTime()));
			result.put("deptList", deptList);
		} catch (Exception be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.REGISTORDER_ERROR + "/" + be.getMessage());
		} finally {
			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
		}

		return new ModelAndView("/pc/order/orderregist", "data", result);
	}

	@RequestMapping("insertorder")
	public ModelAndView insertorder(MultipartHttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();

		try {
			int result = 0;
			result = Service.insertorder(request);
			Service.updateordercount(request);
			if (result == 1) {
				jsonObj.put("result", "1");
			} else {
				jsonObj.put("result", "0");
			}
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		} catch (Exception be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.REGISTORDERSUBMIT_ERROR + "/" + be.getMessage());
		} finally {
			jsonObj.clear();
			jsonObj = null;
		}

		return null;
	}

	@RequestMapping("orderdetail")
	@HPNullCheck(parameters = { "orderno", "pagenum" })
	public ModelAndView orderdetail(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		List<HashMap<String, Object>> list = null;
		List<HashMap<String,Object>> deptList = null;
		try {
			requestMap = this.makeRequestMap(request);
			requestMap.put("company_no", (String) request.getSession().getAttribute("COMPANY_NO"));
			deptList = Service.getorderorgchart(requestMap);
			info = Service.getorderinfo(requestMap);
			Service.updatereplyconfirm(requestMap);
			if (info.get("tel") != null) {
				String tel = info.get("tel").toString();
				String[] telsub = tel.split("-");
				info.put("tel_first", telsub[0]);
				info.put("tel_middle", telsub[1]);
				info.put("tel_last", telsub[2]);
			}
			if (info.get("language") != null) {
				int language = (int) info.get("language");
				if ((language & 0x08) > 0) {
					info.put("ko", "1");
				} else {
					info.put("ko", "0");
				}
				if ((language & 0x04) > 0) {
					info.put("en", "1");
				} else {
					info.put("en", "0");
				}
				if ((language & 0x02) > 0) {
					info.put("cn", "1");
				} else {
					info.put("cn", "0");
				}
				if ((language & 0x01) > 0) {
					info.put("jp", "1");
				} else {
					info.put("jp", "0");
				}
			}
			requestMap.put("ansdate", WebUtil.getDateTime());
			Service.updatereplyconfirm(requestMap);
			Service.updateorderconfirm(requestMap);
			list = Service.getreplylist(requestMap);
			result.put("info", info);
			result.put("list", list);
			result.put("pagenum", requestMap.get("pagenum").toString());
			result.put("user_id", (String) request.getSession().getAttribute("USER_ID"));
			result.put("deptList", deptList);
		} catch (BizException be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETORDERINFO_ERROR + "/" + be.getMessage());
		} finally {
			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
		}

		return new ModelAndView("/pc/order/orderdetail", "data", result);
	}

	@RequestMapping("exceldown")
	@HPNullCheck(parameters = { "file" })
	public void exceldown(HttpServletRequest request, HttpServletResponse response) throws BizException, IOException {
		HashMap<String, Object> requestMap = null;
		PrintWriter out = null;

		requestMap = this.makeRequestMap(request);

		if (requestMap != null) {
			ServletOutputStream outStream = null;
			FileInputStream inputStream = null;
			byte[] outByte = new byte[4096];
			try {
				String filepath = serviceConfig.getRootPath() + requestMap.get("file").toString();
				String filename = filepath.substring(filepath.lastIndexOf("/")+1, filepath.length());
				File downfile = new File(filepath);
				if (downfile.exists()) {
					outStream = response.getOutputStream();
					inputStream = new FileInputStream(downfile);
					response.setContentType("application/octet-stream; charset=UTF-8");
					response.setCharacterEncoding("utf-8");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + URLEncoder.encode(filename, "utf-8") + "\"");

					while (inputStream.read(outByte, 0, 4096) != -1) {
						outStream.write(outByte, 0, 4096);
					}

					outByte = null;
					inputStream.close();
					outStream.flush();
					outStream.close();
				} else {
					response.setContentType("text/html; charset=UTF-8");
					out = response.getWriter();
					out.println("<script>alert('다운로드 할 파일이 없습니다.');");
					out.println("history.back();</script>");
					out.flush();
				}
			} catch (Exception be) {
				new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.EXCELDOWNLOAD_ERROR + "/" + be.getMessage());
			} finally {
				if (requestMap != null) {
					requestMap.clear();
					requestMap = null;
				}
			}
		}
	}

	@RequestMapping("updateorder")
	public ModelAndView updateorder(MultipartHttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();

		try {
			int result = 0;
			result = Service.updateorder(request);
			if (result == 1) {
				jsonObj.put("result", "1");
			} else {
				jsonObj.put("result", "0");
			}
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		} catch (Exception be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.REGISTORDERSUBMIT_ERROR + "/" + be.getMessage());
		} finally {
			jsonObj.clear();
			jsonObj = null;
		}

		return null;
	}

	@RequestMapping("cancelorder")
	public ModelAndView cancelorder(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();

		try {
			int result = 0;
			result = Service.cancelorder(request.getParameter("orderno"));
			if (result == 1) {
				jsonObj.put("result", "1");
			} else {
				jsonObj.put("result", "0");
			}
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		} catch (Exception be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.REGISTORDERSUBMIT_ERROR + "/" + be.getMessage());
		} finally {
			jsonObj.clear();
			jsonObj = null;
		}

		return null;
	}

	@RequestMapping("replysubmit")
	@HPNullCheck(parameters = { "orderno", "content" })
	public void replysubmit(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> result = null;

		try {
			requestMap = this.makeRequestMap(request);
			result = Service.insertreply(requestMap);
			Service.updateordernew(requestMap);
			if (result != null) {
				jsonObj.put("result", "1");
				jsonObj.put("isuser", result.get("isuser").toString());
				jsonObj.put("content", result.get("content").toString());
				jsonObj.put("regdate", result.get("regdate").toString());
			} else {
				jsonObj.put("result", "0");
			}
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		} catch (Exception be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.INSERTREPLY_ERROR + "/" + be.getMessage());
		} finally {
			jsonObj.clear();
			jsonObj = null;
			if (result != null) {
				result.clear();
				result = null;
			}
		}
	}
	@RequestMapping("xlsupdate")
	@HPNullCheck(parameters = {"updatefile", "cd", "orderno"})
	public ModelAndView xlsupdate (MultipartHttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObj = new JSONObject();
		try {
			HashMap<String, Object> result = new HashMap<>();
			result = Service.xlsupdate(request);
			if (result != null) {
				jsonObj.put("result", "1");
				jsonObj.put("realpath", result.get("realpath"));
			} else {
				jsonObj.put("result", "0");
			}
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		} catch (Exception be) {
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.REGISTORDERSUBMIT_ERROR + "/" + be.getMessage());
		} finally {
			jsonObj.clear();
			jsonObj = null;
		}
		return null;
	}
	@RequestMapping("jusopop")
	public ModelAndView jusopop(HttpServletRequest request, HttpServletResponse response){
		try{
			return new ModelAndView("/pc/address/jusoPopup");
		}catch(Exception e){
			return null;
		}
	}
	@RequestMapping("dsfiledown")
	@HPNullCheck(parameters = { "file" })
	public void dsfiledown(HttpServletRequest request, HttpServletResponse response) throws BizException, IOException {
		HashMap<String, Object> requestMap = null;
		PrintWriter out = null;

		requestMap = this.makeRequestMap(request);

		if (requestMap != null) {
			ServletOutputStream outStream = null;
			FileInputStream inputStream = null;
			byte[] outByte = new byte[4096];
			try {
				String filepath = serviceConfig.getRootPath() + requestMap.get("file").toString();
				String filename = filepath.substring(filepath.lastIndexOf("/")+1, filepath.length());
				File downfile = new File(filepath);
				if (downfile.exists()) {
					outStream = response.getOutputStream();
					inputStream = new FileInputStream(downfile);
					response.setContentType("application/octet-stream; charset=UTF-8");
					response.setCharacterEncoding("utf-8");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + URLEncoder.encode(filename, "utf-8") + "\"");

					while (inputStream.read(outByte, 0, 4096) != -1) {
						outStream.write(outByte, 0, 4096);
					}

					outByte = null;
					inputStream.close();
					outStream.flush();
					outStream.close();
				} else {
					response.setContentType("text/html; charset=UTF-8");
					out = response.getWriter();
					out.println("<script>alert('다운로드 할 파일이 없습니다.');");
					out.println("history.back();</script>");
					out.flush();
				}
			} catch (Exception be) {
				new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.EXCELDOWNLOAD_ERROR + "/" + be.getMessage());
			} finally {
				if (requestMap != null) {
					requestMap.clear();
					requestMap = null;
				}
			}
		}
	}
}
