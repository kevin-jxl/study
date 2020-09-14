package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.dto.MaintenanceDTO;
import com.ztman.business.inspection.client.dto.WorkOrderDTO;
import com.ztman.business.inspection.client.entity.WorkOrder;
import com.ztman.business.inspection.client.vo.TrendVO;
import com.ztman.business.inspection.client.vo.WorkOrderBoardVO;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 工单表 服务接口层
 *
 * @author zxx
 * @date 2020-08-27 22:07:49
 */
public interface WorkOrderService extends IService<WorkOrder> {
      
	public WorkOrderDTO getWorkOrderBy(String id, int userId);  
	public List<WorkOrderBoardVO> boardByPage(Integer userId, int page, int size, int type);
	public boolean complete(Integer userId, String realName, MaintenanceDTO maintenanceDTO) throws Exception;
	public boolean accept(Integer userId,String realName, String workOrderId);
	public TrendVO trend(Integer userId, int cycle);
	public TrendVO duration( Integer userId, int cycle);
	public Map<String, Integer> number(Integer userId);

}

