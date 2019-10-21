package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import init.setProvider;
import model.common.nodeModel;
import model.image.resultModel;
import model.image.resultModelList;

public class nodeConnection
{

	public int Extract(String urlStr, String urlStr_similarity, int img_no, int data_no)// image master number, dna info master number
	{
		URL url = null, url_similarity = null;
		HttpURLConnection ucconn = null, ucconn_similarity = null;
		try
		{
			int Ret = 0;
			String urlParameters = URLEncoder.encode("imgno", "UTF-8") + "=" + URLEncoder.encode(String.format("%d", img_no), "UTF-8");
			urlParameters += "&" + URLEncoder.encode("datano", "UTF-8") + "=" + URLEncoder.encode(String.format("%d", data_no), "UTF-8");
			url = new URL(urlStr + "/extract.action");
			ucconn = (HttpURLConnection) url.openConnection();
			ucconn.setUseCaches(false);
			ucconn.setRequestMethod("POST");
			ucconn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			ucconn.setDoInput(true);
			ucconn.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(ucconn.getOutputStream());
			wr.write(urlParameters);
			wr.flush();
			wr.close();
			wr = null;

			if (ucconn.getResponseCode() != 200)
			{
				return -2;
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "UTF-8"));
			String line = "", resData = "";
			String[] spData = null;
			while ((line = rd.readLine()) != null)
			{
				resData += line;
			}
			rd.close();
			rd = null;
			spData = resData.split(";");
			for (int iy = 0; iy < spData.length; iy++)
			{
				if (spData[iy].equals("result"))
				{
					if (spData[iy + 1].equals("1"))
					{
						Ret = 1;
					}
					break;
				}
			}
			spData = null;
			ucconn.disconnect();

			// Similarity
			int Ret2 = 0;
			urlParameters = URLEncoder.encode("imgno", "UTF-8") + "=" + URLEncoder.encode(String.format("%d", img_no), "UTF-8");
			urlParameters += "&" + URLEncoder.encode("datano", "UTF-8") + "=" + URLEncoder.encode(String.format("%d", data_no), "UTF-8");
			url_similarity = new URL(urlStr_similarity + "/extract.action");
			ucconn_similarity = (HttpURLConnection) url_similarity.openConnection();
			ucconn_similarity.setUseCaches(false);
			ucconn_similarity.setRequestMethod("POST");
			ucconn_similarity.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			ucconn_similarity.setDoInput(true);
			ucconn_similarity.setDoOutput(true);

			wr = new OutputStreamWriter(ucconn_similarity.getOutputStream());
			wr.write(urlParameters);
			wr.flush();
			wr.close();
			wr = null;

			if (ucconn_similarity.getResponseCode() != 200)
			{
				return -2;
			}

			rd = new BufferedReader(new InputStreamReader(ucconn_similarity.getInputStream(), "UTF-8"));
			line = "";
			resData = "";
			spData = null;
			while ((line = rd.readLine()) != null)
			{
				resData += line;
			}
			rd.close();
			rd = null;
			spData = resData.split(";");
			for (int iy = 0; iy < spData.length; iy++)
			{
				if (spData[iy].equals("result"))
				{
					if (spData[iy + 1].equals("1"))
					{
						Ret2 = 1;
					}
					break;
				}
			}
			spData = null;
			ucconn_similarity.disconnect();

			if (Ret == 1 || Ret2 == 1)
				Ret = 1;

			return Ret;
		}
		catch (Exception e)
		{
			System.out.println("nodeConnection.Extract() error : " + e.getMessage());
			e.printStackTrace();
			return -1;
		}
		finally
		{
			ucconn = null;
			url = null;
		}
	}

	public resultModelList Matching(String urlStr, String urlStr_similarity, String matpath)
	{
		URL url = null, url_similarity = null;
		HttpURLConnection ucconn = null, ucconn_similarity = null;
		resultModelList model = new resultModelList();
		String matData = null;
		OutputStreamWriter wr;

		try
		{
			matData = URLEncoder.encode("matpath", "UTF-8") + "=" + URLEncoder.encode(String.format("%s", matpath), "UTF-8");
			if (urlStr != null)
			{
				url = new URL(urlStr + "/match.action");
				ucconn = (HttpURLConnection) url.openConnection();
				ucconn.setUseCaches(false);
				ucconn.setDoInput(true);
				ucconn.setDoOutput(true);
				wr = new OutputStreamWriter(ucconn.getOutputStream());
				wr.write(matData);
				wr.flush();
				wr.close();
				wr = null;

				if (ucconn.getResponseCode() != 200)
				{
					return null;
				}

				BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "UTF-8"));
				String line = "", resData = "";
				String[] spData = null;
				while ((line = rd.readLine()) != null)
				{
					resData += line;
				}
				rd.close();
				rd = null;
				spData = resData.split(";");
				for (int iy = 0; iy < spData.length; iy++)
				{
					if (spData[iy].equals("result"))
					{
						resultModel tmp = new resultModel(Integer.parseInt(spData[iy + 1]), Short.parseShort(spData[iy + 2]));
						model.addList(tmp);
					}
				}
			}

			// Similarity
			if (urlStr_similarity != null)
			{
				url_similarity = new URL(urlStr_similarity + "/match.action");
				ucconn_similarity = (HttpURLConnection) url_similarity.openConnection();
				ucconn_similarity.setUseCaches(false);
				ucconn_similarity.setDoInput(true);
				ucconn_similarity.setDoOutput(true);
				wr = new OutputStreamWriter(ucconn_similarity.getOutputStream());
				wr.write(matData);
				wr.flush();
				wr.close();
				wr = null;

				if (ucconn_similarity.getResponseCode() != 200)
				{
					return null;
				}

				BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn_similarity.getInputStream(), "UTF-8"));
				String line = "", resData = "";
				String[] spData = null;
				while ((line = rd.readLine()) != null)
				{
					resData += line;
				}
				rd.close();
				rd = null;
				spData = resData.split(";");
				for (int iy = 0; iy < spData.length; iy++)
				{
					if (spData[iy].equals("result"))
					{
						float dist = 0.0f;

						if (spData[iy + 2].equals("0.000000"))
							dist = 0.000000f;
						else
							dist = Float.parseFloat(spData[iy + 2]);

						resultModel tmp = new resultModel(Integer.parseInt(spData[iy + 1]), dist);
						model.addList(tmp);
					}
				}
			}

			File f = new File(setProvider.getSaveDir() + matpath);
			try
			{
				if (f.exists())
				{
					f.delete();
				}
			}
			catch (Exception e)
			{
			}
			finally
			{
				f = null;
			}
			return model;

		}
		catch (Exception e)
		{
			System.out.println("nodeConnection.Matching() error : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally
		{
			if (ucconn != null)
			{
				ucconn.disconnect();
				ucconn = null;
				url = null;
			}
			if (ucconn_similarity != null)
			{
				ucconn_similarity.disconnect();
				ucconn_similarity = null;
				url_similarity = null;
			}
		}
	}

	public List<Integer> Train(List<nodeModel> model, List<nodeModel> model_similarity)
	{
		URL url = null, url_similarity = null;
		HttpURLConnection ucconn = null, ucconn_similarity = null;
		List<Integer> Result = new ArrayList<Integer>();

		try
		{
			for (int iy = 0; iy < model.size(); iy++)
			{
				url = new URL("http://" + model.get(iy).getIp() + ":81" + "/train.action");
				ucconn = (HttpURLConnection) url.openConnection();
				ucconn.setUseCaches(false);
				ucconn.setRequestMethod("POST");
				ucconn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				ucconn.setDoInput(true);
				ucconn.setDoOutput(true);

				if (ucconn.getResponseCode() != 200)
				{
					Result.add(-2);
				}

				BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "UTF-8"));
				String line = "", resData = "";
				String[] spData = null;
				while ((line = rd.readLine()) != null)
				{
					resData += line;
				}
				rd.close();
				rd = null;
				spData = resData.split(";");
				for (int ix = 0; ix < spData.length; ix++)
				{
					if (spData[ix].equals("result"))
					{
						if (spData[ix + 1].equals("1"))
						{
							Result.add(1);
						}
						break;
					}
				}
				spData = null;
				ucconn.disconnect();
			}
			for (int iy = 0; iy < model_similarity.size(); iy++)
			{
				url_similarity = new URL("http://" + model_similarity.get(iy).getIp() + ":83" + "/train.action");
				ucconn_similarity = (HttpURLConnection) url_similarity.openConnection();
				ucconn_similarity.setUseCaches(false);
				ucconn_similarity.setRequestMethod("POST");
				ucconn_similarity.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				ucconn_similarity.setDoInput(true);
				ucconn_similarity.setDoOutput(true);

				if (ucconn_similarity.getResponseCode() != 200)
				{
					Result.add(-2);
				}

				BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn_similarity.getInputStream(), "UTF-8"));
				String line = "", resData = "";
				String[] spData = null;
				while ((line = rd.readLine()) != null)
				{
					resData += line;
				}
				rd.close();
				rd = null;
				spData = resData.split(";");
				for (int ix = 0; ix < spData.length; ix++)
				{
					if (spData[ix].equals("result"))
					{
						if (spData[ix + 1].equals("1"))
						{
							Result.add(1);
						}
						break;
					}
				}
				spData = null;
				ucconn_similarity.disconnect();
			}
			return Result;
		}
		catch (Exception e)
		{
			System.out.println("nodeConnection.Train() error : " + e.getMessage());
			e.printStackTrace();
			return Result;
		}
		finally
		{
			ucconn = null;
			url = null;
		}
	}

	public int Train(nodeModel model, nodeModel model_similarity)
	{
		URL url = null, url_similarity = null;
		HttpURLConnection ucconn = null, ucconn_similarity = null;
		int Ret = 0;

		try
		{
			url = new URL("http://" + model.getIp() + ":81" + "/train.action");
			ucconn = (HttpURLConnection) url.openConnection();
			ucconn.setUseCaches(false);
			ucconn.setRequestMethod("POST");
			ucconn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			ucconn.setDoInput(true);
			ucconn.setDoOutput(true);

			if (ucconn.getResponseCode() != 200)
			{
				Ret = -2;
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "UTF-8"));
			String line = "", resData = "";
			String[] spData = null;
			while ((line = rd.readLine()) != null)
			{
				resData += line;
			}
			rd.close();
			rd = null;
			spData = resData.split(";");
			for (int ix = 0; ix < spData.length; ix++)
			{
				if (spData[ix].equals("result"))
				{
					if (spData[ix + 1].equals("1"))
					{
						Ret = 1;
					}
					break;
				}
			}
			spData = null;
			ucconn.disconnect();

			url_similarity = new URL("http://" + model_similarity.getIp() + ":83" + "/train.action");
			ucconn_similarity = (HttpURLConnection) url_similarity.openConnection();
			ucconn_similarity.setUseCaches(false);
			ucconn_similarity.setRequestMethod("POST");
			ucconn_similarity.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			ucconn_similarity.setDoInput(true);
			ucconn_similarity.setDoOutput(true);

			if (ucconn.getResponseCode() != 200)
			{
				Ret = -2;
			}

			rd = new BufferedReader(new InputStreamReader(ucconn_similarity.getInputStream(), "UTF-8"));
			line = "";
			resData = "";
			spData = null;
			while ((line = rd.readLine()) != null)
			{
				resData += line;
			}
			rd.close();
			rd = null;
			spData = resData.split(";");
			for (int ix = 0; ix < spData.length; ix++)
			{
				if (spData[ix].equals("result"))
				{
					if (spData[ix + 1].equals("1"))
					{
						Ret = 1;
					}
					break;
				}
			}
			spData = null;
			ucconn_similarity.disconnect();
			return Ret;
		}
		catch (Exception e)
		{
			System.out.println("nodeConnection.Train() error : " + e.getMessage());
			e.printStackTrace();
			return Ret;
		}
		finally
		{
			ucconn = null;
			url = null;
		}
	}

	// 홍영준 2014-09-22 검색 서버 커넥션 체크
	public short nodeConnChk(String urlStr)
	{
		URL url = null;
		HttpURLConnection ucconn = null;
		short Ret = 0;
		try
		{
			url = new URL(urlStr + "/nodeConnChk.action");
			ucconn = (HttpURLConnection) url.openConnection();
			ucconn.setUseCaches(false);
			ucconn.setRequestMethod("POST");
			ucconn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			ucconn.setDoOutput(true);

			if (ucconn.getResponseCode() != 200)
			{
				return 0;
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "UTF-8"));
			String line = "", resData = "";
			String[] spData = null;
			while ((line = rd.readLine()) != null)
			{
				resData += line;
			}
			rd.close();
			rd = null;
			spData = resData.split(";");
			for (int iy = 0; iy < spData.length; iy++)
			{
				if (spData[iy].equals("result"))
				{
					if (spData[iy + 1].equals("1"))
					{
						Ret = 1;
					}
					break;
				}
			}
			spData = null;
			ucconn.disconnect();
			return Ret;
		}
		catch (Exception e)
		{
			return 0;
		}
		finally
		{
			ucconn = null;
			url = null;
		}
	}

	// 크롤러 서버 상태 확인
	public int CrwConnChk(String urlStr)
	{
		URL url = null;
		HttpURLConnection ucconn = null;
		int Ret = 2;
		try
		{
			url = new URL(urlStr + "/main/CMCI01.cknb?reqType=2");
			ucconn = (HttpURLConnection) url.openConnection();
			ucconn.setUseCaches(false);
			ucconn.setRequestMethod("POST");
			ucconn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			ucconn.setDoOutput(true);
			if (ucconn.getResponseCode() != 200)
			{
				Ret = 2;
			}
			else
			{
				BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "UTF-8"));
				String line = "";
				String result = "";
				while ((line = rd.readLine()) != null)
				{
					result += line;
				}
				rd.close();
				rd = null;
				// System.out.println("JSON : " + result);
				if (result != null)
				{
					try
					{
						JSONParser parser = new JSONParser();
						JSONObject object = (JSONObject) parser.parse(result);
						if (!object.get("crawlerStatus").toString().equals("TRUE"))
						{
							Ret = 0;
						}
						else
						{
							Ret = 1;
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			ucconn.disconnect();
			// System.out.println("RET : " + Ret);
			return Ret;
		}
		catch (Exception e)
		{
			return 2;
		}
		finally
		{
			ucconn = null;
			url = null;
		}
	}

	// 크롤러 시작
	public int CrwStart(String urlStr, String crw_code, int Option)// 0:site, 1;api
	{
		URL url = null;
		HttpURLConnection ucconn = null;
		int Ret = 0;
		try
		{
			System.out.println("URL :" + urlStr);
			if (Option == 0)
				url = new URL(urlStr + "/main/siteCrw.cknb" + "?crwCode=" + crw_code);
			else
				url = new URL(urlStr + "/main/CCFM01.cknb" + "?crwCode=" + crw_code);
			ucconn = (HttpURLConnection) url.openConnection();
			ucconn.setUseCaches(false);
			ucconn.setRequestMethod("POST");
			ucconn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			ucconn.setDoOutput(true);
			if (ucconn.getResponseCode() != 200)
			{
				Ret = 0;
			}
			else
			{
				BufferedReader rd = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "UTF-8"));
				String line = "";
				String result = "";
				while ((line = rd.readLine()) != null)
				{
					result += line;
				}
				rd.close();
				rd = null;
				System.out.println("JSON : " + result);
				if (result != null)
				{
					try
					{
						JSONParser parser = new JSONParser();
						JSONObject object = (JSONObject) parser.parse(result);
						if (object.get("result") != null)
						{
							Ret = 1;
						}
						else
						{
							Ret = 0;
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			System.out.println("RET : " + Ret);
			return Ret;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		finally
		{
			ucconn.disconnect();
			ucconn = null;
			url = null;
		}
	}

	// 2015_02_04_june 푸쉬 서버 연결
	public static int push(String project_id, String api_key, String message)
	{
		String urlStr = "http://1.231.29.77/pushSend.jsp";
		URL url = null;
		HttpURLConnection ucconn = null;
		try
		{
			int Ret = 0;
			String urlParameters = URLEncoder.encode("project_id", "UTF-8") + "=" + URLEncoder.encode(project_id, "UTF-8");
			urlParameters += "&" + URLEncoder.encode("api_key", "UTF-8") + "=" + URLEncoder.encode(api_key, "UTF-8");
			urlParameters += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");

			// String urlParameters = "project_id=" + project_id;
			// urlParameters += "&" + "api_key=" + api_key;
			// urlParameters += "&" + "message=" + message;
			url = new URL(urlStr);
			ucconn = (HttpURLConnection) url.openConnection();
			ucconn.setUseCaches(false);
			ucconn.setRequestMethod("GET");
			ucconn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			ucconn.setDoInput(true);
			ucconn.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(ucconn.getOutputStream());
			wr.write(urlParameters);
			wr.flush();
			wr.close();
			wr = null;

			if (ucconn.getResponseCode() != 200)
			{
				// System.out.println("push URL 연결 오류");
				// System.out.println("url = " + url +"?"+ urlParameters);
				return -2;
			}

			ucconn.disconnect();
			return Ret;
		}
		catch (Exception e)
		{
			System.out.println("nodeConnection.push() error : " + e.getMessage());
			e.printStackTrace();
			return -1;
		}
		finally
		{
			ucconn = null;
			url = null;
		}
	}

	public HashMap<String, Object> getCategory(String urlStr, String matpath, String saveDir)
	{
		URL url = null;
		HttpURLConnection ucconn = null;
		HashMap<String, Object> result = new HashMap<>();
		String matData = null;
		OutputStreamWriter wr;
		try
		{
			matData = URLEncoder.encode("matpath", "UTF-8") + "=" + URLEncoder.encode(String.format("%s", matpath), "UTF-8");
			if (urlStr != null)
			{
				url = new URL(urlStr + "/category.ct");
				ucconn = (HttpURLConnection) url.openConnection();
				ucconn.setUseCaches(false);
				ucconn.setDoInput(true);
				ucconn.setDoOutput(true);
				wr = new OutputStreamWriter(ucconn.getOutputStream());
				wr.write(matData);
				wr.flush();
				wr.close();

				wr = null;

				if (ucconn.getResponseCode() != 200)
				{
					return null;
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "utf-8"));
				String inputLine;
				StringBuffer res = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
				{
					res.append(inputLine);
				}
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;
				InputSource is;

				builder = factory.newDocumentBuilder();
				is = new InputSource(new StringReader(res.toString()));
				Document doc = builder.parse(is);
				NodeList list = doc.getElementsByTagName("data");

				int categoryno = Integer.parseInt(list.item(0).getTextContent());
				result.put("category_no", categoryno);
			}

			File f = new File(saveDir + matpath);
			if (f.exists())
			{
				f.delete();
			}
			f = null;

			return result;

		}
		catch (Exception e)
		{
			System.out.println("nodeConnection.getCategory() error : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally
		{
			if (ucconn != null)
			{
				ucconn.disconnect();
				ucconn = null;
				url = null;
			}
		}
	}

	public HashMap<String, Object> getCategoryFeature(String urlStr, String matpath, String saveDir)
	{
		URL url = null;
		HttpURLConnection ucconn = null;
		HashMap<String, Object> result = null;
		String matData = null;
		OutputStreamWriter wr = null;
		BufferedReader in = null;
		StringBuffer res = null;
		DocumentBuilderFactory factory = null; 
		DocumentBuilder builder = null;
		InputSource is = null;
		Document doc = null;
		NodeList list = null;
		try
		{
			matData = URLEncoder.encode("matpath", "UTF-8") + "=" + URLEncoder.encode(String.format("%s", matpath), "UTF-8");
			if (urlStr != null)
			{
				url = new URL(urlStr + "/category.ctf");
				ucconn = (HttpURLConnection) url.openConnection();
				ucconn.setUseCaches(false);
				ucconn.setDoInput(true);
				ucconn.setDoOutput(true);
				wr = new OutputStreamWriter(ucconn.getOutputStream());
				wr.write(matData);
				wr.flush();
				wr.close();

				wr = null;

				if (ucconn.getResponseCode() != 200)
				{
					return null;
				}
				in = new BufferedReader(new InputStreamReader(ucconn.getInputStream(), "utf-8"));
				String inputLine;
				res = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
				{
					res.append(inputLine);
				}
				factory = DocumentBuilderFactory.newInstance();

				builder = factory.newDocumentBuilder();
				is = new InputSource(new StringReader(res.toString()));
				doc = builder.parse(is);
				list = doc.getElementsByTagName("data");

				String[] data = (list.item(0).getTextContent()).split(";");
				if (data.length > 1) 
				{
					result = new HashMap<>();
					int dna_no = Integer.parseInt(data[0]);
					result.put("dna_no", dna_no);
				}
			}

			return result;

		}
		catch (Exception e)
		{
			System.out.println("nodeConnection.getCategoryFeature() error : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally
		{
			File f = new File(saveDir + matpath);
			if (f.exists())
			{
				f.delete();
			}
			f = null;
			if (ucconn != null)
			{
				ucconn.disconnect();
				ucconn = null;
			}
			url = null;
			res = null;
			if (in != null) 
			{
				try 
				{
					in.close();
					in = null;
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			factory = null;
			builder = null;
			is = null;
			doc = null;
			list = null;
		}
	}

}
