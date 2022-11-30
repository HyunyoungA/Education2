package com.kh.conteoller;

import java.util.ArrayList;

import com.kh.medel.vo.Member;
import com.kh.model.dao.MemberDAO;
import com.kh.model.service.MemberService;
import com.kh.view.MemberMenu;

public class MemberController {
	private MemberMenu menu = new MemberMenu();
//	private MemberDAO dao = new MemberDAO();
	private MemberService mService = new MemberService();
	
	//사용자가 원하는 사원정보 추가
	public void insertMember() {
		Member mem = menu.insertMember();
		
		int result = mService.insertMember(mem);
		
		if(result > 0) {
			menu.displaySuccess(result + "개의 행이 추가되었습니다.");
		}else {
			menu.displayError("데이터 삽입 과정 중 오류 발생");
		}
	}

	public void selectMember() {
		int sel = menu.selectMember();
		
		ArrayList<Member> list = null;//패턴만 맞아도 불러올수있게 ArrayList사용
		switch(sel) {
		case 1: 
			String id = menu.inputMemberId();
			list = mService.selectMemberId(id);
			break;
		case 2: 
			char gen = menu.inputGender();
			list = mService.selectGender(gen);
			break;
		case 0: return;
		}
		
		if(!list.isEmpty()) {
			menu.displayMember(list);
		}else {
			menu.displayError("조회 결과가 없습니다.");
		}
	}

	public void selectAll() {
		//별도로 받아올 값이 없음. 전체 회원 조회라서 바로 Service에 연결
		ArrayList<Member> list = mService.selectAll();// 전체 회원 조회이기 때문에 반환값 ArryList<Member> 인 것 먼저 생각해보기
		
		if(!list.isEmpty()) {
			menu.displayMember(list);
		}else {
			menu.displayError("조회할 결과가 없습니다.");
		}
	}
	
	public void deleteMember() {
		// 누구를 삭제할지 아이디를 받아와야함
		String id = menu.inputMemberId();
		//입력 받은 아이디와 일치하는 회원이 있는지 확인
		int check = mService.checkMember(id);
		
		if(check == 1) {//있다면 delete진행
			//정말 삭제할 것인지 확인
			char yn = menu.checkDelete();
			//삭제하지 않겠다고 하면 메인 메뉴 돌아감
			if(yn == 'N') {
				return;
			}
			//진짜 삭제할건지
			int result = mService.deleteMember(id);
			
			if(result > 0) {
				menu.displaySuccess(result + "개의 행이 삭제되었습니다.");
			}else {
				menu.displayError("데이터 삭제 과정 중 오류 발생");
			}
		}else{
			menu.displayError("입력한 아이디가 존재하지 않습니다.");
		}
	}
	
	public void updateMember() {
		String memberId = menu.inputMemberId();
		
		int check = mService.checkMember(memberId);
		
		if(check != 1) {
			menu.displayError("입력한 아이디가 존재하지 않습니다.");
		}else {
			int sel = menu.updateMember();
			
			if(sel == 0) {
				return;
			}
			String input = menu.inputUpdate();
			
			int result = mService.updateMember(sel, input, memberId);
			
			if(result > 0) {
				menu.displaySuccess(result + "개의 행이 수정되었습니다.");
			}else {
				menu.displayError("데이터 수정 과정 중 오류 발생");
			}
		}	
	}

	public void exitProgram() {
		mService.exitProgram();
		
	}	
}
