package com.togogo.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 
 * 数据库工具，读取classpath下的db.properties的配置
 * @author Zhanhao Wong
 *
 */
public class DbKit {
	
	private  String username;
	private  String password;
	private  String driver;
	private  String url;
	private String jndi;
	
	private DataSource dataSource;
	
	public static DbKit me = new DbKit();
	
	
	private DbKit() {
		Properties prop = new Properties();
		try {
			prop.load(DbKit.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			throw new RuntimeException("配置文件db.properties读取失败", e);
		}
		
		username = prop.getProperty("db.username");
		password = prop.getProperty("db.password");
		url = prop.getProperty("db.url");
		driver = prop.getProperty("db.driver");
		jndi = prop.getProperty("db.jndi");
		
		
		try {
			if(jndi != null) {
				Context ctx=new InitialContext();
				DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/mysql"); 
			}
		} catch (Exception e) {
			System.err.println("配置tomcat数据源失败，将直接使用默认配置 " + e.getMessage());
		}
	}
	
	
	/**
	 * 获得连接
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		
		if (dataSource != null) {	// 判断数据源时候可用
			conn = dataSource.getConnection();
		} else {	// 不能用，直接连接用户名密码
			// 获得驱动
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		}
		
		return conn;
	}
	
	
	/**
	 * 关闭连接
	 * @param conn
	 */
	public void closeConnection(Connection conn) {
		if(conn == null) return;
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 关闭 Statement
	 * @param stat
	 */
	public void closeState(Statement stat) {
		if(stat == null) return;
		try {
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 关闭 ResultSet
	 * @param stat
	 */
	public void closeQuery(ResultSet set) {
		if(set == null) return;
		try {
			set.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
