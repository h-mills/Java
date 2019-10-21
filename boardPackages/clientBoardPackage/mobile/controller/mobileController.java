package project.mobile.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import project.common.util.AES256Cipher;
import project.common.util.WebUtil;
import project.mobile.service.mobileService;

@Controller
@RequestMapping("/mobile/")
public class mobileController extends CommonController {

	@Autowired ServiceConfig serviceConfig;
	@Autowired mobileService Service;
	
	@RequestMapping("normal")
	@HPNullCheck(parameters={"gen"})
	public ModelAndView normal(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		String url = "/landing/nodata";
		try 
		{
			requestMap = this.makeRequestMap(request);
			if(requestMap.get("gen") != null)
			{
				String decript = null;
				AES256Cipher cipher = new AES256Cipher();
				try
				{
					decript = cipher.AES_Decode(requestMap.get("gen").toString());
					System.out.println("decript_normal" + decript);
				}
				catch(Exception be) 
				{
					new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.NORMALDECRIPT_ERROR + "/" + be.getMessage());
				}
				//decript = "index=250&type=2&version=04.03.05&app_gubun=1&lang=ko&iqrcategory=0&g=62@4533576,872@9487761&uniq=2_dplJEhNlU28dBZNGC4kC9VRxTyswmgYcPeZdp7DhzWqfY5uWeMi+qvUK9R51xAaD_NULL&uniqtime=1489470574728&server_gubun=2";
				if(decript != null)
				{
					info = Service.detinfo(decript, "normal");
					if(info != null)
					{
						if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("0"))
						{
							url = null;
							url = info.get("url").toString();
						}
						if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("1"))
						{
							url = null;
							url = info.get("url").toString();
							url = url.substring(0, url.lastIndexOf("/") + 1) + "landing_expire";
						}
					}
					result.put("param", requestMap);
					result.put("info", info);
				}
			}
		} 
		catch(Exception be) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.NORMAL_ERROR + "/" + be.getMessage());
		}
		finally
		{
			if(requestMap != null)
			{
				requestMap.clear();
				requestMap = null;
			}
		}
				
		return new ModelAndView(url, "data", result);
	}
	
	@RequestMapping("wide")
	@HPNullCheck(parameters={"gen"})
	public ModelAndView wide(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		String url = "/landing/nodata";
		try 
		{
			requestMap = this.makeRequestMap(request);
			if(requestMap.get("gen") != null)
			{
				String decript = null;
				AES256Cipher cipher = new AES256Cipher();
				try
				{
					decript = cipher.AES_Decode(requestMap.get("gen").toString());
					System.out.println("decript_wide" + decript);
				}
				catch(Exception be) 
				{
					new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.WIDEDECRIPT_ERROR + "/" + be.getMessage());
				}
				//decript = "index=250&type=2&version=04.03.05&app_gubun=1&lang=ko&iqrcategory=0&g=62@4533576,872@9487761&uniq=2_dplJEhNlU28dBZNGC4kC9VRxTyswmgYcPeZdp7DhzWqfY5uWeMi+qvUK9R51xAaD_NULL&uniqtime=1489470574728&server_gubun=2";
				if(decript != null)
				{
					info = Service.detinfo(decript, "wide");
					if(info != null)
					{
						if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("0"))
						{
							
							url = null;
							url = info.get("url").toString();
						}
						if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("1"))
						{
							url = null;
							url = info.get("url").toString();
							url = url.substring(0, url.lastIndexOf("/") + 1) + "landing_expire";
						}
					}
					result.put("param", requestMap);
					result.put("info", info);
				}
			}
		} 
		catch(Exception be) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.WIDEDECRIPT_ERROR + "/" + be.getMessage());
		}
		finally
		{
			if(requestMap != null)
			{
				requestMap.clear();
				requestMap = null;
			}
		}
				
		return new ModelAndView(url, "data", result);
	}
	
	@RequestMapping("micro")
	@HPNullCheck(parameters={"gen"})
	public ModelAndView micro(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		String url = "/landing/nodata";
		try 
		{
			requestMap = this.makeRequestMap(request);
			if(requestMap.get("gen") != null)
			{
				String decript = null;
				AES256Cipher cipher = new AES256Cipher();
				try
				{
					decript = cipher.AES_Decode(requestMap.get("gen").toString());
					System.out.println("decript_micro" + decript);
				}
				catch(Exception be) 
				{
					new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.MICRODECRIPT_ERROR + "/" + be.getMessage());
				}
				//decript = "index=250&type=2&version=04.03.05&app_gubun=1&lang=ko&iqrcategory=0&g=62@4533576,872@9487761&uniq=2_dplJEhNlU28dBZNGC4kC9VRxTyswmgYcPeZdp7DhzWqfY5uWeMi+qvUK9R51xAaD_NULL&uniqtime=1489470574728&server_gubun=2";
				if(decript != null)
				{
					info = Service.detinfo(decript, "micro");
					if(info != null)
					{
						if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("0"))
						{
							url = null;
							url = info.get("url").toString();
						}
						if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("1"))
						{
							url = null;
							url = info.get("url").toString();
							url = url.substring(0, url.lastIndexOf("/") + 1) + "landing_expire";
						}
					}
					result.put("param", requestMap);
					result.put("info", info);
				}
			}
		} 
		catch(Exception be) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.MICRODECRIPT_ERROR + "/" + be.getMessage());
		}
		finally
		{
			if(requestMap != null)
			{
				requestMap.clear();
				requestMap = null;
			}
		}
				
		return new ModelAndView(url, "data", result);
	}
	
	@RequestMapping("microwide")
	@HPNullCheck(parameters={"gen"})
	public ModelAndView microwide(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		String url = "/landing/nodata";
		try 
		{
			requestMap = this.makeRequestMap(request);
			if(requestMap.get("gen") != null)
			{
				String decript = null;
				AES256Cipher cipher = new AES256Cipher();
				try
				{
					decript = cipher.AES_Decode(requestMap.get("gen").toString());
					System.out.println("decript_microwide" + decript);
				}
				catch(Exception be) 
				{
					new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.MICROWIDEDECRIPT_ERROR + "/" + be.getMessage());
				}
				//decript = "index=250&type=2&version=04.03.05&app_gubun=1&lang=ko&iqrcategory=0&g=62@4533576,872@9487761&uniq=2_dplJEhNlU28dBZNGC4kC9VRxTyswmgYcPeZdp7DhzWqfY5uWeMi+qvUK9R51xAaD_NULL&uniqtime=1489470574728&server_gubun=2";
				if(decript != null)
				{
					info = Service.detinfo(decript, "microwide");
					
					if(info != null)
					{
						if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("0"))
						{
							url = null;
							url = info.get("url").toString();
						}
						if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("1"))
						{
							url = null;
							url = info.get("url").toString();
							url = url.substring(0, url.lastIndexOf("/") + 1) + "landing_expire";
						}
					}
					result.put("param", requestMap);
					result.put("info", info);
				}
			}
		} 
		catch(Exception be) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.MICROWIDEDECRIPT_ERROR + "/" + be.getMessage());
		}
		finally
		{
			if(requestMap != null)
			{
				requestMap.clear();
				requestMap = null;
			}
		}
				
		return new ModelAndView(url, "data", result);
	}
	
	@RequestMapping("pclanding")
	@HPNullCheck(parameters={"card_data_no", "gubun", "lang"})//gubun:normal, wide, micro, microwide //lang:ko, en, cn, jp
	public ModelAndView pclanding(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject result = new JSONObject();
		HashMap<String, Object> requestMap = null;
		HashMap<String, Object> info = null;
		String url = "/landing/nodata";
		String imagePath = "/" + serviceConfig.getCompanyPath();
		try 
		{
			requestMap = this.makeRequestMap(request);
			info = Service.pclanding(requestMap);

			if(info != null)
			{
				if(info.get("language") != null)
				{
					int language = (int)info.get("language");
					if((language & 0x08) > 0){info.put("ko", "1");}else{info.put("ko", "0");}
					if((language & 0x04) > 0){info.put("en", "1");}else{info.put("en", "0");}
					if((language & 0x02) > 0){info.put("cn", "1");}else{info.put("cn", "0");}
					if((language & 0x01) > 0){info.put("jp", "1");}else{info.put("jp", "0");}
				}
				if(info.get("image") != null)
				{
					imagePath = imagePath + info.get("image").toString();
					info.put("imagepath", imagePath);
				}
				if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("0"))
				{
					url = null;
					url = info.get("url").toString();
				}
				if(info.get("url") != null && String.valueOf(info.get("isdelete")).equals("1"))
				{
					url = null;
					url = info.get("url").toString();
					url = url.substring(0, url.lastIndexOf("/") + 1) + "landing_expire";
				}
				info.put("lang", requestMap.get("lang").toString());
			} 
			result.put("info", info);

			if (url.equals("/landing/nodata") || url.equals(url.substring(0, url.lastIndexOf("/") + 1) + "landing_expire")) {
				
			}
			String pdfPath = request.getSession().getServletContext().getRealPath("/WEB-INF/jsp" + url + ".jsp");
			if (!new File(pdfPath).exists()) {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html; charset=UTF-8");
				out.print("<script>");
				out.print("alert('랜딩 페이지 제작중');");
				out.print("window.close();");
				out.print("</script>");
				out.flush();
				out.close();
				return null;
			}
		} 
		catch(Exception be) 
		{
			new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.PCLANDING_ERROR + "/" + be.getMessage());
		}
		finally
		{
			if(requestMap != null)
			{
				requestMap.clear();
				requestMap = null;
			}
		}

		return new ModelAndView(url, "data", result);
	}

	@RequestMapping("reply")
	@HPNullCheck(parameters={"carddata_master_no"})
	public void reply(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> paramMap = new HashMap<>();
		List<HashMap<String, Object>> replyList = new ArrayList<>();

		try
		{
			paramMap = this.makeRequestMap(request);

			int defalutListViewSize = 5;
			String listcount = Service.getReplyListCount(paramMap);
			String pageNum = WebUtil.getPageNum(request.getParameter("pageNum"), listcount, defalutListViewSize);
			paramMap.put("pageNum", (Integer.parseInt(pageNum) * defalutListViewSize));
			paramMap.put("listSize", defalutListViewSize);

			replyList = Service.getCardDataReply(paramMap);

			jsonObj.put("pagingValues", WebUtil.makePagingDataMap(serviceConfig.getDefaultPagingSize(), pageNum, listcount, defalutListViewSize, null));
			jsonObj.put("replyList", replyList);

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.PCLANDING_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
			replyList.clear();
			replyList = null;
			jsonObj.clear();
			jsonObj = null;
		}
	}

	@RequestMapping("replysave")
	@HPNullCheck(parameters={"carddata_master_no"})
	public void replysave(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> paramMap = new HashMap<>();

		try
		{
			paramMap = this.makeRequestMap(request);
			int Ret = Service.insertCarddataReply(paramMap);
			jsonObj.put("result", Ret);

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.PCLANDING_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
			jsonObj.clear();
			jsonObj = null;
		}
	}

	@RequestMapping("replydelmain")
	@HPNullCheck(parameters={"no"})
	public ModelAndView replydelmain(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		return new ModelAndView("/landing/replydel", "no", request.getParameter("no"));
	}

	@RequestMapping("replydel")
	@HPNullCheck(parameters={"no"})
	public void replydel(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		JSONObject jsonObj = new JSONObject();
		HashMap<String, Object> paramMap = new HashMap<>();

		try
		{
			paramMap = this.makeRequestMap(request);

			int Ret = Service.getDelteCheck(paramMap);
			if (Ret == 1)
			{
				Ret = Service.deleteCarddataReply(paramMap);
			} 
			else 
			{
				Ret = -1;
			}
			jsonObj.put("result", Ret);

			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();
		    out.close();
		    out = null;
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.MOBILE_SYSTEM, MsgConstants.PCLANDING_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
			jsonObj.clear();
			jsonObj = null;
		}
	}

}
