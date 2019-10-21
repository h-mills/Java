package project.pc.dao;


import java.util.Map;

import project.common.exception.BizException;

public interface LoginDao {
	public Map<String, Object> getUserNickname(String user_id, String user_pwd) throws BizException;
}
