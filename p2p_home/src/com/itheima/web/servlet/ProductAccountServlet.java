package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.domain.JsonMSG;
import com.itheima.service.IAccountService;
import com.itheima.service.IProductAccountService;
import com.itheima.service.impl.AccountServiceImpl;
import com.itheima.service.impl.ProductAccountServiceImpl;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if(method.equals("buy")) {
			try {
				buy(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(method.equals("findByCustomer")) {
			try {
				findByCustomer(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 查询账户资产及购买产品信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */	
	private void findByCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		
		JsonMSG jm = new JsonMSG();
		
		if(customer == null) {
			//未登录
			jm.setStatus(0);
			jm.setMsg("用户未登录,请登录后操作");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;	
		}
		
		IProductAccountService productAccountService = new ProductAccountServiceImpl();
		try {
			Map<String,Object> map = productAccountService.findByCustomer(customer);
			
			jm.setStatus(1);
			jm.setContent(map);
			response.getWriter().write(JSONObject.toJSONString(jm));
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jm.setStatus(0);
			jm.setMsg("资产信息查询失败");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;	
		}
			
		
	}


	/**
	 * 购买产品
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void buy(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//判断用户是否登录
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		JsonMSG jm = new JsonMSG();
		if(customer == null) {
			//未登录
			jm.setStatus(0);
			jm.setMsg("用户未登录,请登录后操作");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;			
		}
		
		
		
		//判断账户余额
		double money = Double.parseDouble(request.getParameter("money"));//投资金额		
		IAccountService accountService = new AccountServiceImpl();
		try {
			Account account = accountService.findByCustomer(customer.getId());//根据客户id查找账户信息
			if(account.getBalance() < money) {
				//余额不足
				jm.setStatus(0);
				jm.setMsg("账户余额不足");
				response.getWriter().write(JSONObject.toJSONString(jm));
				return;		
			}
			
			//完成投资操作
			int pid = Integer.parseInt(request.getParameter("pid"));
			IProductAccountService productAccountService = new ProductAccountServiceImpl();
			productAccountService.buy(account,customer,money,pid);
			
			jm.setStatus(1);
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;		
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			jm.setStatus(0);
			jm.setMsg("投资失败");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;		
			
		}
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
