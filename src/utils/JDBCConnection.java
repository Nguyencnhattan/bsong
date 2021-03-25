package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {
	public static final String URL = "jdbc:mysql://localhost:3306/bsong?useUnicode=true&characterEncoding=utf8";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "";

	public static Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connection : " + conn);
		return conn;
	}

	// overload la nhung phuong thuc cung ten khac nhau ve tham so chuyen vao
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static void close(PreparedStatement pst) {
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static void close(ResultSet rs, Statement st, Connection conn) {
		close(st);
		close(rs);
		close(conn);

	}

	public static void close(PreparedStatement pst, ResultSet rs, Connection conn) {
		close(pst);
		close(rs);
		close(conn);

	}

	public static void close(PreparedStatement pst, Connection conn) {
		close(pst);
		close(conn);

	}
//
//	public static void main(String[] args) {
//		ConnectDAO connectDAO = new ConnectDAO();
//		System.err.println(connectDAO.getConnection());
//	}
}
