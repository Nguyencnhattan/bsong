package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Category;
import models.Songs;
import utils.JDBCConnection;
import utils.DefineUtil;

public class CategoryDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;

	public List<Category> getCategories() {
		String sql = "SELECT * FROM categories";
		List<Category> categories = new ArrayList<Category>();
		conn = JDBCConnection.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Category category = new Category(rs.getInt("id"), rs.getString("name"));
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}

		return categories;

	}

	public int Add(Category cat) {
		String sql = "INSERT INTO categories (name) values (?) ";
		int result = 0;
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, cat.getName());
			result = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, conn);

		}

		return result;
	}

	public Category getItem(int id) {
		Category item = null;

		String sql = "SELECT * FROM categories WHERE id = ?";
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {

				String name = rs.getString("name");
				item = new Category(id, name);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, rs, conn);

		}

		return item;
	}

	public int editItem(Category item) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "UPDATE categories SET name=? WHERE id=?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, item.getName());
			pst.setInt(2, item.getId());
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
		String sql = "DELETE FROM categories WHERE id=?";
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

	public boolean hasCat(String name) {
		conn = JDBCConnection.getConnection();
		String sql = "SELECT * FROM categories WHERE name = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);

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
	public int numberOfItems() {
		String sql = "SELECT COUNT(*) AS count FROM categories";
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
	public int numberOfItemsSearch(String search) {
		String sql = "SELECT COUNT(*) AS count FROM categories WHERE name LIKE ? ";
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+search+"%");
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

	public List<Category> getItemsPagination(int offset) {
		String sql = "SELECT * "
				+ "FROM categories "
				+ "LIMIT ?, ?";
	List<Category> cat = new ArrayList<>();
	conn = JDBCConnection.getConnection();
	try {
		pst = conn.prepareStatement(sql);
		pst.setInt(1, offset);
		pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
		rs = pst.executeQuery();
		while (rs.next()) {
			
			Category category = new Category(rs.getInt("id"), rs.getString("name"));
			cat.add(category);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		JDBCConnection.close(rs, pst, conn);

	}

	return cat;
	}

	

	public List<Category> getSearch(String search, int offset) {
		String sql = "SELECT * FROM categories WHERE name LIKE ? LIMIT ?, ?";
		List<Category> categories = new ArrayList<Category>();
		conn = JDBCConnection.getConnection();
		System.out.println(sql);
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+search+"%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				Category category = new Category(rs.getInt("id"), rs.getString("name"));
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}
		System.out.println(categories);
		return categories;
		
	}
	
}
