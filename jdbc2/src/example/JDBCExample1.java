package example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample1 {
	public static void main(String[] args) {
		/*입력받은 아이디가 포함된 회원의
		 * 사용자 번호, 아이디, 이름, 가입일을
		 * 회원번호 오름차순으로 조회
		 * */			
		/*1. JDBC 객체 참조변수 선언*/
		Connection conn =null;	//DB 연결정보를 가지고 연결하는 객체
		Statement stmt =null;		//SQL 수행, 결과 반환받는 객체
		ResultSet rs =null;			//SELECT 결과를 저장하고 1행씩 접근하는 객체
		
		try {
			/*2. DriverManager 객체를 이용해 Connection 객체생성*/
			Class.forName("oracle.jdbc.OracleDriver");
			//jdbc 드라이버가 어떤 데이터베이스에 연결할지 지정
			String url ="jdbc:oracle:thin:@192.168.130.17:1521:XE";	//학원 DB 서버 URL
			String userName ="KH14_SDK";	//사용자 계정명
			String password ="KH1234";		//계정 비밀번호
			
			conn =DriverManager.getConnection(url, userName, password);
			/*3. SQL 작성*/
			Scanner sc =new Scanner(System.in);
			System.out.print("검색할 아이디 입력 :");
			String input =sc.next();
			
			StringBuilder sb =new StringBuilder();
			sb.append("SELECT USER_NO ,USER_ID ,USER_NAME ,ENROLL_DATE ");
			sb.append("FROM TB_USER ");
			sb.append("WHERE USER_ID LIKE '%"+input+"%' ");
			sb.append("ORDER BY USER_NO ASC ");
			
			stmt =conn.createStatement();
			
			String sql =sb.toString();
			rs= stmt.executeQuery(sql);
			
			/*6. */
			while (rs.next()) {
				int no =rs.getInt("USER_NO");
				String id =rs.getString("USER_ID");
				String name =rs.getString("USER_NAME");
				Date enrollDate =rs.getDate("ENROLL_DATE");	//java.sql.date :DB의 Date타입을 저장하는 클래스
				
//				System.out.println(no +" / "+ id +" / "+ name +" / "+ enrollDate.toString());
				System.out.printf("%d / %s / %s / %s\n",no,id,name,enrollDate.toString());
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
