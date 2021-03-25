package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Category;
import models.Contact;
import models.User;
import utils.JDBCConnection;
import utils.DefineUtil;

public class UserDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;

	public ArrayList<User> getItems() {
		ArrayList<User> items = new ArrayList<>();

		String sql = "SELECT * FROM users ";
		conn = JDBCConnection.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String fullname = rs.getString("fullname");
				User user = new User(id, username, password, fullname);
				items.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}

		return items;
	}

	public int addItem(User item) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "INSERT INTO users (username, password, fullname) VALUES (?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, item.getUsername());
			pst.setString(2, item.getPassword());
			pst.setString(3, item.getFullname());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, conn);
		}
		return result;
	}

	public boolean hasUser(String username) {

		conn = JDBCConnection.getConnection();
		String sql = "SELECT * FROM users WHERE username = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);

			rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, rs, conn);
		}
		return false;
	}

	public User getItem(int id) {
		User item = null;

		String sql = "SELECT * FROM users WHERE id = ?";
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {

				String username = rs.getString("username");
				String password = rs.getString("password");
				String fullname = rs.getString("fullname");
				item = new User(id, username, password, fullname);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, rs, conn);

		}

		return item;
	}

	public int editItem(User item) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "UPDATE users SET password=?, fullname=? WHERE id=?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, item.getPassword());
			pst.setString(2, item.getFullname());
			pst.setInt(3, item.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, conn);
		}
		return result;
	}

	public int delItem(int id) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "DELETE FROM users WHERE id=?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, conn);
		}
		return result;
	}

	public User existUser(String username, String password) {
		User item = null;

		String sql = "SELECT * FROM users WHERE username = ? and password = ?";
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String fullname = rs.getString("fullname");
				item = new User(id, username, password, fullname);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, rs, conn);

		}

		return item;
	}
	public int numberOfItems() {
		String sql = "SELECT COUNT(*) AS count FROM users";
		conn = JDBCConnection.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}
		
		return 0;
	}

	public ArrayList<User> getItemsPagination(int offset) {
		String sql = "SELECT * "
				+ "FROM users "
				+ "LIMIT ?, ?";
		ArrayList<User> users = new ArrayList<>();
	conn = JDBCConnection.getConnection();
	try {
		pst = conn.prepareStatement(sql);
		pst.setInt(1, offset);
		pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
		rs = pst.executeQuery();
		while (rs.next()) {
			
			User user = new User(rs.getInt("id"), rs.getString("username") , rs.getString("password"),rs.getString("fullname"));
			users.add(user);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		JDBCConnection.close(rs, pst, conn);

	}

	return users;
	}

	public List<User> getSearch(String search, int offset) {
		String sql = "SELECT * FROM users WHERE username LIKE ? OR fullname LIKE ? LIMIT ?, ?";
		List<User> users = new ArrayList<User>();
		conn = JDBCConnection.getConnection();
		System.out.println(sql);
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+search+"%");
			pst.setString(2, "%"+search+"%");
			pst.setInt(3, offset);
			pst.setInt(4, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"),rs.getString("password"),rs.getString("fullname"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}
		System.out.println(users);
		return users;
		
	}

	public int numberOfItemsSearch(String search) {
		String sql = "SELECT COUNT(*) AS count FROM users WHERE username LIKE ? OR fullname LIKE ?";
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+search+"%");
			pst.setString(2, "%"+search+"%");
			rs = pst.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, pst, conn);

		}
		
		return 0;
	}
	
}
