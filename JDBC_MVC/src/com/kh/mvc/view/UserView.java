package com.kh.mvc.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.kh.mvc.controller.UserController;
import com.kh.mvc.model.dto.UserDTO;

/**MemberView 클래스는 JDBC실습을 위해 생성하였으며,
 * 사용자에게 입력 및 출력을 수행하는 메소드를 제공합니다.
 * 
 * @author :종로 C강의장
 * @version :0.1
 * @date :2025-03-04
 */
public class UserView {
	private Scanner sc = new Scanner(System.in);
	private UserController userController = new UserController();
	
	/**프로그램 시작 시 사용자에게 보여줄 메인화면을 출력해주는 메소드입니다.
	 */
	public void mainMenu() {
		while (true) {
			System.out.println("\n--- USER테이블 관리 프로그램 ---\n");
			System.out.println("1. 회원 전체 조회");
			System.out.println("2. 회원 추가");
			System.out.println("3. 단일 회원 조회(회원번호)");
			System.out.println("4. 회원 아이디 조회");
			System.out.println("5. 비밀번호 수정");
			System.out.println("6. 회원 삭제");
			System.out.println("9. 프로그램 종료");
			System.out.print("이용할 메뉴를 선택해주세요 >");		
			int menuNo = 0;			
			try {
				menuNo = sc.nextInt();
			} catch (InputMismatchException e) {
				e.printStackTrace();
				sc.nextLine();
				continue;
			}			
			sc.nextLine();		
			
			switch (menuNo) {
				case 1: findAll(); 
					break;				
				case 2: insertUser();
					break;
				case 3: findUserNo();
					break;
				case 4: findUserId();
					break;
				case 5: updateUserPw();
					break;
				case 6: deleteUser();
					break;
				case 7: deleteUserAd();
				break;
				case 9: System.out.println("프로그램 종료"); 
					return;
				default: System.out.println("잘못된 메뉴 선택입니다."); 
			}
		}
	}	
	
	/*회원 전체정보를 조회해주는 기능*/
	private void findAll() {
		System.out.println("\n--- 회원 전체 목록 ---");
		
		List<UserDTO> list = userController.findAll();	//DB 갔다오기
		System.out.println("\n조회된 회원의 수는 "+list.size());	
		
		if(!(list.isEmpty())) {		
			System.out.println("== == == == == == == == == == == ==");			
			for(UserDTO userDTO :list) {
				System.out.println(userDTO.getUserName()+"님의 정보");
				System.out.print("아이디 :"+userDTO.getUserId());
				System.out.println("\t가입일 :"+userDTO.getEnrollDate());
			}
			System.out.println("== == == == == == == == == == == ==");
		} else {
			System.out.println("\n회원이 존재하지 않습니다");		
		}
	}
	
	/*TB_USER에 INSERT할 값을 사용자에게 입력받도록 유도하는 화면*/
	private void insertUser() {
		System.out.println("\n--- 회원 추가 ---");
		System.out.print("아이디를 입력하세요 >");
		String userId = sc.nextLine();	//입력받은 id 가지고 DB가서 조회 결과있으면 출력 

		System.out.print("비밀번호를 입력하세요 >");
		String userPw = sc.nextLine();		
		System.out.print("이름을 입력하세요 >");
		String userName = sc.nextLine();	
		int result = userController.insertUser(userId, userPw, userName);		
		
		if(result >0) {
			System.out.println("\n"+userName+"님 가입 성공하셨습니다");
		} else {
			System.out.println("\n가입 실패하셨습니다");
		}
	}
	
	/*회원 번호로 회원 정보를 조회해주는 기능*/
	private void findUserNo() {
		System.out.println("\n--- 단일 회원 조회(회원번호) ---");
		System.out.print("회원 번호를 입력하세요 >");
		int userNo = sc.nextInt();
		
		List<UserDTO> list = userController.findUserNo(userNo);
		
		if(!(list.isEmpty())) {		
			System.out.println("\n== == == == == == == == == == == ==");			
			for(UserDTO userDTO :list) {
				System.out.println("회원 번호 :"+userNo+"으로 조회된 회원 정보");
				System.out.print("아이디 :"+userDTO.getUserId());
				System.out.print("\t이름 :"+userDTO.getUserName());	
				System.out.println("\t가입일 :"+userDTO.getEnrollDate());
			}
			System.out.println("== == == == == == == == == == == ==");
		} else {
			System.out.println("\n회원이 존재하지 않습니다");		
		}
		
	}
	
	/*회원 아이디를 조회해주는 기능*/
	private void findUserId() {
		System.out.println("\n--- 회원 아이디 조회 ---");
		System.out.print("아이디를 입력하세요 >");
		String userId = sc.nextLine();
		
		List<UserDTO> list = userController.findUserId(userId);
		
		if(!(list.isEmpty())) {
			System.out.println("\n== == == == == == == == == == == ==");			
			for(UserDTO userDTO :list) {
				System.out.println("회원 아이디 :"+userId+"으로 조회된 회원 정보");
				System.out.print("이름 :"+userDTO.getUserName());	
				System.out.println("\t가입일 :"+userDTO.getEnrollDate());
			}
			System.out.println("== == == == == == == == == == == ==");
		} else {
			System.out.println("\n회원이 존재하지 않습니다");		
		}
	}
	
	/*회원 비밀번호 변경을 해주는 기능*/
	private void updateUserPw() {
		System.out.println("\n--- 회원 비밀번호 변경 ---");
		System.out.print("아이디를 입력하세요 >");
		String userId = sc.nextLine();
		System.out.print("비밀번호를 입력하세요 >");
		String userPw = sc.nextLine();		
		System.out.print("새 비밀번호을 입력하세요 >");
		String userNewPw = sc.nextLine();	
		int result = userController.updateUserPw(userId, userPw, userNewPw);	
		
		if(result >0) {
			System.out.println("\n비밀번호가 변경되었습니다");
		} else {
			System.out.println("\n아이디 또는 비밀번호가 일치하지 않습니다");
		}	
	}

	/*회원 삭제해주는 기능*/
	private void deleteUser() {
		System.out.println("\n--- 회원 삭제 ---");				
		System.out.print("아이디를 입력하세요 >");
		String userId = sc.nextLine();	
		System.out.print("비밀번호를 입력하세요 >");
		String userPw = sc.nextLine();				
		int result = userController.deleteUser(userId, userPw);						
		
		if(result >0) {
			System.out.println("\n회원이 삭제 되었습니다");
		} else {
			System.out.println("\n아이디 또는 비밀번호가 일치하지 않습니다");
		}
	}
	
	/*관리자가 회원 삭제해주는 기능*/
	private void deleteUserAd() {
		while(true) {
			System.out.println("\n--- 회원 삭제 ---");			
			System.out.println("관리자 모드");
			System.out.print("삭제할 회원번호를 입력하세요 >");
			int userNo = sc.nextInt();
			sc.nextLine();
			System.out.print("삭제할 아이디를 입력하세요 >");
			String userId = sc.nextLine();
			int result = userController.deleteUserAd(userNo, userId);					
			
			if(result >0) {
				System.out.println("\n회원이 삭제 되었습니다");
			} else {
				System.out.println("\n아이디 또는 비밀번호가 일치하지 않습니다");
			}
		}
	}
	
}
