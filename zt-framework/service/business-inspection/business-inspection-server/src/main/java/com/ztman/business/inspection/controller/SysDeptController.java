package com.ztman.business.inspection.controller;


import com.ztman.business.inspection.client.entity.SysDept;
import com.ztman.business.inspection.service.SysDeptService;
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
 * 部门
 *
 * @author zxx
 * @date 2020-08-30 01:58:13
 */
@RestController
@RequestMapping("/sysdept")
@Slf4j
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;
    

    @Autowired
    private UserDetailsImpl userDetailsImpl;
    
    
    /**
     *  相关部门列表
     * @param params
     * @return
     */
     @GetMapping("/withList")
     public R withList() {
         //数据库字段值完整查询
         // return  sysCompanyService.selectPage(new Query<>(params), new EntityWrapper<>());
         //对象模糊查询
    	 
         return new R<>(sysDeptService.withList(userDetailsImpl.getUserDetail().getId()));
     } 

 
}
