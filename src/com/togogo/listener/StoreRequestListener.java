package com.togogo.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import com.togogo.common.BookStoreConstants;

/**
 * request 创建和销毁的时候监听调用
 * @author Zhanhao Wong
 *
 */
public class StoreRequestListener implements ServletRequestListener {
	
	
	/**
	 * 创建一个单一线程的线程池
	 */
	ExecutorService exector = Executors.newSingleThreadExecutor();

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		//System.out.println("Requset 销毁了:" + sre.getServletRequest());
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		//System.out.println("Requset 初始化了:" + sre.getServletRequest());
		
		ServletContext ctx = sre.getServletContext();
		exector.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				Integer count = (Integer) ctx.getAttribute(BookStoreConstants.REQUEST_COUNT); // 获得list
				if (count == null) {
					count = 0;
				}
				ctx.setAttribute(BookStoreConstants.REQUEST_COUNT, count+1);
				
			}
		});
		

	}

}
