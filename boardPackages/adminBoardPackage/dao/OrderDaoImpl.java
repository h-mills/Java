package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.model.orderModel;
import project.pc.model.replyModel;

@Service
public class OrderDaoImpl implements OrderDao {

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate; 
	
	@Override
	public List<orderModel> getOrderList(Map<String,Object> paramMap) throws BizException {
		try{
			return masterSqlSessionTemplate.selectList("order.getOrderList",paramMap);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETORDERLIST_ERROR_DAO1 + "/" + e.getMessage());
		}
	}
	@Override
	public String getOrderCnt(Map<String,Object> paramMap) throws BizException {
		try{
			return masterSqlSessionTemplate.selectOne("order.getOrderCnt",paramMap);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETORDERCNT_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public orderModel getDetailInfo(int no) throws BizException {
		try{
			return masterSqlSessionTemplate.selectOne("order.getDetailInfo",no);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETDETAILINFO_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void updateOrder(orderModel orderModel) throws BizException {
		try{
			masterSqlSessionTemplate.update("order.updateOrder",orderModel);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEORDER_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void updateToCancleOrder(int no) throws BizException {
		try{
			masterSqlSessionTemplate.update("order.updateToCancleOrder",no);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATETOCANCELORDER_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void insertReply(replyModel replyModel) throws BizException {
		try{
			masterSqlSessionTemplate.insert("order.insertReply",replyModel);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.INSERTREPLY_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public List<replyModel> getReplyInfo(int no) throws BizException {
		try{
			return masterSqlSessionTemplate.selectList("order.getReplyInfo",no);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.GETREPLYINFO_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void updateNewCount(int no) throws BizException {
		try{
			masterSqlSessionTemplate.update("order.updateNewCount",no);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATENEWCOUNT_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void updateReplyConfirm(int no) throws BizException {
		try{
			masterSqlSessionTemplate.update("order.updateReplyConfirm",no);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEREPLYCONFIRM_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void updateClientNew(int no) throws BizException {
		try{
			masterSqlSessionTemplate.update("order.updateClientNew",no);
		}catch(Exception e){
			throw new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.UPDATEREPLYCONFIRM_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public List<HashMap<String, Object>> getxlsfiles(Map<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectList("order.getxlsfiles", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.ORDER_SYSTEM, MsgConstants.EXCELDOWN_ERROR + "/" + e.getMessage());
			return null;
		}
		finally 
		{
			paramMap.clear();
			paramMap = null;
		}
	}
}
