package project.pc.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.common.config.MsgConstants;
import project.common.exception.BizException;

@Service
public class LoginDaoImpl implements LoginDao 
{

	@Autowired private SqlSessionTemplate masterSqlSessionTemplate;
	
	@Override
	public int idcheck(String user_id)
	{
		HashMap<String, Object> paramMap = null;
		try
		{
			paramMap = new HashMap<>();
			paramMap.put("id", user_id);
			return masterSqlSessionTemplate.selectOne("logIn.idcheck", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.IDCHECK_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}
	
	@Override
	public String biznumbercheck(String biznumber)
	{
		HashMap<String, Object> paramMap = null;
		try
		{
			paramMap = new HashMap<>();
			paramMap.put("biznumber", biznumber);
			return masterSqlSessionTemplate.selectOne("logIn.biznumbercheck", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.BIZNUMBERCHECK_ERROR_DAO + "/" + e.getMessage());
			return "-1";
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}
	
	@Override
	public HashMap<String, Object> getcompanyinfo(String no)
	{
		HashMap<String, Object> paramMap = null;
		try
		{
			paramMap = new HashMap<>();
			paramMap.put("no", no);
			return masterSqlSessionTemplate.selectOne("logIn.getcompanyinfo", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETBIZINFO_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}
	
	@Override
	public int insertCompany(HashMap<String, Object> param)
	{
		try
		{
			masterSqlSessionTemplate.insert("logIn.insertCompany", param);
			int no = (Integer)param.get("no");
			return no;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.REGIST_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int updateCompany(HashMap<String, Object> param)
	{
		try
		{
			masterSqlSessionTemplate.update("logIn.updateCompany", param);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.UPDATECOMPANY_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public void updateCompanyFilepath(HashMap<String, Object> param) throws BizException
	{
		try
		{
			masterSqlSessionTemplate.update("logIn.updateCompanyFilepath", param);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.FILEUPDATE_ERROR_DAO + "/" + e.getMessage());
		}
	}
	
	@Override
	public void insertUser(HashMap<String, Object> param) throws BizException
	{
		try
		{
			masterSqlSessionTemplate.insert("logIn.insertUser", param);
		} 
		catch(Exception e) 
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.REGISTUSER_ERROR_DAO + "/" + e.getMessage());
		}
	}
	
	@Override
	public HashMap<String, Object> checkUserInfo(HashMap<String, Object> param)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("logIn.checkUserInfo", param);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.LOGIN_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getid(HashMap<String, Object> param)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("logIn.getid", param);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETID_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getpwd(HashMap<String, Object> param)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("logIn.getpwd", param);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETID_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public HashMap<String, Object> getuserinfo(HashMap<String, Object> param)
	{
		try
		{
			return masterSqlSessionTemplate.selectOne("logIn.getuserinfo", param);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETUSERINFO_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public int updateuserinfo(HashMap<String, Object> param)
	{
		try
		{
			masterSqlSessionTemplate.update("logIn.updateuserinfo", param);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.UPDATEUSER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int updatecompanyinfo(HashMap<String, Object> param)
	{
		try
		{
			masterSqlSessionTemplate.update("logIn.updatecompanyinfo", param);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.UPDATECOMPANYINFO_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int updateuserdelete(HashMap<String, Object> param)
	{
		try
		{
			masterSqlSessionTemplate.update("logIn.updateuserdelete", param);
			return 1;
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR_DAO + "/" + e.getMessage());
			return -1;
		}
	}

	@Override
	public List<HashMap<String, Object>> getIndustryList()
	{
		try
		{
			return masterSqlSessionTemplate.selectList("logIn.getIndustryList");
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
	}

	@Override
	public int deleteIndustry(HashMap<String, Object> param)
	{
		try
		{
			return masterSqlSessionTemplate.delete("logIn.deleteIndustry", param);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public int insertIndustry(HashMap<String, Object> param)
	{
		try
		{
			return masterSqlSessionTemplate.insert("logIn.insertIndustry", param);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.DELETEUSER_ERROR_DAO + "/" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> getIndustryInfo(String no)
	{
		HashMap<String, Object> paramMap = null;
		try
		{
			paramMap = new HashMap<>();
			paramMap.put("no", no);
			return masterSqlSessionTemplate.selectList("logIn.getIndustryInfo", paramMap);
		} 
		catch(Exception e) 
		{
			new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETBIZINFO_ERROR_DAO + "/" + e.getMessage());
			return null;
		}
		finally
		{
			paramMap.clear();
			paramMap = null;
		}
	}
}
