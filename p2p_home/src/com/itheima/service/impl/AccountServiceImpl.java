package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.domain.Account;
import com.itheima.service.IAccountService;

public class AccountServiceImpl implements IAccountService {

	@Override
	public Account findByCustomer(int id) throws Exception {
		IAccountDao accountDao = new AccountDaoImpl();		
		return accountDao.findByCustomer(id);		
	}

}
