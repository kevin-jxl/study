package com.ztman.business.inspection.client.vo;

import java.util.List;

import com.ztman.business.inspection.client.entity.WorkOrder;
import com.ztman.business.inspection.client.entity.WorkOrderProcess;

import lombok.Data;

@Data
public class WorkOrderBoardVO {
	private WorkOrder workOrder;
	private List<WorkOrderProcess> workOrderProcesses;
	
}
