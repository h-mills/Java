package project.pc.service;

import java.util.HashMap;
import java.util.List;

import project.common.exception.BizException;

public interface CategoryService {
	public List<HashMap<String, Object>> getCategory(String filepath) throws BizException;
	public List<HashMap<String, Object>> getCategoryData(List<HashMap<String, Object>> paramMap) throws BizException;
}
