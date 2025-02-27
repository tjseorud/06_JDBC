package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {
	public static void main(String[] args) {
		/*JDBC(Java DataBase Connectivity)
		 * -Java에서 DB에 연결할 수 있게 
		 *  Java 제공 API ->Java에서 주는 코드 쓰면 DB에 연결할수 있어
		 *  
		 * -java.sql
		 * */
		/*JDBC가 제공하는 인터페이스
		 * 1)Connection
		 * 	-특정 DB와 연결하기 위한 정보를 저장하는 객체
		 * 	-DB서버주소, 포트, DB이름, 사용자명, 비밀번호
		 * 	-Connection을 구현한 객체를 통해서 SQL을 전달하고 결과를 반환 받을수 있다
		 * 
		 * 2)Statement
		 *  -SQL을 DB에 전달
		 *  -DB의 SQL 수행결과를 반환 받는 역활
		 *  
		 * 3)ResultSet
		 *  -Select 조회 결과를 저장하고 다루는 객체
		 *  -다룰 때 CURSOR를 이용해 1행씩 접근
		 *  
		 * 4)DriverManager 클래스
		 *  -DB 연결정보와 JDBC 드라이버를 이용해서 원하는 DB와 연결할수 있는 
		 *   Connection객체를 생성하는
		 * */
		/*1. JDBC 객체 참조변수 선언*/
		Connection conn =null;
		Statement stmt =null;
		ResultSet rs =null;
		
		try {
			/*2. DriverManager 객체를 이용해 Connection 객체생성하기*/			
			/*2-1. Oracle JDBC Driver를 메모리에 Load하기
			 * */
			Class.forName("oracle.jdbc.OracleDriver");
			//Class.forName("클래스명");
			//-해당 클래스를 이용해서 객체생성
			//-->객체생성 ==메모리에 할당(적재==Load)
			//
			
			/*2-2. DB 연결 정보 작성*/
			String type ="jdbc:oracle:thin:@";
			String host ="112.221.156.34";
			String port =":12345";
			String dbName =":XE";
			String userName ="KH14_SDK";
			String password ="KH1234";			
			
			/*2-3. DriverManager를 이용해서 Connection 객체생성*/
			conn =DriverManager.getConnection(
					type +host +port +dbName,	//DB URL
					userName,password);		//사용자 계정명, 비밀번호
			//->2-1에서 로드된 JDBC드라이버 + 연결정보를 이용해 DB와 연결된 Connection		
			//System.out.println(conn);	//conn 객체 생성 확인
			
			/*3. SQL 작성
			 ***주의사항**
			 * -JDBC 코드에서 SQL 작성시 마지막에 세미콜론(;) 작성 X
			 * 	->작성시 :"SQL 명렬어가 올바르게 종료되지 않았습니다" 예외 발생
			 * 	(왜? JDBC는 한번에 SQL을 1개씩만 실행 => DB에 SQL전달시 자동으로 끝에 ; 붙여줌)
			 * */
			String sql ="SELECT * FROM DEPARTMENT";
			
			/*4. SQL을 전달하고 결과를 받아올 Statement 객체생성*/
			stmt =conn.createStatement();
			
			/*5. Statement 객체를 이용해서 SQL을 DB로 전달 후 수행*/
			rs =stmt.executeQuery(sql);
			//stmt.executeQuery(sql) 
			// -SELECT문이 작성된 sql을 DB로 전달해 수행 후 DB
			
			/*6. 조회결과가 저장된 ResultSet을 1행씩 접근하여 각행에 기록된 컬럼값을 얻어오기
			 * 	  -1행씩 접근할때 자동으로 Cursor를 이용함
			 * */
			/*rs.next()	:Cursor를 다음 행으로 이동 후 행이 있으면 true, 없으면 false 반환
			 * 단, 첫 호출시에는 1행으로 이동
			 * */
			while(rs.next()) {
				/*rs.get자료형(컬럼명||순서)
				 * -현재 Corsor가 가리키는 행의 컬럼 값을 엍어옴
				 * -자료형 자리에는 DB 값을 읽어 왔을때 Java에서 저장하는 적절한 자료형을 작성
				 * */
				 String deptId =rs.getString("DEPT_ID");
				 String deptTitle =rs.getString("DEPT_TITLE");
				 String locationId =rs.getString("LOCATION_ID");
				 
				 System.out.printf("부서코드 :%S / 부서명 :%S / 지역코드 :%S \n",deptId,deptTitle,locationId);
				
			}
			
		} catch (SQLException e) {
			//SQLException :DB 연결과 관련된 예외중 최상위 예외
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// ojdbc10.jar에서 제공하는
			// OracleDriver
			e.printStackTrace();
		} finally {
			try {
				/*7. 사용완료된 JDBC객체 자원 반환*/
				/*JDBC 객체는 외부자원(DB)와 연결된 상태라서
				 * Java 프로그램 종료 후에도 연결이 유지되고 있다 ->마지막에 꼭 반환*/
				if(rs !=null) rs.close(); 
				if(stmt !=null) stmt.close(); 
				if(conn !=null) conn.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
