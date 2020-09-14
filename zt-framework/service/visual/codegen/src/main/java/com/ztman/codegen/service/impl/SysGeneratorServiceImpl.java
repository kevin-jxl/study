package com.ztman.codegen.service.impl;

import cn.hutool.core.io.IoUtil;
import com.ztman.codegen.entity.GenConfig;
import com.ztman.codegen.mapper.SysGeneratorMapper;
import com.ztman.codegen.service.SysGeneratorService;
import com.ztman.codegen.util.GenUtils;
import com.ztman.common.core.util.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @date 2018-07-30
 */
@Service
@AllArgsConstructor
public class SysGeneratorServiceImpl implements SysGeneratorService {
	private final SysGeneratorMapper sysGeneratorMapper;


	/**
	 * 分页查询表
	 *
	 * @param query 查询条件
	 * @return
	 */
	@Override
	public List<Map<String,Object>> queryPage(Query query) {

		String tableName = (String) query.getCondition().get("tableName");
		return sysGeneratorMapper.queryList(query, tableName);
	}

	/**
	 * 生成代码
	 *
	 * @param genConfig 生成配置
	 * @return
	 */
	@Override
	public byte[] generatorCode(GenConfig genConfig) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		//查询表信息
		Map<String, String> table = queryTable(genConfig.getTableName());
		//查询列信息
		List<Map<String, String>> columns = queryColumns(genConfig.getTableName());
		//生成代码
		GenUtils.generatorCode(genConfig, table, columns, zip);
		IoUtil.close(zip);
		return outputStream.toByteArray();
	}

	private Map<String, String> queryTable(String tableName) {
		return sysGeneratorMapper.queryTable(tableName);
	}

	private List<Map<String, String>> queryColumns(String tableName) {
		return sysGeneratorMapper.queryColumns(tableName);
	}
}
