package dao;

import init.setProvider;
import logic.nodeConnection;

import java.util.HashMap;
import java.util.List;

import model.common.nodeModel;

import org.apache.ibatis.session.SqlSession;

public class categoryDao {
	HashMap<String, Object> result = null;
	public HashMap<String, Object> getCategory(String matPath) {
		SqlSession m_session = setProvider.getSession();
		try 
		{
			String urlStr = null;
			List<nodeModel> nmodel = null;
			nmodel = m_session.selectList("systemMapper.getNodesInfo");

			Thread[] NodeThread = new Thread[nmodel.size()];
			//Run Nodes
			for(int i = 0; i < nmodel.size(); i++)
			{
				if(nmodel.get(i).getStatus() == 1)//ready
				{
					urlStr = "http://" + nmodel.get(i).getIp() + ":81";
				}else if(nmodel.get(i).getStatus_mirror() == 1) {
					urlStr = "http://" + nmodel.get(i).getIp_mirror() + ":82";
				}
				else continue;
				
				NodeThread[i] = new Thread(new RunNode(urlStr, matPath, i, "/scanhit_mobile/query/"));
				NodeThread[i].start();
			}
			
			for(int i = 0; i < nmodel.size(); i++)
			{
				try {
					NodeThread[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		catch (Exception e) 
		{
			result = null;
		}finally {
			m_session.close();
		}
		return result;
	}
	public HashMap<String, Object> getCategoryData(HashMap<String, Object> paramMap) {
		SqlSession m_session = setProvider.getSession();
		try {
			return m_session.selectOne("categoryMapper.getCategoryData", paramMap);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			m_session.close();
		}
	}
	public class RunNode implements Runnable
	{
		private String m_urlStr = null;
		private String m_path = null;
		private String m_saveDir = null;

		public RunNode(String urlStr, String matPath, int Index, String saveDir)
		{
			m_urlStr = urlStr;
			m_path = matPath;
			m_saveDir = saveDir;
		}

		public void run()
		{
			nodeConnection nodeC = new nodeConnection();
			result = nodeC.getCategory(m_urlStr, m_path, m_saveDir);
		}
	}
}
