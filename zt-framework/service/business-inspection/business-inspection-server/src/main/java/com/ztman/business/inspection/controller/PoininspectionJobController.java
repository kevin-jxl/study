package com.ztman.business.inspection.controller;


import com.ztman.business.inspection.client.dto.CasualPoininspectionDTO;
import com.ztman.business.inspection.client.dto.PoininspectionJobDTO;
import com.ztman.business.inspection.client.entity.PoininspectionJob;
import com.ztman.business.inspection.service.CasualPoininspectionService;
import com.ztman.business.inspection.service.PoininspectionJobService;
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
import java.util.List;
import java.util.Map;


/**
 * 点检任务
 *
 * @author zxx
 * @date 2020-08-29 11:00:39
 */
@RestController
@RequestMapping("/poininspectionjob")
@Slf4j
public class PoininspectionJobController {
    @Autowired
    private PoininspectionJobService poininspectionJobService;  
    @Autowired
    private CasualPoininspectionService casualPoininspectionService;  
    @Autowired
    private UserDetailsImpl userDetailsImpl;
   

    /**
     * 提交点检任务
     * @param poininspectionJob
     * @return R
     */
    @PostMapping("/submitJob")
    public R submitJob(@RequestBody PoininspectionJobDTO poininspectionJobDTO) { 
//        
        poininspectionJobDTO.setDoUserId(userDetailsImpl.getUserDetail().getId()+""); 
//        poininspectionJobDTO.setDoUserName(doUserName); 
       Boolean flg = poininspectionJobService.submitJob(poininspectionJobDTO);
       if(!flg) {
    	   return new R<>(new Throwable("提交失败"));
       }
        return new R<>();
    } 
    
    
    /**
     *  随机点检
     * @param params
     * @return
     */
     @PostMapping("/add")
     public R add(@RequestBody CasualPoininspectionDTO casualPoininspectionDTO) {
    	 casualPoininspectionDTO.setDoUserId(userDetailsImpl.getUserDetail().getId()+"");
    	 casualPoininspectionDTO.setDoUserName(userDetailsImpl.getUserDetail().getRealName());
    	boolean flg =  casualPoininspectionService.addBy(casualPoininspectionDTO);
    	 if(!flg) {
    		 return  new R<>(new Throwable("添加失败"));
    	 }
         return new R<>();
     } 
     
     /**
      *  点检周期列表
      * @param params
      * @return
      */
      @GetMapping("/cycleList")
      public R cycleList(@RequestParam() int page,@RequestParam() int size,@RequestParam() int type,@RequestParam() int cycle) {
     	  List<PoininspectionJob> poininspectionJobPages = poininspectionJobService.cycleList(page,size,type,userDetailsImpl.getUserDetail().getId(),cycle);
          return new R<>(poininspectionJobPages);
      }
      
      /**
       *  今日任务列表
       * @param params
       * @return
       */
       @GetMapping("/today/list")
       public R todayList(@RequestParam() int page,@RequestParam() int size) {
      	  List<PoininspectionJob> poininspectionJobPages = poininspectionJobService.todayList(page,size,userDetailsImpl.getUserDetail().getId());
           return new R<>(poininspectionJobPages);
       }
       /**
        *  今日完成列表（随机检查与派遣检查任务）
        * @param params
        * @return
        */
        @GetMapping("/today/complete")
        public R todayComplete(@RequestParam() int page,@RequestParam() int size) {
       	  List<PoininspectionJob> poininspectionJobPages = poininspectionJobService.todayComplete(page,size,userDetailsImpl.getUserDetail().getId());
            return new R<>(poininspectionJobPages);
        }
        
        
}
