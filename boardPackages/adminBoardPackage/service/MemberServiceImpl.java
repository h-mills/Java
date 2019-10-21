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
import project.pc.model.memberModel;


@Service
@Transactional
public class MemberServiceImpl implements MemberService
{
	@Autowired MemberDao memberDao;

	@Override
	public String getMemberList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return memberDao.getMemberList_Cnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETMEMBERLIST_CNT_ERROR_SERVICE + "/" + e.getMessage());
			return "0";
		}
	}

	@Override
	public List<memberModel> getMemberList(Map<String, Object> paramMap)
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
	public String getUserList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return memberDao.getUserList_Cnt(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETUSERLIST_CNT_ERROR_SERVICE + "/" + e.getMessage());
			return "0";
		}
	}

	@Override
	public List<HashMap<String, Object>> getUserList(Map<String, Object> paramMap)
	{
		try
		{
			return memberDao.getUserList(paramMap);
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETUSERLIST_ERROR_SERVICE + "/" + e.getMessage());
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
	public memberModel getMember(Map<String, Object> paramMap) {
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
	public List<HashMap<String, Object>> getCompany()
	{
		try
		{
			return memberDao.getCompany();
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETCOMPANY_ERROR_SERVICE + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int updateUserModify(Map<String, Object> paramMap)
	{
		try
		{
			memberDao.updateUserModify(paramMap);
			return 1;
		}
		catch (Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.UPDATEUSERMODIFY_ERROR_SERVICE + "/" + e.getMessage());
			return 0;
		}
	}

}