package com.itheima.service;

import com.itheima.domain.Customer;

public interface ICustomerService {

	Customer regist(Customer c);

	Customer findByName(Customer c) throws Exception;

	Customer findByEmail(Customer c) throws Exception;

	Customer login(String c_name, String password) throws Exception;

	void updateEmailStatus(String email) throws Exception;


}
