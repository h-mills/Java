package project.pc.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.model.noticeModel;

@Service
public class NoticeDaoImpl implements NoticeDao {

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public List<noticeModel> getNoticeList(Map<String, Object> paramMap) throws BizException {
		try{
			return masterSqlSessionTemplate.selectList("notice.getNoticeList",paramMap);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public void insertNotice(Map<String, Object> map) throws BizException {
		try{
			masterSqlSessionTemplate.insert("notice.insertNotice",map);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.INSERTNOTICE_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void updateNoticeCount(int no) throws BizException {
		try{
			masterSqlSessionTemplate.update("notice.updateNoticeCount",no);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.UPDATENOTICECOUNT_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public noticeModel getDetailData(int no) throws BizException {
		try{
			return masterSqlSessionTemplate.selectOne("notice.getDetailData",no);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETDETAILDATA_ERROR_DAO + "/" + e.getMessage());
		}
	}
	@Override
	public void updateNotice(noticeModel model) throws BizException {
		try{
			masterSqlSessionTemplate.update("notice.updateNotice",model);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.UPDATENOTICE_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public int deleteNotice(List<String> noList) throws BizException {
		try
		{
			return masterSqlSessionTemplate.delete("notice.deleteNotice", noList);
		}
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.DELETENOTICE_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public String getNoticeList_Cnt() throws BizException {
		try{
			return masterSqlSessionTemplate.selectOne("notice.getNoticeList_Cnt");
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELISTCNT_ERROR_DAO + "/" + e.getMessage());
		}
	}

	@Override
	public String getNoticeFile(String no) {
		try{
			return masterSqlSessionTemplate.selectOne("notice.getNoticeFile", no);
		}catch(Exception e){
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELISTCNT_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
}
