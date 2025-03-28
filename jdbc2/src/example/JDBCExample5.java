package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {
	public static void main(String[] args) throws ClassNotFoundException {
		/*아이디, 비밀번호, 새비밀번호를 입력받아
		 * 아이디,비밀번호가 일치하는 회원의 비밀번호 변경*/
		String url ="jdbc:oracle:thin:@192.168.130.17:1521:XE";	//학원 DB 서버 URL
		String userName ="KH14_SDK";	//사용자 계정명
		String password ="KH1234";		//계정 비밀번호
		String sql ="""
					UPDATE TB_USER 
					SET USER_PW =?
					WHERE USER_ID =? AND USER_PW =?
					""";
		Class.forName("oracle.jdbc.OracleDriver");
		/*try-with-resources
		 * -try 선언시 () 내에 try 구문에서 사용하고 반환할 자원을 선언하면
		 *  종료시 자동으로 해당 자원을 반환(.close())하는 코드를 수행 -->메모리 누수 방지효과
		 * (조건 :AutoCloseable을 구현한 객체만 자동 반환가능)
		 * -finally를 이용한 객체 자원반환 가능
		 */
		//커넥션 생성
		try(Connection conn =DriverManager.getConnection(url, userName, password);
				PreparedStatement pstmt =conn.prepareStatement(sql)) {
						
			conn.setAutoCommit(false);		//자동 커밋 끄기
			
			Scanner sc =new Scanner(System.in);
			System.out.print("아이디 입력 :");
			String id =sc.next();
			
			System.out.print("비밀번호 입력 :");
			String pw =sc.next();
			
			System.out.print("새 비밀번호 입력 :");
			String newPw =sc.next();
			
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
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
