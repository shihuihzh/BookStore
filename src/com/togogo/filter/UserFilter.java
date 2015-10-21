package com.togogo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.togogo.common.BookStoreConstants;

public class UserFilter implements Filter {

	public UserFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("通过用户权限过滤器了");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		
		// 从session中获得object
		HttpSession session = req.getSession();
		Object user = session.getAttribute(BookStoreConstants.USER_SESSION_KEY);

		if (user != null) {		// 存在 user 说明登陆过了
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/login.html");
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
