package mvc.emp.model.service;

import java.sql.Connection;
import java.util.List;

import mvc.emp.model.dao.EmpDAO;
import mvc.emp.model.dto.EmpDTO;
import mvc.emp.util.JdbcUtil;

public class EmpService {
	private EmpDAO empDAO = new EmpDAO();

	public List<EmpDTO> seleteEmp(String empId) {
		Connection conn = JdbcUtil.getConnection();
		List<EmpDTO> list = empDAO.seleteEmp(empId, conn); 
		return list;
	}

	public int insertEmp(EmpDTO emp) {
		Connection conn = JdbcUtil.getConnection();		
		return empDAO.insertEmp(emp, conn);
	}

	public int updateEmp(String empId, String dCode) {
		Connection conn = JdbcUtil.getConnection();
		return empDAO.updateEmp(empId, dCode, conn);
	}

	public int deleteEmp(String empId) {
		Connection conn = JdbcUtil.getConnection();
		return empDAO.deleteEmp(empId, conn);
	}
}
