package com.ztman.admin.api.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ztman.admin.api.entity.SysSystemInterface;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统集成的接口管理
 *
 * @author ztman code generator
 * @date 2020-09-07 16:06:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSystemInterfaceVO extends SysSystemInterface implements Serializable{
	private static final long serialVersionUID = 1L;
	private String createName;

}
