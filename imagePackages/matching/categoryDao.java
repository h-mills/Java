package dao;

import org.apache.ibatis.session.SqlSession;

import init.setProvider;

public class categoryDao {
	public int getCategoryCount(){
		SqlSession m_session = setProvider.getSession();
		int result = 0;
		try{
			result = m_session.selectOne("mainMapper.getCategoryCount");
			return result;
		}catch(Exception ex){
			return result;
		} finally{
			m_session.close();
		}
	}
}