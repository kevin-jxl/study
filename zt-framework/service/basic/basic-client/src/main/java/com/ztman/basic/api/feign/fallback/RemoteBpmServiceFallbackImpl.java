package com.ztman.basic.api.feign.fallback;

import com.ztman.basic.api.feign.RemoteBpmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RemoteBpmServiceFallbackImpl implements RemoteBpmService {

	@Override
	public Map<String, Object> startProcess(String defKey, String empNo, String empName, String nodeUsers, String gateWay) {
		return null;
	}

	@Override
	public List pendingJson(String empNo) {
		return null;
	}

	@Override
	public List processList(String empNo, String flag) {
		return null;
	}

	@Override
	public List<Map<String, Object>> flowHistory(String procId) {
		return null;
	}

	@Override
	public Map<String, Object> agree(Map<String, String> vapMaps) {
		return null;
	}

	@Override
	public Map<String, Object> oppose(Map<String, String> vapMaps) {
		return null;
	}

	@Override
	public Map<String, Object> reject(Map<String, String> vapMaps) {
		return null;
	}

	@Override
	public Map<String, Object> complete(Map<String, String> vapMaps) {
		return null;
	}

	@Override
	public List toReject(String taskId) {
		return null;
	}

	@Override
	public Map<String, Object> transfer(String taskId, String taskName, String procId, String subject, String empNo, String tranUsers) {
		return null;
	}

	@Override
	public List<Map<String, Object>> flowProcess(String procId) {
		return null;
	}

	@Override
	public void suspendProcess(String taskId) {

	}

	@Override
	public void recoverProcess(String taskId) {

	}

	@Override
	public void batchSuspendProcess(String taskIds) {

	}

	@Override
	public void batchRecoverProcess(String taskIds) {

	}

	@Override
	public String getNextTaskNode(String procId, String gateWay) {
		return null;
	}

	@Override
	public String getFirstNodeInfo(String defKey, String gateWay) {
		return null;
	}

	@Override
	public String fullNodeInfo(String procId) {
		return null;
	}

	@Override
	public Map<String, Object> doEndProcess(String taskId, String endReason, String empNo) {
		return null;
	}
}
