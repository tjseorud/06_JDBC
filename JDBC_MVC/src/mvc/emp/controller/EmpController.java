package mvc.emp.controller;

import java.util.List;

import mvc.emp.model.dto.EmpDTO;
import mvc.emp.model.service.EmpService;

public class EmpController {
	private EmpService service = new EmpService();
	
	public List<EmpDTO> selectEmp(String empId) {	
		return service.seleteEmp(empId);
	}

	public int insertEmp(String empName, String empNo, String email, String phone, String dCode,
			String jCode, String sLevel, long salary, String managerId) {		
		EmpDTO emp = new EmpDTO();
		emp.setEmpName(empName);
		emp.setEmpNo(empNo);
		emp.setEmail(email);
		emp.setPhone(phone);
		emp.setDeptCode(dCode);
		emp.setJobCode(jCode);
		emp.setSalaryLevel(sLevel);
		emp.setSalary(salary);
		emp.setManagerId(managerId);		
		return service.insertEmp(emp);
	}

	public int updateEmp(String empId, String dCode) {
		return service.updateEmp(empId, dCode);
	}

	public int deleteEmp(String empId) {
		return service.deleteEmp(empId);
	}
}
