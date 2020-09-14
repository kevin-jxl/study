package com.ztman.admin.api.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业表
 *
 * @author jxl
 * @date 2020-08-21 10:04:46
 */
@Data
@Excel("企业管理")
public class SysCompanyVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer comId;
	/**
	 * 企业名称
	 */
	@ExcelField(value = "企业名称")
	private String name;
	/**
	 * 企业编号
	 */
	@ExcelField(value = "企业编号")
	private String code;
	/**
	 * 所属租户id
	 */
	@ExcelField(value = "所属租户")
	private String tenantName;
	/**
	 * 负责人id
	 */
	@ExcelField(value = "负责人")
	private String adminName;
	/**
	 * 联系方式
	 */
	@ExcelField(value = "联系方式")
	private String phone;
	/**
	 * 创建人id
	 */
	@ExcelField(value = "创建人")
	private String createName;
	/**
	 *
	 */
	private Integer tenantId;
	/**
	 * 创建时间
	 */
	@ExcelField(value = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 服务状态
	 */
	@ExcelField(value = "服务状态")
	private String status;
	/**
	 * 描述
	 */
	@ExcelField(value = "备注")
	private String description;
}
