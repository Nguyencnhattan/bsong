package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.Category;
import models.Songs;
import utils.JDBCConnection;
import utils.DefineUtil;

public class SongDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;

	public List<Songs> getAll() {
		String sql = "SELECT s.*, c.name AS catName FROM songs AS s INNER JOIN categories AS c on s.cat_id = c.id ";
		List<Songs> songs = new ArrayList<>();
		conn = JDBCConnection.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Songs song = new Songs(rs.getInt("id"), rs.getString("name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getString("picture"), rs.getTimestamp("date_create"),
						rs.getInt("counter"),new Category(rs.getInt("id"),rs.getString("catName")));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}

		return songs;

	}

	public List<Songs> getAll(int number) {
		String sql = "SELECT s.*, c.name AS catName FROM songs AS s INNER JOIN categories AS c on s.cat_id = c.id LIMIT ? ";
		List<Songs> songs = new ArrayList<>();
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, number);
			rs = pst.executeQuery();
			while (rs.next()) {
				Songs song = new Songs(rs.getInt("id"), rs.getString("name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getString("picture"), rs.getTimestamp("date_create"),
						rs.getInt("counter"),new Category(rs.getInt("id"),rs.getString("catName")));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, pst, conn);

		}

		return songs;

	}
	public int add(Songs song) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "INSERT INTO songs (name,preview_text,detail_text,picture,cat_id) VALUES (?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, song.getName());
			pst.setString(2, song.getPreview());
			pst.setString(3, song.getDetail());
			pst.setString(4, song.getPicture());
			pst.setInt(5, song.getCategory().getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, conn);
		}
		return result;
	}

	public Songs getItem(int id) {
		Songs item = null;

		String sql = "SELECT * FROM songs WHERE id = ?";
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {

				String name = rs.getString("name");
				String preview = rs.getString("preview_text");
				String detail = rs.getString("detail_text");
				String picture = rs.getString("picture");
				Timestamp date = rs.getTimestamp("date_create");
				int count = rs.getInt("counter");
				Category category =new Category(rs.getInt("cat_id"),null);
				item = new Songs(id,name, preview, detail, picture, date,count,category);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, rs, conn);

		}
		
		return item;
	}

	public int delItem(int id) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "DELETE FROM songs WHERE id=?";
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
	public int editItem(Songs item) {
		int result = 0;
		conn = JDBCConnection.getConnection();
		String sql = "UPDATE songs SET name=?, preview_text=?, detail_text=? ,picture=?, cat_id=? WHERE id=?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, item.getName());
			pst.setString(2, item.getPreview());
			pst.setString(3, item.getDetail());
			pst.setString(4, item.getPicture());
			pst.setInt(5, item.getCategory().getId());
			pst.setInt(6, item.getId());
			
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, conn);
		}
		return result;
	}

	public ArrayList<Songs> getAllByCat(int id) {
		String sql = "SELECT s.*, c.name AS catName FROM songs AS s INNER JOIN categories AS c on s.cat_id = c.id WHERE s.cat_id = ?";
		ArrayList<Songs> songs = new ArrayList<>();
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				Songs song = new Songs(rs.getInt("id"), rs.getString("name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getString("picture"), rs.getTimestamp("date_create"),
						rs.getInt("counter"),new Category(rs.getInt("id"),rs.getString("catName")));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, pst, conn);

		}

		return songs;
	}

	public ArrayList<Songs> getRelatedItem(Songs item, int i) {
		String sql = "SELECT s.*, c.name AS catName FROM songs AS s "
				+ "INNER JOIN categories "
				+ "AS c on s.cat_id = c.id "
				+ "WHERE s.cat_id = ? && s.id != ? "
				+ "LIMIT ?";
		ArrayList<Songs> songs = new ArrayList<>();
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, item.getCategory().getId());
			pst.setInt(2, item.getId());
			pst.setInt(3, i);
			rs = pst.executeQuery();
			while (rs.next()) {
				Songs song = new Songs(rs.getInt("id"), rs.getString("name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getString("picture"), rs.getTimestamp("date_create"),
						rs.getInt("counter"),new Category(rs.getInt("id"),rs.getString("catName")));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, pst, conn);

		}

		return songs;
	}

	public void increaseView(int id) {
		
		conn = JDBCConnection.getConnection();
		String sql = "UPDATE songs SET counter = counter + 1 WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCConnection.close(pst, conn);
		}
	
	}

	public int numberOfItems() {
		String sql = "SELECT COUNT(*) AS count FROM songs";
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
	public int numberOfItems(int catid) {
		String sql = "SELECT COUNT(*) AS count FROM songs WHERE cat_id=?";
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, catid);
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

	public List<Songs> getItemsPagination(int offset) {
		String sql = "SELECT s.*, c.name AS catName "
					+ "FROM songs AS s INNER JOIN categories AS c "
					+ "on s.cat_id = c.id "
					+ "LIMIT ?, ?";
		ArrayList<Songs> songs = new ArrayList<>();
		conn = JDBCConnection.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				
				Songs song = new Songs(rs.getInt("id"), rs.getString("name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getString("picture"), rs.getTimestamp("date_create"),
						rs.getInt("counter"),new Category(rs.getInt("id"),rs.getString("catName")));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, pst, conn);

		}

		return songs;
	}

	public ArrayList<Songs> getAllByCatPaginatioon(int offset, int catid) {
		String sql = "SELECT s.*, c.name AS catName "
				+ "FROM songs AS s INNER JOIN categories AS c "
				+ "on s.cat_id = c.id "
				+ "WHERE Cat_id = ? "
				+ "LIMIT ?, ?";
	ArrayList<Songs> songs = new ArrayList<>();
	conn = JDBCConnection.getConnection();
	try {
		pst = conn.prepareStatement(sql);
		pst.setInt(1, catid);
		pst.setInt(2, offset);
		pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
		rs = pst.executeQuery();
		while (rs.next()) {
			
			Songs song = new Songs(rs.getInt("id"), rs.getString("name"), rs.getString("preview_text"),
					rs.getString("detail_text"), rs.getString("picture"), rs.getTimestamp("date_create"),
					rs.getInt("counter"),new Category(rs.getInt("id"),rs.getString("catName")));
			songs.add(song);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		JDBCConnection.close(rs, pst, conn);

	}

	return songs;
	}

	public int numberOfItemsSearch(String search) {
		String sql = "SELECT COUNT(*) AS count "
				+ "FROM songs AS s INNER JOIN categories AS c "
				+ "on s.cat_id = c.id WHERE s.name LIKE ? OR c.name LIKE ? ";
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

	public List<Songs> getSearch(String search, int offset) {
		String sql ="SELECT s.*, c.name AS catName "
				+ "FROM songs AS s INNER JOIN categories AS c "
				+ "on s.cat_id = c.id WHERE s.name LIKE ? OR c.name LIKE ? LIMIT ?, ?";
		List<Songs> songs = new ArrayList<Songs>();
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
				Songs song = new Songs(rs.getInt("id"), rs.getString("name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getString("picture"), rs.getTimestamp("date_create"),
						rs.getInt("counter"),new Category(rs.getInt("id"),rs.getString("catName")));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, st, conn);

		}
		return songs;
	}

}
