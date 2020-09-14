package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生表
 *
 * @author ztman code generator
 * @date 2018-09-29 14:20:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("student")
public class Student extends Model<Student> {

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 创建时间
	 */
	private LocalDateTime createtime;
	/**
	 * 更新时间
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
