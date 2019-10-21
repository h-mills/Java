package project.pc.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;

@Service
public class LandingDAOImpl implements LandingDAO {

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;

	@Override
	public int insertQrDet(HashMap<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.insert("landingMapper.insertQrDet", paramMap);
		} 
		catch(Exception e)
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.INSERTQRDET_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int updateQrCount(HashMap<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.update("landingMapper.updateQrCount", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.UPDATEQRCOUNT_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public HashMap<String, Object> getLandingData(HashMap<String, Object> paramMap) {
		try
		{
			return masterSqlSessionTemplate.selectOne("landingMapper.getLandingData", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LANDING_SYSTEM, MsgConstants.GETLANDINGDATA_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

}
