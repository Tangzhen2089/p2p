package com.itheima.dao;

import java.util.List;

import com.itheima.domain.ProductAccount;

public interface IProductAccountDao {


	void add(ProductAccount productAccount) throws Exception;

	List<Object[]> findByCustomer(int id)throws Exception;

}
