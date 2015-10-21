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
		
		
		// 从session中获得username
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");

		if (username != null) {		// 存在 username 说明登陆过了
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/login2.html");
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
