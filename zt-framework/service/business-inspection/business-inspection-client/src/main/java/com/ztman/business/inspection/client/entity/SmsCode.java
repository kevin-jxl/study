package com.ztman.business.inspection.client.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信验证码
 *
 * @author zxx
 * @date 2020-08-29 11:11:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sms_code")
public class SmsCode extends Model<SmsCode> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 验证码类型
	 */
	private String type;
	/**
	 * 手机号
	 */
	private String phoneNumber;
	/**
	 * 代码
	 */
	private String code;
	/**
	 * 过期时间
	 */
	private LocalDateTime expiresTime;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
