package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample5 {
	public static void main(String[] args) {
		/*부서명을 입력받아 해당 부서의 근무하는 사원의
		 * 사번,이름,부서명,직급명을
		 * 직급코드 오름차순으로 조회*/
		Scanner sc =new Scanner(System.in);
		Connection conn =null;
		Statement stmt =null;
		ResultSet rs =null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String type ="jdbc:oracle:thin:@";
			String host ="112.221.156.34";
			String port =":12345";
			String dbName =":XE";
			String userName ="KH14_SDK";
			String password ="KH1234";
			
			conn =DriverManager.getConnection(type+host+port+dbName, userName, password);
			
			System.out.println("*** 해당 부서의 근무하는 사원조회 ***");
			System.out.print("검색할 부서명 :");
			String input =sc.nextLine();
			
			StringBuilder sb =new StringBuilder();
			sb.append("SELECT e.EMP_ID ,e.EMP_NAME ,d.DEPT_TITLE ,j.JOB_NAME ");
			sb.append("FROM EMPLOYEE e ");
			sb.append(" LEFT JOIN DEPARTMENT d ON (e.DEPT_CODE=d.DEPT_ID) ");
			sb.append(" JOIN JOB j ON (e.JOB_CODE=j.JOB_CODE) ");
			sb.append("WHERE d.DEPT_TITLE IN ('"+input+"') ");
//			sb.append("WHERE d.DEPT_TITLE ='"+input+"' ");
			sb.append("ORDER BY e.JOB_CODE ASC ");
			
//			//Oracle 형식
//			sb.append("SELECT e.EMP_ID ,e.EMP_NAME ,d.DEPT_TITLE ,j.JOB_NAME ");
//			sb.append("FROM EMPLOYEE e ,DEPARTMENT d,JOB j ");
//			sb.append("WHERE e.DEPT_CODE=d.DEPT_ID and e.JOB_CODE=j.JOB_CODE and d.DEPT_TITLE IN ('"+input+"') ");
//			sb.append("ORDER BY e.JOB_CODE ASC ");
			
			String sql =sb.toString();
			
			stmt =conn.createStatement();
			
			rs =stmt.executeQuery(sql);
			
			System.out.println("\n사번  / 이름  / 부서명 / 직급명");
			while (rs.next()) {
				String eId =rs.getString("EMP_ID");
				String eName =rs.getString("EMP_NAME");
				String dTitle =rs.getString("DEPT_TITLE");
				String jCode =rs.getString("JOB_NAME");
	
				System.out.println(eId+" / "+eName+" / "+dTitle+" / "+jCode);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
