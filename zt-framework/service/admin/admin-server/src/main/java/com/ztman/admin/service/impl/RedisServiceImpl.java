package com.ztman.admin.service.impl;

import com.ztman.admin.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    //流水号加1后返回，流水号长度为4
    private static final String STR_FORMAT = "0000";

    @Override
    public String getBusinessCode(String type) {
        //设置默认编码
        Integer codeInt = 1;
        //格式化当前日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        //redis设置缓存名
        String BusinessCode = "BusinessCode-";
        //去缓存中查询数据
        Object redisObj = redisTemplate.opsForValue().get(BusinessCode + type);
        if (redisObj != null) {
            //取出缓存的数据进行增加
            codeInt = Integer.valueOf(redisObj.toString());
            codeInt++;
        }
        //将验证码存入redis
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setValueSerializer(stringSerializer);
        DecimalFormat dft = new DecimalFormat(STR_FORMAT);
        redisTemplate.opsForValue().set(BusinessCode + type, dft.format(codeInt));
        return type + df.format(new Date()) + dft.format(codeInt);
    }
}
