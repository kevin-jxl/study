package com.ztman.admin.api.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.ztman.admin.api.dto.DeptTree;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class RoleVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id", type = IdType.AUTO)
	private Integer roleId;
	private String roleName;
	private String roleCode;
	private String roleDesc;
	private Integer dsType;
	private String dsScope;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	@TableLogic
	private String delFlag;//删除标识（0-正常,1-删除）

	private List<DeptTree> deptTrees;//角色的数据权限

}
