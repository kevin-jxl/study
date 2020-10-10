package com.example.demo.spike.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * 项目名称：testQRcode
 * 类 名 称：GoodsMapper
 * 类 描 述：TODO
 * 创建时间：2020/8/18 16:50
 * 创 建 人：纪星亮
 */
@Mapper
public interface GoodsMapper {
    int updateGoodsNum(@Param("id") Integer id, @Param("num") Integer num);

    HashMap<String,Object> findById(Integer id);

    void update(@Param("id") Integer id, @Param("num") Integer num);
}