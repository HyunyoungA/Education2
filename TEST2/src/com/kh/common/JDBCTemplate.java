package com.kh.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
	private static Connection conn = null;
	//외부파일에서 입력하기
	private static Connection getConnection() {
		if(conn == null) {
			
			try {
				Properties prop = new Properties();
				prop.load(new FileReader("driver.properties"));
				
				Class.forName(prop.getProperty("driver"));
				conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
		
}
