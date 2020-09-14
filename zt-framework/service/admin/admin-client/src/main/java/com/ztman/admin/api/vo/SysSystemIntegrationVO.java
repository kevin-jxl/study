package com.ztman.admin.api.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ztman.admin.api.entity.SysSystemIntegration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 1、系统集成、2、物联网集成
 *
 * @author ztman code generator
 * @date 2020-09-07 16:06:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSystemIntegrationVO extends SysSystemIntegration implements Serializable {
	private static final long serialVersionUID = 1L;

	private int interfaceCount;
	private String createName;
}
