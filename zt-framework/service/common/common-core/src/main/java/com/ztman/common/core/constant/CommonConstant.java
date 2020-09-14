
package com.ztman.common.core.constant;

/**
 * 通用信息
 */
public interface CommonConstant {
	String ZTFRAMEWOEK = "基础平台";
	String ZTSPARK = "集成运营平台";
	/**
	 * token请求头名称
	 */
	String REQ_HEADER = "Authorization";

	/**
	 * token分割符
	 */
	String TOKEN_SPLIT = "Bearer ";

	/**
	 * jwt签名
	 */
	String SIGN_KEY = "spark";
	/**
	 * 删除
	 */
	String STATUS_DEL = "0";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "1";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 按钮
	 */
	String BUTTON = "1";

	/**
	 * 目录
	 */
	String LIST = "9";

	/**
	 * 删除标记
	 */
	String DEL_FLAG = "del_flag";
	String ROLE_ADMIN = "ROLE_ADMIN";
	/**
	* 角色状态
	* */
	String ROLE_FLAG = "role_flag";
	/**
	 * 创建人
	 * */
	String CREATE_BY = "create_by";

	/**
	 * 创建人
	 * */
	String UPDATE_BY = "update_by";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	/**
	 * 阿里大鱼
	 */
	String ALIYUN_SMS = "aliyun_sms";

	/**
	 * 路由信息Redis保存的key
	 */
	String ROUTE_KEY = "_ROUTE_KEY";
}
