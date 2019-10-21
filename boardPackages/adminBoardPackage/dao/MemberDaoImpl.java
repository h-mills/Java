package project.pc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;
import project.pc.model.memberModel;

@Service
public class MemberDaoImpl implements MemberDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public String getMemberList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("memberMapper.getMemberList_Cnt", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETMEMBERLIST_CNT_ERROR_DAO + "/" + e.getMessage());
			return "0";
		}
	}

	@Override
	public List<memberModel> getMemberList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("memberMapper.getMemberList", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETMEMBERLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public String getUserList_Cnt(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("memberMapper.getUserList_Cnt", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETUSERLIST_CNT_ERROR_DAO + "/" + e.getMessage());
			return "0";
		}
	}

	@Override
	public List<HashMap<String, Object>> getUserList(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectList("memberMapper.getUserList", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETUSERLIST_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int insertMember(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.insert("memberMapper.insertMember", paramMap);
		}
		catch(Exception e)
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.INSERTMEMBER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public int updateMember(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.update("memberMapper.updateMember", paramMap);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.UPDATEMEMBER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public int deleteMember(List<String> noList)
	{
		try
		{
			return masterSqlSessionTemplate.delete("memberMapper.deleteMember", noList);
		}
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.DELETEMEMBER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public memberModel getMember(Map<String, Object> paramMap)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("memberMapper.getMember", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETMEMBER_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> getCompany()
	{
		try
		{
			return masterSqlSessionTemplate.selectList("memberMapper.getCompany");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETCOMPANY_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int updateUserModify(Map<String, Object> paramMap)
	{
		try
		{
			masterSqlSessionTemplate.insert("memberMapper.updateUserModify", paramMap);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.UPDATEUSERMODIFY_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

}
