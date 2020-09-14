package com.ztman.admin.util.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 用于实现个性化皮肤。<br/>
 * 
 * <pre>
 * 使用方法如下：
 * 
 * &lt;f:link href="style.css" >&lt;/f:link>
 * </pre>
 *
 * <pre> 
 * 构建组：ibps-platform-admin
 * 作者：hugh zhuang
 * 邮箱：3378340995@qq.com
 * 日期：2015-11-5-上午10:55:18
 * 版权：广州流辰信息技术有限公司版权所有
 * </pre>
 */
@SuppressWarnings("serial")
public class StyleTag extends BodyTagSupport {
	protected Logger logger = LoggerFactory.getLogger(StyleTag.class);
	/**
	 * href地址
	 */
	private String href;
	/**
	 * 是否是通用
	 */
	private String isCommon;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	
	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}

	private String getOutput() throws Exception {

		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String ctx = request.getContextPath();
		String skin = "default";
		StringBuffer content = new StringBuffer("<link  rel=\"stylesheet\" type=\"text/css\" href=\"");
		content.append(ctx);
		boolean common = (isCommon!=null && ("false").equals(isCommon))?false:true;
		if(common)
			content.append("/styles/commons/css/");
		else
			content.append("/styles/").append(skin).append("/css/");
		content.append(href);
		content.append("\"/>");

		return content.toString();
	}

	public int doEndTag() throws JspTagException {

		try {
			String str = getOutput();
			pageContext.getOut().print(str);
		} catch (Exception e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}

}
