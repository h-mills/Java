package project.pc.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;
import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.common.util.WebUtil;
import project.pc.dao.QrDAO;
import project.pc.generator.QRGenerator;

@Service
@Transactional
public class APIServiceImpl implements APIService
{
	@Autowired
	QrDAO qrDao;
	@Autowired
	ServiceConfig serviceConfig;

	@Override
	public JSONObject gen(HashMap<String, Object> paramMap) throws Exception
	{
		JSONObject jsonObj = new JSONObject();
		String title = paramMap.get("title").toString();
		String gen_name = paramMap.get("gen_name").toString();
		String imagePath = "";
		try
		{
			// DB 업로드
			int qr_config_no = qrDao.insertQRConfig(title, gen_name);
			if (qr_config_no > 0)
			{
				paramMap.put("qr_config_no", qr_config_no);
				int qr_master_no = qrDao.insertQRMaster(paramMap);
				if (qr_master_no > 0)
				{
					imagePath = serviceConfig.getImgSavePath() + WebUtil.getDate() + "/" + qr_master_no + ".jpg";
					qrDao.updateImgPath(qr_master_no, imagePath);
					// qr 생성
					paramMap.put("saveDir", serviceConfig.getRootPath() + serviceConfig.getImgSavePath() + WebUtil.getDate());
					paramMap.put("qr_master_no", qr_master_no);
					paramMap.put("imgPath", serviceConfig.getRootPath() + imagePath);
					paramMap.put("workDir", serviceConfig.getRootPath() + serviceConfig.getJNIDir());
					paramMap.put("landingUrl", serviceConfig.getDomainInfo() + serviceConfig.getLandingUrl());
					paramMap.put("bgcolor", "#FFFFFF");
					paramMap.put("fgcolor", "#000000");
					paramMap.put("ltcolor", "#000000");
					paramMap.put("lbcolor", "#000000");
					paramMap.put("rtcolor", "#000000");
					QRGenerator qrgen = new QRGenerator();
					int result = qrgen.createQR(paramMap);
					qrgen = null;
					if (result == 1)
					{
						File file = new File(paramMap.get("imgPath").toString());
						String extension = imagePath.substring(imagePath.lastIndexOf(".") + 1);
						FileInputStream ins = null;
						try
						{
							// Reading a Image file from file system
							ins = new FileInputStream(file);
							byte imageData[] = new byte[(int) file.length()];
							ins.read(imageData);

							// Converting Image byte array into Base64 String
							String imageDataString = Base64.encodeBase64URLSafeString(imageData);

							jsonObj.put("result", "1");
							jsonObj.put("extension", extension);
							jsonObj.put("image", imageDataString);
						}
						catch (Exception e)
						{
							new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GEN_ERROR_SERVICE + "/" + e.getMessage());
							jsonObj.put("result", "-1");
						}
						finally
						{
							ins.close();
						}
					}
					else
					{
						jsonObj.put("result", "-1");
					}
				}
				else
				{
					jsonObj.put("result", "-1");
				}
			}
			else
			{
				jsonObj.put("result", "-1");
			}
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.QR_SYSTEM, MsgConstants.GEN_ERROR_SERVICE + "/" + e.getMessage());
			jsonObj.put("result", "-1");
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
		return jsonObj;
	}
}
