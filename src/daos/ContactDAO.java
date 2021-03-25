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

public class ContactDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;
	public int addItem(Contact item) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "INSERT INTO contacts (name, email, website, message) VALUES (?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, item.getName());
			pst.setString(2, item.getEmail());
			pst.setString(3, item.getWebsite());
			pst.setString(4, item.getMessage());
			
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, conn);
		}
		return result;
	}
	public List<Contact> getContact() {
		String sql = "SELECT * FROM contacts";
		List<Contact> contact = new ArrayList<Contact>();
		conn = JDBCConnection.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Contact item = new Contact(rs.getInt("id"), rs.getString("name"),rs.getString("email"),rs.getString("website"),rs.getString("message"));
				contact.add(item);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}
		return contact;
	}
	public int delItem(int id) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "DELETE FROM contacts WHERE id=?";
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
	public int numberOfItems() {
		String sql = "SELECT COUNT(*) AS count FROM contacts";
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
	public List<Contact> getItemsPagination(int offset) {
		String sql = "SELECT * "
				+ "FROM contacts "
				+ "LIMIT ?, ?";
		List<Contact> contact = new ArrayList<>();
	conn = JDBCConnection.getConnection();
	try {
		pst = conn.prepareStatement(sql);
		pst.setInt(1, offset);
		pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
		rs = pst.executeQuery();
		while (rs.next()) {
			
			Contact contacts = new Contact(rs.getInt("id"), rs.getString("name") ,rs.getString("email"), rs.getString("website"),rs.getString("message"));
			contact.add(contacts);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		JDBCConnection.close(rs, pst, conn);

	}

	return contact;
	}
	public int numberOfItemsSearch(String search) {
		String sql = "SELECT COUNT(*) AS count FROM contacts WHERE name LIKE ? OR email LIKE ?";
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
	public List<Contact> getSearch(String search, int offset) {
		String sql = "SELECT * FROM contacts WHERE name LIKE ? OR email LIKE ? LIMIT ?, ?";
		List<Contact> contacts = new ArrayList<Contact>();
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
				Contact contact = new Contact(rs.getInt("id"), rs.getString("name"),rs.getString("email"),rs.getString("website"),rs.getString("message"));
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}
		System.out.println(contacts);
		return contacts;
	}

}
