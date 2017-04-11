/**
 * 
 */
package com.ucmed.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ucmed.common.config.CommonConstants;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.UrlUtil;

/**
 * @author John Lee
 */
public class LoginFilter implements Filter {

	private static final Logger logger = Logger.getLogger(LoginFilter.class);

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
//		ServletContext application = filterConfig.getServletContext();
//		application.setAttribute("HospitalName", UrlUtil.getParams(CommonConstants.HOSPITAL_NAME));
//		String wechatThemeColor = CommonConstants.WECHAT_THEME_COLOR;
//		String wechatThemeColorOld = CommonConstants.WECHAT_THEME_COLOR_OLD;
//		String absoluteUrl = CommonConstants.ABSOLUTE_URL;
//		if(wechatThemeColor != null && !"".equals(wechatThemeColor)
//				&& wechatThemeColor.indexOf("#") == 0
//				&& !wechatThemeColorOld.equals(wechatThemeColor)) {
//			// 修改文件fileName中的wechatThemeColor
//			String fileName = absoluteUrl + "/common/self-style/colorcssstyle.css";
//			String fileStr = FileUtil.readFile(fileName, wechatThemeColor, wechatThemeColorOld);
//			FileUtil.saveFile(fileName, fileStr);
//		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			Object object = req.getSession().getAttribute(Constants.USER_SESSION);
			if(object != null && object != ""
					&& !"".equals(req.getSession().getAttribute(Constants.USER_SESSION))) {
				chain.doFilter(request, response);
				return;
			}
			String path = httpServletRequest.getRequestURI();
			String action = request.getParameter("action");
			String code = request.getParameter("code");
			String hospitalId = request.getParameter("hospitalId");
			if(null != code && !"".equals(code)) {
				req.getSession().setAttribute(Constants.CODE, code);
				logger.info("code!!" + code);
			}
			path = path.toLowerCase();

			// 忽略login.htm、静态js、css图片等
			if(path.endsWith("/weixin/login.htm") || path.endsWith(".js")
					|| path.endsWith("my97datepicker.html") || path.endsWith(".css")
					|| path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".bmp")
					|| path.endsWith(".gif") || path.endsWith(".ico")) {
				chain.doFilter(request, response);
				return;
			} else {
				this.go(response.getWriter(), "/weixin/weixin.htm");
				return;
			}
		} catch(Exception e) {
			logger.error(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	private void go(PrintWriter pw, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type='text/javascript'>parent.parent.window.location.href='");
		sb.append(url);
		sb.append("';</script>");
		pw.println(sb);

		pw.flush();
		pw.close();
	}
}
