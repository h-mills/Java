package project.pc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;

@Controller
@RequestMapping("/pc/main/")
public class MainController extends CommonController {

	@Autowired
	ServiceConfig serviceConfig;
		
	@RequestMapping("frame")
	public ModelAndView frame(HttpServletRequest request, HttpServletResponse response) throws BizException {
		return new ModelAndView("/pc/frame");
	}

	@RequestMapping("top")
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response) throws BizException {
		return new ModelAndView("/pc/top");
	}

	@RequestMapping("left")
	public ModelAndView left(HttpServletRequest request, HttpServletResponse response) throws BizException {
		return new ModelAndView("/pc/left");
	}

	@RequestMapping("bottom")
	public ModelAndView bottom(HttpServletRequest request, HttpServletResponse response) throws BizException {
		return new ModelAndView("/pc/bottom");
	}
}
