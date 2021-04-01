package com.itheima.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
  
    public LoginFilter() {
        
    }

	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;		
		HttpServletResponse res = (HttpServletResponse) response;
		
		User user = (User) req.getSession().getAttribute("user");
		
		if(user!=null) {
			chain.doFilter(request, response);
		}else {
			res.sendRedirect(req.getContextPath()+"/login.jsp");
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
