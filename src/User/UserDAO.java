package User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	private static UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	public UserDAO() {

	}

	private Connection getConnection() throws Exception {
		Connection conn = null;
		String jdbcUrl = "jdbc:mysql://localhost:3306/db_term?serverTimezone=UTC&useSSL=false";
		String dbId = "root";
		String dbPass = "1%dmlghkrfbf";

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		return conn;

	}

	public boolean loginUser(String id, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "select client_password from client where client_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			r = pstmt.executeQuery();

			if (r.next()) {
				if (r.getString("client_password").equals(passwd)) {
					result = true;
				} else {
					System.out.println("비밀번호가 틀렸습니다.");
				}
			} else {
				System.out.println("잘못된 아이디입니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean signUpUser(UserDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		boolean result = false;

		try {
			if (table_confirm(dto.getUser_id())) {
				conn = getConnection();

				String sql = "insert into client VALUES (?,?,?,?,?,?,?,?)";
				String sql2 = "select exists (select * from client where client_id = ?) as success";
				pstmt = conn.prepareStatement(sql);
				pstmt2 = conn.prepareStatement(sql2);
				pstmt.setString(1, dto.getUser_id());
				pstmt.setString(2, dto.getUser_password());
				pstmt.setString(3, dto.getUser_name());
				pstmt.setString(6, dto.getUser_birth());
				pstmt.setString(4, dto.getUser_address());
				pstmt.setString(5, dto.getUser_number());
				pstmt.setString(7, Integer.toString(dto.getUser_point()));
				pstmt.setString(8, Integer.toString(dto.getUser_purchase_count()));

				pstmt2.setString(1, dto.getUser_id());
				ResultSet rs = pstmt2.executeQuery();
				
				rs.next();		
				if(rs.getInt(1) == 1){
					System.out.println(dto.getUser_id() + "은 존재하는 아이디입니다.");
				}else{
					int r = pstmt.executeUpdate();
					if (r > 0) {
						result = true;
					}	
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}

	private boolean table_confirm(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;
		boolean confirm = true;
		try {
			conn = getConnection();
			String sql = "select * from client where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			r = pstmt.executeQuery();

			while (r.next()) {
				confirm = false;
				break;
			}

		} catch (Exception e) {

		}
		return confirm;
	}

	public UserDTO getUser(String id) {
		UserDTO dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT client_id, client_password,client_name,client_birth,client_address,client_number,client_point,client_purchase_count FROM client WHERE client_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			r = pstmt.executeQuery();

			if (r.next()) {
				String client_id = r.getString("client_id");
				String client_password = r.getString("client_password");
				String client_name = r.getString("client_name");
				String client_birth = r.getString("client_birth");
				String client_address = r.getString("client_address");
				String client_number = r.getString("client_number");
				int client_point = r.getInt("client_point");
				int client_purchase_count = r.getInt("client_purchase_count");

				dto = new UserDTO(client_id, client_password, client_name, client_birth, client_address, client_number,
						client_point, client_purchase_count);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				try {
					r.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return dto;
	}
	
	public UserDTO getUser_byTicketNo(int ticket_no) {
		UserDTO dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT client_id, client_password,client_name,client_birth,client_address,client_number,client_point,client_purchase_count "
					+ "FROM client WHERE client_id in (select client_id from ticketing where ticket_number = ? )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(ticket_no));
			r = pstmt.executeQuery();

			if (r.next()) {
				String client_id = r.getString("client_id");
				String client_password = r.getString("client_password");
				String client_name = r.getString("client_name");
				String client_birth = r.getString("client_birth");
				String client_address = r.getString("client_address");
				String client_number = r.getString("client_number");
				int client_point = r.getInt("client_point");
				int client_purchase_count = r.getInt("client_purchase_count");

				dto = new UserDTO(client_id, client_password, client_name, client_birth, client_address, client_number,
						client_point, client_purchase_count);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	

	public boolean updateUser(UserDTO dto, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "UPDATE client SET client_id = ?, client_password = ?, client_name = ?, client_birth = ?, client_address = ?, client_number = ?, client_point = ?, client_purchase_count = ? WHERE client_id = ?";
			String sql2 = "select exists (select * from theater where client_id = ?) as success";
			pstmt = conn.prepareStatement(sql);
			pstmt2 = conn.prepareStatement(sql2);
			
			pstmt.setString(1, dto.getUser_id());
			pstmt.setString(2, dto.getUser_password());
			pstmt.setString(3, dto.getUser_name());
			pstmt.setString(4, dto.getUser_birth());
			pstmt.setString(5, dto.getUser_address());
			pstmt.setString(6, dto.getUser_number());
			pstmt.setString(7, Integer.toString(dto.getUser_point()));
			pstmt.setString(8, Integer.toString(dto.getUser_purchase_count()));
			pstmt.setString(9, id);
			
			pstmt2.setString(1, dto.getUser_id());
			ResultSet rs = pstmt2.executeQuery();
			
			rs.next();		
			if(rs.getInt(1) == 1){
				System.out.println(dto.getUser_id() + "은 존재하는 아이디입니다.");
			}else{
				int r = pstmt.executeUpdate();
				if (r > 0) {
					result = true;
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteUser(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = getConnection();

			String sql = "DELETE FROM client WHERE client_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int r = pstmt.executeUpdate();
			if (r > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
		}
		return result;
	}

}
