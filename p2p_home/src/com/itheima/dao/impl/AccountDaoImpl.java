package com.itheima.dao.impl;



import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.utils.JDBCUtils;

public class AccountDaoImpl implements IAccountDao {

	//����˻�
	@Override
	public void addAccount(Account a) throws Exception {
		
		
		
		  QueryRunner queryRunner = new QueryRunner();
		  queryRunner.update(JDBCUtils.getConnection(),"insert into account values(null,?,?,?,?)",a.getTotal(),a.getBalance(),a.getInterest(),a.getCustomer().getId());
		 

		
	}

	//��ѯ�˻�
	@Override
	public Account findByCustomer(int id) throws Exception {
		 QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		 
		 return queryRunner.query("select * from account where c_id = ?",new BeanHandler<Account>(Account.class),id);
		 
	}

	//�޸����
	@Override
	public void updateAccount(double balance, int id) throws Exception {
		QueryRunner queryRunner = new QueryRunner();
		
		queryRunner.update(JDBCUtils.getConnection(),"update account set balance = ? where id = ?",balance,id);
						
	}

}
