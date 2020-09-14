package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysDictType;

import java.util.List;

/**
 * mapper层ex
 *
 * @author ztman code generator
 * @date 2019-03-08 09:40:04
 */
public interface ExSysDictTypeMapper extends BaseMapper<SysDictType> {
    //根据typeId查询出所有子节点
    List<Integer> getChildrenOrgOfDictType(Integer typeId);
}
