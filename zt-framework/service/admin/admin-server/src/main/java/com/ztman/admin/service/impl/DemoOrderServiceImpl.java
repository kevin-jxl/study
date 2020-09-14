package com.ztman.admin.service.impl;

import com.ztman.admin.api.entity.DemoOrder;
import com.ztman.admin.mapper.DemoOrderMapper;
import com.ztman.admin.service.DemoOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;




@Service("demoOrderService")
public class DemoOrderServiceImpl extends ServiceImpl<DemoOrderMapper, DemoOrder> implements DemoOrderService {

}
