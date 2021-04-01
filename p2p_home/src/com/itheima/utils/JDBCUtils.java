package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static final ComboPooledDataSource dataSource= new ComboPooledDataSource();
	private static final ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	public static DataSource getDataSource() {
		return dataSource;				
	}
	
	
	//获得连接
	public static Connection getConnection() throws SQLException {		
		Connection conn = tl.get();
		
		if(conn == null) {
			conn = dataSource.getConnection();
			tl.set(conn);
		}		
		return conn;		
	}
	
	//开启事务
	public static void startTransaction() throws SQLException {
		
		Connection conn = getConnection();
		
		if(conn != null) {
			conn.setAutoCommit(false);
		}		
	}
	
	//开启事务
	public static void rollBack() throws SQLException {
		
		Connection conn = getConnection();
		
		if(conn != null) {
			conn.rollback();
		}		
	}
	
	//提交事务
	public static void commit() throws SQLException {
		
		Connection conn = getConnection();
		
		if(conn != null) {
			conn.commit();
			
		}		
	}
	
	//关闭连接
	public static void close() throws SQLException {
		
		Connection conn = getConnection();
		
		if(conn != null) {
			conn.close();
			tl.remove();//关闭connection时,还需要将该连接从当前线程移除
		}		
	}	
	
}
