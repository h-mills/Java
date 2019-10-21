package project.pc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.extentech.ExtenXLS.ImageHandle;
import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import project.common.annotation.HPNullCheck;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.common.util.FileUtil;
import project.common.util.WebUtil;
import project.pc.model.cardStatsModel;
import project.pc.model.companyModel;
import project.pc.model.orderModel;
import project.pc.service.CardService;
import project.pc.service.DeptService;
import project.pc.service.IndustryService;

@Controller
@RequestMapping("/pc/card/")
public class CardController extends CommonController
{

	@Autowired
	ServiceConfig serviceConfig;
	@Autowired
	CardService cardService;
	@Autowired
	IndustryService industryService;
	@Autowired
	DeptService deptService;

	@RequestMapping("card_view")
	@HPNullCheck(parameters = { "pageNum" })
	public ModelAndView card_view(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_view");
		Map<String, Object> paramMap = null;
		List<HashMap<String, Object>> cardList = null;
		List<HashMap<String, Object>> industryList = null;
		List<HashMap<String, Object>> deptList = null;
		String companyPath = serviceConfig.getCompanyPath();
		try
		{
			paramMap = this.makeRequestMap(request);

			String listcount = cardService.getCardViewList_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());

			cardList = cardService.getCardViewList(paramMap);
			if (cardList != null)
			{
				for (int i = 0; i < cardList.size(); i++)
				{
					int language = Integer.parseInt(cardList.get(i).get("language").toString());
					String first_lang = "";
					int lang_count = 0;

					if ((language & 0x08) > 0)
					{
						cardList.get(i).put("ko_lang", "1");
						first_lang = (first_lang.equals("") ? "ko" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("ko_lang", "0");
					}
					if ((language & 0x04) > 0)
					{
						cardList.get(i).put("en_lang", "1");
						first_lang = (first_lang.equals("") ? "en" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("en_lang", "0");
					}
					if ((language & 0x02) > 0)
					{
						cardList.get(i).put("cn_lang", "1");
						first_lang = (first_lang.equals("") ? "cn" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("cn_lang", "0");
					}
					if ((language & 0x01) > 0)
					{
						cardList.get(i).put("jp_lang", "1");
						first_lang = (first_lang.equals("") ? "jp" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("jp_lang", "0");
					}

					cardList.get(i).put("lang_count", lang_count);
					cardList.get(i).put("first_lang", first_lang);
					cardList.get(i).put("landinginfo", serviceConfig.getLandingInfo());
					cardList.get(i).put("imageViewPath", "/" + companyPath + cardList.get(i).get("image"));
				}
			}

			industryList = industryService.getIndustryListST();
			deptList = deptService.getDeptListST();

			mav.addObject("industryList", industryList);
			mav.addObject("deptList", deptList);
			mav.addObject("listcount", listcount);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			mav.addObject("cardList", cardList);
			mav.addObject("st_company", paramMap.get("st_company"));
			mav.addObject("st_order", paramMap.get("st_order"));
			mav.addObject("rd_isdelete", paramMap.get("rd_isdelete"));
			mav.addObject("search", "1");
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_VIEW_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("card_view2")
	@HPNullCheck(parameters = { "pageNum" })
	public ModelAndView card_view2(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_view");
		Map<String, Object> paramMap = null;
		List<HashMap<String, Object>> cardList = null;
		List<HashMap<String, Object>> industryList = null;
		List<HashMap<String, Object>> deptList = null;
		String companyPath = serviceConfig.getCompanyPath();
		try
		{
			paramMap = this.makeRequestMap(request);

			String[] st_industry = request.getParameterValues("st_industry");
			paramMap = this.makeRequestMap(request);
			if (st_industry != null)
			{
				paramMap.put("st_industry", st_industry);
			}

			String listcount = cardService.getCardViewList_Cnt2(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());

			cardList = cardService.getCardViewList2(paramMap);
			if (cardList != null)
			{
				for (int i = 0; i < cardList.size(); i++)
				{
					int language = Integer.parseInt(cardList.get(i).get("language").toString());
					String first_lang = "";
					int lang_count = 0;

					if ((language & 0x08) > 0)
					{
						cardList.get(i).put("ko_lang", "1");
						first_lang = (first_lang.equals("") ? "ko" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("ko_lang", "0");
					}
					if ((language & 0x04) > 0)
					{
						cardList.get(i).put("en_lang", "1");
						first_lang = (first_lang.equals("") ? "en" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("en_lang", "0");
					}
					if ((language & 0x02) > 0)
					{
						cardList.get(i).put("cn_lang", "1");
						first_lang = (first_lang.equals("") ? "cn" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("cn_lang", "0");
					}
					if ((language & 0x01) > 0)
					{
						cardList.get(i).put("jp_lang", "1");
						first_lang = (first_lang.equals("") ? "jp" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("jp_lang", "0");
					}

					cardList.get(i).put("lang_count", lang_count);
					cardList.get(i).put("first_lang", first_lang);
					cardList.get(i).put("landinginfo", serviceConfig.getLandingInfo());
					cardList.get(i).put("imageViewPath", "/" + companyPath + cardList.get(i).get("image"));
				}
			}

			industryList = industryService.getIndustryListST();
			if (st_industry != null && industryList != null)
			{
				for (int i = 0; i < industryList.size(); i++)
				{
					for (int j = 0; j < st_industry.length; j++)
					{
						if (industryList.get(i).get("cd").toString().equals(st_industry[j]))
						{
							industryList.get(i).put("select", 1);
						}
					}
				}
			}

			deptList = deptService.getDeptListST();

			mav.addObject("listcount", listcount);
			mav.addObject("industryList", industryList);
			mav.addObject("deptList", deptList);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			mav.addObject("cardList", cardList);
			mav.addObject("st_category", null);
			mav.addObject("st_company", null);
			mav.addObject("st_order", null);
			mav.addObject("st_dept", paramMap.get("st_dept"));
			mav.addObject("rd_isdelete", paramMap.get("rd_isdelete"));
			mav.addObject("search", "2");
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_VIEW2_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("card_view3")
	@HPNullCheck(parameters = { "pageNum" })
	public ModelAndView card_view3(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_view");
		Map<String, Object> paramMap = null;
		List<HashMap<String, Object>> cardList = null;
		List<HashMap<String, Object>> industryList = null;
		List<HashMap<String, Object>> deptList = null;
		String companyPath = serviceConfig.getCompanyPath();
		try
		{
			paramMap = this.makeRequestMap(request);

			String listcount = cardService.getCardViewList_Cnt3(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());

			cardList = cardService.getCardViewList3(paramMap);
			if (cardList != null)
			{
				for (int i = 0; i < cardList.size(); i++)
				{
					int language = Integer.parseInt(cardList.get(i).get("language").toString());
					String first_lang = "";
					int lang_count = 0;

					if ((language & 0x08) > 0)
					{
						cardList.get(i).put("ko_lang", "1");
						first_lang = (first_lang.equals("") ? "ko" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("ko_lang", "0");
					}
					if ((language & 0x04) > 0)
					{
						cardList.get(i).put("en_lang", "1");
						first_lang = (first_lang.equals("") ? "en" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("en_lang", "0");
					}
					if ((language & 0x02) > 0)
					{
						cardList.get(i).put("cn_lang", "1");
						first_lang = (first_lang.equals("") ? "cn" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("cn_lang", "0");
					}
					if ((language & 0x01) > 0)
					{
						cardList.get(i).put("jp_lang", "1");
						first_lang = (first_lang.equals("") ? "jp" : first_lang);
						++lang_count;
					}
					else
					{
						cardList.get(i).put("jp_lang", "0");
					}

					cardList.get(i).put("lang_count", lang_count);
					cardList.get(i).put("first_lang", first_lang);
					cardList.get(i).put("landinginfo", serviceConfig.getLandingInfo());
					cardList.get(i).put("imageViewPath", "/" + companyPath + cardList.get(i).get("image"));
				}
			}
			
			industryList = industryService.getIndustryListST();
			deptList = deptService.getDeptListST();

			mav.addObject("industryList", industryList);
			mav.addObject("deptList", deptList);
			mav.addObject("listcount", listcount);
			mav.addObject("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
			mav.addObject("cardList", cardList);
			mav.addObject("st_category", null);
			mav.addObject("st_company", null);
			mav.addObject("st_order", null);
			mav.addObject("st_dept", paramMap.get("st_dept"));
			mav.addObject("rd_isdelete", paramMap.get("rd_isdelete"));
			mav.addObject("search", "3");
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_VIEW3_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("card_make")
	public ModelAndView card_make(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_make");
		Map<String, Object> paramMap = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			mav.addObject("st_company", paramMap.get("st_company"));
			mav.addObject("st_order", paramMap.get("st_order"));
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_MAKE_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("companylist")
	public ModelAndView card_companylist(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		List<companyModel> companyList = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			companyList = cardService.getCardCompanyList(paramMap);

			if (companyList.size() <= 0)
			{
				jsonObj.put("result", "0");
			}
			else
			{
				jsonObj.put("result", "1");
				jsonObj.put("companyList", companyList);
			}

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_COMPANYLIST_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
			companyList.clear();
			companyList = null;
			paramMap.clear();
			paramMap = null;
		}

		return null;
	}

	@RequestMapping("orderlist")
	public ModelAndView card_orderlist(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		List<orderModel> orderList = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			orderList = cardService.getCardOrderList(paramMap);

			if (orderList.size() <= 0)
			{
				jsonObj.put("result", "0");
			}
			else
			{
				jsonObj.put("result", "1");
				jsonObj.put("orderList", orderList);
			}

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_ORDERLIST_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
			orderList.clear();
			orderList = null;
			paramMap.clear();
			paramMap = null;
		}
		return null;
	}

	@RequestMapping("orderlist_make")
	public ModelAndView card_orderlist_make(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		List<orderModel> orderList = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			orderList = cardService.getCardOrderListMake(paramMap);

			if (orderList.size() <= 0)
			{
				jsonObj.put("result", "0");
			}
			else
			{
				jsonObj.put("result", "1");
				jsonObj.put("orderList", orderList);
			}

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_ORDERLIST_MAKE_ERROR + "/" + e.getMessage());
		}
		finally
		{
			jsonObj.clear();
			jsonObj = null;
			orderList.clear();
			orderList = null;
			paramMap.clear();
			paramMap = null;
		}
		return null;
	}

	@RequestMapping("del")
	public ModelAndView isdelte_del(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pc/card/card_view");
		HashMap<String, Object> noMap = null;

		try
		{
			String[] list = request.getParameterValues("box");
			if (list != null & list.length > 0) {
				for (int i=0; i<list.length; i++) {
					noMap = new HashMap<>();
					noMap.put("no", list[i].split(":")[0]);
					noMap.put("category", list[i].split(":")[1]);
					cardService.updateIsdelte(noMap);
				}
			}

			mav.addObject("st_category", request.getParameter("st_category"));
			mav.addObject("st_order", request.getParameter("st_order"));
			mav.addObject("st_company", request.getParameter("st_company"));
			mav.addObject("rd_isdelete", request.getParameter("rd_isdelete"));
			mav.addObject("listSize", request.getParameter("listSize"));
			mav.addObject("category", request.getParameter("category"));
			mav.addObject("pageNum", request.getParameter("pageNum"));
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.ISDELTE_DEL_ERROR + "/" + e.getMessage());
		}
		finally
		{
			noMap.clear();
			noMap = null;
		}
		return mav;
	}

	@RequestMapping("normal")
	public ModelAndView isdelte_normal(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pc/card/card_view");
		HashMap<String, Object> noMap = null;

		try
		{
			String[] list = request.getParameterValues("box");
			if (list != null & list.length > 0) {
				for (int i=0; i<list.length; i++) {
					noMap = new HashMap<>();
					noMap.put("no", list[i].split(":")[0]);
					noMap.put("category", list[i].split(":")[1]);
					cardService.updateIsdelte_normal(noMap);
				}
			}

			mav.addObject("st_category", request.getParameter("st_category"));
			mav.addObject("st_order", request.getParameter("st_order"));
			mav.addObject("st_company", request.getParameter("st_company"));
			mav.addObject("rd_isdelete", request.getParameter("rd_isdelete"));
			mav.addObject("listSize", request.getParameter("listSize"));
			mav.addObject("category", request.getParameter("category"));
			mav.addObject("pageNum", request.getParameter("pageNum"));
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.ISDELTE_NORMAL_ERROR + "/" + e.getMessage());
		}
		finally
		{
			noMap.clear();
			noMap = null;
		}
		return mav;
	}

	@RequestMapping("ordersearch")
	public ModelAndView ordersearch(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_make");
		Map<String, Object> paramMap = null;
		Map<String, Object> orderMap = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			orderMap = cardService.getOrderData(paramMap);

			if (orderMap.get("language") != null)
			{
				int language = (int) orderMap.get("language");
				if ((language & 0x08) > 0)
				{
					orderMap.put("ko", "1");
				}
				else
				{
					orderMap.put("ko", "0");
				}
				if ((language & 0x04) > 0)
				{
					orderMap.put("en", "1");
				}
				else
				{
					orderMap.put("en", "0");
				}
				if ((language & 0x02) > 0)
				{
					orderMap.put("cn", "1");
				}
				else
				{
					orderMap.put("cn", "0");
				}
				if ((language & 0x01) > 0)
				{
					orderMap.put("jp", "1");
				}
				else
				{
					orderMap.put("jp", "0");
				}
			}

			if (orderMap.get("status") != null)
			{
				int status = (int) orderMap.get("status");
				if (status == 0)
				{
					orderMap.put("status_view", "주문");
				}
				if (status == 1)
				{
					orderMap.put("status_view", "접수");
				}
				if (status == 2)
				{
					orderMap.put("status_view", "제작");
				}
				if (status == 3)
				{
					orderMap.put("status_view", "배송");
				}
				if (status == 4)
				{
					orderMap.put("status_view", "완료");
				}
				if (status == 5)
				{
					orderMap.put("status_view", "취소");
				}
			}

			if (orderMap.get("company_no") != null && orderMap.get("orderth") != null)
			{
				orderMap.put("url", "/landing/" + orderMap.get("company_no") + "/" + orderMap.get("orderth") + "/landing");
			}

			mav.addObject("orderMap", orderMap);
			mav.addObject("st_category", paramMap.get("st_category"));
			mav.addObject("st_company", paramMap.get("st_company"));
			mav.addObject("st_order", paramMap.get("st_order"));
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.ORDERSEARCH_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("filedown")
	@HPNullCheck(parameters = { "order_no" })
	public void excelfiledown(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> paramMap = null;
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		byte[] outByte = new byte[4096];
		HashMap<String, Object> filepath = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			filepath = cardService.cardxlsdowntozip(paramMap);
			File downfile = new File(filepath.get("zipFile").toString());
	        outStream = response.getOutputStream();
	        inputStream = new FileInputStream(downfile);
	        response.setContentType("application/octet-stream; charset=UTF-8");
	        response.setCharacterEncoding("utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode("orderxlses.zip", "utf-8") + "\"");
	              
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
			new BizException(MsgConstants.CARD_SYSTEM , MsgConstants.EXCELDOWN_ERROR + "/" + e.getMessage());
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

	@RequestMapping("orderdata")
	public void orderdata(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> paramMap = null;
		JSONObject jsonObj = new JSONObject();
		List<HashMap<String, Object>> orderdatalist = new ArrayList<>();
		try
		{
			paramMap = this.makeRequestMap(request);

			orderdatalist = cardService.getOrderDataList(paramMap);
			jsonObj.put("orderdatalist", orderdatalist);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARDCREATE_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
			orderdatalist.clear();
			orderdatalist = null;
			jsonObj.clear();
			jsonObj = null;
		}
	}

	@RequestMapping("create")
	public ModelAndView cardcreate(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_make");
		JSONObject jsonObj = new JSONObject();
		JSONArray cardList = new JSONArray();
		JSONObject ListScan = new JSONObject();
		Map<String, Object> paramMap = null;
		WorkBookHandle tbo = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			int company_no = Integer.parseInt(request.getParameter("company_no"));
			int language = Integer.parseInt(request.getParameter("language"));
			int card_config_no = Integer.parseInt(request.getParameter("config_no"));
			int orderth = Integer.parseInt(request.getParameter("orderth"));
			String category = request.getParameter("category");
			String dept_no = request.getParameter("cd");
			String jobclass = request.getParameter("jobclass");
			String file = request.getParameter("file");

			String imagePath = serviceConfig.getRootPath() + String.format("%05d", company_no) + "/" + serviceConfig.getCardPath() + String.format("%03d", orderth);
			String imageViewPath = "/" + serviceConfig.getCompanyPath() + String.format("%05d", company_no) + "/" + serviceConfig.getCardPath() + String.format("%03d", orderth);
			String image = String.format("%05d", company_no) + "/" + serviceConfig.getCardPath() + String.format("%03d", orderth);
			String landingPath = serviceConfig.getLandingPath() + String.valueOf(company_no) + "/" + String.valueOf(orderth);

			if (card_config_no == 0) {
				// 이미지 저장 디렉토리, 랜딩페이지 디렉토리 생성
				File f = new File(imagePath);
				if (f.exists() == false)
				{
					f.mkdirs();
				}
				f = null;

				f = new File(landingPath);
				if (f.exists() == false)
				{
					f.mkdirs();
				}
				f = null;
			}

			if (file != null) 
			{
				//생성
				int langCnt = 0;
				boolean lang_ko = (language & 0x08) > 0;
				boolean lang_en = (language & 0x04) > 0;
				boolean lang_cn = (language & 0x02) > 0;
				boolean lang_jp = (language & 0x01) > 0;
				if (lang_ko)
				{
					langCnt++;
				}
				if (lang_en)
				{
					langCnt++;
				}
				if (lang_cn)
				{
					langCnt++;
				}
				if (lang_jp)
				{
					langCnt++;
				}

				int arrIdx = 0;
				String[] arr = new String[langCnt];
				if (lang_ko)
				{
					arr[arrIdx] = "ko";
					arrIdx++;
				}
				if (lang_en)
				{
					arr[arrIdx] = "en";
					arrIdx++;
				}
				if (lang_cn)
				{
					arr[arrIdx] = "cn";
					arrIdx++;
				}
				if (lang_jp)
					arr[arrIdx] = "jp";
				String firstLang = arr[0];

				int carddata_master_no = 0;

				// 엑셀 파싱
				String filepath = serviceConfig.getRootPath() + file;
				tbo = new WorkBookHandle(filepath);
				WorkSheetHandle sheet = tbo.getWorkSheet(0);
				String[] colArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M" };
				String txt = "";

				// 이미지 y축 정렬
				ImageHandle[] extracted = sheet.getImages();
				for (int i = 0; i < extracted.length; i++)
				{
					for (int j = 0; j < extracted.length - 1; j++)
					{
						short[] coords1 = extracted[j].getCoords();
						short[] coords2 = extracted[j + 1].getCoords();
						if (coords1[1] > coords2[1])
						{
							ImageHandle tmp = extracted[j];
							extracted[j] = extracted[j + 1];
							extracted[j + 1] = tmp;
						}
					}
				}

				// totalRow 구하기
				int totalRow = 0;
				String tmp = "";
				for (int i = 1; i < sheet.getRows().length; i++)
				{
					tmp = sheet.getRow(i).getCells()[0].getStringVal();
					if (tmp.equals("") || tmp == null)
					{
						break;
					}
					else
					{
						totalRow++;
					}
				}

				arrIdx = 0;
				
				if (totalRow > 0 && extracted.length == (totalRow / langCnt))
				{
					int imageIdx = 0;

					List<Map<String, Object>> dataList_ko = new ArrayList<>();
					List<Map<String, Object>> dataList_en = new ArrayList<>();
					List<Map<String, Object>> dataList_cn = new ArrayList<>();
					List<Map<String, Object>> dataList_jp = new ArrayList<>();
					Map<String, Object> data_master = null;
					Map<String, Object> data = null;

					// card_config insert
					if (card_config_no == 0)
					{
						card_config_no = cardService.insertCardConfig(paramMap);
					}

					// data 생성
					for (int i = 1; i <= totalRow; i++)
					{
						data = new HashMap<>();
						for (int j = 0; j < colArr.length; j++)
						{
							txt = sheet.getCell(colArr[j] + String.valueOf(i + 1)).getStringVal();
							if (colArr[j].equals("A"))
								data.put("name", txt);
							if (colArr[j].equals("B"))
								data.put("part", txt);
							if (colArr[j].equals("C"))
								data.put("position", txt);
							if (colArr[j].equals("D"))
								data.put("duty", txt);
							if (colArr[j].equals("E"))
								data.put("address", txt);
							if (colArr[j].equals("F"))
								data.put("email", txt);
							if (colArr[j].equals("G"))
								data.put("tel", txt);
							if (colArr[j].equals("H"))
								data.put("fax", txt);
							if (colArr[j].equals("I"))
								data.put("mobile", txt);
							if (arr[arrIdx].equals(firstLang))
							{
								if (colArr[j].equals("J"))
								{
									data_master = new HashMap<>();
									data_master.put("kakaoid", txt);
								}
								if (colArr[j].equals("K"))
									data_master.put("lineid", txt);
								if (colArr[j].equals("L"))
									data_master.put("youtube", txt);
								if (colArr[j].equals("M"))
								{
									// 이미지 저장
									String fileName = extracted[imageIdx].getName();
									String ext = extracted[imageIdx].getType();
									fileName = UUID.randomUUID().toString() + "." + ext;
									String imageFullPath = imagePath + "/" + fileName;
									FileOutputStream outimg = new FileOutputStream(imageFullPath);
									extracted[imageIdx].write(outimg);
									outimg.flush();
									outimg.close();
									imageIdx++;

									data_master.put("langCnt", langCnt);
									data_master.put("firstLang", firstLang);
									data_master.put("category", category);
									data_master.put("image", image + "/" + fileName);
									data_master.put("imageViewPath", imageViewPath + "/" + fileName);
									data_master.put("card_config_no", card_config_no);
									data_master.put("dept_no", dept_no);
									data_master.put("jobclass", jobclass);
									// carddata_master DB 저장
									carddata_master_no = cardService.insertCardData_Master(data_master);
									ListScan.put("master", data_master);
									data_master.clear();
									data_master = null;
								}
							}
						}
						if (arr[arrIdx].equals("ko"))
						{
							data.put("carddata_master_no", carddata_master_no);
							dataList_ko.add(data);
							ListScan.put("data_ko", data);
						}
						if (arr[arrIdx].equals("en"))
						{
							data.put("carddata_master_no", carddata_master_no);
							dataList_en.add(data);
							ListScan.put("data_en", data);
						}
						if (arr[arrIdx].equals("cn"))
						{
							data.put("carddata_master_no", carddata_master_no);
							dataList_cn.add(data);
							ListScan.put("data_cn", data);
						}
						if (arr[arrIdx].equals("jp"))
						{
							data.put("carddata_master_no", carddata_master_no);
							dataList_jp.add(data);
							ListScan.put("data_jp", data);
						}

						// next language, jsonList에 객체 담기
						++arrIdx;
						if (arrIdx == arr.length)
						{
							cardList.add(ListScan);
							arrIdx = 0;
						}
					}

					// carddata DB 저장
					Map<String, Object> insertMap = null;
					if (lang_ko && dataList_ko.size() > 0)
					{
						insertMap = new HashMap<>();
						insertMap.put("lang", "ko");
						insertMap.put("category", category);
						insertMap.put("data", dataList_ko);
						cardService.insertCardData(insertMap);
					}
					if (lang_en && dataList_en.size() > 0)
					{
						insertMap = new HashMap<>();
						insertMap.put("lang", "en");
						insertMap.put("category", category);
						insertMap.put("data", dataList_en);
						cardService.insertCardData(insertMap);
					}
					if (lang_cn && dataList_cn.size() > 0)
					{
						insertMap = new HashMap<>();
						insertMap.put("lang", "cn");
						insertMap.put("category", category);
						insertMap.put("data", dataList_cn);
						cardService.insertCardData(insertMap);
					}
					if (lang_jp && dataList_jp.size() > 0)
					{
						insertMap = new HashMap<>();
						insertMap.put("lang", "jp");
						insertMap.put("category", category);
						insertMap.put("data", dataList_jp);
						cardService.insertCardData(insertMap);
					}
					jsonObj.put("card_config_no", card_config_no);
					jsonObj.put("cardList", cardList);
					jsonObj.put("result", 1);
					PrintWriter out = response.getWriter();
					out.print(jsonObj);
					out.close();
					tbo.close();
					tbo = null;
					data.clear();
					data = null;
					dataList_ko.clear();
					dataList_ko = null;
					dataList_en.clear();
					dataList_en = null;
					dataList_cn.clear();
					dataList_cn = null;
					dataList_jp.clear();
					dataList_jp = null;
					insertMap.clear();
					insertMap = null;
				}
				else
				{
					jsonObj.put("card_config_no", card_config_no);
					jsonObj.put("result", -1);
					PrintWriter out = response.getWriter();
					out.print(jsonObj);
					out.close();
					tbo.close();
					tbo = null;
				}
			}

			jsonObj.put("card_config_no", card_config_no);
			jsonObj.put("result", -1);
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.close();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARDCREATE_ERROR + "/" + e.getMessage());
			return null;
		}
		finally
		{
			ListScan.clear();
			ListScan = null;
			cardList.clear();
			cardList = null;
			jsonObj.clear();
			jsonObj = null;
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("carddown")
	public void carddown(HttpServletRequest request, HttpServletResponse response)
	{
		ServletOutputStream outStream = null;
		byte[] outByte = null;
		String data = null;
		List<Map<String, Object>> list = null;
		Map<String, Object> paramMap = null;
		Map<String, Object> orderInfo = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			String card_config_no = request.getParameter("card_config_no");
			String category = request.getParameter("category");
			paramMap = new HashMap<>();
			paramMap.put("category", category);
			paramMap.put("card_config_no", card_config_no);

			if (card_config_no.length() <= 0)
				return;
			if (category.length() <= 0)
				return;

			if (card_config_no != null && category != null)
			{
				list = cardService.getCardDown(paramMap);

				if (list != null && list.size() > 0)
				{
					orderInfo = cardService.getCardOrderInfo(paramMap);
					if (orderInfo != null && orderInfo.size() > 0)
					{
						outStream = response.getOutputStream();

						String name = orderInfo.get("name") + "_" + orderInfo.get("title") + ".csv";
						name = WebUtil.getDisposition(name, WebUtil.getBrowser(request));

						response.setContentType("application/octet-stream; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment;filename=\"" + name + "\";");
						response.setHeader("Content-Transfer-Encoding", "binary");

						for (int i = 0; i < list.size(); i++)
						{
							data = String.format("%s\n", list.get(i).get("no"));
							outByte = data.getBytes("MS949");
							outStream.write(outByte);
							outByte = null;
							data = null;
						}

						outStream.flush();
						outStream.close();
					}
				}
			}
		}
		catch (Exception be)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARDDOWN_ERROR + "/" + be.getMessage());
		}
		finally
		{
			list.clear();
			list = null;
			paramMap.clear();
			paramMap = null;
			orderInfo.clear();
			orderInfo = null;
		}
	}

	@RequestMapping("card_stats_scan")
	public ModelAndView card_stats_scan(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_stats_scan");
		Map<String, Object> paramMap = null;
		List<cardStatsModel> monthData = null;
		List<cardStatsModel> dailyData = null;
		List<cardStatsModel> hourData = null;
		Map<String, Object> orderData = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			orderData = cardService.getStatsOrderData(paramMap);
			int count = cardService.getStatsTotalCnt(paramMap);
			monthData = cardService.getStatsMonthData(paramMap);
			dailyData = cardService.getStatsDailyData(paramMap);
			hourData = cardService.getStatsHourData(paramMap);
			request.getSession().setAttribute("orderData", orderData);
			request.getSession().setAttribute("count", count);
			request.getSession().setAttribute("monthData", monthData);
			request.getSession().setAttribute("dailyData", dailyData);
			request.getSession().setAttribute("hourData", hourData);
			request.getSession().setAttribute("paramMap", paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_STATS_SCAN_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("card_stats_local")
	public ModelAndView stats_local(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/card_stats_local");
		Map<String, Object> paramMap = null;
		List<cardStatsModel> nationData = null;
		List<cardStatsModel> cityData = null;
		List<cardStatsModel> addressData = null;
		Map<String, Object> countMap = null;
		Map<String, Object> orderData = null;

		try
		{
			paramMap = this.makeRequestMap(request);
			String listcount = cardService.getStatsAddressData_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());

			orderData = cardService.getStatsOrderData(paramMap);
			countMap = cardService.getStatsLocalCnt(paramMap);
			nationData = cardService.getStatsNationData(paramMap);
			cityData = cardService.getStatsCityData(paramMap);
			addressData = cardService.getStatsAddressData(paramMap);
			request.getSession().setAttribute("orderData", orderData);
			request.getSession().setAttribute("countMap", countMap);
			request.getSession().setAttribute("nationData", nationData);
			request.getSession().setAttribute("cityData", cityData);
			request.getSession().setAttribute("addressData", addressData);
			request.getSession().setAttribute("paramMap", paramMap);
			request.getSession().setAttribute("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.CARD_STATS_LOCAL_ERROR + "/" + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("addrmore")
	public ModelAndView addrmore(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jsonObj = new JSONObject();
		Map<String, Object> paramMap = null;
		List<cardStatsModel> addrList = null;

		try
		{
			paramMap = this.makeRequestMap(request);

			String listcount = cardService.getStatsAddressData_Cnt(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, serviceConfig.getDefaultListViewSize());
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * serviceConfig.getDefaultListViewSize()));
			paramMap.put("listSize", serviceConfig.getDefaultListViewSize());

			addrList = cardService.getStatsAddressData(paramMap);

			jsonObj.put("addrList", addrList);
			jsonObj.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, serviceConfig.getDefaultListViewSize(), null));

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
			out = null;
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.ADDRMORE_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return null;
	}

	@RequestMapping("excel_card_stats_scan")
	public ModelAndView excel_card_stats_scan(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/pc/card/excel_card_stats_scan");
	}

	@RequestMapping("excel_card_stats_local")
	public ModelAndView excel_card_stats_local(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> paramMap = null;
		List<cardStatsModel> excelAddressData = null;
		try
		{
			paramMap = this.makeRequestMap(request);
			excelAddressData = cardService.getExcelStatsAddressData(paramMap);
			request.getSession().setAttribute("excelAddressData", excelAddressData);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.EXCEL_CARD_STATS_LOCAL_ERROR + "/" + e.getMessage());
		}
		return new ModelAndView("/pc/card/excel_card_stats_local");
	}

	@RequestMapping("updateOrderInfo")
	public void updateOrderInfo(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> paramMap = new HashMap<>();
		int result = 0;
		try
		{
			paramMap = this.makeRequestMap(request);
			// order_master DB(iqr번호, count 갱신)
			result = cardService.updateOrderInfo(paramMap);
			if (result > 0)
			{
				// company_master DB(cardcount 갱신)
				cardService.updateCompanyInfo(paramMap);
			}
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEORDERINFO_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}

	@RequestMapping("getcarddata")
	@HPNullCheck(parameters = { "category", "card_master_no", "lang" })
	public ModelAndView getcarddata(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject result = new JSONObject();
		Map<String, Object> paramMap = null;
		Map<String, Object> info = null;
		String imagePath = "/" + serviceConfig.getCompanyPath();

		try
		{
			paramMap = this.makeRequestMap(request);
			info = cardService.getCardData(paramMap);
			if (info != null)
			{
				if (info.get("language") != null)
				{
					int language = (int) info.get("language");
					if ((language & 0x08) > 0)
					{
						info.put("ko", "1");
					}
					else
					{
						info.put("ko", "0");
					}
					if ((language & 0x04) > 0)
					{
						info.put("en", "1");
					}
					else
					{
						info.put("en", "0");
					}
					if ((language & 0x02) > 0)
					{
						info.put("cn", "1");
					}
					else
					{
						info.put("cn", "0");
					}
					if ((language & 0x01) > 0)
					{
						info.put("jp", "1");
					}
					else
					{
						info.put("jp", "0");
					}
				}
				if (info.get("image") != null)
				{
					imagePath = imagePath + info.get("image").toString();
					info.put("imagepath", imagePath);
				}
				info.put("lang", paramMap.get("lang").toString());
				info.put("category", paramMap.get("category").toString());
			}
			result.put("info", info);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDDATA_ERROR + "/" + e.getMessage());
		}
		finally
		{
			if (paramMap != null)
			{
				paramMap.clear();
				paramMap = null;
			}
		}

		return new ModelAndView("/pc/card/updatecarddata", "data", result);
	}

	@RequestMapping("updatecarddata")
	public ModelAndView updatecarddata(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/updatecarddata");
		Map<String, Object> paramMap = new HashMap<>();
		JSONObject result = new JSONObject();

		try
		{
			paramMap = this.makeRequestMap(request);

			String res = "success";
			int ret = cardService.updateCardData(paramMap);
			if (ret != 1)
			{
				res = "fail";
			}
			result.put("category", paramMap.get("category").toString());
			result.put("card_master_no", paramMap.get("card_master_no").toString());
			result.put("lang", paramMap.get("lang").toString());
			mav.addObject("res", res);
			mav.addObject("data", result);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATECARDDATA_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

	@RequestMapping("updateimage")
	public ModelAndView updateimage(MultipartHttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/card/updatecarddata");
		Map<String, Object> paramMap = new HashMap<>();
		JSONObject result = new JSONObject();

		try
		{
			MultipartFile file = request.getFile("file");
			if (file.isEmpty() == false)
			{
				paramMap.put("lang", request.getParameter("lang"));
				paramMap.put("category", request.getParameter("category"));
				paramMap.put("no", request.getParameter("card_master_no"));
			}

			// 사진 저장
			String filePath = serviceConfig.getRootPath() + request.getParameter("image");
			filePath = filePath.substring(0, filePath.lastIndexOf("/"));
			String realPath = null;
			String ext = FileUtil.getFileExtension(file.getOriginalFilename());

			File f = new File(filePath);
			if (f.exists() == false)
			{
				f.mkdirs();
			}
			f = null;

			realPath = filePath + "/" + UUID.randomUUID().toString() + "." + ext;

			f = new File(realPath);
			file.transferTo(f);
			f = null;

			realPath = realPath.replace(serviceConfig.getRootPath(), "");
			paramMap.put("file", realPath);

			// DB 저장
			String res = "success";
			int ret = cardService.updateImage(paramMap);
			if (ret != 1)
			{
				res = "fail";
			}
			result.put("category", paramMap.get("category").toString());
			result.put("card_master_no", paramMap.get("no").toString());
			result.put("lang", paramMap.get("lang").toString());
			mav.addObject("res", res);
			mav.addObject("data", result);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEIMAGE_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return mav;
	}

}