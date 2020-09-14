package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysDictIndex;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper层ex
 *
 * @author ztman code generator
 * @date 2019-03-08 10:41:04
 */
public interface ExSysDictIndexMapper extends BaseMapper<SysDictIndex> {

    /**
     * 根据分类查询字典列表
     *
     * @param typeIds
     * @return
     */
    List selectDictIndex(@Param("typeIds") List<Integer> typeIds);
}
