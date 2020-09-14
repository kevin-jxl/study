package com.ztman.business.inspection.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.business.inspection.client.dto.MaintenanceDTO;
import com.ztman.business.inspection.client.dto.WorkOrderDTO;
import com.ztman.business.inspection.client.dto.WorkOrderProcessDTO;
import com.ztman.business.inspection.client.entity.Maintenance;
import com.ztman.business.inspection.client.entity.WorkOrder;
import com.ztman.business.inspection.client.entity.WorkOrderProcess;
import com.ztman.business.inspection.client.vo.TrendVO;
import com.ztman.business.inspection.client.vo.WorkOrderBoardVO;
import com.ztman.business.inspection.mapper.MaintenanceMapper;
import com.ztman.business.inspection.mapper.WorkOrderMapper;
import com.ztman.business.inspection.mapper.WorkOrderProcessMapper;
import com.ztman.business.inspection.service.WorkOrderService;

@Service("workOrderService")
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {

	@Autowired
	private WorkOrderMapper workOrderMapper;

	@Autowired
	private WorkOrderProcessMapper workOrderProcessMapper;

	@Autowired
	private MaintenanceMapper maintenanceMapper;

	@Override
	public WorkOrderDTO getWorkOrderBy(String id, int userId) {
		// TODO Auto-generated method stub
		WorkOrderDTO workOrderDTO = null;
		WorkOrder workOrder = new WorkOrder();
		workOrder.setId(id);
		workOrder.setAssignUserId(userId + "");
		workOrder = workOrderMapper.selectOne(workOrder);

		workOrderDTO.setId(workOrder.getId());
		workOrderDTO.setReportAddr(workOrder.getReportAddr());
		workOrderDTO.setEquipmentName(workOrder.getEquipmentName());
		workOrderDTO.setEquipmentId(workOrder.getEquipmentId());
		workOrderDTO.setProblemType(workOrder.getProblemType());
		workOrderDTO.setProblemDesc(workOrder.getProblemDesc());
		workOrderDTO.setProblemLevel(workOrder.getProblemLevel());
		workOrderDTO.setExigencyStatus(workOrder.getExigencyStatus());

		WorkOrderProcess workOrderProcess = new WorkOrderProcess();
		workOrderProcess.setWorkOrderId(workOrder.getId());
		List<WorkOrderProcess> workOrderProcessList = workOrderProcessMapper
				.selectWorkOrderProcessList(workOrderProcess);
		List<WorkOrderProcessDTO> workOrderProcessDTOList = new ArrayList<WorkOrderProcessDTO>();
		workOrderProcessList.forEach(item -> {
			WorkOrderProcessDTO workOrderProcessDTO = new WorkOrderProcessDTO();
			workOrderProcessDTO.setJoinName(item.getJoinName());
			workOrderProcessDTO.setId(item.getId());
			// workOrderProcessDTO.setJoinUserId(item.getJoinUserId());
			workOrderProcessDTO.setJoinName(item.getJoinName());
			workOrderProcessDTO.setOperationTime(item.getOperationTime());
			workOrderProcessDTO.setTag(item.getTag());
			workOrderProcessDTO.setTagName(item.getTagName());
			workOrderProcessDTO.setNote(item.getNote());
			// workOrderProcessDTO.setWorkOrderId(item.getWorkOrderId());
			workOrderProcessDTOList.add(workOrderProcessDTO);
		});

		workOrderDTO.setWorkOrderProcessDTO(workOrderProcessDTOList);
		return workOrderDTO;
	}

	@Override
	public List<WorkOrderBoardVO> boardByPage(Integer userId, int page, int size, int type) {// type:0新报修 1待维修 2已完成
		// TODO Auto-generated method stub
		List<WorkOrderBoardVO> workOrderBoardVOs = null;

		List<WorkOrder> workOrders = null;

		Pagination pagination = new Pagination(page, size);
		WorkOrder workOrder = new WorkOrder();
		workOrder.setDelFlag("0");
		workOrder.setAssignUserId(userId + "");
		EntityWrapper<WorkOrder> wrapper = new EntityWrapper<>(workOrder);
		// workOrder.setAssignUserId(userId+"");
		if (type == 0) {// 新报修：已经派遣给某用户，并且未进行受理的
			wrapper.andNew(
					"EXISTS(SELECT 1 FROM work_order_process AS wop WHERE wop.work_order_id = work_order.id AND wop.tag = '1'  ) AND "
							+ " NOT EXISTS(SELECT 1 FROM work_order_process AS wop WHERE wop.work_order_id = work_order.id AND wop.tag = '2'  )",
					"");
		} else if (type == 1) {// 待维修：已受理未完成的
			wrapper.andNew(
					" EXISTS(SELECT 1 FROM work_order_process AS wop WHERE wop.work_order_id = work_order.id AND wop.tag = '2') AND "
							+ "NOT EXISTS(SELECT 1 FROM work_order_process AS wop WHERE wop.work_order_id = work_order.id AND wop.tag = '4')",
					"");
		} else if (type == 2) {// 已完成：已完成或已关闭的
			wrapper.andNew(
					" EXISTS(SELECT 1 FROM work_order_process AS wop WHERE wop.work_order_id = work_order.id AND wop.tag  IN ('4','5'))",
					"");
			wrapper.setSqlSelect(
					"work_order.*,(select operation_time from work_order_process where tag='4' and work_order_id = work_order.id ) as operationTime ");
			// wrapper.orderBy("operationTime", true);
			Collection<String> columns = new ArrayList<String>();
			columns.add("id");
			columns.add("operationTime");
			wrapper.orderDesc(columns);
		}

		workOrders = workOrder.selectList(wrapper);
		if (null != workOrder) {
			workOrderBoardVOs = new ArrayList<WorkOrderBoardVO>();
			for (WorkOrder item : workOrders) {
				WorkOrderBoardVO workOrderBoardVO = new WorkOrderBoardVO();
				workOrderBoardVO.setWorkOrder(item);

				WorkOrderProcess workOrderProcess = new WorkOrderProcess();
				workOrderProcess.setWorkOrderId(item.getId());
				EntityWrapper<WorkOrderProcess> workOrderProcessWrapper = new EntityWrapper<>(workOrderProcess);

				workOrderBoardVO.setWorkOrderProcesses(workOrderProcessMapper.selectList(workOrderProcessWrapper));
				workOrderBoardVOs.add(workOrderBoardVO);
			}
		}
		return workOrderBoardVOs;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean complete(Integer userId, String realName, MaintenanceDTO maintenanceDTO) throws Exception {
		// TODO Auto-generated method stub
		// 1.新增过程信息 2.新增维修信息
		WorkOrderProcess workOrderProcess = new WorkOrderProcess();
		workOrderProcess.setJoinName(realName);
		workOrderProcess.setJoinUserId(userId + "");
		workOrderProcess.setOperationTime(maintenanceDTO.getCompleteDatetime());
		workOrderProcess.setTag("4");
		workOrderProcess.setTagName("已完成");
		workOrderProcess.setWorkOrderId(maintenanceDTO.getWorkOrderId());
		if (workOrderProcessMapper.insertWorkOrderProcess(workOrderProcess) > 0) {
			Maintenance maintenance = new Maintenance();
			maintenance.setWorkOrderId(maintenanceDTO.getWorkOrderId());
			maintenance.setPart(maintenanceDTO.getPart());
			maintenance.setDoUserName(realName);
			maintenance.setDoUserId(userId + "");
			maintenance.setDescription(maintenanceDTO.getDescription());
			maintenance.setCompleteDatetime(maintenanceDTO.getCompleteDatetime());
			maintenance.setAttachmentGroupId(maintenanceDTO.getAttachmentGroupId());

			if (!(maintenanceMapper.insertMaintenance(maintenance) > 0)) {
				throw new Exception();
			}
		}

		return true;
	}

	@Override
	public boolean accept(Integer userId, String realName, String workOrderId) {
		// TODO Auto-generated method stub
		// 添加受理过程

		WorkOrderProcess workOrderProcess = new WorkOrderProcess();
		workOrderProcess.setJoinName(realName);
		workOrderProcess.setJoinUserId(userId + "");
		workOrderProcess.setOperationTime(LocalDateTime.now());
		workOrderProcess.setTag("2");
		workOrderProcess.setTagName("已受理");
		workOrderProcess.setWorkOrderId(workOrderId);

		return workOrderProcessMapper.insertWorkOrderProcess(workOrderProcess) > 0;
	}

	@Override
	public TrendVO trend(Integer userId, int cycle) {// type 0总报修 1新报修 2待维修 3已完
		// TODO Auto-generated method stub
		// 报修趋势：在一定周期内，关于某用户下所产生的上报且已派遣订单数量按天进行分组统计，天的格式为YD,
		// 报修趋势对象
		TrendVO trendVO = null;
		// 工单数据
		List<WorkOrder> workOrders = null;

		WorkOrder workOrder = new WorkOrder();
		EntityWrapper<WorkOrder> wrapper = new EntityWrapper<>(workOrder);
		workOrder.setAssignUserId(userId + "");
		workOrder.setDelFlag("0");
		workOrders = workOrder.selectList(wrapper);
		if (null != workOrders) {
			trendVO = new TrendVO();
			List<String> categories = new ArrayList<String>();
			// 计算y轴，按年月日匹配累加求和
			List<Integer> series = new ArrayList<Integer>();
			// 计算周期内的年月日
			for (int i = 0; i < cycle; i++) {
				LocalDate date = LocalDateTime.now().minusDays(i).toLocalDate();
				categories.add(date.getMonthValue() + "月" + date.getDayOfMonth() + "日");
				int num = 0;
				for (WorkOrder item : workOrders) {
					if (item.getReportTime().toLocalDate().equals(date)) {
						num++;
					}
				}
				series.add(num);
			}
			trendVO.setSeries(series);
			trendVO.setCategories(categories);

		}

		return trendVO;
	}

	@Override
	public TrendVO duration(Integer userId, int cycle) {
		// TODO Auto-generated method stub
		// 报修单维修时长top10：在一定周期内，关于某用户下已上报且完成维修的及关闭的订单按天分组统计数量，天的格式为TD
		// 报修单维修时长top10对象
		TrendVO trendVO = null;
		// 工单数据
		List<WorkOrder> workOrders = null;
		// 工单看板集合
		List<WorkOrderBoardVO> workOrderBoardVOs = null;
		WorkOrder workOrder = new WorkOrder();
		EntityWrapper<WorkOrder> wrapper = new EntityWrapper<>(workOrder);
		workOrder.setAssignUserId(userId + "");
		workOrder.setDelFlag("0");
		workOrders = workOrder.selectList(wrapper);
		if (null != workOrders) {
			workOrderBoardVOs = new ArrayList<WorkOrderBoardVO>();
			for (WorkOrder item : workOrders) {
				WorkOrderBoardVO workOrderBoardVO = new WorkOrderBoardVO();
				workOrderBoardVO.setWorkOrder(item);

				WorkOrderProcess workOrderProcess = new WorkOrderProcess();
				workOrderProcess.setWorkOrderId(item.getId());
				EntityWrapper<WorkOrderProcess> workOrderProcessWrapper = new EntityWrapper<>(workOrderProcess);

				workOrderBoardVO.setWorkOrderProcesses(workOrderProcessMapper.selectList(workOrderProcessWrapper));
				workOrderBoardVOs.add(workOrderBoardVO);
			}

			trendVO = new TrendVO();
			List<String> categories = new ArrayList<String>();
			// 计算y轴，按年月日匹配累加求和
			List<Integer> series = new ArrayList<Integer>();
			// 计算周期内的年月日
			for (int i = 0; i < cycle; i++) {
				LocalDate date = LocalDateTime.now().minusDays(i).toLocalDate();
				categories.add(date.getMonthValue() + "月" + date.getDayOfMonth() + "日");
				int num = 0;
				for (WorkOrderBoardVO item : workOrderBoardVOs) {

					// if(item.getWorkOrder().getReportTime().toLocalDate().equals(date)) {
					// num++;
					// }
				}
				series.add(num);
			}
			trendVO.setSeries(series);
			trendVO.setCategories(categories);

		}

		return trendVO;
	}

	@Override
	public Map<String, Integer> number(Integer userId) {
		// TODO Auto-generated method stub
		Map<String, Integer> nums = new HashMap<String, Integer>();
		nums.put("zbx", 0);
		nums.put("dsl", 0);
		nums.put("czz", 0);
		nums.put("ywc", 0);
		;
		if (true) {
			WorkOrder workOrder = new WorkOrder();
			workOrder.setAssignUserId(userId + "");
			workOrder.setDelFlag("0");
			EntityWrapper<WorkOrder> workOrderWrapper = new EntityWrapper<>(workOrder);
			nums.put("zbx", workOrderMapper.selectCount(workOrderWrapper));
			workOrderWrapper = new EntityWrapper<>(workOrder);
			workOrderWrapper.andNew(
					"EXISTS(SELECT 1 FROM work_order_process AS a WHERE a.work_order_id = work_order.id and tag = '1' ) AND"
							+ " NOT EXISTS(SELECT 1 FROM work_order_process AS a WHERE a.work_order_id = work_order.id and tag = '2' )",
					"");
			nums.put("dsl", workOrderMapper.selectCount(workOrderWrapper));
			workOrderWrapper = new EntityWrapper<>(workOrder);
			workOrderWrapper.andNew(
					"EXISTS(SELECT 1 FROM work_order_process AS a WHERE a.work_order_id = work_order.id and tag = '2' ) AND"
							+ " NOT EXISTS(SELECT 1 FROM work_order_process AS a WHERE a.work_order_id = work_order.id and tag = '4' )",
					"");
			nums.put("czz", workOrderMapper.selectCount(workOrderWrapper));
			workOrderWrapper = new EntityWrapper<>(workOrder);
			workOrderWrapper.andNew(
					"EXISTS(SELECT 1 FROM work_order_process AS a WHERE a.work_order_id = work_order.id and tag in('4','5'))",
					"");
			nums.put("ywc", workOrderMapper.selectCount(workOrderWrapper));
		}
		return nums;
	}

}
