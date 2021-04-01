package com.itheima.utils;

import redis.clients.jedis.Jedis;

public class RedisUtils {
	
	private static Jedis jedis;
	static {
		jedis = new Jedis("192.168.0.125");
	}
	
	//存
	public static void set(String key,String value) {
		jedis.set(key,value);
	}
	
	//存数据，并设置保存时间
	public static void set(String key,int seconds,String value) {
		jedis.setex(key, seconds, value);
	}
	
	//取
	public static String get(String key) {
		return jedis.get(key);
	}
}
