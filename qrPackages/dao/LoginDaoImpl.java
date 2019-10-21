package project.pc.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.exception.BizException;

@Service
public class LoginDaoImpl implements LoginDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public Map<String, Object> getUserNickname(String user_id, String user_pwd) throws BizException
	{
		Map<String, Object> paramMap = null;
		try
		{
			paramMap = new HashMap<>();
			paramMap.put("id", user_id);
			paramMap.put("pwd", user_pwd);
			return masterSqlSessionTemplate.selectOne("logIn.getUserNickname", paramMap);
		} 
		catch(Exception e) 
		{
			throw new BizException("DBER01", "DB select 오류" + " / " + e.getMessage());
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}
}
