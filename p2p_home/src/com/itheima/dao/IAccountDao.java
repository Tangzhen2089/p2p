package com.itheima.dao;

import com.itheima.domain.Account;

public interface IAccountDao {

	void addAccount(Account account) throws Exception;

	Account findByCustomer(int id) throws Exception;

	void updateAccount(double balance, int id) throws Exception;

}
