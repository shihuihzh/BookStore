package com.togogo.book;

import java.util.List;
import java.util.Set;

import com.togogo.bean.Book;
import com.togogo.common.CommonDao;

/**
 * 
 * Book表的数据访问对象
 * @author Zhanhao Wong
 *
 */
public class BookDao extends CommonDao<Book> {

	
	public List<Book> getAllBook() {
		return find(Book.class, "select * from Book", null, null);
	}

	public List<Book> findByIds(Set<String> keySet) {
		// TODO Auto-generated method stub
		return null;
	}

	public Book findByIds(String id) {
		List<Book> books = find(Book.class, "select * from Book", "where id=?", id);
		return books.isEmpty()?null:books.get(0);
	}
}
