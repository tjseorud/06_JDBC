package mvc.emp.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.emp.model.dto.EmpDTO;

public class EmpDAO {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("없을수도?");
		}
	}

	public List<EmpDTO> seleteEmp(String empId, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql ="""
				SELECT EMP_NAME, EMAIL, PHONE, DEPT_CODE, JOB_CODE, MANAGER_ID, HIRE_DATE
				FROM EMPLOYEE
				WHERE EMP_ID = ?
				""";
		List<EmpDTO> list = new ArrayList<EmpDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, empId);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				EmpDTO empDTO = new EmpDTO();
				empDTO.setEmpName(rset.getString("EMP_NAME"));
				empDTO.setEmail(rset.getString("EMAIL"));
				empDTO.setPhone(rset.getString("PHONE"));
				empDTO.setDeptCode(rset.getString("DEPT_CODE"));
				empDTO.setJobCode(rset.getString("JOB_CODE"));
				empDTO.setManagerId(rset.getString("MANAGER_ID"));
				empDTO.setHireDate(rset.getDate("HIRE_DATE"));				
				list.add(empDTO);
			}
		} catch (SQLException e) {
			System.out.println("SQL?");
		}
		return list;
	}

	public int insertEmp(EmpDTO emp, Connection conn) {
		PreparedStatement pstmt = null;
		String sql ="""
				INSERT INTO EMPLOYEE
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?, SYSDATE, NULL, DEFAULT)
				""";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getEmpNo());
			pstmt.setString(3, emp.getEmail());
			pstmt.setString(4, emp.getPhone());
			pstmt.setString(5, emp.getDeptCode());
			pstmt.setString(6, emp.getJobCode());
			pstmt.setString(7, emp.getSalaryLevel());
			pstmt.setLong(8, emp.getSalary());
			pstmt.setString(9, emp.getManagerId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL?");
		}
		return result;
	}

	public int updateEmp(String empId, String dCode, Connection conn) {
		PreparedStatement pstmt = null;
		String sql = """
				UPDATE EMPLOYEE 
					SET DEPT_CODE = ?
				WHERE EMP_ID = ?
				""";
		int result = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dCode);
			pstmt.setString(2, empId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL?");
		}
		return result;
	}

	public int deleteEmp(String empId, Connection conn) {
		PreparedStatement pstmt = null;
		String sql = """
				DELETE FROM EMPLOYEE 
				WHERE EMP_ID = ?
				""";
		int result = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, empId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL?");
		}
		return result;
	}

}
