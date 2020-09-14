package com.ztman.admin.api.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ztman.admin.api.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author ztman code generator
 * @date 2019-01-24 14:53:07
 */
@Data
public class SysUserVO extends SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;
}
