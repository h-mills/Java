package project.pc.dao;

import project.common.exception.BizException;
import project.pc.model.noticeModel;

import java.util.*;

public interface NoticeDao {
	public String getNoticeList_Cnt() throws BizException;
	public List<noticeModel> getNoticeList(Map<String, Object> paramMap) throws BizException;
	public void insertNotice(Map<String, Object> map) throws BizException;
	public void updateNoticeCount(int no) throws BizException;
	public noticeModel getDetailData(int no) throws BizException;
	public void updateNotice(noticeModel model) throws BizException;
	public int deleteNotice(List<String> noList) throws BizException;
	public String getNoticeFile(String no);
}
