package com.louis.fileGenerator.dao;

import java.util.List;
import java.util.Map;

/**
 * 文件数据DAO
 * 
 * @author lenovo
 *
 */
public interface FileDataDAO {

	/**
	 * 查找已入库数据
	 * 
	 * @param variableMap
	 */
	public List<Map<String, String>> selectStoredFileData(Map<String, String> variableMap);
}
