package com.togogo.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.togogo.common.BookStoreConstants;

/**
 * 当session新建或者销毁的时候会调用这个listener
 * 
 * @author Zhanhao Wong
 *
 */
public class StoreSessionListener implements HttpSessionListener {

	/*
	 * session创建时调用
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.
	 * HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		//System.out.println("session 创建了");

		// 获得 servletcontext
		ServletContext ctx = se.getSession().getServletContext();
		List<HttpSession> sessionList = (List<HttpSession>) ctx.getAttribute(BookStoreConstants.SESSION_LIST); // 获得list
		if (sessionList == null) {
			sessionList = new ArrayList<HttpSession>();
			ctx.setAttribute(BookStoreConstants.SESSION_LIST, sessionList);
		}

		// 把session放进列表
		sessionList.add(se.getSession());
	}

	/*
	 * 
	 * session销毁是调用
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.
	 * http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
		//System.out.println("session 销毁了");
		
		// 获得 servletcontext
		ServletContext ctx = se.getSession().getServletContext();
		List<HttpSession> sessionList = (List<HttpSession>) ctx.getAttribute(BookStoreConstants.SESSION_LIST); // 获得list
		
		
		// 把销毁的session从列表中去掉
		sessionList.remove(se.getSession());

	}

}
