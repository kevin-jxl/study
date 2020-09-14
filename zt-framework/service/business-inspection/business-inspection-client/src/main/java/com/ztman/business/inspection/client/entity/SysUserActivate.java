package com.ztman.business.inspection.client.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户激活
 *
 * @author zxx
 * @date 2020-08-29 16:05:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_activate")
public class SysUserActivate extends Model<SysUserActivate> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String tenantId;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 
	 */
	private String type;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.tenantId;
  }
}
