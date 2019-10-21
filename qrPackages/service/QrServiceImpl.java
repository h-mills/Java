package project.pc.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.common.util.FileUtil;
import project.common.util.WebUtil;
import project.pc.dao.QrDAO;
import project.pc.generator.QRGenerator;

@Service
@Transactional
public class QrServiceImpl implements QrService {
	@Autowired QrDAO qrDao;
	@Autowired ServiceConfig serviceConfig;
	
	@Override
	public List<HashMap<String, Object>> getQRList(Map<String, Object> paramMap) {
		try
		{
			return qrDao.getQRList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETQRLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public String getQRListCount(Map<String, Object> paramMap) {
		try{
			return qrDao.getQRListCount(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETQRLISTCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public int updateIsdelte(Map<String, Object> paramMap) {
		try{
			return qrDao.updateIsdelte(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.UPDATEISDELTE_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateIsdelte_normal(Map<String, Object> paramMap) {
		try{
			return qrDao.updateIsdelte_normal(paramMap);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.UPDATEISDELTE_NORMAL_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public HashMap<String, Object> getQRInfo(HashMap<String, Object> requestMap) {
		try{
			return qrDao.getQRInfo(requestMap);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETQRINFO_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}
	@Override
	public int updateQRData(HashMap<String, Object> requestMap) {
		try{
			return qrDao.updateQRData(requestMap);
		}catch(Exception e){
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.UPDATEQRDATA_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}
	@Override
	public HashMap<String, Object> getImageList(HashMap<String, Object> noMap) {
		List<HashMap<String, Object>> imgList = null;
		HashMap<String, Object> result = new HashMap<>();
		WorkBookHandle book = null;
		try
		{
			imgList = qrDao.getImageList(noMap);

			//엑셀파일 생성
			book = new WorkBookHandle();
			WorkSheetHandle sheet = null;
			sheet = book.getWorkSheet(0);
			String[] addrArry = {"A", "B", "C"};
			String[] category = {"URL", "이메일", "GPS", "전화번호", "SMS", "명함", "메시지"};
			int colIdx = 0;
			String excelPath = serviceConfig.getRootPath()+"qrInfo.xls";

			sheet.add("이미지파일명", addrArry[0] + String.valueOf(1));
			sheet.add("구분", addrArry[1] + String.valueOf(1));
			sheet.add("데이터", addrArry[2] + String.valueOf(1));
			for(int i = 0;i<imgList.size();i++){
	            sheet.add(imgList.get(i).get("no").toString(), addrArry[colIdx] + String.valueOf(i+2));
	            sheet.add(category[(Integer)imgList.get(i).get("category") - 1], addrArry[++colIdx] + String.valueOf(i+2));
	            sheet.add(imgList.get(i).get("data").toString(), addrArry[++colIdx] + String.valueOf(i+2));
	            colIdx = 0;
	        }

			File f = new File(excelPath);
            FileOutputStream fos = new FileOutputStream(f);
            BufferedOutputStream bbout = new BufferedOutputStream(fos);
            book.write(bbout);
            bbout.flush();
		    fos.close();

			String zipName = serviceConfig.getRootPath() + UUID.randomUUID().toString() + ".zip";
			
			if (imgList != null) {
				byte[] buf = new byte[4096];
				//압축 파일명
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipName));

				//파일 압축
				for (int i=0; i<imgList.size(); i++) 
				{
					String filePath = serviceConfig.getRootPath() + imgList.get(i).get("image").toString();
					FileInputStream in = new FileInputStream(filePath);
			        Path p = Paths.get(filePath);
			        String fileName = p.getFileName().toString();

			        //압축 항목추가
			        ZipEntry ze = new ZipEntry(fileName);
			        out.putNextEntry(ze);

			        //바이트 전송
			        int len;
			        while ((len = in.read(buf)) > 0) 
			        {
			            out.write(buf, 0, len);
			        }

			        out.closeEntry();
			        in.close();

			        if (i == 0) {
			        	filePath = excelPath;
						in = new FileInputStream(filePath);
				        p = Paths.get(filePath);
				        fileName = p.getFileName().toString();

				        //압축 항목추가
				        ze = new ZipEntry(fileName);
				        out.putNextEntry(ze);

				        //바이트 전송
				        while ((len = in.read(buf)) > 0) 
				        {
				            out.write(buf, 0, len);
				        }

				        out.closeEntry();
				        in.close();
			        }
				}
				out.close();
			}
			result.put("zipFile", zipName);
			result.put("excelFile", excelPath);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETIMAGELIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
		finally
		{
			if (book != null)
			{
				book.close();
				book = null;
			}
		}
		return result;
	}
	@Override
	public JSONObject gen(MultipartHttpServletRequest request, HashMap<String, Object> paramMap) 
	{
		JSONObject jsonObj = new JSONObject();
		String title = paramMap.get("title").toString();
		String gen_name = paramMap.get("gen_name").toString();
		String imagePath = "";
		try
		{
			//DB 업로드
			int qr_config_no = qrDao.insertQRConfig(title, gen_name);
			paramMap.put("qr_config_no", qr_config_no);
			int qr_master_no = qrDao.insertQRMaster(paramMap);
			imagePath = serviceConfig.getImgSavePath()+WebUtil.getDate()+"/"+qr_master_no+".jpg";
			qrDao.updateImgPath(qr_master_no, imagePath);
			//qr 생성
			paramMap.put("saveDir", serviceConfig.getRootPath() + serviceConfig.getImgSavePath() + WebUtil.getDate());
			paramMap.put("qr_master_no", qr_master_no);
			paramMap.put("imgPath", serviceConfig.getRootPath() + imagePath);
			paramMap.put("workDir", serviceConfig.getRootPath() + serviceConfig.getJNIDir());
			paramMap.put("landingUrl", serviceConfig.getDomainInfo() + serviceConfig.getLandingUrl());
			QRGenerator qrgen = new QRGenerator();
			qrgen.createQR(paramMap);
			
			jsonObj.put("qr_master_no", qr_master_no);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GEN_ERROR_SERVICE + "/" + e.getMessage());
			jsonObj.put("result", -1);
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return jsonObj;
	}
	@Override
	public String getfilepath(HashMap<String, Object> requestMap)
	{
		try
		{
			return qrDao.getfilepath(requestMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GETFILEPATH_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public JSONObject largegen(MultipartHttpServletRequest request, HashMap<String, Object> paramMap) 
	{
		JSONObject jsonObj = new JSONObject();
		JSONArray qrList = new JSONArray();
		JSONObject listScan = new JSONObject();
		WorkBookHandle tbo = null;

		try
		{
			String title = paramMap.get("title").toString();
			String gen_name = paramMap.get("gen_name").toString();
			int qr_config_no = Integer.parseInt(paramMap.get("qr_config_no").toString());

			String filePath = null;

			int startRow = Integer.parseInt(request.getParameter("startRow"));
			int totalRow = Integer.parseInt(request.getParameter("totalRow")); // 총 row 수

			if (startRow == 0) {
				MultipartFile file = request.getFile("file");
				filePath = serviceConfig.getRootPath() + serviceConfig.getExcelPath() + WebUtil.getDate();
				String ext = FileUtil.getFileExtension(file.getOriginalFilename());
				String fileName = UUID.randomUUID().toString() + "." + ext;

				//디렉토리 생성
				File f = new File(filePath);
				if(f.exists() == false)
				{
					f.mkdirs();
				}
				f = null;

				//파일 업로드
				filePath = filePath + "/" + fileName;
				f = new File(filePath);
				file.transferTo(f);
				f = null;

				//totalRow 구하기
				tbo = new WorkBookHandle(filePath);
				WorkSheetHandle sheet = tbo.getWorkSheet(0);

				String tmp = "";
				for (int i=0; i<sheet.getRows().length; i++) {
					 tmp = sheet.getRow(i).getCells()[0].getStringVal();
					if (tmp.equals("") || tmp == null) {
						break;
					} else {
						totalRow++;
					}
				}

				if (totalRow <= 1) {
					jsonObj.put("result", -1);
				} else {
					qr_config_no = qrDao.insertLargeConfig(title, gen_name, fileName);

					jsonObj.put("startRow", 1);
					jsonObj.put("totalRow", totalRow);
					jsonObj.put("filePath", filePath);
					jsonObj.put("qr_config_no", qr_config_no);
					jsonObj.put("result", 1);
				}
			}

			if (startRow >= 1) {
				if (qr_config_no <= 0) {
					jsonObj.put("result", -1);
				}
				else 
				{
					HashMap<String, Object> dataMap = new HashMap<>();

					filePath = request.getParameter("filePath");

					int cut = 10; // 한번에 파싱할 ROW 수
					int endRow = 0;
					
					endRow = startRow + cut;
					if (endRow >= totalRow) {
						endRow = totalRow;
					}

					//엑셀파싱
					Date d = new Date();
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			        tbo = new WorkBookHandle(filePath);
					WorkSheetHandle sheet = tbo.getWorkSheet(0);
					String[] colArr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

					StringBuilder data = new StringBuilder();
					String text = null;
					String category = null;
					String categoryName = null;

					for (int i=startRow; i<endRow; i++) {
						for (int j=0; j<colArr.length; j++) {
							text = sheet.getCell(colArr[j] + String.valueOf(i+1)).getStringVal();
							if (j==0) {
								category = text;
								switch (category) {
								case "1": categoryName="URL"; break;
								case "2": categoryName="이메일"; break;
								case "3": categoryName="GPS"; break;
								case "4": categoryName="전화번호"; break;
								case "5": categoryName="SMS"; break;
								case "6": categoryName="명함"; break;
								case "7": categoryName="메시지"; break;
								}
								listScan.put("category", category);
								listScan.put("categoryName", categoryName);
								dataMap.put("category", category);
							} else {
								if (category.equals("3")) { //GPS
									if (j < 4) {
										data.append(text);
										data.append(";");
									} else {break;}
								} else if (category.equals("5")) { //SMS
									if (j < 3) {
										data.append(text);
										data.append(";");
									} else {break;}
								} else if (category.equals("6")) { //명함
									if (j < 10) {
										data.append(text);
										data.append(";");
									} else {break;}
								} else if (category.equals("7")) { //메시지
									if (j < 2) {
										data.append(text);
										data.append(";");
									} else {break;}
								} else { //URL, 이메일, 전화번호
									if (j < 6) {
										data.append(text);
										data.append(";");
									} else {break;}
								}
							}
						}

						listScan.put("data", data.substring(0, data.lastIndexOf(";")).toString());
						dataMap.put("data", listScan.get("data"));
						dataMap.put("qr_config_no", qr_config_no);
						//qr DB 입력
						int qr_master_no = qrDao.insertQr(dataMap);
				        String imgPath = "img/" + sdf.format(d).toString() + "/" + String.valueOf(qr_master_no) + ".jpg";
				        //이미지경로 업데이트
						qrDao.updateImage(qr_master_no, imgPath);

						listScan.put("rownum", i);
						listScan.put("title", title);
						listScan.put("gen_name", gen_name);
						listScan.put("image", "/" + imgPath);
						qrList.add(listScan);
						//qr 이미지 생성
						paramMap.put("saveDir", serviceConfig.getRootPath() + serviceConfig.getImgSavePath() + WebUtil.getDate());
						paramMap.put("qr_master_no", qr_master_no);
						paramMap.put("imgPath", serviceConfig.getRootPath() + imgPath);
						paramMap.put("workDir", serviceConfig.getRootPath() + serviceConfig.getJNIDir());
						paramMap.put("landingUrl", serviceConfig.getDomainInfo() + serviceConfig.getLandingUrl());
						QRGenerator qrgen = new QRGenerator();
						qrgen.createQR(paramMap);

						data.delete(0, data.length());
						startRow++;
					}
					jsonObj.put("startRow", startRow);
					jsonObj.put("totalRow", totalRow);
					jsonObj.put("filePath", filePath);
					jsonObj.put("qr_config_no", qr_config_no);
					jsonObj.put("qrList", qrList);
					jsonObj.put("result", 1);
				}
			}
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.LARGEGEN_ERROR_SERVICE + "/" + e.getMessage());
			jsonObj.put("result", -1);
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
			if (tbo != null) 
			{
				tbo.close();
				tbo = null;
			}
		}
		return jsonObj;
	}

	@Override
	public HashMap<String, Object> largedown(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> imgList = null;
		HashMap<String, Object> result = new HashMap<>();
		WorkBookHandle book = null;
		try
		{
			imgList = qrDao.getImages(paramMap);

			//엑셀파일 생성
			book = new WorkBookHandle();
			WorkSheetHandle sheet = null;
			sheet = book.getWorkSheet(0);
			String[] addrArry = {"A", "B", "C"};
			String[] category = {"URL", "이메일", "GPS", "전화번호", "SMS", "명함", "메시지"};
			int colIdx = 0;
			String excelPath = serviceConfig.getRootPath() + "qrInfo.xls";

			sheet.add("이미지파일명", addrArry[0] + String.valueOf(1));
			sheet.add("구분", addrArry[1] + String.valueOf(1));
			sheet.add("데이터", addrArry[2] + String.valueOf(1));
			for(int i = 0;i<imgList.size();i++){
	            sheet.add(imgList.get(i).get("no").toString(), addrArry[colIdx] + String.valueOf(i+2));
	            sheet.add(category[(Integer)imgList.get(i).get("category") - 1], addrArry[++colIdx] + String.valueOf(i+2));
	            sheet.add(imgList.get(i).get("data").toString(), addrArry[++colIdx] + String.valueOf(i+2));
	            colIdx = 0;
	        }

			File f = new File(excelPath);
            FileOutputStream fos = new FileOutputStream(f);
            BufferedOutputStream bbout = new BufferedOutputStream(fos);
            book.write(bbout);
            bbout.flush();
		    fos.close();

			String zipName = serviceConfig.getRootPath() + UUID.randomUUID().toString() + ".zip";
			
			if (imgList != null) {
				byte[] buf = new byte[4096];
				//압축 파일명
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipName));

				//파일 압축
				for (int i=0; i<imgList.size(); i++) 
				{
					String filePath = serviceConfig.getRootPath() + imgList.get(i).get("image").toString();
					FileInputStream in = new FileInputStream(filePath);
			        Path p = Paths.get(filePath);
			        String fileName = p.getFileName().toString();

			        //압축 항목추가
			        ZipEntry ze = new ZipEntry(fileName);
			        out.putNextEntry(ze);

			        //바이트 전송
			        int len;
			        while ((len = in.read(buf)) > 0) 
			        {
			            out.write(buf, 0, len);
			        }

			        out.closeEntry();
			        in.close();

			        if (i == 0) {
			        	filePath = excelPath;
						in = new FileInputStream(filePath);
				        p = Paths.get(filePath);
				        fileName = p.getFileName().toString();

				        //압축 항목추가
				        ze = new ZipEntry(fileName);
				        out.putNextEntry(ze);

				        //바이트 전송
				        while ((len = in.read(buf)) > 0) 
				        {
				            out.write(buf, 0, len);
				        }

				        out.closeEntry();
				        in.close();
			        }
				}
				out.close();
			}
			result.put("zipFile", zipName);
			result.put("excelFile", excelPath);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.LARGEDOWN_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
		finally 
		{
			if (book != null)
			{
				book.close();
				book = null;
			}
		}
		return result;
	}
}
