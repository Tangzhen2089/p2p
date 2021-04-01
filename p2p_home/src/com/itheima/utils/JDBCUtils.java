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
	
	
	//�������
	public static Connection getConnection() throws SQLException {		
		Connection conn = tl.get();
		
		if(conn == null) {
			conn = dataSource.getConnection();
			tl.set(conn);
		}		
		return conn;		
	}
	
	//��������
	public static void startTransaction() throws SQLException {
		
		Connection conn = getConnection();
		
		if(conn != null) {
			conn.setAutoCommit(false);
		}		
	}
	
	//��������
	public static void rollBack() throws SQLException {
		
		Connection conn = getConnection();
		
		if(conn != null) {
			conn.rollback();
		}		
	}
	
	//�ύ����
	public static void commit() throws SQLException {
		
		Connection conn = getConnection();
		
		if(conn != null) {
			conn.commit();
			
		}		
	}
	
	//�ر�����
	public static void close() throws SQLException {
		
		Connection conn = getConnection();
		
		if(conn != null) {
			conn.close();
			tl.remove();//�ر�connectionʱ,����Ҫ�������Ӵӵ�ǰ�߳��Ƴ�
		}		
	}	
	
}
