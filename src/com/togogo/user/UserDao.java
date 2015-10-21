package com.togogo.user;

import java.util.List;

import com.togogo.bean.User;
import com.togogo.common.CommonDao;

public class UserDao extends CommonDao {
	
	
	public static void main(String[] args) {
		new UserDao();
	}

	public User findByUsername(String username) {
		List<User> user = find(User.class, "select * from user", "where username=?", username);
		return !user.isEmpty()?user.get(0):null;
	}

}
