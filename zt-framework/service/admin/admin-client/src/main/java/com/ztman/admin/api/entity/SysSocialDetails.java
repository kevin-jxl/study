package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统社交登录账号表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_social_details")
public class SysSocialDetails extends Model<SysSocialDetails> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主鍵
	 */
	@TableId
	private Integer id;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * appid
	 */
	private String appId;
	/**
	 * app_secret
	 */
	private String appSecret;
	/**
	 * 回调地址
	 */
	private String redirectUrl;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 删除标记
	 */
	@TableLogic
	private String delFlag;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
