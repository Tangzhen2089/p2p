package com.itheima.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.IProductAccountDao;
import com.itheima.dao.IProductDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.dao.impl.ProductAccountDaoImpl;
import com.itheima.dao.impl.ProductDaoImp;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.domain.Product;
import com.itheima.domain.ProductAccount;
import com.itheima.service.IProductAccountService;
import com.itheima.utils.JDBCUtils;

public class ProductAccountServiceImpl implements IProductAccountService {

	//购买产品
	@Override
	public void buy(Account account, Customer customer, double money, int pid)  {
		
		IAccountDao accountDao = new AccountDaoImpl();
		//1.修改客户账户信息,现阶段只需要修改余额
//		double balance = account.getBalance() - money;//敏感数据操作时，使用BigInteger BigDecimal
		BigDecimal bd1 = new BigDecimal(account.getBalance());//原来的账户余额
		BigDecimal bd2 = new BigDecimal(money);//购买本金
		
		double balance = bd1.subtract(bd2).doubleValue();//减后的余额
		
		//开启事务
		try {
			JDBCUtils.startTransaction();
			
			accountDao.updateAccount(balance,account.getId());
			
			//2.向账户产品表插入数据
			//2.1 查询产品
			IProductDao productDao = new ProductDaoImp();
			Product product = productDao.findById(pid);
			
			//2.2 计算收益
			BigDecimal bd3 = new BigDecimal(product.getAnnualized());//利率
			BigDecimal bd4 = new BigDecimal(product.getProLimit());//期限
			double interest = bd2.multiply(bd3).multiply(bd4).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			//2.3 封装数据
			ProductAccount productAccount = new ProductAccount();
			productAccount.setCustomer(customer);
			productAccount.setProduct(product);
			productAccount.setInterest(interest);
			productAccount.setMoney(money);
			String pa_num = "tz"+new Date().getTime();
			productAccount.setPa_num(pa_num);
			
			//2.4  添加记录
			IProductAccountDao productAccountDao = new ProductAccountDaoImpl();
			productAccountDao.add(productAccount);			
			
			
			
			
		} catch (Exception e) {
			//出现异常回滚
			e.printStackTrace();
			try {
				JDBCUtils.rollBack();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			//提交事务，关闭连接
			try {
				JDBCUtils.commit();
				JDBCUtils.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}

	/**
	 * 查询账户信息及购买产品信息
	 */
	@Override
	public Map<String, Object> findByCustomer(Customer customer) throws Exception {
		
		//查询账户信息
		IAccountDao accountDao = new AccountDaoImpl();
		Account account = accountDao.findByCustomer(customer.getId());
		
		//购买产品信息
		IProductAccountDao productAccountDao = new ProductAccountDaoImpl();
		List<Object[]> list = productAccountDao.findByCustomer(customer.getId());
		
		//封装数据
		List<ProductAccount> pas = new ArrayList<ProductAccount>();
		for (Object[] Obj : list) {
			ProductAccount pa = new ProductAccount();
			pa.setPa_num((String) Obj[0]);
			pa.setInterest((double) Obj[4]);
			pa.setPa_date((Date) Obj[6]);
			pa.setMoney((double) Obj[7]);
			
			Product p = new Product();
			p.setProName((String) Obj[1]);
			p.setProLimit((int) Obj[2]);
			p.setAnnualized((double) Obj[3]);			
			pa.setProduct(p);
			
			Customer c = new Customer();
			c.setC_name((String) Obj[5]);
			pa.setCustomer(c);
			
			pa.setStatus(checkStatus(pa.getPa_date(),p.getProLimit()));//购买产品是否到期
			
			pas.add(pa);
			
			
		}
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("account", account);
		map.put("productAccount", pas);
				
		return map;
	}

	/**
	 * 
	 * @param pa_date 购买产品的日期
	 * @param proLimit 产品的期限
	 * @return
	 */
	private int checkStatus(Date pa_date, int proLimit) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(pa_date);
		c.add(Calendar.MONTH, proLimit);
		if(c.getTimeInMillis()-new Date().getTime()>=0) {
			//未到期
			return 0;
		}else {
			//到期
			return 1;
		}
	}

}
