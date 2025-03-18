package mvc.emp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
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
}
