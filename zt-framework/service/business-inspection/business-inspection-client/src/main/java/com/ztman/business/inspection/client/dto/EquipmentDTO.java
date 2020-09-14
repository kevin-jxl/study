package com.ztman.business.inspection.client.dto;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 设备表
 *
 * @author zxx
 * @date 2020-08-29 10:35:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("equipment")
public class EquipmentDTO extends Model<EquipmentDTO> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 设备编号
	 */
	private String code; 
	/**
	 * 设备名称
	 */
	private String name;
	/**
	 * 安装时间
	 */
	private LocalDateTime installTime;
	/**
	 * 所属车场
	 */
	private String ofParking;
	/**
	 * 地点
	 */
	private String address;
	/**
	 * 类型
	 */
	private String type;
  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
