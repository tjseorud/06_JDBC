package com.kh.mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
	/*JDBC API 사용 중  중복 코드가 너무 많음
	 * 중복된 코드를 메소드로 분리하여 필요할때마다 '재사용'하자
	 */
	
	public static Connection getConnection() {
		final String URL = "jdbc:oracle:thin:@127.0.0.1:12345:XE";
		final String USERNAME = "KH14_SDK";
		final String PASSWORD = "KH1234";		
		Connection conn = null;
		try {		
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeAll(ResultSet rset, Statement stmt, Connection conn) {
		try {
			if(rset != null) rset.close();
		} catch (SQLException e) {
			System.out.println("DB?");
		}
		try {
			if(stmt != null && !(stmt.isClosed())) stmt.close();
		} catch (SQLException e) {
			System.out.println("PreparedStatement?");
		}
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			System.out.println("Connection?");
		}
	}

	public static void close(Statement stmt, Connection conn) {		
		try {
			if(stmt != null && !(stmt.isClosed())) stmt.close();
		} catch (SQLException e) {
			System.out.println("PreparedStatement?");
		}
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			System.out.println("Connection?");
		}
	}
}
