package project.pc.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.pc.dao.CardDao;
import project.pc.model.cardStatsModel;
import project.pc.model.companyModel;
import project.pc.model.orderModel;

@Service
@Transactional
public class CardServiceImpl implements CardService
{
	@Autowired
	CardDao cardDao;
	@Autowired
	ServiceConfig serviceConfig;

	@Override
	public List<companyModel> getCardCompanyList(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardCompanyList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDCOMPANYLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<orderModel> getCardOrderList(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardOrderList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDORDERLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<orderModel> getCardOrderListMake(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardOrderListMake(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDORDERLISTMAKE_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCardViewList(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardViewList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCardViewList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardViewList_Cnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int updateIsdelte(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.updateIsdelte(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEISDELTE_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateIsdelte_normal(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.updateIsdelte_normal(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEISDELTE_NORMAL_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Map<String, Object> getOrderData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getOrderData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETORDERDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertCardConfig(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.insertCardConfig(paramMap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.INSERTCARDCONFIG_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public int insertCardData_Master(Map<String, Object> data_master)
	{
		try
		{
			return cardDao.insertCardData_Master(data_master);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.INSERTCARDDATA_MASTER_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public void insertCardData(Map<String, Object> insertMap)
	{
		try
		{
			cardDao.insertCardData(insertMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.INSERTCARDDATA_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> getCardDown(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardDown(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDDOWN_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getCardOrderInfo(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardOrderInfo(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDORDERINFO_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getStatsOrderData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsOrderData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSORDERDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getStatsTotalCnt(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsTotalCnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSTOTALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<cardStatsModel> getStatsMonthData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsMonthData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSMONTHDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsDailyData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsDailyData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSDAILYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsHourData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsHourData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSHOURDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public Map<String, Object> getStatsLocalCnt(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsLocalCnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSLOCALCNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsNationData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsNationData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSNATIONDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsCityData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsCityData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSCITYDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getStatsAddressData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsAddressData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int updateOrderInfo(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.updateOrderInfo(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEORDERINFO_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public void updateCompanyInfo(Map<String, Object> paramMap)
	{
		try
		{
			cardDao.updateCompanyInfo(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATECOMPANYINFO_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getCardData(Map<String, Object> paraMap)
	{
		try
		{
			return cardDao.getCardData(paraMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int updateCardData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.updateCardData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATECARDDATA_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateImage(Map<String, Object> paramMap)
	{

		try
		{
			return cardDao.updateImage(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.UPDATEIMAGE_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCardViewList2(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardViewList2(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST2_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCardViewList_Cnt2(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardViewList_Cnt2(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT2_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCardViewList3(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardViewList3(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST3_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getCardViewList_Cnt3(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getCardViewList_Cnt3(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETCARDVIEWLIST_CNT3_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getStatsAddressData_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getStatsAddressData_Cnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETSTATSADDRESSDATA_CNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<cardStatsModel> getExcelStatsAddressData(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getExcelStatsAddressData(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETEXCELSTATSADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getOrderDataList(Map<String, Object> paramMap)
	{
		try
		{
			return cardDao.getOrderDataList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.GETEXCELSTATSADDRESSDATA_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public HashMap<String, Object> cardxlsdowntozip(Map<String, Object> paramMap) {
		List<HashMap<String, Object>> xlsList = null;
		HashMap<String, Object> result = new HashMap<>();
		try
		{
			xlsList = cardDao.getcardxlsfiles(paramMap);

			String zipName = serviceConfig.getRootPath() + UUID.randomUUID().toString() + ".zip";
			
			if (xlsList != null) {
				byte[] buf = new byte[4096];
				//압축 파일명
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipName));

				//파일 압축
				for (int i=0; i<xlsList.size(); i++) 
				{
					String filePath = serviceConfig.getRootPath() + xlsList.get(i).get("file").toString();
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
				}
				out.close();
			}
			result.put("zipFile", zipName);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.CARD_SYSTEM, MsgConstants.EXCELDOWN_ERROR + "/" + e.getMessage());
			return null;
		}
		return result;
	}

}