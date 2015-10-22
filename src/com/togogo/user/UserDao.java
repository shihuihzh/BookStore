package com.togogo.user;

import java.util.List;

import com.togogo.bean.User;
import com.togogo.common.CommonDao;

/**
 * User表的数据访问对象
 * @author Zhanhao Wong
 *
 */
public class UserDao extends CommonDao<User> {
	
	
	public User findByUsername(String username) {
		List<User> user = find(User.class, "select * from User", "where username=?", username);
		return !user.isEmpty()?user.get(0):null;
	}

}
