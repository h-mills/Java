package project.pc.service;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.dao.MemberDao;


@Service
@Transactional
public class MemberServiceImpl implements MemberService
{
	@Autowired MemberDao memberDao;

	@Override
	public String getMemberList_Cnt()
	{
		try
		{
			return memberDao.getMemberList_Cnt();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETMEMBERLIST_CNT_ERROR_SERVICE + "/" + e.getMessage());
			return "0";
		}
	}

	@Override
	public List<HashMap<String,Object>> getMemberList(Map<String, Object> paramMap)
	{
		try
		{
			return memberDao.getMemberList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETMEMBERLIST_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertMember(Map<String, Object> paramMap) {
		try
		{
			return memberDao.insertMember(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.INSERTMEMBER_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public int updateMember(Map<String, Object> paramMap) {
		try
		{
			return memberDao.updateMember(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.UPDATEMEMBER_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public int deleteMember(String[] no) {
		List<String> NoList = new ArrayList<String>();
		int result = -1;
		try
		{
			for(int iy = 0; iy < no.length; iy++)
			{
				NoList.add(no[iy]);
			}
			
			result = memberDao.deleteMember(NoList);
			
			for(int iy = NoList.size()-1; iy >= 0; iy--)
			{
				NoList.remove(iy);
			}
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.DELETEMEMBER_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
		finally
		{
			NoList.clear();
			NoList = null;
		}
		return result;
	}

	@Override
	public HashMap<String,Object> getMember(Map<String, Object> paramMap) {
		try
		{
			return memberDao.getMember(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETMEMBER_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int getIdCount(String id) {
		int count = 0;
		try
		{
			count = memberDao.getIdCount(id);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETIDCOUNT_ERROR_SERVICE + "/" + e.getMessage());
			return -1;
		}
		return count;
	}

}