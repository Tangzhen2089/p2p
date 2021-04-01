package com.itheima.web.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyHttpServletRequest extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	public MyHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	@Override
	//增强request.getParameter()方法
	public String getParameter(String name) {
		//获得请求方式
		String method = request.getMethod();
		//乱码处理
		if("get".equalsIgnoreCase(method)) {
			String value =super.getParameter(name);
//			try {
//				value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return value;
		}else if("post".equalsIgnoreCase(method)) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return super.getParameter(name);
	}
	

	
	

}
