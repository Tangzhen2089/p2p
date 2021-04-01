package com.itheima.utils;

import redis.clients.jedis.Jedis;

public class RedisUtils {
	
	private static Jedis jedis;
	static {
		jedis = new Jedis("192.168.0.125");
	}
	
	//´æ
	public static void set(String key,String value) {
		jedis.set(key,value);
	}
	//È¡
	public static String get(String key) {
		return jedis.get(key);
	}
}
