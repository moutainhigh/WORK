/**
 * @Title: ContextPathFilter.java
 * @Package com.xlkfinance.bms.web.filter
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月10日 下午6:18:42
 * @version V1.0
 */
package com.xlkfinance.bms.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.xlkfinance.bms.common.util.BoolUtil;
import com.xlkfinance.bms.web.constant.Constants;

public class ContextPathFilter implements Filter {
	private boolean isRemoteResources = false;
	private String remotereSourcesUrl;

	@Override
	public void init(FilterConfig config) throws ServletException {
		isRemoteResources = BoolUtil.isTrue(config.getInitParameter("isRemoteResources"));
		remotereSourcesUrl = config.getInitParameter("remotereSourcesUrl");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest)request);
		if (!isRemoteResources) {
			String contextPath = xssRequest.getContextPath();
			xssRequest.setAttribute(Constants.CONTEXT_PATH, contextPath);
		} else {
			xssRequest.setAttribute(Constants.CONTEXT_PATH, remotereSourcesUrl);
		}
		chain.doFilter(xssRequest, response);*/
		
		if (!isRemoteResources) {
			String contextPath = ((HttpServletRequest) request).getContextPath();
			request.setAttribute(Constants.CONTEXT_PATH, contextPath);
		} else {
			request.setAttribute(Constants.CONTEXT_PATH, remotereSourcesUrl);
		}
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
	}

	@Override
	public void destroy() {

	}

}
