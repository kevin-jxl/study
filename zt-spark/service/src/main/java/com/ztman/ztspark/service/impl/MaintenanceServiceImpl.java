package com.ztman.ztspark.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.workorder.Maintenance;
import com.ztman.ztspark.mapper.MaintenanceMapper;
import com.ztman.ztspark.service.MaintenanceService;


@Service("maintenanceService")
public class MaintenanceServiceImpl extends ServiceImpl<MaintenanceMapper, Maintenance> implements MaintenanceService {

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    

}
