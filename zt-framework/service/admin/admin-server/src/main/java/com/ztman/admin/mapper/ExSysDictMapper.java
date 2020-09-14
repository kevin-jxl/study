package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *   mapper层
 *
 * @author ztman code generator
 * @date 2019-03-08 13:39:56
 */
public interface ExSysDictMapper extends BaseMapper<SysDict> {

    List selectDicts(@Param("dictId") Integer dictId);

    void insetDictRelevance(@Param("indexId") Integer dictIndexId, @Param("dictId")Integer id);
}
