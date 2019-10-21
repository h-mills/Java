package project.pc.service;

import java.util.Map;

import project.common.exception.BizException;

public interface LoginService {
	public Map<String, Object> getUserNickname(String user_id, String user_pwd) throws BizException;
}
