package com.itheima.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.domain.User;
import com.itheima.utils.JDBCUtils;

public class UserDaoImp implements IUserDao{

	@Override
	public User findUser(String username, String password) throws SQLException {
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		return queryRunner.query("select * from user where username = ? and password = ?",new BeanHandler<User>(User.class),username,password );
		
		
	}

}
