package com.itheima.utils;

import redis.clients.jedis.Jedis;

public class RedisUtils {
	
	private static Jedis jedis;
	static {
		jedis = new Jedis("192.168.0.125");
	}
	
	//��
	public static void set(String key,String value) {
		jedis.set(key,value);
	}
	
	//�����ݣ������ñ���ʱ��
	public static void set(String key,int seconds,String value) {
		jedis.setex(key, seconds, value);
	}
	
	//ȡ
	public static String get(String key) {
		return jedis.get(key);
	}
}
