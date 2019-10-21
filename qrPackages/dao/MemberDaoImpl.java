package project.pc.dao;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;

@Service
public class MemberDaoImpl implements MemberDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public String getMemberList_Cnt()
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("memberMapper.getMemberList_Cnt");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETMEMBERLIST_CNT_ERROR_DAO + "/" + e.getMessage());
			return "0";
		}
	}

	@Override
	public List<HashMap<String, Object>> getMemberList(Map<String, Object> paramMap)
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
	public HashMap<String,Object> getMember(Map<String, Object> paramMap)
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
	public int getIdCount(String id) {
		int count = 0;
		try
		{
			count = masterSqlSessionTemplate.selectOne("memberMapper.getIdCount", id);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.MEMBER_SYSTEM, MsgConstants.GETIDCOUNT_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
		return count;
	}

}
