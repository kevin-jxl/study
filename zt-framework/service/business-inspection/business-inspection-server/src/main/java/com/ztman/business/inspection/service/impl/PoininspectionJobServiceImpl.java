package com.ztman.business.inspection.service.impl;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.baomidou.mybatisplus.mapper.EntityWrapper; 
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl; 
import com.ztman.business.inspection.mapper.PoininspectionJobMapper; 
import com.ztman.business.inspection.client.dto.PoininspectionJobDTO;
import com.ztman.business.inspection.client.entity.PoininspectionJob;
import com.ztman.business.inspection.client.vo.TrendVO; 
import com.ztman.business.inspection.service.PoininspectionJobService;


@Service("poininspectionJobService")
public class PoininspectionJobServiceImpl extends ServiceImpl<PoininspectionJobMapper, PoininspectionJob> implements PoininspectionJobService {

    @Autowired
    private PoininspectionJobMapper poininspectionJobMapper;


	@Override
	public Boolean insert(PoininspectionJobDTO poininspectionJobDTO) {
		// TODO Auto-generated method stub
		PoininspectionJob poininspectionJob =new PoininspectionJob();
//		poininspectionJob.set
		return poininspectionJobMapper.insert(poininspectionJob)>0;
	}

	@Override
	public List<PoininspectionJob> cycleList(int page, int size, int type, Integer userId,int cycle) {
		// TODO Auto-generated method stub 0正常 1不正常  2未完成
		PoininspectionJob poininspectionJob = new PoininspectionJob();
		poininspectionJob.setDoUserId(userId+"");
		
		if(type<2) {
			poininspectionJob.setIsOn(type+"");
		} 
		
		Pagination pagination = new Pagination(page, size);
		EntityWrapper<PoininspectionJob> wrapper = new EntityWrapper<PoininspectionJob>(poininspectionJob);
		
		if(type == 2) { 
			wrapper.isNull("do_time");
		}
		
		wrapper.between("assign_time", LocalDateTime.now().minusDays(cycle), LocalDateTime.now());
		 
		return poininspectionJobMapper.selectPage(pagination,wrapper);
	}

	@Override
	public Boolean submitJob(PoininspectionJobDTO poininspectionJobDTO) {
		// TODO Auto-generated method stub
		PoininspectionJob poininspectionJob = new PoininspectionJob();
		poininspectionJob.setDescription(poininspectionJobDTO.getDescription());
		poininspectionJob.setDoUserId(poininspectionJobDTO.getDoUserId());
		poininspectionJob.setDoUserName(poininspectionJobDTO.getDoUserName());
		poininspectionJob.setDoTime(poininspectionJobDTO.getDoTime());
		poininspectionJob.setIsOn(poininspectionJobDTO.getIsOn());
		poininspectionJob.setAttachmentGroupId(poininspectionJobDTO.getAttachmentGroupId());
		poininspectionJob.setId(poininspectionJobDTO.getId());
		return poininspectionJobMapper.updateById(poininspectionJob)>0;
	}
 

	@Override
	public List<PoininspectionJob> todayList(int page, int size, Integer userId) {
		// TODO Auto-generated method stub
		TrendVO trendVO = null;
		Pagination pagination = new Pagination(page, size);
		PoininspectionJob poininspectionJob = new PoininspectionJob();
		poininspectionJob.setDoUserId(userId+"");
		EntityWrapper<PoininspectionJob> wrapper = new EntityWrapper<>(poininspectionJob);
		wrapper.isNull("do_time");
		List<PoininspectionJob> poininspectionJobs = poininspectionJobMapper.selectList(wrapper); 
		return poininspectionJobs;
	}

	@Override
	public List<PoininspectionJob> todayComplete(int page, int size, Integer userId) {
		// TODO Auto-generated method stub
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime t_zero = today.minusHours(today.getHour()).minusMinutes(today.getMinute()).minusSeconds(today.getSecond());
		TrendVO trendVO = null;
		Pagination pagination = new Pagination(page, size);
		PoininspectionJob poininspectionJob = new PoininspectionJob();
		poininspectionJob.setDoUserId(userId+"");
		EntityWrapper<PoininspectionJob> wrapper = new EntityWrapper<>(poininspectionJob);
		wrapper.isNotNull("do_time");
		wrapper.and();
		wrapper.between("do_time",t_zero ,today); 
		List<PoininspectionJob> poininspectionJobs = poininspectionJobMapper.selectList(wrapper);
		return poininspectionJobs;
	}

}
