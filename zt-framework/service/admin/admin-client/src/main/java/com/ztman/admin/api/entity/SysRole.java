package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id", type = IdType.AUTO)
	private Integer roleId;
	private String roleName;
	private String roleCode;
	private String roleDesc;
	private Integer dsType;
	private String dsScope;
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createTime;
	private LocalDateTime updateTime;
	private Integer createBy;
	private String roleFlag;
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableLogic
	private String delFlag;

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

	public SysRole() {
	}

	public SysRole(String roleName, String roleCode, String roleDesc, Date createTime, Integer createBy, String roleFlag, String delFlag) {
		this.roleName = roleName;
		this.roleCode = roleCode;
		this.roleDesc = roleDesc;
		this.createTime = createTime;
		this.createBy = createBy;
		this.roleFlag = roleFlag;
		this.delFlag = delFlag;
	}
}
