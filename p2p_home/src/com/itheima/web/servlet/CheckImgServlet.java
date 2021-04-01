package com.itheima.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一次性验证码生成
 */
public class CheckImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
         	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int w = 120;
		int h = 30;
		
		//绘制一张内存中的图片
		BufferedImage bufimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		//绘制图片背景颜色 --通过绘图对象
		Graphics2D grap = (Graphics2D) bufimage.getGraphics();
		grap.setColor(getRandColor(200,50));
		grap.fillRect(0, 0, w, h);
		
		//绘制边框
		grap.setColor(Color.WHITE);
		grap.drawRect(0, 0, w-1, h-1);
		
		//设置输出字体
		grap.setFont(new Font("宋体",Font.BOLD,18));
		
		String words="abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNOPQRSTUVWXYZ1234567890";
		Random r = new Random();//生成随机数
		
		//定义坐标
		int x= 10;
		StringBuilder sb =new StringBuilder();
		for(int i=0;i<4;i++) {
			//随机颜色
			grap.setColor(new Color(20+r.nextInt(110),20+r.nextInt(110),20+r.nextInt(110)));
			//旋转-30--30度
			int jiaodu = r.nextInt(60)-30;
			//换算弧度
			double theta=jiaodu*Math.PI/180;
			
			//生成一个随机字符
			int index = r.nextInt(words.length());
			char c = words.charAt(index);
			sb.append(c);
			//将字符输出到图片
			grap.rotate(theta,x,20);
			grap.drawString(String.valueOf(c), x, 20);
			grap.rotate(-theta, x, 20);
			x += 30;			
		}
		request.getSession().setAttribute("checkcode", sb.toString());;		
		//绘制干扰线
		grap.setColor(getRandColor(150,0)); 
		int x1;
		int x2;
		int y1;
		int y2;
		for(int i = 0; i<10;i++) {
			x1=r.nextInt(w);
			x2=r.nextInt(12);
			y1=r.nextInt(h);
			y2=r.nextInt(12);
			grap.drawLine(x1, y1, x1+x2, x2+y2);			
		}
		
		//将上面图片输出到浏览器ImageIO
		grap.dispose();//释放资源
		ImageIO.write(bufimage, "jpg", response.getOutputStream());
		
	}

	/**
	 * 生成随机颜色
	 * @return
	 */
	private Color getRandColor(int r,int a) {
		int red = new Random().nextInt(r)+a;
		int green = new Random().nextInt(r)+a;
		int blue = new Random().nextInt(r)+a;
		return new Color(red,green,blue);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}
