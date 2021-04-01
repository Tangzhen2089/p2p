package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.IProductService;
import com.itheima.service.ProductServiceImp;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getParameter("method");
		if (method.equals("findAll")) {
			try {
				findAll(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (method.equals("addProduct")) {
			try {
				addProduct(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (method.equals("findById")) {
			try {
				findById(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (method.equals("updateProduct")) {
			try {
				updateProduct(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (method.equals("deleteProduct")) {
			try {
				deleteProduct(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (method.equals("findAllProduct")) {
			try {
				findAllProduct(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 跨域查询所有产品
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void findAllProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String callback = request.getParameter("callback");

		IProductService productService = new ProductServiceImp();
		List<Product> list = productService.findAllProduct();

		String json = JSONObject.toJSONString(list);
		response.getWriter().write(callback + "(" + json + ")");
	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int id = Integer.parseInt(request.getParameter("id"));

		IProductService productService = new ProductServiceImp();
		productService.deleteProduct(id);

	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		String proNum = request.getParameter("proNum");
		String proName = request.getParameter("proName");
		int proLimit = Integer.parseInt(request.getParameter("proLimit"));
		double annualized = Double.parseDouble(request.getParameter("annualized"));

		IProductService productService = new ProductServiceImp();
		productService.updateProduct(id, proNum, proName, proLimit, annualized);

	}

	private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));

		IProductService productService = new ProductServiceImp();
		Product product = productService.findById(id);

		// 判断是否是跨域请求
		String callback = request.getParameter("callback");
		if (callback == null || "".equals(callback)) {
			String json = JSONObject.toJSONString(product);
			response.getWriter().write(json);
		} else {
			String json = JSONObject.toJSONString(product);
			response.getWriter().write(callback + "(" + json + ")");
		}
	}

	private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String proNum = request.getParameter("proNum");
		String proName = request.getParameter("proName");
		int proLimit = Integer.parseInt(request.getParameter("proLimit"));
		double annualized = Double.parseDouble(request.getParameter("annualized"));

		if (proNum.trim().equals("") || proName.trim().equals("")) {

		}

		IProductService productService = new ProductServiceImp();
		productService.addProduct(proNum, proName, proLimit, annualized);
	}

	// 查询所有产品
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");

		IProductService productService = new ProductServiceImp();

		PageBean pb = productService.findAll(pageNo, pageSize);

//		response.setCharacterEncoding("utf-8");
//		response.setContentType("application/json");

		String json = JSONObject.toJSONString(pb);
		response.getWriter().write(json);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
