package com.ztman.business.inspection.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztman.business.inspection.client.vo.TrendVO;
import com.ztman.business.inspection.service.PoininspectionJobService;
import com.ztman.business.inspection.service.SysCompanyService;
import com.ztman.business.inspection.service.WorkOrderProcessService;
import com.ztman.business.inspection.service.WorkOrderService;
import com.ztman.business.inspection.service.impl.UserDetailsImpl;
import com.ztman.common.core.util.R;

import lombok.extern.slf4j.Slf4j;

/**
 * 短信验证码
 *
 * @author zxx
 * @date 2020-08-29 11:11:37
 */
@RestController
@RequestMapping("/statistics")
@Slf4j 
public class StatisticsController {
	@Autowired
    private PoininspectionJobService poininspectionJobService;  
    @Autowired
    private UserDetailsImpl userDetailsImpl;
    @Autowired
    private WorkOrderService workOrderService;
    
    /**
     * @param   
     * 
     * */
    @GetMapping("/trend")
    public R trend(@RequestParam("type") int type ,@RequestParam("cycle") int cycle) {
		
    	TrendVO trendVO =workOrderService.trend( userDetailsImpl.getUserDetail().getId(), cycle);
    	
    	return new R<>(trendVO);
	}
    @GetMapping("/duration")
    public R duration(@RequestParam("type") int type ,@RequestParam("cycle") int cycle) {
		
    	TrendVO trendVO =workOrderService.duration( userDetailsImpl.getUserDetail().getId(), cycle);
    	return new R<>(trendVO);
	}
    
    /**
     * @param   
     * 
     * */
    @GetMapping("/number")
    public R number() {
		
    	Map<String,Integer> number =workOrderService.number(userDetailsImpl.getUserDetail().getId());
    	
    	return new R<>(number);
	}
}
