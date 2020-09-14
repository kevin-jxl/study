package com.ztman.admin.api.entity;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author ztman code generator
 * @date 2019-01-24 14:53:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Integer userId;
	/**
	 * 登陆名称
	 */
	private String username;
	/**
	 * 登陆密码
	 */
	private String password;
	/**
	 * 用户真实姓名
	 */
	private String userRealname;
	/**
	 * 0-男  1-女
	 */
	private String sex;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 员工号
	 */
	private String empno;
	/**
	 * 用户电子签名
	 */
	private String signName;
	/**
	 * 部门ID
	 */
	private Integer deptId;
	/**
	 * 随机盐
	 */
	private String salt;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 用户状态 0-正常 1-停用
	 */
	private String userFlag;
	/**
	 * 密码到期时间
	 */
	private LocalDateTime passwordEndtime;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 0-正常，1-删除
	 */
	private String delFlag;
	/**
	 * 微信openid
	 */
	private String wxOpenid;
	/**
	 * QQ openid
	 */
	private String qqOpenid;
	/**
	 *
	 */
	private String remark;
	/**
	 * 工程师备注
	 */
	private String adminRemark;
	private String postName;
	private Integer areaId;
	private Integer organId;
	private Integer companyId;
	private Integer serviceOrganId;
	private String tenantFlag;
	private Integer tenantId;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.userId;
	}
}
