package project.pc.controller;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import project.common.config.MsgConstants;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.pc.service.APIService;

@Controller
@RequestMapping("/api/")
public class APIController extends CommonController
{
	@Autowired
	APIService service;

	@RequestMapping("create")
	public void create(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		HashMap<String, Object> paramMap = new HashMap<>();
		JSONObject result = new JSONObject();
		try
		{
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String apikey = request.getParameter("apikey");
			String title = request.getParameter("title");
			String gen_name = request.getParameter("name");
			String data = request.getParameter("data");

			if (apikey != null && !apikey.trim().equals("") && title != null && !title.trim().equals("") && gen_name != null && !gen_name.trim().equals("") && data != null && !data.trim().equals(""))
			{
				paramMap.put("title", title);
				paramMap.put("gen_name", gen_name);
				paramMap.put("ca", "1");
				paramMap.put("data", data);
				result = service.gen(paramMap);
				if (result.get("result").equals("1"))
				{
					result.put("resultmsg", "success");
					out.print(result);
					out.flush();
					out.close();
					out = null;
				}
				else
				{
					result.put("resultmsg", "GenerateError");
					out.print(result);
					out.flush();
					out.close();
					out = null;
				}
			}
			else
			{
				result.put("result", "-1");
				result.put("resultmsg", "MissingKeyMapError");
				out.print(result);
				out.flush();
				out.close();
				out = null;
			}
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.LANDING_ERROR + "/" + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
			result.clear();
			result = null;
		}
	}
}
