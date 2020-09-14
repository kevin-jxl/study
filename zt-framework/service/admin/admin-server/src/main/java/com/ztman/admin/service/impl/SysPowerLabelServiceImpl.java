package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysPosdummy;
import com.ztman.admin.api.entity.SysPowerLabel;
import com.ztman.admin.api.vo.SysPowerLabelVO;
import com.ztman.admin.mapper.SysPosdummyMapper;
import com.ztman.admin.mapper.SysPowerLabelMapper;
import com.ztman.admin.service.SysPowerLabelService;
import com.ztman.common.core.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("sysPowerLabelService")
public class SysPowerLabelServiceImpl extends ServiceImpl<SysPowerLabelMapper, SysPowerLabel> implements SysPowerLabelService {

    @Autowired
    private SysPowerLabelMapper sysPowerLabelMapper;
    @Autowired
    private SysPosdummyMapper sysPosdummyMapper;

    /**
     * 查询信息
     *
     * @param id ID
     * @return 信息
     */
    @Override
    public SysPowerLabel selectSysPowerLabelById(Integer id) {
        return sysPowerLabelMapper.selectSysPowerLabelById(id);
    }

    /**
     * 查询列表
     *
     * @param sysPowerLabel 信息
     * @return 集合
     */
    @Override
    public List<SysPowerLabel> selectSysPowerLabelList(SysPowerLabel sysPowerLabel) {
        return sysPowerLabelMapper.selectSysPowerLabelList(sysPowerLabel);
    }


    /**
     * 分页模糊查询列表
     *
     * @return 集合
     */
    @Override
    public Page selectSysPowerLabelListByLike(Query query) {
        SysPowerLabel sysPowerLabel = BeanUtil.mapToBean(query.getCondition(), SysPowerLabel.class, false);
        List<SysPowerLabelVO> labels = sysPowerLabelMapper.selectSysPowerLabelListByLike(query, sysPowerLabel);
        query.setRecords(labels);
        return query;
    }

    /**
     * 新增
     *
     * @param sysPowerLabel 信息
     * @return 结果
     */
    @Override
    public int insertSysPowerLabel(SysPowerLabel sysPowerLabel) {
        return sysPowerLabelMapper.insertSysPowerLabel(sysPowerLabel);
    }

    /**
     * 修改
     *
     * @param sysPowerLabel 信息
     * @return 结果
     */
    @Override
    public int updateSysPowerLabel(SysPowerLabel sysPowerLabel) {
        return sysPowerLabelMapper.updateSysPowerLabel(sysPowerLabel);
    }


    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteSysPowerLabelById(Integer id) {
        return sysPowerLabelMapper.deleteSysPowerLabelById(id);
    }

    ;


    /**
     * 批量删除对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysPowerLabelByIds(String[] ids) {
        return sysPowerLabelMapper.deleteSysPowerLabelByIds(ids);
    }
    /**
     * 列表查询不分页
     * @param params
     * @return
     */
    @Override
    public Map sysPowerLabelList(Map<String, Object> params) {
        Map<String, List> map = new HashMap<>();
        List<Integer> checkeds = new ArrayList<>();
        List<SysPowerLabelVO> labels = new ArrayList<>();
        Object menuId = params.get("menuId");
        Object powerName = params.get("powerName");
        Object powerTable = params.get("powerTable");
        labels = sysPowerLabelMapper.sysPowerLabelList(menuId,powerName,powerTable);
        if (labels != null && labels.size() > 0) {
            //判断是不是虚拟岗位页面的列表查询
            Object posdummyId = params.get("posDummyId");
            if (posdummyId != null) {
                //查询出来选中的值
                SysPosdummy sysPosdummy = sysPosdummyMapper.selectById(Integer.valueOf(posdummyId.toString()));
                if (sysPosdummy != null) {
                    String powerIds = sysPosdummy.getPowerIds();
                    if (powerIds != null && !"".equals(powerIds)) {
                        String[] split = powerIds.split(",");
                        for (String s : split) {
                            checkeds.add(Integer.valueOf(s));
                        }
                    }
                }
            }
        }
        map.put("labels", labels);
        map.put("checkeds", checkeds);
        return map;
    }

}
