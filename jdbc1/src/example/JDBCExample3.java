package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample3 {
	public static void main(String[] args) {
		/*EMPLOYEE 테이블에서 급여를 300만 이상 500만 이하로 받는 사원의 
		 * 사번,이름,부서코드,급여를 
		 * 내림차순으로 출력*/
		Connection conn =null;
		Statement stmt =null;
		ResultSet rs =null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String type ="jdbc:oracle:thin:@";
			String host ="192.168.130.17";
			String port =":1521";
			String dbName =":XE";
			String userName ="KH14_SDK";
			String password ="KH1234";
			
			conn =DriverManager.getConnection(type +host +port +dbName, userName, password);
			
			StringBuilder sb =new StringBuilder();
			sb.append("SELECT EMP_ID ,EMP_NAME ,DEPT_CODE ,SALARY ");
			sb.append("FROM EMPLOYEE ");
			sb.append("WHERE SALARY BETWEEN 3000000 AND 5000000 ");
			sb.append("ORDER BY SALARY DESC ");
			
			String sql =sb.toString();
			
			stmt =conn.createStatement();
			
			rs =stmt.executeQuery(sql);
			
			while (rs.next()) {
				String eId =rs.getString("EMP_ID");
				String eName =rs.getString("EMP_NAME");
				String dCode =rs.getString("DEPT_CODE");
				int salary =rs.getInt("SALARY");
				
				System.out.println(eId+" / "+eName+" / "+dCode+" / "+salary);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs !=null) rs.close();
				if(stmt !=null) stmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
