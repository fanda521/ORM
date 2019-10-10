package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.DataSourceConnectionFactory;

public class DbUtil {

	//��ȡ���ݿ�����
	/**
	 * 
	 * @return ��ȡ���ݿ�����
	 */
	static String url = "jdbc:mysql://127.0.0.1:3306/custom_info";
	static String user = "root";
	static String pwd = "331224";
	static String driver = "com.mysql.jdbc.Driver";
	static final ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	//ע������
	private static BasicDataSource ds = null;
	static DataSourceConnectionFactory connectionFactory = null;//���ӳع���
	
	static {
		try {
			ds = new BasicDataSource();//����Դ
			ds.setDriverClassName(driver);
			ds.setUrl(url);
			ds.setUsername(user);
			ds.setPassword(pwd);
			
			/*
			  // ��ʼ������
			ds.setInitialSize(20);
	        // ���Ļ�ȡ������
			ds.setMaxActive(100);
	        // ��С���ÿ���������
			ds.setMinIdle(10);
	        // �����ÿ���������
			ds.setMaxIdle(30);       
			*/
			
			// ��ʼ�����ӳع���
			connectionFactory = new DataSourceConnectionFactory(ds) ;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ���ݿ����ӷ���
	 * @return
	 */
	public static synchronized Connection getCon() {
		try {
			Connection conn = conns.get();
			if(null == conn) {
				conn = connectionFactory.createConnection();
				conns.set(conn); 
			}
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//�ر����ݿ���Դ
	public static void close(Connection conn,Statement stmt,ResultSet rs) {
		try {
			if(null != conn) conn.close();
			if(null != stmt) stmt.close();
			if(null != rs) rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void close2() {
		Connection conn = conns.get();
		if(null != conn)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		conns.remove();
	}
	public static void close(Connection conn) {
		close(conn,null,null);
	}
	public static void close(Connection conn,Statement stmt) {
		close( conn, stmt,null);
	}
}
