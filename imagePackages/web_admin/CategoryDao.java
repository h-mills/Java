package project.pc.dao;

import java.util.HashMap;
import java.util.List;

import project.common.exception.BizException;

public interface CategoryDao {
	public List<HashMap<String, Object>> getCategory(String matPath) throws BizException;
	public List<HashMap<String, Object>> getCategoryData(List<HashMap<String, Object>> paramMap) throws BizException;
}
