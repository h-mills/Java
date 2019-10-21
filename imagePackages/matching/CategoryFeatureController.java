package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.matchModel;
import service.CategoryFeatureService;

public class CategoryFeatureController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	String g_ipaddress = "scanhit_node";

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String command = uri.substring(context.length() + 1);
		request.setCharacterEncoding("utf-8");
		command = command.substring(command.lastIndexOf('/') + 1, command.length());

		if (command.equals("category.ctf"))
		{
			CategoryFeatureService service = new CategoryFeatureService();
			String imgPath = (String) request.getParameter("matpath");
			response.setContentType("application/xml");
			PrintWriter out = response.getWriter();
			matchModel[] match = null;

			try
			{
				match = service.match(g_ipaddress, imgPath);

				out.println("<?xml version=\"1.0\"?>");
				out.println("<document>");
				if (match != null)
				{
					out.println("<data>");
					for (int iy = 0; iy < match.length; iy++)
					{
						out.println("<categoryno>" + match[iy].getDnaIdx() + ";" + match[iy].getScore() + ";" + "</categoryno>");
					}
					out.println("</data>");
				}
				else
				{
					out.println("<data>");
					out.println("<categoryno>nodata</categoryno>");
					out.println("</data>");
				}
				out.println("</document>");
			}
			catch (Exception e)
			{
				System.out.println("category.ctf error : " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				match = null;
				service = null;
				out.flush();
				out.close();
				out = null;
			}
		}
	}
}
