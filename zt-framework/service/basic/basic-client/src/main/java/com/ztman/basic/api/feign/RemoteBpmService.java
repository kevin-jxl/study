package com.ztman.basic.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "bpm")
public interface RemoteBpmService {

    /**
     * 流程发起
     */
    @PostMapping("/platform/startProcess")
    public Map<String, Object> startProcess(@RequestParam("defKey") String defKey,
                                            @RequestParam("empNo") String empNo,
                                            @RequestParam("empName") String empName,
                                            @RequestParam("nodeUsers") String nodeUsers,
                                            @RequestParam("gateWay") String gateWay
    );

    /**
     * 待办任务
     */
    @GetMapping("/platform/pendingJson")
    public List pendingJson(@RequestParam String empNo);

    /**
     * 流程相关数据，包含待办、已办理、已完结
     */
    @GetMapping("/platform/processList")
    public List processList(@RequestParam("empNo") String empNo, @RequestParam("flag") String flag);

    /**
     * 审批历史
     */
    @GetMapping("/platform/flowHistory")
    public List<Map<String, Object>> flowHistory(@RequestParam String procId);

    /**
     * 审批通过
     */
    @PostMapping("/platform/agree")
    public Map<String, Object> agree(@RequestParam Map<String, String> vapMaps);

    /**
     * 审批不通过
     */
    @PostMapping("/platform/oppose")
    public Map<String, Object> oppose(@RequestParam Map<String, String> vapMaps);

    /**
     * 审批驳回
     */
    @GetMapping("/platform/reject")
    public Map<String, Object> reject(@RequestParam Map<String, String> vapMaps);

    /**
     * 审批驳回
     */
    @GetMapping("/platform/complete")
    public Map<String, Object> complete(@RequestParam Map<String, String> vapMaps);

    /**
     * 查询可驳回节点
     */
    @GetMapping("/platform/toReject")
    public List toReject(@RequestParam String taskId);

    /**
     * 流程任务转办
     */
    @GetMapping("/platform/transfer")
    public Map<String, Object> transfer(@RequestParam("taskId") String taskId,
                                        @RequestParam("taskName") String taskName,
                                        @RequestParam("procId") String procId,
                                        @RequestParam("subject") String subject,
                                        @RequestParam("empNo") String empNo,
                                        @RequestParam("tranUsers") String tranUsers);

    /**
     * 获取流程图节点状态信息
     */
    @GetMapping("/platform/flowProcess")
    List<Map<String,Object>> flowProcess(@RequestParam String procId);

    /**
     * 流程挂起
     */
    @GetMapping("/platform/suspendProcess")
    public void suspendProcess(@RequestParam String taskId);

    /**
     * 流程恢复
     */
    @GetMapping("/platform/recoverProcess")
    public void recoverProcess(@RequestParam String taskId);

    /**
     * 流程实例-批量挂起
     */
    @GetMapping("/platform/batchSuspendProcess")
    public void batchSuspendProcess(@RequestParam String taskIds);

    /**
     * 流程实例-批量恢复
     */
    @GetMapping("/platform/batchRecoverProcess")
    public void batchRecoverProcess(@RequestParam String taskIds);

    /**
     * 获取流程任务下一级审批节点信息
     */
    @GetMapping("/platform/getNextTaskNode")
    public String getNextTaskNode(@RequestParam("procId") String procId, @RequestParam("gateWay") String gateWay);

    /**
     * 获取流程图第一个任务节点
     */
    @GetMapping("/platform/getFirstNodeInfo")
    public String getFirstNodeInfo(@RequestParam("defKey") String defKey, @RequestParam("gateWay") String gateWay);

    /**
     * 根据流程实例ID获取流程定义里所有任务节点及节点审批状态
     */
    @GetMapping("/platform/fullNodeInfo")
    public String fullNodeInfo(@RequestParam String procId);

    /**
     * 终止流程
     */
    @GetMapping("/platform/doEndProcess")
    public Map<String, Object> doEndProcess(@RequestParam("taskId") String taskId, @RequestParam("endReason") String endReason, @RequestParam("empNo") String empNo);
}
