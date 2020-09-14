package com.ztman.admin.util.tag;//package com.ztman.bpm.util.tag;
//
//import com.google.common.collect.Maps;
//import com.lc.ibps.auth.utils.SecurityUtil;
//import com.lc.ibps.auth.utils.SubSystemUtil;
//import com.lc.ibps.base.core.util.string.StringUtil;
//import com.lc.ibps.base.web.util.ThreadLocalUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.jsp.JspTagException;
//import javax.servlet.jsp.tagext.BodyTagSupport;
//import java.util.Map;
//
///**
// * 功能按钮权限标签。 <br>
// * 功能：这个标签配合权限分配，可以实现页面上的按钮是否可以点击操作，将权限操作控制到按钮级别。
// * <ul>
// * <li>权限标签的写法: &lt;f:a alias="xxx_edit" classes="fa fa-add" href="xxxEdit.htm"&gt;添加&lt;/f:a&gt;</li>
// * <li>标签中，有个alias（别名）属性，系统根据该别名控制当前用户是否有该操作的的权限。</br>
// * 		showNoRights表示权限是否显示该标签。其它属性类似html中的a标签的属性。</li>
// * <li>标签的使用需要在tld文件和web.xml中进行配置。</li>
// * </ul>
// *
// * <pre>
// *
// * 构建组：ibps-platform-admin
// * 作者：hugh zhuang
// * 邮箱：3378340995@qq.com
// * 日期：2017年6月6日-上午10:50:19
// * 版权：广州流辰信息技术有限公司版权所有
// * </pre>
// */
//@SuppressWarnings("serial")
//public class AnchorTag extends BodyTagSupport {
//	protected Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	/** 链接的 别名 */
//	private String alias;
//	/** 链接的样式class */
//	private String classes;
//	/** 链接的样式 style */
//	private String style;
//	/** 链接的 name */
//	private String name;
//	/** 链接的 name */
//	private String id;
//	/** 链接的 href */
//	private String href;
//	/** 链接的 action */
//	private String action;
//	/** 链接的 onclick */
//	private String onclick;
//	/** 链接的 目标 */
//	private String target;
//
//	/** 当没有权限的时候超链接是否显示。 */
//	private boolean showNoRights = false;
//
//	public void setTarget(String target) {
//		this.target = target;
//	}
//
//	public void setShowNoRights(boolean showNoRights) {
//		this.showNoRights = showNoRights;
//	}
//
//	public void setClasses(String classes) {
//		this.classes = classes;
//	}
//
//	public void setStyle(String style) {
//		this.style = style;
//	}
//
//	public void setAlias(String alias) {
//		this.alias = alias;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public void setHref(String href) {
//		this.href = href;
//	}
//
//	public void setAction(String action) {
//		this.action = action;
//	}
//
//	public void setOnclick(String onclick) {
//		this.onclick = onclick;
//	}
//
//	public int doStartTag() throws JspTagException {
//		return EVAL_BODY_BUFFERED;
//	}
//
//	/**
//	 * 是否有权限
//	 *
//	 * @param systemAlias
//	 *            系统别名
//	 * @return
//	 */
//	protected boolean getHasRights() {
//		String systemAlias = SubSystemUtil.getCurrentSystemAlias((HttpServletRequest) pageContext.getRequest());
//		if (StringUtil.isEmpty(systemAlias))
//			return false;
//		String key = systemAlias + "_" + this.alias;
//		Map<String, Boolean> map = ThreadLocalUtil.get();
//		if (map == null) {
//			map = Maps.newHashMap();
//			return this.setPermissionMap(map, key, systemAlias);
//		} else {
//			if (map.containsKey(key)) {
//				return map.get(key);
//			} else {
//				return this.setPermissionMap(map, key, systemAlias);
//			}
//		}
//
//	}
//
//	/**
//	 * 设置权限map
//	 *
//	 * @param map
//	 * @param key
//	 * @param systemAlias
//	 * @return
//	 */
//	private boolean setPermissionMap(Map<String, Boolean> map, String key, String systemAlias) {
//		// 判断别名是否可以访问。
//		boolean canAccess = SecurityUtil.hasFuncPermission(systemAlias, alias);
//		map.put(key, canAccess);
//		ThreadLocalUtil.set(map);
//		return canAccess;
//	}
//
//	/**
//	 * 输出标签
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	private String getOutput() throws Exception {
//		boolean canAccess = this.getHasRights();
//		// 当没有权限不显示,返回空串。
//		if (!showNoRights && !canAccess)
//			return "";
//
//		String body = this.getBodyContent().getString();
//		StringBuffer sb = new StringBuffer("<a ");
//
//		if (id != null) // ID
//			sb.append(" id=\"" + id + "\" ");
//		if (name != null) // name
//			sb.append(" name=\"" + name + "\" ");
//		if (target != null) // target
//			sb.append(" target=\"" + target + "\" ");
//
//		// 可以访问的情况。
//		if (canAccess) {
//			if (classes != null)
//				sb.append(" class=\"" + classes + "\" ");
//			if (style != null)
//				sb.append(" style=\"" + style + "\" ");
//			if (href != null)
//				sb.append(" href=\"" + href + "\" ");
//			if (action != null)
//				sb.append(" action=\"" + action + "\" ");
//			if (onclick != null)
//				sb.append(" onclick=\"" + onclick + "\" ");
//		} else {
//			if (classes != null)
//				sb.append(" class=\"" + classes + " disabled\" ");
//			else
//				sb.append(" class=\"disabled\" ");
//			// 拼接没权限
//			sb.append(" onclick=\"$.noRights()\" ");
//		}
//
//		sb.append(">").append(body).append("</a>");
//
//		return sb.toString();
//	}
//
//	public int doEndTag() throws JspTagException {
//		try {
//			pageContext.getOut().print(this.getOutput());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new JspTagException(e.getMessage());
//		}
//		return SKIP_BODY;
//	}
//}
