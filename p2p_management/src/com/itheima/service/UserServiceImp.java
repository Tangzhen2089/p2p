package com.itheima.service;

import com.itheima.dao.IUserDao;
import com.itheima.dao.UserDaoImp;
import com.itheima.domain.User;

public class UserServiceImp implements IUserService {

	@Override
	public User login(String username, String password) {
		IUserDao userDao = new UserDaoImp();
		
		try {
			return userDao.findUser(username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();			
		}
		return null;		
	}

}
