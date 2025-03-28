package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCExample3 {
	public static void main(String[] args) {
		/*아이디, 비밀번호, 이름을 입력받아
		 * 아이디,비밀번호가 일치하는 사용자의 이름을 수정(UPDATE)
		 */
		Connection conn =null;
		PreparedStatement pstmt =null;
		/*UPDATE는 수정된 행의 개수가 반환 될 예정 -> ResultSet 불필요*/
		try {
			//커넥션 생성
			Class.forName("oracle.jdbc.OracleDriver");
			String url ="jdbc:oracle:thin:@192.168.130.17:1521:XE";	//학원 DB 서버 URL
			String userName ="KH14_SDK";	//사용자 계정명
			String password ="KH1234";		//계정 비밀번호		
			conn =DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(false);		//자동 커밋 끄기
			
			//SQL 작성
			String sql ="""
					UPDATE TB_USER 
					SET USER_NAME =?
					WHERE USER_ID =? AND USER_PW =?
					""";
			
			Scanner sc =new Scanner(System.in);
			System.out.print("아이디 입력 :");
			String id =sc.next();
			
			System.out.print("비밀번호 입력 :");
			String pw =sc.next();
			
			System.out.print("수정할 이름 입력 :");
			String name =sc.next();
			
			//PreparedStatement 객체생성 + 값 세팅
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			
			int result =pstmt.executeUpdate();
			
			//DML 수행시 SQL수행결과에 따른 처리 + 트랜잭션 제어
			if(result >0) {
				System.out.println("수정 성공");
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
		}	finally {
			try {	//jdbc 객체 자원반환
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
