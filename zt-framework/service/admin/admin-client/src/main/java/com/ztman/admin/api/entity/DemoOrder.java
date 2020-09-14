package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 *
 * @author ztman code generator
 * @date 2018-10-24 10:02:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("demo_order")
public class DemoOrder extends Model<DemoOrder> {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID
	 */
	@TableId
	private Integer id;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品数量
	 */
	private Integer productNum;
	/**
	 * 下单时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
