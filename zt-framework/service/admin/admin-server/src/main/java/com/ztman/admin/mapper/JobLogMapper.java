package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.JobLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务执行历史
 *
 * @author ztman code generator
 * @date 2018-11-13 18:38:43
 */
@Mapper
public interface JobLogMapper extends BaseMapper<JobLog> {

}
