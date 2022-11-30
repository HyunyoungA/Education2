package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.close;//Connection은 close를 한번도 처리해주지않아서 닫아준다. 
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.getConnection;//insertMember안에 JDBCTemplate을 생략해주기 위해서 변경
import static com.kh.common.JDBCTemplate.rollback;


import com.kh.medel.vo.Member;
import com.kh.model.dao.MemberDAO;
	
	/*
	 <Service의 역할>
	 1. controller로부터 인자 전달 받음
	 2. Connection객체 생성
	 3. DAO객체 생성 후 Controller로부터 전달받은 인자와 conn 전달
	 4. 트랜잭션 관리
	 * */
	
public class MemberService {
	private MemberDAO mDAO = new MemberDAO();
	
	public int insertMember(Member mem) {//커넥션 받아오기
//		Connection conn = JDBCTemplate.getConnection();//static
		Connection conn = getConnection();
		
		int result = mDAO.insertMember(mem, conn);//MemberDAO의 insertMember의 매개변수에 conn추가
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
	}

	public ArrayList<Member> selectMemberId(String id) {
		Connection conn = getConnection();
		ArrayList<Member> list = mDAO.selectMemberId(conn, id);
		
		return list;
	}

	public ArrayList<Member> selectGender(char gen) {
		Connection conn = getConnection();
		ArrayList<Member> list = mDAO.selectGender(conn, gen);
		
		return list;
	}

	public ArrayList<Member> selectAll() {//Service에 들어와서 제일 먼저 해야하는 일 : Connection 생성
		Connection conn = getConnection();//getConnection()는 static메소드여서 앞에 클래스명.이 들어가야 맞는데 위에 import static을 해둔 상태이기 때문에 메소드만 호출해도 괜찮은 상태.
		ArrayList<Member> list = mDAO.selectAll(conn);
		
		return list;
	}

	public int checkMember(String memberId) {
		Connection conn = getConnection();
		
		int result = mDAO.checkMember(conn,memberId);
		
		return result;
	}

	public int updateMember(int sel, String input, String memberId) {
		Connection conn = getConnection();
		
		int result = mDAO.updeteMember(conn, sel, input, memberId);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
	}
	
	public int deleteMember(String id) {
		Connection conn = getConnection();
		int result = mDAO.deleteMember(conn, id);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	public void exitProgram() {
		Connection conn = getConnection();
		close(conn);
		
	}	
}
