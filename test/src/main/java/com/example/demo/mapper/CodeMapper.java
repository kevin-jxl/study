package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：test
 * 类 名 称：CodeMapper
 * 类 描 述：TODO
 * 创建时间：2020/10/12 16:46
 * 创 建 人：纪星亮
 */
@Mapper
public interface CodeMapper {
    List<HashMap> list(Map<String, Object> params);
}
