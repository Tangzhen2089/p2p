package com.itheima.service.impl;

import java.sql.SQLException;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.ICustomerDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.dao.impl.CustomerDaoImpl;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.service.ICustomerService;
import com.itheima.utils.JDBCUtils;

public class CustomerServiceImpl implements ICustomerService {

	@Override
	public Customer regist(Customer c) {
		try {
			JDBCUtils.startTransaction();
		//注册用户
			ICustomerDao customerDao = new CustomerDaoImpl();
			customerDao.addCustomer(c);
			Customer c1 = customerDao.findByName(c.getC_name());
			
			
		//添加账户,需要customer的id
			Account account = new Account();
			account.setCustomer(c1);
			
			IAccountDao accountDao = new AccountDaoImpl();
			accountDao.addAccount(account);
		
			return c1;
			
		}catch(Exception e) {
			e.printStackTrace();
			try {
				JDBCUtils.rollBack();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			try {
				JDBCUtils.commit();
				JDBCUtils.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return null;
	}

	@Override
	public Customer findByName(Customer c) throws Exception {
		ICustomerDao customerDao = new CustomerDaoImpl();
		return customerDao.findByName(c.getC_name());
	}

	@Override
	public Customer findByEmail(Customer c) throws Exception {
		ICustomerDao customerDao = new CustomerDaoImpl();
		return customerDao.findByEmail(c.getEmail());
	}

	@Override
	public Customer login(String c_name, String password) throws Exception {
		ICustomerDao customerDao = new CustomerDaoImpl();
		
		return customerDao.login(c_name,password);
		
	}

	@Override
	public void updateEmailStatus(String email) throws Exception {
		ICustomerDao customerDao = new CustomerDaoImpl();		
		customerDao.updateEmailStatus(email);
	}

}
