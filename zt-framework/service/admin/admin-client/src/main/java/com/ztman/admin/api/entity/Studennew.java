package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生表测试
 *
 * @author  code generator
 * @date 2019-01-21 19:02:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("student_new")
public class Studennew extends Model<Studennew> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 地址
	 */
	private String deptId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createtime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updatetime;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
