package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.dto.PoininspectionJobDTO;
import com.ztman.business.inspection.client.entity.PoininspectionJob;
import com.ztman.business.inspection.client.vo.TrendVO;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 点检任务 服务接口层
 *
 * @author zxx
 * @date 2020-08-29 11:00:39
 */
public interface PoininspectionJobService extends IService<PoininspectionJob> {
    

	public Boolean insert(PoininspectionJobDTO poininspectionJobDTO);

	public List<PoininspectionJob> cycleList(int page, int size, int type, Integer userId,int cycle);

	public Boolean submitJob(PoininspectionJobDTO poininspectionJobDTO); 

	public List<PoininspectionJob> todayList(int page, int size, Integer userId);

	public List<PoininspectionJob> todayComplete(int page, int size, Integer userId);

}

