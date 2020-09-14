package com.ztman.business.inspection.controller;


import com.ztman.business.inspection.client.dto.MaintenanceDTO;
import com.ztman.business.inspection.client.dto.WorkOrderDTO;
import com.ztman.business.inspection.client.entity.WorkOrder;
import com.ztman.business.inspection.client.vo.WorkOrderBoardVO;
import com.ztman.business.inspection.service.MaintenanceService;
import com.ztman.business.inspection.service.WorkOrderService;
import com.ztman.business.inspection.service.impl.UserDetailsImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page; 
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.security.DTO.SecruityUser; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ztman.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j; 
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 工单表
 *
 * @author zxx
 * @date 2020-08-27 22:07:49
 */
@RestController
@RequestMapping("/workorder")
@Slf4j
public class WorkOrderController {
    @Autowired
    private WorkOrderService workOrderService; 
    @Autowired
    private UserDetailsImpl userDetailsImpl;
    @Autowired
    private MaintenanceService maintenanceService;
    
    /**
    *  维修工单看板
    * @param params
    * @return
    */
    @GetMapping("/board/{type}/{page}/{size}")
    public R boardByPage(@PathVariable("page")int page,@PathVariable("size")int size,@PathVariable("type")int type) { 
    	List<WorkOrderBoardVO> workOrderBoardVOs = workOrderService.boardByPage(userDetailsImpl.getUserDetail().getId(),page,size,type);
    	
        return new R<>(workOrderBoardVOs);
    }

    /**
     *  工单详情
     * @param params
     * @return
     */
     @GetMapping("/{id}")
     public R info(@PathVariable("id") String id) { 
    	WorkOrderDTO workOrderDTO = workOrderService.getWorkOrderBy(id,userDetailsImpl.getUserDetail().getId());
        return new R<>();
     }
     
     /**
      *  工单维修信息
      * @param params
      * @return
      */
      @GetMapping("/maintenance/{workOrderId}")
      public R maintenanceInfo(@PathVariable("workOrderId") String workOrderId) { 
    	 MaintenanceDTO maintenanceDTO = maintenanceService.info(workOrderId); 
         return new R<>(maintenanceDTO);
      }
     /**
      *  工单受理
      * @param params
      * @return
      */
      @PostMapping("/accept")
      public R accept(@RequestParam String workOrderId) {  
    	  boolean flg = workOrderService.accept(userDetailsImpl.getUserDetail().getId(),userDetailsImpl.getUserDetail().getRealName(),workOrderId);
    	  if (!flg) {
    		  return new R<>(new Throwable("提交失败"));
		}
         return new R<>();
      }
     /**
      *  完成维修
      * @param params
      * @return
      */
      @PostMapping("/maintenance/complete")
      public R maintenanceAdd(@RequestBody MaintenanceDTO maintenanceDTO) {  
    	  boolean flg = false;
		try {
			flg = workOrderService.complete(userDetailsImpl.getUserDetail().getId(),userDetailsImpl.getUserDetail().getRealName(),maintenanceDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  if (!flg) {
    		  return new R<>(new Throwable("提交失败"));
		}
         return new R<>();
      }

     
}
