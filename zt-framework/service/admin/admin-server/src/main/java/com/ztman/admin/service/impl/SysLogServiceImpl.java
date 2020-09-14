package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysLog;
import com.ztman.admin.mapper.SysLogMapper;
import com.ztman.admin.service.SysLogService;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author ztman
 * @since 2017-11-20
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    private final SysLogMapper sysLogMapper;

    @Override
    public Boolean updateByLogId(Long id) {

        SysLog sysLog = new SysLog();
        sysLog.setId(id);
        sysLog.setDelFlag(CommonConstant.STATUS_DEL);
        sysLog.setUpdateTime(LocalDateTime.now());
        return updateById(sysLog);
    }

    @Override
    public Page selectWithPage(Query<Object> query) throws Exception {
        String startTime = null;
        String endTime = null;
        Object type = query.getCondition().get("type");
        Object title = query.getCondition().get("title");
        Object start = query.getCondition().get("startTime");
        if (start != null) {
            startTime = start.toString();
        }
        Object end = query.getCondition().get("endTime");
        if (end != null) {
            endTime = end.toString();
        }
        List list = new ArrayList();
        if(SecurityUtils.getRoles().contains(CommonConstant.ROLE_ADMIN)){
            list = sysLogMapper.selectWithPage(query, type, startTime, endTime,title,null);
        }else{
            list = sysLogMapper.selectWithPage(query, type, startTime, endTime,title,SecurityUtils.getUserInfo().getUserName());
        }

        query.setRecords(list);
        return query;
    }

    @Override
    public R deleteIds(String ids) {
        String[] id = ids.split(",");
        for (int i = 0; i < id.length ; i++) {
            SysLog sysLog = new SysLog();
            sysLog.setId(Long.valueOf(String.valueOf(id[i])));
            sysLog.setDelFlag(CommonConstant.STATUS_DEL);
            sysLog.setUpdateTime(LocalDateTime.now());
//            updateById(sysLog);
            sysLogMapper.updateById(sysLog);
        }
        return new R(true);
    }
}
