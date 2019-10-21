package project.pc.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.common.config.ServiceConfig;
import project.common.exception.BizException;
import project.pc.dao.LoginDao;


@Service
@Transactional
public class LoginServiceImpl implements LoginService 
{
	@Autowired LoginDao 	loginDao;
	@Autowired ServiceConfig serviceConfig;
	
	@Override
	public Map<String, Object> getUserNickname(String user_id, String user_pwd) throws BizException 
	{
		// TODO Auto-generated method stub
		return loginDao.getUserNickname(user_id, user_pwd);
	}
}