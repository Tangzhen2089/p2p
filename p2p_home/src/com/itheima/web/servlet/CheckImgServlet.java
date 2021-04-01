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
 * һ������֤������
 */
public class CheckImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
         	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int w = 120;
		int h = 30;
		
		//����һ���ڴ��е�ͼƬ
		BufferedImage bufimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		//����ͼƬ������ɫ --ͨ����ͼ����
		Graphics2D grap = (Graphics2D) bufimage.getGraphics();
		grap.setColor(getRandColor(200,50));
		grap.fillRect(0, 0, w, h);
		
		//���Ʊ߿�
		grap.setColor(Color.WHITE);
		grap.drawRect(0, 0, w-1, h-1);
		
		//�����������
		grap.setFont(new Font("����",Font.BOLD,18));
		
		String words="abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNOPQRSTUVWXYZ1234567890";
		Random r = new Random();//���������
		
		//��������
		int x= 10;
		StringBuilder sb =new StringBuilder();
		for(int i=0;i<4;i++) {
			//�����ɫ
			grap.setColor(new Color(20+r.nextInt(110),20+r.nextInt(110),20+r.nextInt(110)));
			//��ת-30--30��
			int jiaodu = r.nextInt(60)-30;
			//���㻡��
			double theta=jiaodu*Math.PI/180;
			
			//����һ������ַ�
			int index = r.nextInt(words.length());
			char c = words.charAt(index);
			sb.append(c);
			//���ַ������ͼƬ
			grap.rotate(theta,x,20);
			grap.drawString(String.valueOf(c), x, 20);
			grap.rotate(-theta, x, 20);
			x += 30;			
		}
		request.getSession().setAttribute("checkcode", sb.toString());;		
		//���Ƹ�����
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
		
		//������ͼƬ����������ImageIO
		grap.dispose();//�ͷ���Դ
		ImageIO.write(bufimage, "jpg", response.getOutputStream());
		
	}

	/**
	 * ���������ɫ
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
