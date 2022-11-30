package com.kh.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import com.kh.medol.vo.Member;

public class MemberDAO {
	
	private Properties prop = null;
	
	public MemberDAO() {
		prop = new Properties();//기능을 불러오는 과정을 담을 곳
		try {
			prop.load(new FileReader("query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertMember(Member mem, Connection conn) {
		return 0;
	}

}
