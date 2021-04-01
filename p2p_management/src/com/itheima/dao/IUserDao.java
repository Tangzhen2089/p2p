package com.itheima.dao;

import com.itheima.domain.User;

public interface IUserDao {

	User findUser(String username, String password) throws Exception;

}
