package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.common.exception.BizException;
import project.pc.model.orderModel;
import project.pc.model.replyModel;

public interface OrderDao {
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
	public List<HashMap<String, Object>> getxlsfiles(Map<String, Object> paramMap);
}
