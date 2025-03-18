package com.kh.mvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.mvc.model.dto.UserDTO;
import com.kh.mvc.util.JdbcUtil;

/**DAO(Date Access Object)
 * 데어터베이스 관련된 작업(CRUD)을 전문적으로 담당하는 객체
 * DAO안에 모든 메소드들은 데이터베이스와 관련된 기능으로 만들것
 * 
 * Controller를 통해 호출된 기능을 수행
 * DB에 직접 접근한 후 SQL문을 수행하고 결과 반환받기(JDBC)
 */
public class UserDAO {
	/*JDBC 용 객체
	 * -Connection :DB와의 연결정보를 담고있는 객체(IP주소,Port,사용자명,비번)
	 * -Statement  :Connection이 가지고 있는 연결정보 DB에 SQL문을 전달하고 실행하고 결과도 받아오는 객체
	 * -ResultSet	 :실행한 SQL문이 SELECT문일 경우 조회된 결과가 처음 담기는 객체
	 * 
	 * -PreparedStatement	:SQL문을 미리 준비하는 개념 '?'(위치홀더)로 확보해놓은 공간을
	 * 										 사용자가 입력한 값들과 바인딩해서 SQL문을 수행
	 * 
	 * **처리절차
	 * 1)JDBC Driver등록 :해당 DBMS에서 제공하는 클래스를 동적으로 등록
	 * 2)Connection 객체 생성 :접속하고자하는 DB의 정보를 입력해서 생성(url,userName,password)
	 * 3_1)PreparedStatement 객체 생성 :Connection 객체를 이용해서 생성(미완성된 SQL문을 미리 전달)
	 * 3_2)미완성된 SQL문을 완성형태로 만들어줘야함 => 미완성된 경우에만 해당 / 완성된 경우에는 생략
	 * 4)SQL문을 실행 :execute???() => SQL을 인자로 전달하지 않음!
	 * 								>SELECT(DQL) :executeQuery()
	 * 								>DML				 :executeUpdate()
	 * 5)결과받기	:
	 * 			>SELECT :ReaultSet타입 객체(조회데이터담김)
	 * 			>DML		:int(처리된 행의 개수)
	 * 6_1)ReaultSet에 담겨있는 데이터들을 하나하나씩 뽑아서 DTO객체 필드에 옮겨담은 후 
	 * 			조회결과가 여러행일 경우 List로 관리
	 * 6_2)트랜잭션 처리
	 * 7(생략될수 있음))자원반납(Close) => 생성의 역순으로
	 * 8)결과반환 -> Controller
	 * 							SELECT >6_1에서 만든거
	 * 							DML		 >처리된 행의 개수
	 */
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");		
		} catch (ClassNotFoundException e) {
			System.out.println("OracleDriver가 없을수도?");
		}	
	}	
//	private final String URL = "jdbc:oracle:thin:@127.0.0.1:12345:XE";
//	private final String USERNAME = "KH14_SDK";
//	private final String PASSWORD = "KH1234";
	
	public List<UserDTO> findAll(Connection conn) {
		/*VO / DTO / Entity
		 * 1명의 회원의 정보는 1개의 UserDTO객체의 필드에 값을 담아야겠다.
		 * 문제점 :UserDTO가 몇개가 나올지 알수없음
		 */
		List<UserDTO> list = new ArrayList<UserDTO>();
		String sql ="""
				SELECT USER_NO, USER_ID, USER_PW, USER_NAME, ENROLL_DATE 
				FROM TB_USER 
				ORDER BY ENROLL_DATE DESC 
				""";	
//		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;	
		try {			
//			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();			
			
			while(rset.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserNo(rset.getInt("USER_NO"));
				userDTO.setUserId(rset.getString("USER_ID"));
				userDTO.setUserPw(rset.getString("USER_PW"));
				userDTO.setUserName(rset.getString("USER_NAME"));
				userDTO.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				list.add(userDTO);
			}		
		} catch (SQLException e) {
			System.out.println("오타가 있을수도?");
		} finally {
			JdbcUtil.closeAll(rset, pstmt, conn);
		}		
		return list;
	}
	
	/**
	 * @param user 사용자가 입력한 ID / PW / NAME이 각각 필드에 대입되어있음
	 * @return
	 */
	public int insertUser(UserDTO user, Connection conn) {
		PreparedStatement pstmt = null;
		String sql ="""
				INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, SYSDATE)
				""";
		int result = 0;			
		try {
//			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//conn.setAutoCommit(false);	//자동커밋끔
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			result = pstmt.executeUpdate();			 		
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			JdbcUtil.close(pstmt, conn);
		}	
		return result;
	}

	/**
	 * @param userNo 사용자가 입력한 NO가 필드에 대입되어있음
	 * @return
	 */
	public List<UserDTO> findUserNo(int userNo, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;	
		String sql ="""
				SELECT USER_ID, USER_NAME, ENROLL_DATE
				FROM TB_USER
				WHERE USER_NO = ?
				""";		
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);	
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(rset.getString("USER_ID"));
				userDTO.setUserName(rset.getString("USER_NAME"));
				userDTO.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				list.add(userDTO);
			}			
		} catch (SQLException e) {
			e.printStackTrace();			
		} finally {
			JdbcUtil.closeAll(rset, pstmt, conn);
		}	
		return list;
	}

	/**
	 * @param userId 사용자가 입력한 ID가 필드에 대입되어있음
	 * @return
	 */
	public List<UserDTO> findUserId(String userId, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql ="""
				SELECT USER_NAME, ENROLL_DATE
				FROM TB_USER
				WHERE USER_ID = ?
				""";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset =pstmt.executeQuery();
			
			while (rset.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserName(rset.getString("USER_NAME"));
				userDTO.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				list.add(userDTO);
			}
		} catch (SQLException e) {		
			System.out.println("이건가?");
		}	finally {
			JdbcUtil.closeAll(rset, pstmt, conn);
		}		
		return list;
	}

	/**
	 * @param userId 	사용자 아이디
	 * @param userPw	사용자 현재 비밀번호
	 * @param userNewPw	새 비밀번호
	 * @return
	 */
	public int updateUserPw(String userId, String userPw, String userNewPw, Connection conn) {
		PreparedStatement pstmt = null;
		String sql = """
				UPDATE TB_USER SET USER_PW = ?
				WHERE USER_ID = ? AND USER_PW = ?
				""";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userNewPw);
			pstmt.setString(2, userId);
			pstmt.setString(3, userPw);
			result = pstmt.executeUpdate();					
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			JdbcUtil.close(pstmt, conn);
		}	
		return result;
	}

	/**
	 * 사용자 본인일 경우
	 * @param userId	사용자 아이디
	 * @param userPw	사용자 비밀번호
	 * @return
	 */
	public int deleteUser(String userId, String userPw, Connection conn) {
		PreparedStatement pstmt = null;
		String sql ="""
				DELETE FROM TB_USER
				WHERE USER_ID = ? AND USER_PW = ?
				""";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}	
		return result;
	}

	/**
	 * 관리자일 경우
	 * @param userNo 사용자 번호
	 * @param userId 사용자 아이디
	 * @return
	 */
	public int deleteUserAd(int userNo, String userId, Connection conn) {
		PreparedStatement pstmt = null;
		String sql ="""
				DELETE FROM TB_USER
				WHERE USER_NO = ? AND USER_ID = ?
				""";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, conn);
		}	
		return result;
	}
	
}
