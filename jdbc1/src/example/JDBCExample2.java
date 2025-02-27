package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample2 {
	public static void main(String[] args) {
		/*EMPLOYEE 테이블에서 
		 * 모든 사원의 사번,이름,급여를 
		 * 오름차순으로 조회*/
		Connection conn =null;	//DB 연결 정보저장, 연결하는 객체
		Statement stmt =null;	//SQL 수행, 결과 반환받는 객체
		ResultSet rs =null;	//select 수행결과 저장객체
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");	//Oracle JDBC Driver를 메모리에 로드(적재)
			//DB 연결정보
			String type ="jdbc:oracle:thin:@";
			String host ="112.221.156.34";
			String port =":12345";
			String dbName =":XE";
			String userName ="KH14_SDK";
			String password ="KH1234";
			//Connection
			conn =DriverManager.getConnection(type +host +port +dbName, userName, password);
			
			StringBuilder sb =new StringBuilder();
			sb.append("select EMP_ID,EMP_NAME,SALARY ");
			sb.append("FROM EMPLOYEE ");
			sb.append("ORDER BY SALARY ASC ");
			
			String sql =sb.toString();
			
			stmt =conn.createStatement();
			
			rs =stmt.executeQuery(sql);
			
			System.out.println("\n사번  / 이름  / 급여");
			while (rs.next()) {
				//rs.next()	:ResultSet의 Cursor를 다음 행으로 이동 후 행이 있으면 true, 없으면 false
				String empId =rs.getString("EMP_ID");
				String empName =rs.getString("EMP_NAME");
				int salary =rs.getInt("SALARY");
				
				System.out.println(empId +" / "+ empName +" / "+ salary);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt !=null) stmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e) {	
				e.printStackTrace();
			}
		}
	}
}
