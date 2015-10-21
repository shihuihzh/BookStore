package com.togogo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 当容器启动这个项目或者销毁的时候会调用
 * @author Zhanhao Wong
 *
 */
public class StoreContextListener implements ServletContextListener {

	/* 
		初始化调用
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("SevletContexnt 初始化了:" + sce.getServletContext());
	}

	/* 
	 * 销毁调用
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("SevletContexnt 销毁化了:" + sce.getServletContext());
	}

}
