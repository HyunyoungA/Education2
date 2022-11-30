package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.EmployeeDAO;
import com.kh.model.vo.Employee;
import com.kh.view.Menu;

public class EmployeeController {
	private EmployeeDAO empDAO = new EmployeeDAO();
	private Menu menu = new Menu();
	
	//전체 사원 정보 조회
	public void selectAll() {
		ArrayList<Employee> list = empDAO.selectAll();
		
		if(list.isEmpty()) {
			menu.displayError("조회 결과가 없습니다.");
		}else {
			menu.selectAll(list);
		}
	}
	
	//사번으로 사원 정보 조회
	public void selectEmployee() {
		int empNo = menu.selectEmpNo();
		Employee emp = empDAO.selectEmployee(empNo);
		
		if(emp != null) {
			menu.selectEmployee(emp);
		}else {
			menu.displayError("해당 사번의 검색결과가 없습니다.");
		}
	}
	
	//새로운 사원 정보 추가
	public void insertEmployee() {
		Employee e = menu.insertEmployee();
		
		int result = empDAO.insertEmployee(e);
		
		if(result > 0) {
			menu.displaySuccess(result + "개의 행이 추가되었습니다.");
		}else {
			menu.displayError("데이터 삽입 과정 중 오류 발생");
		}
	}
	//사번과 일치하는 사원의 정보 수정
	public void updateEmployee() {
		int empNo = menu.selectEmpNo();
		
		Employee emp = menu.updateEmployee();
		int result = empDAO.updateEmployee(emp);
		if(result > 0) {
			menu.displaySuccess(result + "개의 행이 수정되었습니다");
		}else {
			menu.displayError("데이터 수정 과정 중 오류 발생");
		}
	}
	
	public void deleteEmployee() {
		int empNo = menu.selectEmpNo();
		
		String check = menu.deleteEmployee();
		if(check.equalsIgnoreCase("Y")) {
			int result = empDAO.deleteEmployee(empNo);
			
			if(result > 0) {
				menu.displaySuccess(result+ "개의 행이 삭제되었습니다.");
			}else {
				menu.displayError("데이터 삭제 과정 중 오류 발생");
			}
		}else if(check.equalsIgnoreCase("N")) {
			menu.displaySuccess("사원 정보 삭제 취소");
		}else {
			menu.displayError("잘못 입력하셨습니다. y 또는 n을 입력해주세요.");
		}
	}
}
	

