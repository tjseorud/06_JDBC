package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
	public static void main(String[] args) {
		//"최소","최대" 급여 범위를 입력받아
		/*EMPLOYEE 테이블에서 급여를 "최소"이상 "최대" 이하로 받는 사원의 
		 * 사번,이름,부서코드,급여를 
		 * 내림차순으로 출력*/
		Scanner sc =new Scanner(System.in);
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
			
			//벙위 입력 받기
			System.out.println("*** 범위 내 급여 받는 직원 조회 ***");
			System.out.print("최소 값 입력 :");
			int min =sc.nextInt();
			
			System.out.print("최대 값 입력 :");
			int max =sc.nextInt();
			
			StringBuilder sb =new StringBuilder();
			sb.append("SELECT EMP_ID ,EMP_NAME ,DEPT_CODE ,SALARY ");
			sb.append("FROM EMPLOYEE ");
			sb.append("WHERE SALARY BETWEEN ");
			sb.append(min);
			sb.append("AND ");
			sb.append(max);
			sb.append(" ORDER BY SALARY DESC ");
			
			String sql =sb.toString();
			
			stmt =conn.createStatement();
			
			rs =stmt.executeQuery(sql);
			
			System.out.println("\n사번  / 이름  / 부서코드 / 급여");
			while (rs.next()) {
				String eId =rs.getString("EMP_ID");
				String eName =rs.getString("EMP_NAME");
				String dCode =rs.getString("DEPT_CODE");
				int salary =rs.getInt("SALARY");
				
				System.out.println(eId+" / "+eName+" / "+dCode+" / "+salary);
			}
			sc.close();
			
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
