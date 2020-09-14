package com.ztman.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysLog;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author ztman
 * @since 2017-11-20
 */
public interface SysLogService extends IService<SysLog> {

	/**
	 * 通过ID删除日志（逻辑删除）
	 *
	 * @param id 日志ID
	 * @return true/false
	 */
	Boolean updateByLogId(Long id);

    Page selectWithPage(Query<Object> objectQuery) throws Exception;

    R deleteIds(String ids);
}
