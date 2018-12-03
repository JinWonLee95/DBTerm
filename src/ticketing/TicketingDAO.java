package ticketing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Auditorium.AuditoriumDTO;
import Time_table.Time_tableDTO;
import theater.TheaterDTO;

public class TicketingDAO {

	private static TicketingDAO instance = new TicketingDAO();
	Connection conn = null;
	PreparedStatement pstmt = null;

	public static TicketingDAO getInstance() {
		return instance;
	}

	public TicketingDAO() {

	}

	private Connection getConnection() throws Exception {
		String jdbcUrl = "jdbc:mysql://localhost:3306/db_term?serverTimezone=UTC&useSSL=false";
		String dbId = "root";
		String dbPass = "1%dmlghkrfbf";

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		return conn;

	}

	// 영화 등록하기
	public boolean insertTicketing(TicketingDTO dto) {
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "insert into ticketing VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getClient_id());
			pstmt.setString(3, dto.getMovie_id());
			pstmt.setString(5, Integer.toString(dto.getPeople()));
			pstmt.setString(2, Integer.toString(dto.getTicket_number()));
			pstmt.setString(4, dto.getTheater_name());
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

	public List<TheaterDTO> getTicketingTheater(String m_name) {
		List<TheaterDTO> list = new ArrayList<TheaterDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT theater_name, theater_address, theater_number FROM theater where theater_name "
					+ "in (select theater_name from movie where movie_name = ? )";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_name);
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

	public List<AuditoriumDTO> getTicketingAuditorium(String selectedTheater, String m_name) {
		List<AuditoriumDTO> list = new ArrayList<AuditoriumDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, seat, theater_name "
					+ "FROM auditorium where auditorium_name in (select auditorium_name from time_table where movie_id "
					+ "in (select movie_id from movie where movie_name = ? ) ) and theater_name = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_name);
			pstmt.setString(2, selectedTheater);
			r = pstmt.executeQuery();

			while (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				int seat = r.getInt("seat");
				String theater_name = r.getString("theater_name");
				list.add(new AuditoriumDTO(auditorium_name, seat, theater_name));
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

	public Time_tableDTO getTicketingDate(String selectedAuditorium) {
		Time_tableDTO dto = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, show_time, movie_id, start_date, end_date from time_table where auditorium_name = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, selectedAuditorium);
			r = pstmt.executeQuery();

			if (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				String show_time = r.getString("show_time");
				String movie_id = r.getString("movie_id");
				String start_date = r.getDate("start_date").toString();
				String end_date = r.getDate("end_date").toString();
				dto = new Time_tableDTO(auditorium_name, show_time, movie_id, start_date, end_date);
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

	public List<Time_tableDTO> getTicketingShow_time(String selectedAuditorium) {
		List<Time_tableDTO> list = new ArrayList<Time_tableDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, show_time, movie_id, start_date, end_date from time_table where auditorium_name = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, selectedAuditorium);
			r = pstmt.executeQuery();

			while (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				String show_time = r.getString("show_time");
				String movie_id = r.getString("movie_id");
				String start_date = r.getDate("start_date").toString();
				String end_date = r.getDate("end_date").toString();
				list.add(new Time_tableDTO(auditorium_name, show_time, movie_id, start_date, end_date));
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

	// 영화이름에 해당하는 영화 정보 보기
	public TicketingDTO getTicketing(int t_number) {
		TicketingDTO dto = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT client_id, movie_id, " + "people, ticket_number, theater_name "
					+ "FROM ticketing WHERE ticket_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(t_number));
			r = pstmt.executeQuery();

			if (r.next()) {
				String client_id = r.getString("client_id");
				String movie_id = r.getString("movie_id");
				int people = r.getInt("people");
				int ticket_number = r.getInt("ticket_number");
				String theater_name = r.getString("theater_name");
				dto = new TicketingDTO(client_id, movie_id, theater_name, people, ticket_number);
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
	public List<TicketingDTO> getTicketingList() {
		List<TicketingDTO> list = new ArrayList<TicketingDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT client_id, movie_id, theater_name, people, ticket_number FROM ticketing ORDER BY ticket_number ASC";

			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				String client_id = r.getString("client_id");
				String movie_id = r.getString("movie_id");
				String theater_name = r.getString("theater_name");
				int people = r.getInt("people");
				int ticket_number = r.getInt("ticket_number");
				list.add(new TicketingDTO(client_id, movie_id, theater_name, people, ticket_number));
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

	// 회원 삭제
	public boolean deleteTicketing(int t_number) {
		boolean result = false;
		try {
			conn = getConnection();

			String sql = "DELETE FROM ticketing WHERE ticket_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(t_number));
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

	// 예매 현황 출력
	public void getTicketInfo(String id) {

		try {
			conn = getConnection();
			String sql = "select * from ticketing where client_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			String str = "";
			while (rs.next()) {
				str += rs.getString("ticketing.client_id") + "\t\t" + rs.getString("ticketing.ticket_number") + "\t\t"
						+ rs.getString("ticketing.movie_id") + "\t\t" + rs.getString("ticketing.theater_name") + "\t\t"
						+ rs.getString("ticketing.people");
			}
			System.out.println(str + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteArrange(int ticket_No) {
		try {
			conn = getConnection();
			String sql;

			// 회원 예매 횟수 --
			sql = "update client set client_purchase_count = client_purchase_count-1 where client_id in ( select client_id from ticketing where ticket_number = "
					+ ticket_No + ") ";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			// 영화 예매 횟수 --
			sql = "update movie set movie_reserve_count = movie_reserve_count-1 where movie_id in ( select movie_id from ticketing where ticket_number = "
					+ ticket_No + ") ";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

			// 회원 예매 내역에서 삭제
			sql = "delete from purchased where client_id in (select client_id from ticketing where ticket_number = "+ticket_No
					+") and movie_name in (select movie_name from movie where movie_id in ( select movie_id from ticketing where ticket_number = "
					+ticket_No+"))";
			
			// 예매 현황에서 삭제
			sql = "delete from ticketing where ticket_number = " + ticket_No;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();


		} catch (Exception e) {

		}
	}

}