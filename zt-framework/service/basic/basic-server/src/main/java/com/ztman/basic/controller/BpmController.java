package com.ztman.basic.controller;

import com.alibaba.fastjson.JSON;
import com.ztman.basic.api.feign.RemoteBpmService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2018/9/14.
 */
@CrossOrigin
@RestController
public class BpmController {

    @Resource
    private RemoteBpmService remoteBpmService;

    @RequestMapping("/platform/startProcess")
    public String startProcess(String defKey, String empNo, String empName, String nodeUsers, String gateWay){
        return JSON.toJSONString(remoteBpmService.startProcess(defKey, empNo, empName, nodeUsers, gateWay));
    }
    @RequestMapping("/platform/processList")
    public String processList(String empNo, String flag){
        return JSON.toJSONString(remoteBpmService.processList(empNo, flag));
    }
    @RequestMapping("/platform/agree")
    public String agree(String procId, String taskId, String opinion, String empNo, String empName){
        Map<String, String> vapMaps = new HashMap<>();
        vapMaps.put("procId", procId);
        vapMaps.put("taskId", taskId);
        vapMaps.put("opinion", opinion);
        vapMaps.put("empNo", empNo);
        vapMaps.put("empName", empName);
        String json = JSON.toJSONString(remoteBpmService.agree(vapMaps));

        return json;
    }
    @RequestMapping("/platform/oppose")
    public String oppose(String procId, String taskId, String opinion, String empNo, String empName){
        Map<String, String> vapMaps = new HashMap<>();
        vapMaps.put("procId", procId);
        vapMaps.put("taskId", taskId);
        vapMaps.put("opinion", opinion);
        vapMaps.put("empNo", empNo);
        vapMaps.put("empName", empName);
        return JSON.toJSONString(remoteBpmService.oppose(vapMaps));
    }
    @RequestMapping("/platform/complete")
    public String complete(String procId, String taskId, String empNo, String empName){
        Map<String, String> vapMaps = new HashMap<>();
        vapMaps.put("procId", procId);
        vapMaps.put("taskId", taskId);
        vapMaps.put("actionName", "revoke");//actionName agree,通过，oppose,反对，reject,驳回，revoke,撤回
        vapMaps.put("empNo", empNo);
        vapMaps.put("empName", empName);
        return JSON.toJSONString(remoteBpmService.complete(vapMaps));
    }
    @RequestMapping("/platform/reject")
    public String reject(String procId, String taskId, String empNo, String empName, String backHandMode, String destination){
        Map<String, String> vapMaps = new HashMap<>();
        vapMaps.put("procId", procId);
        vapMaps.put("taskId", taskId);
        vapMaps.put("backHandMode", backHandMode);
        vapMaps.put("destination", destination);
        vapMaps.put("empNo", empNo);
        vapMaps.put("empName", empName);
        return JSON.toJSONString(remoteBpmService.reject(vapMaps));
    }
    @RequestMapping("/platform/toReject")
    public String toReject( String taskId){
        return JSON.toJSONString(remoteBpmService.toReject(taskId));
    }

    @RequestMapping("/platform/doEndProcess")
    public String doEndProcess(String taskId, String endReason, String empNo){
        return JSON.toJSONString(remoteBpmService.doEndProcess(taskId, endReason, empNo));
    }
}
