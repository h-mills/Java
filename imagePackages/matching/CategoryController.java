package controller;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.categoryService;

public class CategoryController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	String g_ipaddress = "scanhit_node";
		
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String command = uri.substring(context.length()+1);
		request.setCharacterEncoding("utf-8");
		command = command.substring(command.lastIndexOf('/')+1, command.length());
		
		if(command.equals("category.ct"))
		{
			System.out.println("go for seraching!");
			categoryService service = new categoryService();
			String imgPath = (String)request.getParameter("matpath");
			response.setContentType("application/xml");
			PrintWriter out = response.getWriter();
			try
			{
				int categoryno = -1;
				categoryno = service.getCategory("/scanhit_mobile/"+imgPath);
				System.out.print("categoryno = " + categoryno);
				
				out.println("<?xml version=\"1.0\"?>");
				out.println("<document>");
				if(categoryno != -1)
				{
					out.println("<data>");
					out.println("<categoryno>"+categoryno+"</categoryno>");
					out.println("</data>");
				}
				else
				{
					out.println("<data>");
					out.println("<categoryno>-1</categoryno>");
					out.println("</data>");
				}
				out.println("</document>");
				out.flush();
			}
			catch (Exception e) 
			{
				System.out.println("category.action error : " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				service = null;
				out.close();
				out = null;
			}
		}
		else if(command.equals("iscategory.ct")){
			System.out.println("go for web seraching!");
			categoryService service = new categoryService();
			String imgPath = (String)request.getParameter("matpath");
			response.setContentType("application/xml");
			PrintWriter out = response.getWriter();
			try
			{
				String result = "";
				result = service.getIsCategory("/scanhit_mobile/"+imgPath);
				
				out.println("<?xml version=\"1.0\"?>");
				out.println("<document>");
				if(result != null)
				{
					out.println("<data>");
					out.println("<categoryno>"+result+"</categoryno>");
					out.println("</data>");
				}
				else
				{
					out.println("<data>");
					out.println("<categoryno>nodata</categoryno>");
					out.println("</data>");
				}
				out.println("</document>");
				out.flush();
			}
			catch (Exception e) 
			{
				System.out.println("category.action error : " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				service = null;
				out.close();
				out = null;
			}
		}
	}
}
