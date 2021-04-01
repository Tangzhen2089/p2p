package com.itheima.dao.impl;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.ICustomerDao;
import com.itheima.domain.Customer;
import com.itheima.utils.JDBCUtils;

public class CustomerDaoImpl implements ICustomerDao {

	//添加用户
	@Override
	public void addCustomer(Customer c) throws Exception {
		QueryRunner queryRunner = new QueryRunner();
		
		queryRunner.update(JDBCUtils.getConnection(), "insert into customer values(null,?,?,0,?)",c.getC_name(),c.getEmail(),c.getPassword());
		
	}

	//根据用户名查询
	@Override
	public Customer findByName(String c_name) throws Exception {
		QueryRunner queryRunner = new QueryRunner();		
		return queryRunner.query(JDBCUtils.getConnection(),"select * from customer where c_name = ?",new BeanHandler<Customer>(Customer.class),c_name);
	
	}

	//根据邮箱查找
	@Override
	public Customer findByEmail(String email) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());		
		return queryRunner.query( "select * from customer where email = ?",new BeanHandler<Customer>(Customer.class),email);
	}

	@Override
	public Customer login(String c_name, String password) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());		
		Customer c = queryRunner.query( "select * from customer where c_name = ? and password = ?",new BeanHandler<Customer>(Customer.class),c_name,password);
		if(c==null) {
			c = queryRunner.query( "select * from customer where email = ? and password = ?",new BeanHandler<Customer>(Customer.class),c_name,password);
		}
		return c;
		
	}

	@Override
	public void updateEmailStatus(String email) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());		
		queryRunner.update( "UPDATE customer SET email_status = 1 WHERE email = ?",email);
		
	}

}
