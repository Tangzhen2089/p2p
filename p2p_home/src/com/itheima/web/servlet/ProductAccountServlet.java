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
	 * ��ѯ�˻��ʲ��������Ʒ��Ϣ
	 * @param request
	 * @param response
	 * @throws IOException 
	 */	
	private void findByCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		
		JsonMSG jm = new JsonMSG();
		
		if(customer == null) {
			//δ��¼
			jm.setStatus(0);
			jm.setMsg("�û�δ��¼,���¼�����");
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
			jm.setMsg("�ʲ���Ϣ��ѯʧ��");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;	
		}
			
		
	}


	/**
	 * �����Ʒ
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void buy(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//�ж��û��Ƿ��¼
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		JsonMSG jm = new JsonMSG();
		if(customer == null) {
			//δ��¼
			jm.setStatus(0);
			jm.setMsg("�û�δ��¼,���¼�����");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;			
		}
		
		
		
		//�ж��˻����
		double money = Double.parseDouble(request.getParameter("money"));//Ͷ�ʽ��		
		IAccountService accountService = new AccountServiceImpl();
		try {
			Account account = accountService.findByCustomer(customer.getId());//���ݿͻ�id�����˻���Ϣ
			if(account.getBalance() < money) {
				//����
				jm.setStatus(0);
				jm.setMsg("�˻�����");
				response.getWriter().write(JSONObject.toJSONString(jm));
				return;		
			}
			
			//���Ͷ�ʲ���
			int pid = Integer.parseInt(request.getParameter("pid"));
			IProductAccountService productAccountService = new ProductAccountServiceImpl();
			productAccountService.buy(account,customer,money,pid);
			
			jm.setStatus(1);
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;		
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			jm.setStatus(0);
			jm.setMsg("Ͷ��ʧ��");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;		
			
		}
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
