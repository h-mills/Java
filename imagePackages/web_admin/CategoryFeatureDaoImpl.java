package project.pc.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.common.config.MsgConstants;
import project.common.config.ServiceConfig;
import project.common.connect.nodeConnection;
import project.common.exception.BizException;
import project.pc.model.system.nodeModel;

@Repository("categoryfeaturedao")
public class CategoryFeatureDaoImpl implements CategoryFeatureDao
{
	@Autowired
	ServiceConfig serviceConfig;
	@Autowired
	private SqlSessionTemplate masterSqlSessionTemplate;
	List<HashMap<String, Object>> result = null;

	@Override
	public List<HashMap<String, Object>> getCategory(String matPath) throws BizException
	{
		try
		{
			String urlStr = null;
			List<nodeModel> nmodel = null;
			nmodel = masterSqlSessionTemplate.selectList("systemMapper.getNodesInfo");

			Thread[] NodeThread = new Thread[nmodel.size()];
			// Run Nodes
			for (int i = 0; i < nmodel.size(); i++)
			{
				if (nmodel.get(i).getStatus() == 1)// ready
				{
					urlStr = "http://" + nmodel.get(i).getIp() + ":81";
				}
				else if (nmodel.get(i).getStatus_mirror() == 1)
				{
					urlStr = "http://" + nmodel.get(i).getIp_mirror() + ":82";
				}
				else
					continue;

				NodeThread[i] = new Thread(new RunNode(urlStr, matPath, i, serviceConfig.getSaveDir()));
				NodeThread[i].start();
			}

			for (int i = 0; i < nmodel.size(); i++)
			{
				try
				{
					NodeThread[i].join();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			throw new BizException(MsgConstants.LOGIN_SYSTEM, MsgConstants.GETUSERNICKNAME_ERROR_DAO + "/" + e.getMessage());
		}
		return result;
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
			result = nodeC.getCategoryFeature(m_urlStr, m_path, m_saveDir);
		}
	}

	@Override
	public List<HashMap<String, Object>> getCategoryData(List<HashMap<String, Object>> paramMap) throws BizException
	{
		try
		{
			return masterSqlSessionTemplate.selectList("categoryMapper.getProductData", paramMap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
