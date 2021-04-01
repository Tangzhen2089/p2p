package com.itheima.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.IProductDao;
import com.itheima.dao.ProductDaoImp;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

public class ProductServiceImp implements IProductService {

	@Override
	public PageBean findAll(String pageNo, String pageSize) throws Exception {
		IProductDao productDao = new ProductDaoImp();
		List<Product> list = productDao.findAll(pageNo,pageSize);
		int totalCount= productDao.findCount();
		int totalPage=(int) Math.ceil(totalCount*1.0/Integer.parseInt(pageSize));//int数据相除还是int，应*1.0转成double,结果向上取整
		
		
		PageBean pb = new PageBean();
		pb.setPageNo(Integer.parseInt(pageNo));
		pb.setPageSize(Integer.parseInt(pageSize));
		pb.setTotalCount(totalCount);
		pb.setContent(list);
		pb.setTotalPage(totalPage);		
		return pb;
		
	}

	@Override
	public void addProduct(String proNum, String proName, int proLimit, double annualized) throws Exception {
		IProductDao productDao = new ProductDaoImp();
		productDao.addProduct(proNum,proName,proLimit,annualized);
		
	}

	@Override
	public Product findById(int id) throws Exception {
		IProductDao productDao = new ProductDaoImp();
		
		return productDao.findById(id);
		
	}

	@Override
	public void updateProduct(int id, String proNum, String proName, int proLimit, double annualized) throws Exception {
		IProductDao productDao = new ProductDaoImp();
		productDao.updateProduct(id,proNum,proName,proLimit,annualized);

		
	}

	@Override
	public void deleteProduct(int id) throws SQLException {
		IProductDao productDao = new ProductDaoImp();
		productDao.deleteProduct(id);
		
	}

	@Override
	public List<Product> findAllProduct() throws Exception {
		IProductDao productDao = new ProductDaoImp();
		return productDao.findAllProduct();
	}

}
