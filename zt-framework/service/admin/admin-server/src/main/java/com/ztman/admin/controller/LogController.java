package com.ztman.admin.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysLog;
import com.ztman.admin.api.vo.SysConsumerVO;
import com.ztman.admin.service.SysLogService;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.dianping.cat.Cat;
//import com.dianping.cat.message.Event;
//import com.dianping.cat.message.Transaction;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author ztman
 * @since 2017-11-20
 */
@RestController
@RequestMapping("/log")
public class LogController {
	@Autowired
	private SysLogService sysLogService;
	//excel工具类
	ExcelUtil util = new ExcelUtil();
	/**
	 * 分页查询日志信息
	 *
	 * @param params 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/logPage")
	public Page logPage(@RequestParam Map<String, Object> params) throws Exception {
		return sysLogService.selectWithPage(new Query<>(params));
	}

	/**
	 * 根据ID
	 *
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_log_del')")
	public R<Boolean> delete(@PathVariable Long id) {
		return new R<>(sysLogService.updateByLogId(id));
	}

	/**
	 * 删除多个
	 * @return success、false
	 */
	@DeleteMapping("/delete")
	@PreAuthorize("@pms.hasPermissions('sys_log_del,sys_bus_del,sys_login_del')")
	public R<Boolean> roleMenuUpd(@RequestParam(value = "ids") String ids) {
		return sysLogService.deleteIds(ids);
	}
	/**
	 * 插入日志
	 *
	 * @param sysLog 日志实体
	 * @return success/false
	 */
	@PostMapping
	public R<Boolean> save(@RequestBody SysLog sysLog) {
		return new R<>(sysLogService.insert(sysLog));
	}

	//下载Excel
	@GetMapping("/downloadExcel")
	@PreAuthorize("@pms.hasPermissions('sys_log_del,sys_bus_del,sys_login_del')")
	public void downloadExcel( @RequestParam Map<String, Object> params,HttpServletResponse res) throws IOException {
		List<SysLog> records = null;
		try {
			records = logPage(params).getRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] headInfo = {"序号", "编号", "用户名", "操作时间", "ip", "操作记录"};
		String[] sheetName = {"日志"};
		util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
		//创建第一行标题头
		List<String[]> list2 = new ArrayList<String[]>();
		list2.add(headInfo);

		List<Integer[]> alist = new ArrayList<>();
		Integer[] a1 = {0, 0, 0, headInfo.length};//开始行,结束行,开始列,结束列
		alist.add(a1);
		List<Integer[]> blist = new ArrayList<>();

		util.setValue(list2, util.getCellType4Merge(0, 10, blist), 0, 0);
		for (int i = 0; i < records.size(); i ++) {
			SysLog obj = records.get(i);
			List<String[]> cacheList = new ArrayList<String[]>();
			String[] tb = new String[headInfo.length];
			int index = 0;
			tb[index++] = String.valueOf(i+1);
			tb[index++] = String.valueOf(obj.getId());
			tb[index++] = obj.getCreateBy();
			tb[index++] = obj.getCreateTime().toLocalDate().toString();
			tb[index++] = obj.getRemoteAddr();
			tb[index++] = obj.getTitle();
			cacheList.add(tb);
			util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
		}
		util.exportExcel(res, "日志");
	}

}
