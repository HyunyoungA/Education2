package com.kh.model.service;

import java.sql.Connection;

import com.kh.medol.vo.Member;
import com.kh.model.dao.MemberDAO;

public class MemberService {
	private MemberDAO mDAO = new MemberDAO();

	public int insertMember(Member mem) {
		Connection conn = getConnection();
		
		int result = mDAO.insertMember(mem, conn);
		return 0;
	}

	
	}
	
	
}
