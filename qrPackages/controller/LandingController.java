package project.pc.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import project.common.config.MsgConstants;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.pc.service.LandingService;

@Controller
@RequestMapping("/landing/")
public class LandingController extends CommonController{
	@Autowired LandingService service;

	@RequestMapping("terms")
	public ModelAndView terms(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/landing/terms");
	}

	@RequestMapping("landing")
	public ModelAndView landing(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> requestMap = null;
		String url = "/landing/nodata";
		HashMap<String, Object> data = new HashMap<>();
		try
		{
			requestMap = this.makeRequestMap(request);

			if (requestMap.get("idx") != null) 
			{

				HashMap<String, Object> qrList = null;

				qrList = service.detinfo(requestMap);
				
				if (qrList != null) 
				{
					if (qrList.get("category").toString().equals("6"))
					{
						String vcard = "";
						String[] vcardStr;
						vcardStr = qrList.get("data").toString().split(";");
						String[] vcardLabel = {"\"N\":", "\"C\":", "\"T\":", "\"F\":", "\"H\":", "\"M\":", "\"A\":", "\"U\":", "\"NOTE\":"};
						String[] vcardData = {"", "", "", "", "", "", "", "", ""};
						for (int i=0; i<vcardStr.length; i++) {
							vcardData[i] = vcardStr[i];
						}
						
						for (int i=0; i<vcardLabel.length; i++) {
							vcard += (i == 0 ? "{" : "") + vcardLabel[i] + "\"" + vcardData[i] + "\"" + (i == (vcardLabel.length - 1) ? "}" : ",");
						}

						data.put("vcard", vcardData);
						data.put("savevcard", vcard);
					}
					if (qrList.get("category").toString().equals("1")) 
					{
						String[] ArrData = qrList.get("data").toString().split(";");
						if (ArrData != null)
						{
							if (ArrData.length == 1)
							{
								ArrData[0] = ArrData[0].replaceAll("\\s", "");
								if (ArrData[0].indexOf("http://") == -1 && ArrData[0].indexOf("https://") == -1)
								{
									ArrData[0] = "http://" + ArrData[0];
								}
								response.sendRedirect(ArrData[0]);
								return null;
							}
						}
					}
					data.put("qrList", qrList);
					url = "landing/landing";
				}
			}
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.LANDING_ERROR + "/" + e.getMessage());
		}
		finally
		{
			if (requestMap != null)
			{
				requestMap.clear();
				requestMap = null;
			}
		}
		return new ModelAndView(url, "data", data);
	}
}
