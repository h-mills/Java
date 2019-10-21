package project.pc.dao;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class StatsMailDAOImpl implements StatsMailDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "statsMailMapper";

	@Override
	public int getDetCount(int interval) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<>();
		int count = 0;
		try {
			paramMap.put("interval", interval);
			count = sqlSession.selectOne(namespace+".getDetCount", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			paramMap.clear();
		}
		return count;
	}

	@Override
	public int getPeriodCount(int start, int end) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<>();
		int count = 0;
		try {
			paramMap.put("start", start);
			paramMap.put("end", end);
			count = sqlSession.selectOne(namespace+".getPeriodCount", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			paramMap.clear();
		}
		return count;
	}
	
	@Override
	public int getCusCount(int interval) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<>();
		int count = 0;
		try {
			paramMap.put("interval", interval);
			count = sqlSession.selectOne(namespace+".getCusCount", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			paramMap.clear();
		}
		return count;
	}
	
	@Override
	public int getCusDetCount() throws Exception {
		return sqlSession.selectOne(namespace+".getCusDetCount");
	}
	
	@Override
	public int getAppCount(int interval) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<>();
		int count = 0;
		try {
			paramMap.put("interval", interval);
			count = sqlSession.selectOne(namespace+".getAppCount", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			paramMap.clear();
		}
		return count;
	}
	
	@Override
	public int getPeriodAppCount(int start, int end) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<>();
		int count = 0;
		try {
			paramMap.put("start", start);
			paramMap.put("end", end);
			count = sqlSession.selectOne(namespace+".getPeriodAppCount", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			paramMap.clear();
		}
		return count;
	}
}
