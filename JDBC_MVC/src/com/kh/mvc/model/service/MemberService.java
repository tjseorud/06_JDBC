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
	
	
}
