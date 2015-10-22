package com.togogo.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.togogo.common.BookStoreConstants;

/**
 * 添加网站信息到页面最后
 */
public class InfoFilter implements Filter {

    public InfoFilter() {
    }

	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		ServletContext ctx = request.getServletContext();
		long oldTime = System.currentTimeMillis();
		String author = ctx.getInitParameter("author");
		List sessionList = (List)ctx.getAttribute(BookStoreConstants.SESSION_LIST);
		Integer rq = (Integer) ctx.getAttribute(BookStoreConstants.REQUEST_COUNT);
		
		chain.doFilter(request, response);
		
		
		try {
			// 把信息打印
			PrintWriter out = response.getWriter();
			out.println(String.format("<p>Power by %s <br>网站在线人数：%d <br>网站总请求次数: %d <br>页面渲染时间：%d ms</p>", author, sessionList == null?0:sessionList.size(), rq==null?0:rq, System.currentTimeMillis()-oldTime));
		} catch (Exception e) {
		}
		
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
