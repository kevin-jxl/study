package com.ztman.ztspark.entity.company;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文档管理表
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("company_file")
public class CompanyFile extends Model<CompanyFile> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 文档名称
	 */
	private String documentName;
	/**
	 * 签订方
	 */
	private String signingParty;
	/**
	 * 签订时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date signingTime;
	/**
	 * 终止时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date terminationTime;

	private Integer comId;
	/**
	 * 合作模式
	 */
	private String cooperationMode;
	/**
	 * 负责人
	 */
	private String leading;
	/**
	 * 文档编码
	 */
	private String code;
	/**
	 * 文档备注
	 */
	private String remark;
	/**
	 * 负责人电话
	 */
	private String tel;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createTime;
	/**
	 * 创建人
	 */
	private Integer createBy;
	/**
	 * 修改时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date updateTime;
	/**
	 * 修改人
	 */
	private Integer updateBy;
	/**
	 * 删除状态，0-删除，1-正常
	 */
	private String delFlag;

	private List imgs;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
