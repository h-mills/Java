package project.pc.service;

import java.io.BufferedOutputStream;
import java.io.File;
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

import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;

import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.pc.dao.OrderDao;
import project.pc.model.orderModel;
import project.pc.model.replyModel;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	ServiceConfig serviceConfig;
	@Autowired
	OrderDao orderDao;

	@Override
	public List<orderModel> getOrderList(Map<String, Object> paramMap) throws BizException {
		try {
			return orderDao.getOrderList(paramMap);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.GETORDERLIST_ERROR_SERVICE1 + "/" + e.getMessage());
		}
	}

	@Override
	public String getOrderCnt(Map<String, Object> paramMap) throws BizException {
		try {
			return orderDao.getOrderCnt(paramMap);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.GETORDERCNT_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public orderModel getDetailInfo(int no) throws BizException {
		try {
			return orderDao.getDetailInfo(no);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.GETDETAILINFO_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void updateOrder(orderModel orderModel) throws BizException {
		try {
			orderDao.updateOrder(orderModel);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.UPDATEORDER_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void updateToCancleOrder(int no) throws BizException {
		try {
			orderDao.updateToCancleOrder(no);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.UPDATETOCANCELORDER_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void insertReply(replyModel replyModel) throws BizException {
		try {
			orderDao.insertReply(replyModel);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.INSERTREPLY_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public List<replyModel> getReplyInfo(int no) throws BizException {
		try {
			return orderDao.getReplyInfo(no);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.GETREPLYINFO_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void updateNewCount(int no) throws BizException {
		try {
			orderDao.updateNewCount(no);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.UPDATENEWCOUNT_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void updateReplyConfirm(int no) throws BizException {
		try {
			orderDao.updateReplyConfirm(no);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.UPDATEREPLYCONFIRM_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void updateClientNew(int no) throws BizException {
		try {
			orderDao.updateClientNew(no);
		} catch (Exception e) {
			throw new BizException(MsgConstants.ORDER_SYSTEM,
					MsgConstants.UPDATEREPLYCONFIRM_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public HashMap<String, Object> xlsdowntozip(Map<String, Object> paramMap) throws BizException {
		List<HashMap<String, Object>> xlsList = null;
		HashMap<String, Object> result = new HashMap<>();
		WorkBookHandle book = null;
		try
		{
			xlsList = orderDao.getxlsfiles(paramMap);

			String zipName = null;
			
			if (xlsList != null && xlsList.size() > 0) {
				zipName = serviceConfig.getRootPath() + UUID.randomUUID().toString() + ".zip";
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
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.EXCELDOWN_ERROR + "/" + e.getMessage());
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
