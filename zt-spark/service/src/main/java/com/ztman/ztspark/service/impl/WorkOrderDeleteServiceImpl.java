package com.ztman.ztspark.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.workorder.WorkOrderDelete;
import com.ztman.ztspark.mapper.WorkOrderDeleteMapper;
import com.ztman.ztspark.service.WorkOrderDeleteService;


@Service("workOrderDeleteService")
public class WorkOrderDeleteServiceImpl extends ServiceImpl<WorkOrderDeleteMapper, WorkOrderDelete> implements WorkOrderDeleteService {

    @Autowired
    private WorkOrderDeleteMapper workOrderDeleteMapper;

     

}
