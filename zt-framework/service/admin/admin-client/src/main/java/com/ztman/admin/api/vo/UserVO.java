package com.ztman.admin.api.vo;

import com.ztman.admin.api.entity.SysPost;
import com.ztman.admin.api.entity.SysRole;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 随机盐
	 */
	private String salt;

	/**
	 * 微信openid
	 */
	private String wxOpenid;

	/**
	 * QQ openid
	 */
	private String qqOpenid;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 0-正常，1-删除
	 */
	private String delFlag;
	/**
	 * 简介
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 用户真实姓名
	 */
	private String userRealname;
	/**
	 * 0-男  1-女
	 */
	private String sex;
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
	 * 用户状态 0-正常 1-停用
	 */
	private String userFlag;
	/**
	 * 密码到期时间
	 */
	private LocalDateTime passwordEndtime;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 *
	 */
	private String remark;
	/**
	 * 工程师备注
	 */
	private String adminRemark;


	/**
	 * 部门ID
	 */
	private Integer deptId;
	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 角色列表
	 */
	private List<SysRole> roleList;
	/**
	 * 岗位列表
	 */
	private List<SysPost> postList;

	/**
	 * 部门的层级列表
	 */
	private List<Integer> deptList;

	/**
	 * 岗位ID
	 */
	private String postIds;
	/**
	 * 岗位名称
	 */
	private String postNames;
	private String postName;
	private Integer areaId;
	private Integer organId;
	private Integer companyId;
	private Integer serviceOrganId;

}
