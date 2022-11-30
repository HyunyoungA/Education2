package com.kh.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Employee;

public class EmployeeDAO {

	public ArrayList<Employee> selectAll() {
		// JDBC드라이버 등록
		ArrayList<Employee> list = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1522:orcl", "SCOTT", "SCOTT");
			//jdbc:oracle:thin -->jdbc드라이버 타입
			//@127.0.0.1 --> 접근해야하는 데이터베이스의 ip주소값(내 컴퓨터에 대한 주소값)
			//1521 : 오라클 listener의 포트번호
			//xe : 오라클SID
			//"계정", "계정비밀번호"
			
			//System.out.println(conn);
		
			String query = "SELECT * FROM EMP";//쿼리를 데이터베이스에 전달하는 역할-->커넥션 이용
			
			stmt = conn.createStatement();//
			rset = stmt.executeQuery(query);//ResultSet : emp전체사원정보
			
			//ResultSet에 쿼리문에 대한 결과 값이 담겨져 있는 상태이긴 하지만
			//ResultSet자체로 활용할 수 없으니 ResultSet안에 담겨있는 데이터를 다른 객체에 옮겨 담을 것
			//-->여러 개를 담을 수 있는 객체가 좋겠다.(배열 or 컬렉션V) + 각 컬럼의 정보를 하나씩 담을 수 있게 Employee객체 사용
//			ArrayList<Employee> list = new ArrayList();//순서지키기위해 -->ArrayList<Employee>빼서 밖인 위에 적어줌. 마지막에 list를 return해주기 위해서.
			list = new ArrayList();//순서지키기위해
			while(rset.next()) {
				//ResultSet.next() : 다음 행이 존재하면 true반환, 없으면 false반환
				int empNo = rset.getInt("EMPNO");//변수에 담음 = 리절트셋 안에 있는 NUMBER타입 EMPNO을 가져온다.
				String empName = rset.getString("ENAME");//변수에 담음 = 리절트셋 안에 있는 VRCHAR2타입 ENAME을 가져온다.
				String job = rset.getString("JOB");
				int mgr = rset.getInt("MGR");
				Date hireDate =rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptno = rset.getInt("DEPTNO");//("")안에 들어있는 값은 ResultSet의 컬럼명이다.
				
				Employee e = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptno);
				list.add(e);//ArrayList<Employee> list = new ArrayList();에 담기
			}
			
//			System.out.println(list);//들어간 정보확인
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {//마지막에 발생시킨 순서대로 객체들 다 닫아줘야됨.
			try {
				rset.close();//마지막 순서를 첫번째로 닫아줌.
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;//list가 try안에 있어서 위치를 찾을 수 없음.-->반환타입이 void여서
	}

	public Employee selectEmployee(int empNo) {
		Employee emp = null;
		Connection conn = null;
		//1) Statement 버전
//		Statement stmt = null;
		
		//2) PrepaeredStatement버전
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl", "SCOTT", "SCOTT");
			//@localhost = @127.0.0.1
			
			//1) Statement버전 : 완성된 query
//			String query = "SELECT * FROM EMP WHERE EMPNO = " + empNo;
			
			//2) PreparedStatement버전 : 비어있는 공간이 존재하는 query(미완성 query)
			//			위치홀더(?) : 값을 넣기 위한 공간 확보
			String query = "SELECT * FROM EMP WHERE EMPNO = ?";
			
			//1) Statement버전
//			stmt = conn.createStatement(); 쿼리가 완성형이라 넣지않고 
//			rset = stmt.executeQuery(query); 보낼때 쿼리를 넣어줌
			
			//2) PreparedStatement버전 : 값을 세팅할 자료형에, 몇번째 위치홀더에 어떤 값으로 값을 세팅하겠다 지정
			pstmt = conn.prepareStatement(query);//메소드를통해 만드는데 인자로 내가 썼던 쿼리가 들어가야한다..
			//Statement를 사용할 때 완성된 쿼리여서 인자를 넣지 않고, PreparedStatement는 위치홀더때문에 미완성된 쿼리여서 그 비어있는 값을 집어넣기위해서 인자에 쿼리를 집에 넣음.
			pstmt.setInt(1, empNo);//(위치홀더에 대한 위치, 넣을 값)
			rset = pstmt.executeQuery();//위에서 쿼리를 보내줬기 때문에 여기선 보내주지않음
			
			if(rset.next()) {//0개이거나 1개이거나 =if 사용, 반복할 필요가 없다.
//				int empNo = rset.getInt("EMPNO"); 위에 selectEmployee(매개변수)로 입력받아올 empNo값이 있어서 따로 받아오진않는다. if문이 도는것 자체가 empNo가 존재한다기 때문에 실행되는 것이기 때문에 또 넣어주지 않아도 된다.
				String empName = rset.getString("ENAME");
				String job = rset.getString("job");
				int mgr = rset.getInt("MGR");
				Date hiredate = rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptno = rset.getInt("DEPTNO");
				
				emp = new Employee(empNo, empName, job, mgr, hiredate, sal, comm,deptno);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return emp;
		
	}

	public int insertEmployee(Employee e) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl", "SCOTT", "SCOTT");
			conn.setAutoCommit(false);
//			String query = "INSERT INTO EMP VALUES(" + e.getEmpNo() + ", " + e.getEmpName() + ", "
//													 + e.getJob() + ", " + e.getMgr() + ", "
//													 + "SYSDETE, " + e.getSal() + ", "
//													 + e.getComm() + ", " + e.getDeptNo() + ")";
			String query = "INSERT INTO EMP VALUES(?, ?, ?, ?, SYSDATE, ?, ?, ?)";//위치만 잘 확인해주면 된다.코드가 훨씬 짧아지고 간단해진다.
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, e.getEmpNo());
			pstmt.setString(2, e.getEmpName());
			pstmt.setString(3, e.getJob());
			pstmt.setInt(4, e.getMgr());
			pstmt.setInt(5, e.getSal());
			pstmt.setInt(6, e.getComm());
			pstmt.setInt(7, e.getDeptNo());//순서와 들어갈 값을 잘 확인해야한다.
			result = pstmt.executeUpdate();//업데이트되는것의 개수이기 때문에 int사용
			
			//트랜잭션 처리
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	//사번과 일치하는 사원의 정보 수정
	public int updateEmployee(Employee emp) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl", "SCOTT", "SCOTT");
			conn.setAutoCommit(false);
			
			String query = "UPDATE EMP SET JOB = ?, SAL = ?, COMM = ? WHERE EMPNO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emp.getJob());
			pstmt.setInt(2, emp.getSal());
			pstmt.setInt(3, emp.getComm());
			pstmt.setInt(4, emp.getEmpNo());
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int deleteEmployee(int empNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl", "SCOTT", "SCOTT");
			conn.setAutoCommit(false);
			
			String query = "DELETE FROM EMP WHERE EMPNO = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empNo);
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}return result;
	}
	
}
