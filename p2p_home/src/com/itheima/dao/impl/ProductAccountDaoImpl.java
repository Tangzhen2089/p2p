package com.itheima.dao.impl;


import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import com.itheima.dao.IProductAccountDao;
import com.itheima.domain.ProductAccount;
import com.itheima.utils.JDBCUtils;

public class ProductAccountDaoImpl implements IProductAccountDao {
	//天机投资记录
	@Override
	public void add(ProductAccount productAccount) throws Exception {
		QueryRunner queryRunner = new QueryRunner();
		queryRunner.update(JDBCUtils.getConnection(),
				"insert into product_account values(null,?,CURRENT_DATE,?,?,?,?)",
				productAccount.getPa_num(),productAccount.getCustomer().getId(),
				productAccount.getProduct().getId(),productAccount.getMoney(),productAccount.getInterest());
		
	}

	@Override
	public List<Object[]> findByCustomer(int id) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		List<Object[]> list = queryRunner.query("select pa.pa_num,p.proName,p.proLimit,p.annualized,pa.interest,c.c_name,pa.pa_date,pa.money "
				+ "from product_account pa,product p,customer c where pa.c_id=c.id and pa.p_id=p.id and c.id = ?",
				new ArrayListHandler(),id);
		return list;
	}

}
