package com.itheima.dao;

import com.itheima.domain.Customer;

public interface ICustomerDao {

	void addCustomer(Customer c) throws Exception;

	Customer findByName(String c_name) throws Exception;

	Customer findByEmail(String email) throws Exception;

	Customer login(String c_name, String password) throws Exception;

	void updateEmailStatus(String email) throws Exception;

}
