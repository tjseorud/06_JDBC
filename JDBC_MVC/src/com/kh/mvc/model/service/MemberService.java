package com.kh.mvc.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.mvc.model.dao.UserDAO;
import com.kh.mvc.model.dto.UserDTO;
import com.kh.mvc.util.JdbcUtil;

/**
 * Service :의사결정코드를 작성하는 부분 / 비즈니스 로직 
 * 	Controller에서는 Service단의 메소드를 호출
 * 	Service에서 실질적인 동작을 시켜야하는 코드를 작성
 * 	=> Service단을 추가함으로 DAO는 순수하게 SQL문을 처리하는 부분만 남겨놓을 것
 */
public class MemberService {
	private UserDAO userDAO = new UserDAO();
	
	public List<UserDTO> findAll() {
		Connection conn = JdbcUtil.getConnection();	
		List<UserDTO> list = userDAO.findAll(conn);
		return list;
	}

	public int insertUser(UserDTO user) {
		Connection conn = JdbcUtil.getConnection();			
		return userDAO.insertUser(user, conn);
	}

	public List<UserDTO> findUserNo(int userNo) {
		Connection conn = JdbcUtil.getConnection();	
		List<UserDTO> list = userDAO.findUserNo(userNo, conn);
		return list;
	}

	public List<UserDTO> findUserId(String userId) {
		Connection conn = JdbcUtil.getConnection();
		List<UserDTO> list = userDAO.findUserId(userId, conn);
		return list;
	}

	public int updateUserPw(String userId, String userPw, String userNewPw) {
		Connection conn = JdbcUtil.getConnection();
		return userDAO.updateUserPw(userId, userPw, userNewPw, conn);
	}

	public int deleteUser(String userId, String userPw) {
		Connection conn = JdbcUtil.getConnection();
		return userDAO.deleteUser(userId, userPw, conn);
	}

	public int deleteUserAd(int userNo, String userId) {
		Connection conn = JdbcUtil.getConnection();
		return userDAO.deleteUserAd(userNo, userId, conn);
	}
}
