package project.pc.controller;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import project.common.config.ServiceConfig;
import project.common.controller.CommonController;
import project.common.exception.BizException;
import project.pc.service.CategoryFeatureService;

@Controller("categoryfeature")
@RequestMapping("/product/")
public class CategoryFeatureController extends CommonController
{
	@Autowired
	ServiceConfig serviceConfig;
	@Autowired
	CategoryFeatureService service;

	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		return new ModelAndView("/pc/image/product");
	}

	@RequestMapping("preview")
	public ModelAndView preview(MultipartHttpServletRequest request, HttpServletResponse response) throws BizException
	{
		ModelAndView mav = new ModelAndView("/pc/image/product");
		MultipartFile file = null;
		try
		{
			file = request.getFile("matchfile");
			Random random = new Random();
			String filename = file.getOriginalFilename();
			String ext = filename.substring(filename.lastIndexOf("."));
			String rename = String.format("%d%d", System.currentTimeMillis(), random.nextInt(10)) + ext;
			String filePath = serviceConfig.getSaveDir() + "query/" + rename;
			File f = new File(filePath);
			file.transferTo(f);
			f = null;

			String urlPath = filePath.replace(serviceConfig.getSaveDir(), "/");
			filePath = filePath.replace(serviceConfig.getSaveDir(), "");

			mav.addObject("urlpath", urlPath);
			mav.addObject("realpath", filePath);
		}
		catch (Exception e)
		{
			System.out.println("categoryfeaturecontroller preview error : " + e.getMessage());
		}
		return mav;
	}

	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws BizException
	{
		ModelAndView mav = new ModelAndView("/pc/image/product");
		List<HashMap<String, Object>> category = null;
		List<HashMap<String, Object>> category_data = null;
		File f = null;
		try
		{
			String queryPath = request.getParameter("realPath");
			f = new File(serviceConfig.getSaveDir() + queryPath);
			category = service.getCategory(queryPath);

			if (category != null && category.size() > 0)
			{
				category_data = service.getCategoryData(category);
				if (category_data != null && category_data.size() > 0)
				{
					// 스코어 추가
					for (int i = 0; i < category_data.size(); i++)
					{
						for (int j = 0; j < category.size(); j++)
						{
							if (category.get(j).get("dna_no").toString().equals(category_data.get(i).get("dna_no").toString()))
							{
								String score = category.get(j).get("score").toString();
								String rownum = category.get(j).get("rownum").toString();
								category_data.get(i).put("score", score);
								category_data.get(i).put("rownum", rownum);
							}
						}
					}
					// 정렬 rownum 오름차순
					Collections.sort(category_data, new Comparator<HashMap<String, Object>>()
					{
						@Override
						public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2)
						{
							int val1 = Integer.parseInt(o1.get("rownum").toString());
							int val2 = Integer.parseInt(o2.get("rownum").toString());
							return val1 < val2 ? -1 : val1 > val2 ? 1 : 0;
						}
					});
					// 중복 제거
					int nSize = category_data.size();
					for(int i = 0; i < nSize-1; i++)
					{
						for(int j = i+1; j < nSize; j++)
						{
							if(category_data.get(j).get("category_no").toString().equals(category_data.get(i).get("category_no").toString()))
							{
								category_data.remove(j);
								nSize--; j--;
							}
						}
					}
				}
			}
			mav.addObject("data", category_data);
		}
		catch (Exception e)
		{
			System.out.println("categoryfeaturecontroller search error : " + e.getMessage());
		}
		finally
		{
			if (f.exists())
			{
				f.delete();
				f = null;
			}
		}
		return mav;
	}
}