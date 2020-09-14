package com.ztman.business.inspection.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;
import com.ztman.business.inspection.mapper.EquipmentMapper;
import com.ztman.business.inspection.client.dto.EquipmentDTO;
import com.ztman.business.inspection.client.entity.Equipment;
import com.ztman.business.inspection.service.EquipmentService;


@Service("equipmentService")
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    
    
	@Override
	public EquipmentDTO getBy(Long id) {
		// TODO Auto-generated method stub
		EquipmentDTO equipmentDTO = null;
		Equipment equipment=equipmentMapper.selectEquipmentById(id);
		if(null != equipment) {
			equipmentDTO = new EquipmentDTO();
			equipmentDTO.setId(equipment.getId());
			equipmentDTO.setName(equipment.getName());
			equipmentDTO.setCode(equipment.getCode());
			equipmentDTO.setInstallTime(equipment.getCreateTime());
			equipmentDTO.setType("暂无");
			equipmentDTO.setAddress("暂无");
			equipmentDTO.setOfParking("暂无");
			
			equipmentDTO.setId(equipment.getId());
		}
		return equipmentDTO;
	}

}
