package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.JobAndTrigger;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务
 *
 * @author ztman code generator
 * @date 2018-10-12 21:00:05
 */
@Mapper
public interface JobAndTriggerMapper extends BaseMapper<JobAndTrigger> {
    /**
     * 获取定时任务信息
     * @return
     * @param query
     * @param description
     */
     List<JobAndTrigger> getJobAndTriggerDetails(Query  query,@Param("description") Object description );

    /**
     * 根据类名查找任务信息
     * @return
     * @param jobClassName
     */
    List<JobAndTrigger> getJobByClassName(@Param("jobClassName") Object jobClassName );
}
