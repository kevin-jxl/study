package com.example.demo.spike.service;

import com.example.demo.spike.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Random;

/**
 * 项目名称：testQRcode
 * 类 名 称：GoodsService
 * 类 描 述：TODO
 * 创建时间：2020/8/18 16:49
 * 创 建 人：纪星亮
 */
@Service
public class GoodsService {

    @Resource
    GoodsMapper goodsMapper;

    public boolean updateGoodsNum(Integer id, Integer num) {
        HashMap<String, Object> map = goodsMapper.findById(id);
        int i = (int) map.get("num") - num;

        if (i < 0) {
            return false;
        }
        boolean b = goodsMapper.updateGoodsNum(id, num) > 0 ? true : false;
        return b;
    }

    private void waitForLock() {
        try {
            Thread.sleep(new Random().nextInt(10) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int selectNumById(int id) {
        return (int) goodsMapper.findById(id).get("num");
    }

    public void init(Integer id, Integer num) {
        goodsMapper.update(id, num);
    }
}