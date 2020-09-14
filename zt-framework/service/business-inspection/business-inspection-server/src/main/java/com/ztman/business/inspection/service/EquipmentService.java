package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.dto.EquipmentDTO;
import com.ztman.business.inspection.client.entity.Equipment;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 设备表 服务接口层
 *
 * @author zxx
 * @date 2020-08-29 10:35:07
 */
public interface EquipmentService extends IService<Equipment> {
     

	public EquipmentDTO getBy(Long id);

}

