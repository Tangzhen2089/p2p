package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.User;
import com.itheima.service.IUserService;
import com.itheima.service.UserServiceImp;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("login".equals(method)) {
			//��¼����
			login(request,response);
		}else if("logout".equals(method)) {
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath());
		}
	}

	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�õ���¼�û���������
		String username = request.getParameter("username");
		String password = request.getParameter("password");
				
		//��֤username��password��Ϊ��
		if(username == null || username.trim().equals("") ||password == null || password.trim().equals("")) {
			request.setAttribute("msg", "�û���������Ϊ��");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}	
		
		//����Service����ȥ���ݿ���֤�û���������
		IUserService userService = new UserServiceImp();		
		User user = userService.login(username,password);
		
		if(user == null) {
			request.setAttribute("msg", "�û������������");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}else {
			request.getSession().setAttribute("user", user);			
			response.sendRedirect(request.getContextPath()+"/views/home.jsp");
			return;
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
