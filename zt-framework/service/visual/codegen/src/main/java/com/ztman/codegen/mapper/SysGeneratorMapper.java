package com.ztman.codegen.mapper;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @date 2018-07-30
 */
public interface SysGeneratorMapper {

	/**
	 * 分页查询表格
	 *
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryList(Query params,@Param("tableName") String tableName);

	/**
	 * 查询表数量
	 *
	 * @param query
	 * @return
	 */
	int queryTotal(Map query);

	/**
	 * 查询表信息
	 * @param tableName 表名称
	 * @return
	 */
	Map<String, String> queryTable(String tableName);

	/**
	 * 查询表列信息
	 * @param tableName 表名称
	 * @return
	 */
	List<Map<String, String>> queryColumns(String tableName);
}
