package com.ztman.ztspark.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.R;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import com.ztman.ztspark.entity.workorder.dto.AttachmentDTO;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderAssignDTO;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderDTO;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderProcessDTO;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderSuperviseDTO;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderVerifyDTO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderAssignVO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderListVO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderVerifyVO;
import com.ztman.ztspark.service.AttachmentService;
import com.ztman.ztspark.service.WorkOrderAssignService;
import com.ztman.ztspark.service.WorkOrderProcessService;
import com.ztman.ztspark.service.WorkOrderService;
import com.ztman.ztspark.service.WorkOrderSuperviseService;
import com.ztman.ztspark.service.WorkOrderVerifyService;

import lombok.extern.slf4j.Slf4j;

/**
 * 调度管理
 *
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@RestController
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {

    // excel工具类
    ExcelUtil util = new ExcelUtil();

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private WorkOrderVerifyService workOrderVerifyService;

    @Autowired
    private WorkOrderSuperviseService workOrderSuperviseService;

    @Autowired
    private WorkOrderAssignService workOrderAssignService;

    @Autowired
    private WorkOrderProcessService workOrderProcessService;

    /**
     * 工单调度列表
     * 
     * @author zxx
     * @param params
     * @return
     */
    @GetMapping("/workOrderAssignList") // type:0全部、1派遣栏、2处置栏、3完成栏
    public R workOrderAssignList(@RequestParam("page") int page, @RequestParam("size") int size,
        @RequestParam("searchKey") String searchKey, @RequestParam("date") String date,
        @RequestParam("type") String type) {
        SecruityUser user = SecurityUtils.getUserInfo();
        WorkOrderAssignVO workOrderAssignVOs =
            workOrderService.workOrderAssignList(user.getTenantId(), page, size, searchKey, date, type);
        return new R<>(workOrderAssignVOs);
    }

    /**
     * 工单批量导出
     * 
     * @author zxx
     * @param params
     * @return
     */
    @GetMapping("/import")
    public void workOrderListImport(HttpServletResponse res, @RequestParam("tableName") String tableName,
        @RequestParam("searchKey") String searchKey, @RequestParam("date") String date,
        @RequestParam("type") String type) {
        try {
            SecruityUser user = SecurityUtils.getUserInfo();
            WorkOrderAssignVO workOrderAssignVO =
                workOrderService.workOrderAssignList(user.getTenantId(), -1, -1, searchKey, date, type);
            String[] headInfo = {"序号", "工单编号", "状态", "设备名称", "问题类型", "上报人", "上报地点", "上报时间", "描述"};
            String[] sheetName = {"调度工单表"};
            util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
            // 创建第一行标题头
            List<String[]> list2 = new ArrayList<String[]>();
            list2.add(headInfo);

            List<Integer[]> alist = new ArrayList<>();
            Integer[] a1 = {0, 0, 0, headInfo.length};// 开始行,结束行,开始列,结束列
            alist.add(a1);
            List<Integer[]> blist = new ArrayList<>();

            util.setValue(list2, util.getCellType4Merge(0, 10, blist), 0, 0);
            for (int i = 0; i < workOrderAssignVO.getRecords().size(); i++) {
                WorkOrderAssignVO.WorkOrderVO obj = workOrderAssignVO.getRecords().get(i);
                List<String[]> cacheList = new ArrayList<String[]>();
                String[] tb = new String[headInfo.length];
                int index = 0;
                tb[index++] = String.valueOf(i + 1);
                tb[index++] = obj.getId();
                tb[index++] = obj.getProcess();
                tb[index++] = obj.getProblemType();
                tb[index++] = obj.getProblemType();
                tb[index++] = obj.getReportPersonName();
                tb[index++] = obj.getReportAddr();
                tb[index++] = obj.getReportTime().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                tb[index++] = obj.getProblemDesc();
                cacheList.add(tb);
                util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
            }
            util.exportExcel(res, tableName);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * 工单列表
     * 
     * @author zxx
     * @param params
     * @return
     */
    @GetMapping("/workOrder/page")
    public R workOrderList(@RequestParam("page") int page, @RequestParam("size") int size,
        @RequestParam("searchKey") String searchKey, @RequestParam("type") String type,
        @RequestParam("park") String park) {
        SecruityUser user = SecurityUtils.getUserInfo();
        WorkOrderListVO workOrderVO =
            workOrderService.workOrderList(user.getTenantId(), page, size, searchKey, type, park);

        return new R<>(workOrderVO);
    }

    /**
     * 工单详情
     * 
     * @author zxx
     * @param params
     * @return
     */
    @GetMapping("/workOrder/{id}")
    public R workOrderDetail(@PathVariable("id") String id) {
        WorkOrderDTO workOrderDTO = workOrderService.workOrderDetail(id);
        return new R<>(workOrderDTO);
    }

    /**
     * 工单删除
     * 
     * @author zxx
     * @param params
     * @return
     */
    @DeleteMapping("/workOrder/{id}")
    public R workOrderDelete(@PathVariable("id") String id) {
        SecruityUser user = SecurityUtils.getUserInfo();

        boolean flag = workOrderService.workOrderDelete(user.getId(), id);
        if (flag) {
            return new R<>(new Throwable("工单删除失败"));
        }
        return new R<>();
    }

    /**
     * 工单导出
     * 
     * @author zxx
     * @param params
     * @return
     * @throws IOException
     */
    @GetMapping("/workOrder/import")
    public void workOrderImport(HttpServletResponse res, @RequestParam("tableName") String tableName,
        @RequestParam("searchKey") String searchKey, @RequestParam("type") String type,
        @RequestParam("park") String park) throws IOException {
        SecruityUser user = SecurityUtils.getUserInfo();
        WorkOrderListVO workOrderVO = workOrderService.workOrderList(user.getTenantId(), -1, -1, searchKey, type, park);
        String[] headInfo = {"序号", "工单编号", "问题类型", "上报人", "联系方式", "上报位置", "上报时间", "所属车场", "进度状态"};
        String[] sheetName = {"工单表"};
        util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
        // 创建第一行标题头
        List<String[]> list2 = new ArrayList<String[]>();
        list2.add(headInfo);

        List<Integer[]> alist = new ArrayList<>();
        Integer[] a1 = {0, 0, 0, headInfo.length};// 开始行,结束行,开始列,结束列
        alist.add(a1);
        List<Integer[]> blist = new ArrayList<>();

        util.setValue(list2, util.getCellType4Merge(0, 10, blist), 0, 0);
        for (int i = 0; i < workOrderVO.getRecords().size(); i++) {
            WorkOrderListVO.WorkOrderVO obj = workOrderVO.getRecords().get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getId();
            tb[index++] = obj.getProblemType();
            tb[index++] = obj.getReportPersonName();
            tb[index++] = obj.getContact();
            tb[index++] = obj.getReportAddr();
            tb[index++] = obj.getReportTime().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            tb[index++] = obj.getOfPark();
            tb[index++] = obj.getProcess();
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, tableName);
    }

    /**
     * 工单派遣
     * 
     * @author zxx
     * @param params
     * @return
     */
    @PutMapping("/workOrder/assign")
    public R workOrderAssign(@RequestBody WorkOrderAssignDTO workOrderAssignDTO) {

        SecruityUser user = SecurityUtils.getUserInfo();
        
        if(!workOrderAssignService.assigned(workOrderAssignDTO.getWorkOrderId()))
            return new R<>(new Throwable("请勿重复派遣"));
        
        workOrderAssignDTO.setAssignUserId(user.getId() + "");
        boolean flag = workOrderAssignService.insertAssign(user.getRealName(), workOrderAssignDTO);
        if (!flag) {
            return new R<>(new Throwable("派遣添加失败"));
        }
        return new R<>();
    }

    /**
     * 工单督办
     * 
     * @author zxx
     * @param params
     * @return
     */
    @PutMapping("/workOrder/supervise")
    public R workOrderSupervise(@RequestBody WorkOrderSuperviseDTO workOrderSuperviseDTO) {

        SecruityUser user = SecurityUtils.getUserInfo();

        workOrderSuperviseDTO.setSuperviseUserId(user.getId() + "");
        
        if(!workOrderSuperviseService.supervised(workOrderSuperviseDTO.getWorkOrderId()))
            return new R<>(new Throwable("请勿重复督办"));
        
        boolean flag = workOrderSuperviseService.insertSupervise(workOrderSuperviseDTO);
        if (!flag) {
            return new R<>(new Throwable("督办添加失败"));
        }
        return new R<>();
    }

    /**
     * 工单核查
     * 
     * @author zxx
     * @param params
     * @return@RequestBody WorkOrderVerify workOrderVerify
     */
    @PutMapping("/workOrder/verify")
    public R workOrderVerify(@RequestBody WorkOrderVerifyDTO workOrderVerifyDTO) {

        SecruityUser user = SecurityUtils.getUserInfo();

        workOrderVerifyDTO.setUserId(user.getId() + "");

        if(!workOrderVerifyService.verified(workOrderVerifyDTO.getWorkOrderId()))
            return new R<>(new Throwable("请勿重复核查"));
         
        boolean flag = workOrderVerifyService.insertVerify(workOrderVerifyDTO, user.getRealName());
        if (!flag) {
            return new R<>(new Throwable("核查添加失败"));
        }
        return new R<>();
    }

    /**
     * 工单附件
     * 
     * @author zxx
     * @param params
     * @return
     */
    @GetMapping("/workOrder/attachment/{id}")
    public R workOrderAttachment(@PathVariable("id") String groupId) {
        List<AttachmentDTO> attachmentDTOs = attachmentService.workOrderAttachment(groupId);
        return new R<>(attachmentDTOs);
    }

    /**
     * 工单办理过程
     * 
     * @author zxx
     * @param params
     * @return
     */
    @GetMapping("/workOrder/process/{workOrderId}")
    public R workOrderProcess(@PathVariable("workOrderId") String workOrderId) {
        List<WorkOrderProcessDTO> workOrderProcessDTOs = workOrderProcessService.workOrderProcess(workOrderId);
        return new R<>(workOrderProcessDTOs);
    }

    /**
     * 工单核查信息
     * 
     * @author zxx
     * @param params
     * @return
     */
    @GetMapping("/workOrder/verify/{workOrderId}")
    public R workOrderVerifyDetail(@PathVariable("workOrderId") String workOrderId) {
        WorkOrderVerifyVO workOrderVerifyVO = workOrderVerifyService.workOrderVerifyDetail(workOrderId);
        return new R<>(workOrderVerifyVO);
    }

    /**
     * 工单调度统计信息
     * 
     * @author zxx
     * @param params
     * @return
     */
    @GetMapping("/workOrderStatistics")
    public R workOrderStatistics() {
        SecruityUser user = SecurityUtils.getUserInfo();
        List<HashMap<String, String>> map = workOrderService.workOrderStatistics(user.getTenantId());
        return new R<>(map);
    }
}
