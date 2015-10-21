package com.togogo.test;

import java.util.Random;

import com.togogo.bean.Book;
import com.togogo.book.BookDao;
import com.togogo.common.CommUtil;

/**
 * book数据表初始化
 * @author Zhanhao Wong
 *
 */
public class BookInit {
	
	public static void main(String[] args) {
		// 随机
		Random rand = new Random(); 
		
		// 实例化bookDao
		BookDao dao = new BookDao();
		
		// 产生20本书
		for(int  i = 1; i <= 20; i++) {
			dao.save(new Book(CommUtil.UUID(), "Java 丛书(" + i + ")", rand.nextInt(20)+30, "/image/book.png"));
		}
	}

}
