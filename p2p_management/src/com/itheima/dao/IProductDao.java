package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Product;

public interface IProductDao {

	List<Product> findAll(String pageNo, String pageSize) throws Exception;

	int findCount() throws SQLException;

	void addProduct(String proNum, String proName, int proLimit, double annualized) throws Exception;

	Product findById(int id) throws Exception;

	void updateProduct(int id, String proNum, String proName, int proLimit, double annualized) throws Exception;

	void deleteProduct(int id) throws SQLException;

	List<Product> findAllProduct() throws Exception;


}
