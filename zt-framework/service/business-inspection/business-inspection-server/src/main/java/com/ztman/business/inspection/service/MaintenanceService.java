package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.dto.MaintenanceDTO;
import com.ztman.business.inspection.client.dto.WorkOrderDTO;
import com.ztman.business.inspection.client.entity.Maintenance;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 工单过程表 服务接口层
 *
 * @author zxx
 * @date 2020-08-29 10:34:49
 */
public interface MaintenanceService extends IService<Maintenance> {
    

	public MaintenanceDTO info(String workOrderId);

}

