package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysLog;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 日志表 Mapper 接口
 * </p>
 *
 * @author ztman
 * @since 2017-11-20
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    List selectWithPage(Query<Object> query, @Param("type") Object type, @Param("startTime") String startTime, @Param("endTime") String  endTime,@Param("title") Object title,@Param("username")String username);
}
