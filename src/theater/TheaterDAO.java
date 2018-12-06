package theater;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import movie.MovieDTO;

public class TheaterDAO {

	private static TheaterDAO instance = new TheaterDAO();
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;

	public static TheaterDAO getInstance() {
		return instance;
	}

	public TheaterDAO() {

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

	// 영화 등록하기
	public boolean insertTheater(TheaterDTO dto) {
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "insert into theater VALUES (?,?,?);";
			String sql2 = "select exists (select * from theater where theater_name = ?) as success";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTheater_name());
			pstmt.setString(2, dto.getTheater_address());
			pstmt.setString(3, dto.getTheater_number());

			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, dto.getTheater_name());
			ResultSet rs = pstmt2.executeQuery();

			rs.next();
			if (rs.getInt(1) == 1) {
				System.out.println(dto.getTheater_name() + "은 존재하는 영화관입니다.");
			} else {
				int r = pstmt.executeUpdate();
				if (r > 0) {
					result = true;
				}
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

	// 영화이름에 해당하는 영화 정보 보기
	public TheaterDTO getTheater(String t_name) {
		TheaterDTO dto = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT theater_name, theater_address, theater_number FROM theater WHERE theater_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t_name);
			r = pstmt.executeQuery();

			if (r.next()) {
				String theater_name = r.getString("theater_name");
				String theater_address = r.getString("theater_address");
				String theater_number = r.getString("theater_number");
				// String m_registdate = r.getDate("m_registdate").toString();
				dto = new TheaterDTO(theater_name, theater_address, theater_number);
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

	// 저장된 회원 목록 보기
	public List<TheaterDTO> getTheaterList() {
		List<TheaterDTO> list = new ArrayList<TheaterDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT theater_name, theater_address, theater_number FROM theater ORDER BY theater_name DESC";

			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				String theater_name = r.getString("theater_name");
				String theater_address = r.getString("theater_address");
				String theater_number = r.getString("theater_number");
				list.add(new TheaterDTO(theater_name, theater_address, theater_number));
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

		return list;
	}

	// 영화관 수정
	public boolean updateTheater(TheaterDTO dto, String t_name, String name) {
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "UPDATE theater SET theater_name = ? , theater_address = ?, theater_number = ? WHERE theater_name = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getTheater_name());
			pstmt.setString(2, dto.getTheater_address());
			pstmt.setString(3, dto.getTheater_number());
			pstmt.setString(4, t_name);

			if (!t_name.equals(name)) {
				String sql2 = "select exists (select * from theater where theater_name = ?) as success";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, dto.getTheater_name());
				ResultSet rs = pstmt2.executeQuery();
				rs.next();
				if (rs.getInt(1) == 1) {
					System.out.println(dto.getTheater_name() + "은 존재하는 영화관입니다.");
				} else {
					int r = pstmt.executeUpdate();
					if (r > 0) {
						result = true;
					}
				}
			}
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

	// 영화관 삭제
	public boolean deleteTheater(String t_name) {
		boolean result = false;
		try {
			conn = getConnection();

			String sql = "DELETE FROM theater WHERE theater_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t_name);
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

	public Boolean confirmTheater(String t_name) {
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "select exists (select * from theater where theater_name = ?) as success";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t_name);
			r = pstmt.executeQuery();

			r.next();
			if (r.getInt(1) == 1) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
