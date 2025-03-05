package com.kh.mvc.controller;

import java.util.List;

import com.kh.mvc.model.dao.UserDAO;
import com.kh.mvc.model.dto.UserDTO;
import com.kh.mvc.model.service.MemberService;

/**VIEW에서 온 요청을 처리해주는 클래스입니다.
 * 메소드로 전달된 데이터값을 가공한후 DAO로 전달합니다.
 * DAO로부터 반환받은 값을 View(응답화면)에 반환합니다.
 */
public class UserController {
	private UserDAO userDAO = new UserDAO();
	private MemberService service = new MemberService();
	
	public List<UserDTO> findAll() {
		return service.findAll();	
	}
	
	public int insertUser(String userId, String userPw, String userName) {		
		UserDTO user = new UserDTO(); 
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);
		
		return userDAO.insertUser(user);
	}
	
}
