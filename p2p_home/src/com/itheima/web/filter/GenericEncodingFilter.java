package com.itheima.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * ͨ�õ��ַ������������
 */
public class GenericEncodingFilter implements Filter {

  
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//������������
		HttpServletRequest req = (HttpServletRequest)request;
		MyHttpServletRequest myReq = new MyHttpServletRequest(req);
		
		//������Ӧ����
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		chain.doFilter(myReq, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
