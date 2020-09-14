package com.ztman.admin.service;

import com.baomidou.mybatisplus.service.IService;

/**
 * redis service
 */
public interface RedisService{
    String getBusinessCode(String type);
}

