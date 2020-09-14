package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.dto.SysDeptDTO;
import com.ztman.business.inspection.client.entity.SysDept;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 部门 服务接口层
 *
 * @author zxx
 * @date 2020-08-30 01:58:13
 */
public interface SysDeptService extends IService<SysDept> {
 

	public List<SysDeptDTO> withList(Integer userId);

}

