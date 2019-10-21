package project.pc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.pc.dao.NoticeDao;
import project.pc.model.noticeModel;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeDao noticeDao;
	@Autowired
	ServiceConfig serviceConfig;

	@Override
	public List<noticeModel> getNoticeList(Map<String, Object> paramMap) throws BizException {
		try{
			return noticeDao.getNoticeList(paramMap);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELIST_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void insertNotice(Map<String, Object> map) throws BizException {
		try{
			noticeDao.insertNotice(map);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.INSERTNOTICE_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public noticeModel getDetailData(int no) throws BizException {
		try{
			return noticeDao.getDetailData(no);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETDETAILDATA_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void updateNotice(noticeModel model) throws BizException {
		try{
			noticeDao.updateNotice(model);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.UPDATENOTICE_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void updateNoticeCount(int no) throws BizException {
		try{
			noticeDao.updateNoticeCount(no);
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.UPDATENOTICECOUNT_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public void deleteNotice(String[] no) throws BizException {
		try{
			List<String> NoList = new ArrayList<String>();
			for (int iy = 0; iy < no.length; iy++) {
				NoList.add(no[iy]);
			}

			noticeDao.deleteNotice(NoList);

			for (int iy = NoList.size() - 1; iy >= 0; iy--) {
				NoList.remove(iy);
			}
			NoList = null;
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.DELETENOTICE_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public String getNoticeList_Cnt() throws BizException {
		try{
			return noticeDao.getNoticeList_Cnt();
		}catch(Exception e){
			throw new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELISTCNT_ERROR_SERVICE + "/" + e.getMessage());
		}
	}

	@Override
	public String getNoticeFile(String no) {
		try{
			return noticeDao.getNoticeFile(no);
		}catch(Exception e){
			new BizException(MsgConstants.NOTICE_SYSTEM, MsgConstants.GETNOTICELISTCNT_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

}
