package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCExample4 {
	public static void main(String[] args) {
		/*아이디, 비밀번호, 새비밀번호를 입력받아
		 * 아이디,비밀번호가 일치하는 회원의 비밀번호 변경*/
		Connection conn =null;
		PreparedStatement pstmt =null;
		
		try {
		//커넥션 생성
			Class.forName("oracle.jdbc.OracleDriver");
			String url ="jdbc:oracle:thin:@192.168.130.17:1521:XE";	//학원 DB 서버 URL
			String userName ="KH14_SDK";	//사용자 계정명
			String password ="KH1234";		//계정 비밀번호		
			conn =DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(false);		//자동 커밋 끄기
			
			String sql ="""
					UPDATE TB_USER 
					SET USER_PW =?
					WHERE USER_ID =? AND USER_PW =?
					""";
			Scanner sc =new Scanner(System.in);
			System.out.print("아이디 입력 :");
			String id =sc.next();
			
			System.out.print("비밀번호 입력 :");
			String pw =sc.next();
			
			System.out.print("새 비밀번호 입력 :");
			String newPw =sc.next();
			
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, newPw);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			
			int result =pstmt.executeUpdate();
			
			if(result >0) {
				System.out.println("비밀번호가 변경되었습니다");
				conn.commit();
			}else {
				System.out.println("아이디 또는 비밀번호가 일치하지 않습니다");
				conn.rollback();
			}
			sc.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
