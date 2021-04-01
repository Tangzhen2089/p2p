package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.IProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.JDBCUtils;

public class ProductDaoImp implements IProductDao{

	//查询所有产品
	@Override
	public List<Product> findAll(String pageNo,String pageSize) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		int pn = Integer.parseInt(pageNo);
		int ps = Integer.parseInt(pageSize);
		return queryRunner.query("select * from product limit ?,?",new BeanListHandler<Product>(Product.class),(pn-1)*ps,ps);
	}

	@Override
	public int findCount() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query("select count(*) from product",new ScalarHandler<Long>()).intValue();
	}

	@Override
	public void addProduct(String proNum, String proName, int proLimit, double annualized) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update("INSERT INTO product VALUES(NULL,?,?,?,?,CURRENT_DATE)",proNum,proName,proLimit,annualized);		
//		queryRunner.update("INSERT INTO product VALUES(NULL,'cz00','xx',20,2.6,CURRENT_DATE)");
	}

	@Override
	public Product findById(int id) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());		
		return queryRunner.query("select * from product where id = ?",new BeanHandler<Product>(Product.class),id);	
	}

	@Override
	public void updateProduct(int id, String proNum, String proName, int proLimit, double annualized) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update("update product set proNum=?,proName=?,proLimit=?,annualized=? where id=?",proNum,proName,proLimit,annualized,id);		
		
	}

	@Override
	public void deleteProduct(int id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update("delete from product where id=?",id);
		
	}

	@Override
	public List<Product> findAllProduct() throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query("select * from product",new BeanListHandler<Product>(Product.class));
	}

}
