package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.Account;
import com.itheima.domain.Customer;
import com.itheima.domain.JsonMSG;
import com.itheima.service.IAccountService;
import com.itheima.service.ICustomerService;
import com.itheima.service.impl.AccountServiceImpl;
import com.itheima.service.impl.CustomerServiceImpl;

/**
 * Servlet implementation class AccountServlet
 */
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if("findByCustomer".equals(method)) {
			try {
				findByCustomer(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


	public  void findByCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		
		JsonMSG jm = new JsonMSG();
		if(customer == null) {
			//用户未登录
			jm.setStatus(0);
			jm.setMsg("还未登录，请先登录");
			response.setContentType("appliction/json");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;
		}else {
			
			IAccountService accountService = new AccountServiceImpl();
			Account account = accountService.findByCustomer(customer.getId());
			if(account == null) {
				jm.setStatus(0);
				jm.setMsg("用户账户信息查询失败");
				response.setContentType("appliction/json");
				response.getWriter().write(JSONObject.toJSONString(jm));
				return;
			}
			ICustomerService customerService = new CustomerServiceImpl();
			Customer c = customerService.findByEmail(customer);
			account.setCustomer(c);
			jm.setStatus(1);
			jm.setContent(account);
			response.setContentType("appliction/json");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;
				
			
			
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
