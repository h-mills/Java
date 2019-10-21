package project.pc.service;
import java.util.*;

import project.common.exception.BizException;
import project.pc.model.orderModel;
import project.pc.model.replyModel;
public interface OrderService {
	public List<orderModel> getOrderList(Map<String,Object> paramMap) throws BizException;
	public String getOrderCnt(Map<String,Object> paramMap) throws BizException;
	public orderModel getDetailInfo(int no) throws BizException;
	public void updateOrder(orderModel orderModel) throws BizException;
	public void updateToCancleOrder(int no) throws BizException;
	public void insertReply(replyModel replyModel) throws BizException;
	public List<replyModel> getReplyInfo(int no) throws BizException;
	public void updateNewCount(int no) throws BizException;
	public void updateReplyConfirm(int no) throws BizException;
	public void updateClientNew(int no) throws BizException;
	public HashMap<String, Object> xlsdowntozip(Map<String,Object> paramMap) throws BizException;
}
