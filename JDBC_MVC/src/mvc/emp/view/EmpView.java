package mvc.emp.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import mvc.emp.controller.EmpController;
import mvc.emp.model.dto.EmpDTO;


public class EmpView {
	private Scanner sc = new Scanner(System.in);
	private EmpController controller = new EmpController();
	
	public void mainMenu() {
		while(true) {
			System.out.println("\n=== EMPLOYEE 테이블 관리 프로그램 ===");
			System.out.println("1. 조회하기");
			System.out.println("2. 추가하기");
			System.out.println("3. 수정하기");
			System.out.println("4. 삭제하기");
			System.out.println("0. 종료하기");
			System.out.print("이용하려는 메뉴를 선택해주세요 >>");
			int menuNo = -1;
			try {
				menuNo = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(">숫자만 입력해주세요");
				sc.nextLine();
				continue;
			}
			sc.nextLine();
			
			switch (menuNo) {
			case 1:	selectEmp();
				break;
			case 2: insertEmp();
				break;
			case 3: updateEmp();
				break;
			case 4: deleteEmp();
				break;
			case 0: System.out.println(">프로그램을 종료합니다.");
				return;
			default: System.out.println(">다른 메뉴를 선택해주세요");
			}
		}
	}

	private void selectEmp() {
		System.out.println("\n== 사원번호로 조회하기 ==");
		System.out.print("사원번호를 입력하세요 >>");
		String empId = sc.nextLine();
		
		List<EmpDTO> list = controller.selectEmp(empId);
		
		if(!(list.isEmpty())) {
			System.out.println("\n=== === === === === === ===");			
			for(EmpDTO empDTO :list) {
				System.out.println("  EMP_NAME | "+empDTO.getEmpName());
				System.out.println("     EMAIL | "+empDTO.getEmail());
				System.out.println("     PHONE | "+empDTO.getPhone());
				System.out.println(" DEPT_CODE | "+empDTO.getDeptCode());
				System.out.println("  JOB_CODE | "+empDTO.getJobCode());
				System.out.println("MANAGER_ID | "+empDTO.getManagerId());
				System.out.println(" HIRE_DATE | "+empDTO.getHireDate());
			}
			System.out.println("=== === === === === === ===");	
		}
	}

	private void insertEmp() {
		while (true) {
			System.out.println("\n== 사원 추가하기 ==");
			System.out.print("이름을 입력하세요 >>");
			String empName = sc.nextLine();
			System.out.print("주민번호를 입력하세요 >>");
			String empNo = sc.nextLine();
			System.out.print("이메일을 입력하세요 >>");
			String email = sc.nextLine();
			System.out.print("전화번호를 입력하세요 >>");
			String phone = sc.nextLine();
			System.out.print("부서코드를 입력하세요 >>");
			String dCode = sc.nextLine();
			System.out.print("직급코드를 입력하세요 >>");
			String jCode = sc.nextLine();
			System.out.print("급여등급을 입력하세요 >>");
			String sLevel = sc.nextLine();
			System.out.print("급여를 입력하세요 >>");
			long salary = 0;
			try {
				salary = sc.nextLong();				
			} catch (InputMismatchException e) {
				System.out.println(">숫자만 입력하세요");
				sc.nextLine();
				continue;
			}
			sc.nextLine();	
			System.out.print("사수번호를 입력하세요 >>");
			String managerId = sc.nextLine();
			
			int result = controller.insertEmp(empName, empNo, email, phone, dCode, jCode, sLevel, salary, managerId);
			
			if(result >0) {
				System.out.println("\n등록 되었습니다");
			} else {
				System.out.println("\n등록 실패");
			}
		}
	}

	private void updateEmp() {
		System.out.println("\n== 사원정보 수정하기 ==");
		System.out.print("사원번호를 입력하세요 >>");
		String empId = sc.nextLine();
		System.out.print("수정할 사원의 부서코드를 입력하세요 >>");
		String dCode = sc.nextLine();

		int result = controller.updateEmp(empId, dCode);
		
		if(result >0) {
			System.out.println("\n"+empId+" 사원이 수정 되었습니다");
		} else {
			System.out.println("\n수정 실패");
		}
	}

	private void deleteEmp() {
		System.out.println("\n== 사원 삭제하기 ==");
		System.out.print("사원번호를 입력하세요 >>");
		String empId = sc.nextLine();

		int result = controller.deleteEmp(empId);
		
		if(result >0) {
			System.out.println("\n"+empId+" 사원이 삭제 되었습니다");
		} else {
			System.out.println("\n삭제 실패");
		}
	}
}
