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
		
		//注册操作
		if(method.equals("registe")) {
			try {
				regist(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//登录操作
		if(method.equals("login")) {
			try {
				login(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//查找用户
		if(method.equals("findCustomer")) {
			try {
				findCustomer(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		//发送邮件
		if(method.equals("sendEmail")) {
			
				sendEmail(request,response);
					
		}
		
		//发送邮件
		if(method.equals("checkEmail")) {
			
			checkEmail(request,response);
					
		}
		
	}
	
	//邮箱验证码校验
	public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("emailAddr");
		String emailCode = request.getParameter("emailCheckCode");
		
		String emailCheckCode = (String) request.getSession().getAttribute("emailCheckCode");
		
		JsonMSG jm = new JsonMSG();
		if(!emailCheckCode.equalsIgnoreCase(emailCode)) {
			jm.setStatus(0);
			jm.setMsg("邮箱验证码不正确");		
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
			jm.setMsg("邮箱认证成功");	
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jm.setStatus(0);
			jm.setMsg("邮箱认证失败");	
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
			MailUtils.sendMail(email, "邮箱认证码为:"+emailCheckCode, "验证信息");
			jm.setStatus(1);
			
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;
		} catch (Exception e) {			
			e.printStackTrace();
			jm.setStatus(0);
			jm.setMsg("邮件发送失败");
			
			try {
				response.getWriter().write(JSONObject.toJSONString(jm));
				return;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
	}
	//查找用户,用于邮箱认证
	private void findCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer c = (Customer) request.getSession().getAttribute("customer");
		
		ICustomerService customerService = new CustomerServiceImpl();
		Customer customer = customerService.findByEmail(c);
		JsonMSG jsonMsg = new JsonMSG();
		
		if(customer.getEmail_status()!=0) {
			
			jsonMsg.setStatus(0);
			jsonMsg.setMsg("邮箱已认证");			
		}else {
			
			jsonMsg.setStatus(1);
			jsonMsg.setContent(customer);
			
		}		
		response.setContentType("application/json");		
		response.getWriter().write(JSONObject.toJSONString(jsonMsg));
		
	}

	//登录
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String c_name = request.getParameter("c_name");
		String password = request.getParameter("password");
		String checkcode = request.getParameter("checkcode");
		String remeber = request.getParameter("remeber");
		JsonMSG  jm= new JsonMSG();
		
		//验证码验证
		if(!checkcode.equalsIgnoreCase((String) request.getSession().getAttribute("checkcode"))) {
			//验证码错误
			jm.setStatus(0);
			jm.setMsg("验证码错误");
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jm));
			//移除session中的验证码
			request.getSession().removeAttribute("checkcode");
			return;
		}
		
		//登录操作
		ICustomerService customerService = new CustomerServiceImpl();
		Customer c = customerService.login(c_name,password);
		
		if(c==null) {
			jm.setStatus(0);
			jm.setMsg("用户名或密码错误");
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jm));
			return;			
		}else {
			request.getSession().setAttribute("customer", c);
			jm.setStatus(1);
			jm.setMsg("登录成功");
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jm));
		}
		
		
		//记住用户名
		if(remeber.equals("on")) {			
			//已勾选
			Cookie cookie = new Cookie("remember",c.getC_name());
			//设置有效路径
			cookie.setPath("/p2p_home");
			//设置有效时长
			cookie.setMaxAge(60*60*24);
			//回写到浏览器
			response.addCookie(cookie);
		}								
	}

	
	
	//注册
	public void regist(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		
		Customer c = new Customer();
		
		BeanUtils.populate(c, request.getParameterMap());
		
		
		ICustomerService customerService = new CustomerServiceImpl();
		
		JsonMSG jsonMsg = new JsonMSG();
		//用户名存在
		Customer c1 = customerService.findByName(c);
		if(c1!=null) {
			jsonMsg.setStatus(0);
			jsonMsg.setMsg("用户名已存在");	
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jsonMsg));
			return;
			
		}
		//邮箱存在
		Customer c2 = customerService.findByEmail(c);
		if(c2!=null) {
			jsonMsg.setStatus(0);
			jsonMsg.setMsg("邮箱已存在");
			response.setContentType("application/json");
			response.getWriter().write(JSONObject.toJSONString(jsonMsg));
			return;
		}
		
		//将注册后的用户返回
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
