package com.itheima.utils;

import java.util.Random;

public class RandomChar {

	public static String  getRandomchar(int num) {
		
		String words="abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNOPQRSTUVWXYZ1234567890";
		Random r = new Random();//生成随机数
		
		StringBuilder sb =new StringBuilder();
		for(int i=0;i<num;i++) {
			//生成一个随机字符
			int index = r.nextInt(words.length());
			char c = words.charAt(index);
			sb.append(c);
		}
		return sb.toString();				
	}
	
	//测试
	public static void main(String[] args) {
		System.out.println(getRandomchar(4));
		
	}
}
