package com.ztman.codegen.service;

import com.ztman.codegen.entity.GenConfig;
import com.ztman.common.core.util.Query;

import java.util.List;
import java.util.Map;

/**
 * @date 2018/7/29
 */
public interface SysGeneratorService {
	/**
	 * 生成代码
	 *
	 * @param tableNames 表名称
	 * @return
	 */
	byte[] generatorCode(GenConfig tableNames);

	/**
	 * 分页查询表
	 * @param query 查询条件
	 * @return
	 */
	List<Map<String,Object>> queryPage(Query query);
}
