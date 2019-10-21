package controller.mobile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import init.setProvider;
import service.CategoryFeatureService;

public class CategoryFeatureController extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String command = uri.substring(context.length() + 1);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		command = command.substring(command.lastIndexOf('/') + 1, command.length());

		if (command.equals("mobile_search.ctf"))
		{
			File src = null;
			File dst = null;
			MultipartRequest multi = null;
			CategoryFeatureService service = new CategoryFeatureService();
			HashMap<String, Object> category = null;
			HashMap<String, Object> category_data = null;
			PrintWriter out = null;
			Random random = null;

			try
			{
				multi = new MultipartRequest(request, setProvider.getSaveDir() + "query/", 20 * 1024 * 1024, "utf-8", new DefaultFileRenamePolicy());
				String fileName = multi.getFilesystemName("matchfile");

				int pos = fileName.lastIndexOf(".");
				String ext = fileName.substring(pos);
				random = new Random();
				String newfileName = setProvider.getSaveDir() + "query/" + String.format("%d%d", System.currentTimeMillis(), random.nextInt(10)) + ext;

				src = new File(setProvider.getSaveDir() + "query/" + fileName);
				dst = new File(newfileName);
				src.renameTo(dst);

				String filePath = newfileName;
				filePath = filePath.replace(setProvider.getSaveDir(), "");
				category = service.getCategory(filePath);

				if (category != null)
				{
					category_data = service.getCategoryData(category);
				}

				out = response.getWriter();

				if (category_data != null && category_data.size() > 0)
				{
					String c_name = category_data.get("name").toString();
					String c_imgpath = category_data.get("imgpath").toString();
					String c_markpath = category_data.get("markpath").toString();
					out.println("<CATEGORYNAME>" + URLEncoder.encode(c_name, "UTF-8") + "</CATEGORYNAME>");
					out.println("<IMAGEPATH>" + c_imgpath + "</IMAGEPATH>");
					out.println("<MARKPATH>" + c_markpath + "</MARKPATH>");
				}
				else
				{
					out.println("<CATEGORYNAME>NOCATEGORY</CATEGORYNAME>");
					out.println("<IMAGEPATH>NOIMAGE</IMAGEPATH>");
					out.println("<MARKPATH>NOMARK</MARKPATH>");
				}
				out.flush();
				out.close();
				out = null;
			}
			catch (Exception e)
			{
				System.out.println("mobile_search.ctf error = " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				if (category != null)
				{
					category.clear();
					category = null;
				}
				if (category_data != null)
				{
					category_data.clear();
					category_data = null;
				}
				if (src != null && src.exists())
				{
					src.delete();
					src = null;
				}
				if (dst != null && dst.exists())
				{
					dst.delete();
					dst = null;
				}
				random = null;
				multi = null;
				service = null;
			}

		}
	}
}
