package com.itheima.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.Customer;
import com.itheima.domain.JsonMSG;
import com.itheima.service.ICustomerService;
import com.itheima.service.impl.CustomerServiceImpl;
import com.itheima.utils.MailUtils;
import com.itheima.utils.RandomChar;


/**
 * Servlet implementation class CustomerServlet
 */
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		
		//ע�����
		if(method.equals("registe")) {
			try {
				regist(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//��¼����
		if(method.equals("login")) {
			try {
				login(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//�����û�
		if(method.equals("findCustomer")) {
			try {
				findCustomer(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		//�����ʼ�
		if(method.equals("sendEmail")) {
			
				sendEmail(request,response);
					
		}
		
		//�����ʼ�
		if(method.equals("checkEmail")) {
			
			checkEmail(request,response);
					
		}
		
	}
	
	//������֤��У��
	public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("emailAddr");
		String emailCode = request.getParameter("emailCheckCode");
		
		String emailCheckCode = (String) request.getSession().getAttribute("emailCheckCode");
		
		JsonMSG jm = new JsonMSG();
		if(!emailCheckCode.equalsIgnoreCase(emailCode)) {
			jm.setStatus(0);
			jm.setMsg("������֤�벻��ȷ");		
			try {
				response.getWriter().write(JSONObject.toJSONString(jm));
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		ICustomerService customerService = new CustomerServiceImpl();
		try {
			customerService.updateEmailStatus(email);
			jm.setStatus(1);
			jm.setMsg("������֤�ɹ�");	
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jm.setStatus(0);
			jm.setMsg("������֤ʧ��");	
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;
		}
		
		
				
	}

	public void sendEmail(HttpServletRequest request, HttpServletResponse response)  {
		
		String email = request.getParameter("email");
		
		String emailCheckCode = RandomChar.getRandomchar(4);
	
		request.getSession().setAttribute("emailCheckCode", emailCheckCode);
		
		JsonMSG jm = new JsonMSG();
		try {
			MailUtils.sendMail(email, "������֤��Ϊ:"+emailCheckCode, "��֤��Ϣ");
			jm.setStatus(1);
			
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;
		} catch (Exception e) {			
			e.printStackTrace();
			jm.setStatus(0);
			jm.setMsg("�ʼ�����ʧ��");
			
			try {
				response.getWriter().write(JSONObject.toJSONString(jm));
				return;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
	}
	//�����û�,����������֤
	private void findCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer c = (Customer) request.getSession().getAttribute("customer");
		
		ICustomerService customerService = new CustomerServiceImpl();
		Customer customer = customerService.findByEmail(c);
		JsonMSG jsonMsg = new JsonMSG();
		
		if(customer.getEmail_status()!=0) {
			
			jsonMsg.setStatus(0);
			jsonMsg.setMsg("��������֤");			
		}else {
			
			jsonMsg.setStatus(1);
			jsonMsg.setContent(customer);
			
		}		
		response.setContentType("application/json");		
		response.getWriter().write(JSONObject.toJSONString(jsonMsg));
		
	}

	//��¼
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String c_name = request.getParameter("c_name");
		String password = request.getParameter("password");
		String checkcode = request.getParameter("checkcode");
		String remeber = request.getParameter("remeber");
		JsonMSG  jm= new JsonMSG();
		
		//��֤����֤
		if(!checkcode.equalsIgnoreCase((String) request.getSession().getAttribute("checkcode"))) {
			//��֤�����
			jm.setStatus(0);
			jm.setMsg("��֤�����");
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jm));
			//�Ƴ�session�е���֤��
			request.getSession().removeAttribute("checkcode");
			return;
		}
		
		//��¼����
		ICustomerService customerService = new CustomerServiceImpl();
		Customer c = customerService.login(c_name,password);
		
		if(c==null) {
			jm.setStatus(0);
			jm.setMsg("�û������������");
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;			
		}else {
			request.getSession().setAttribute("customer", c);
			jm.setStatus(1);
			jm.setMsg("��¼�ɹ�");
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jm));
		}
		
		
		//��ס�û���
		if(remeber.equals("on")) {			
			//�ѹ�ѡ
			Cookie cookie = new Cookie("remember",c.getC_name());
			//������Ч·��
			cookie.setPath("/p2p_home");
			//������Чʱ��
			cookie.setMaxAge(60*60*24);
			//��д�������
			response.addCookie(cookie);
		}								
	}

	
	
	//ע��
	public void regist(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		
		Customer c = new Customer();
		
		BeanUtils.populate(c, request.getParameterMap());
		
		
		ICustomerService customerService = new CustomerServiceImpl();
		
		JsonMSG jsonMsg = new JsonMSG();
		//�û�������
		Customer c1 = customerService.findByName(c);
		if(c1!=null) {
			jsonMsg.setStatus(0);
			jsonMsg.setMsg("�û����Ѵ���");	
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jsonMsg));
			return;
			
		}
		//�������
		Customer c2 = customerService.findByEmail(c);
		if(c2!=null) {
			jsonMsg.setStatus(0);
			jsonMsg.setMsg("�����Ѵ���");
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jsonMsg));
			return;
		}
		
		//��ע�����û�����
		Customer customer = customerService.regist(c);
		request.getSession().setAttribute("customer", customer);
		jsonMsg.setStatus(1);
		response.setContentType("application/json");
		response.getWriter().write(JSONObject.toJSONString(jsonMsg));
		return;
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doGet(request, response);
	}

}
