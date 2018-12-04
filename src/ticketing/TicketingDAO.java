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
	Connection conn2 = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;

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
	public boolean insertTicketing(TicketingDTO dto, String client_id) {
		boolean result = false;

		try {
			conn = getConnection();
			conn2 = getConnection();

			String sql = "insert into ticketing VALUES (?,?,?,?,?);";
			String sql2 = "UPDATE movie SET movie_reserve_count = movie_reserve_count + 1 "
					+ "where movie_id in (select movie_id from ticketing where " + "client_id = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt2 = conn2.prepareStatement(sql2);
			pstmt.setString(1, dto.getClient_id());
			pstmt.setString(3, dto.getMovie_id());
			pstmt.setString(5, Integer.toString(dto.getPeople()));
			pstmt.setString(2, Integer.toString(dto.getTicket_number()));
			pstmt.setString(4, dto.getTheater_name());
			pstmt2.setString(1, client_id);

			int r = pstmt.executeUpdate();
			int r2 = pstmt2.executeUpdate();
			if (r > 0 && r2 > 0) {
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
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException sqle) {
				}
			if (conn2 != null)
				try {
					conn2.close();
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

	public int getSeatSum(String month, String day, String show_time, String movie_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;
		int sum_number = -1;

		try {
			conn = getConnection();

			String sql = "select sum(people) from ticketing where ticket_number DIV 100 = ?" + " and movie_id = ?";

			pstmt = conn.prepareStatement(sql);
			String temp = month + day + show_time;
			int num = Integer.parseInt(temp);

			pstmt.setString(1, Integer.toString(num));
			pstmt.setString(2, movie_id);
			r = pstmt.executeQuery();

			if (r.next()) {
				sum_number = r.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum_number;
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
		} 
		return dto;
	}

	// 저장된 티켓 목록 보기
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

	public List<TicketingDTO> getTicketingList_for_pay(String c_id) {
		List<TicketingDTO> list = new ArrayList<TicketingDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT client_id, movie_id, theater_name, people, ticket_number FROM ticketing "
					+ "where client_id = ? ORDER BY ticket_number ASC";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, c_id);
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
		}

		return list;
	}

	// 예매 현황 출력
	public void getTicketInfo(String id) {

		try {
			conn = getConnection();
			String sql = "select * from ticketing where client_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			String str = "";
			while (rs.next()) {
				str += rs.getString("ticketing.client_id") + "\t\t" + rs.getString("ticketing.ticket_number") + "\t\t"
						+ rs.getString("ticketing.movie_id") + "\t\t" + rs.getString("ticketing.theater_name") + "\t\t"
						+ rs.getString("ticketing.people")+"\n";
			}
			System.out.println(str + "\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteArrange(int ticket_No) {
		try {

			System.out.println("들어감");
			conn = getConnection();
			// 회원 예매 횟수 --
			String sql = "update client set client_purchase_count = client_purchase_count-1 where client_id in ( select client_id from ticketing where ticket_number ="
					+ ticket_No + ") ";
			pstmt = conn.prepareStatement(sql);
			int r1 = pstmt.executeUpdate();

			// 영화 예매 횟수 --
			sql = "update movie set movie_reserve_count = movie_reserve_count-1 where movie_id in ( select movie_id from ticketing where ticket_number = "
					+ ticket_No + ") ";
			pstmt = conn.prepareStatement(sql);
			int r2 = pstmt.executeUpdate();

			// 회원 예매 내역에서 삭제
			sql = "delete from purchased where client_id in (select client_id from ticketing where ticket_number = "
					+ ticket_No
					+ ") and movie_name in (select movie_name from movie where movie_id in ( select movie_id from ticketing where ticket_number = "
					+ ticket_No + "))";
			pstmt = conn.prepareStatement(sql);
			int r3 = pstmt.executeUpdate();

			// 결제에서 삭제
			sql = "delete from payment where ticket_no =" + ticket_No;
			pstmt = conn.prepareStatement(sql);
			int r4 = pstmt.executeUpdate();

			// 예매 현황에서 삭제
			sql = "delete from ticketing where ticket_number =" + ticket_No;
			pstmt = conn.prepareStatement(sql);
			int r5 = pstmt.executeUpdate();

		} catch (Exception e) {

		}
	}

	// 영화표 발권
	public void getPrintTicket(int t_no) {
		try {
			String sql;
			if (isPayed(t_no)) {
				conn = getConnection();
				sql = "select * from ticketing, payment as ticket where ticketing.ticket_number = ticket.ticket_no and ticketing.ticket_number =?;";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, t_no);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String str = " 예매 번호 : " + rs.getInt("ticket_number") + "\n 무비 ID: " + rs.getString("movie_id")
							+ "\n 영화관 이름 : " + rs.getString("theater_name") + "\n 관람 인원: " + rs.getInt("people")
							+ "\n 가격 : " + rs.getInt("ticket.price") + "\n";
					System.out.println("==============================티켓 정보입니다.=========================");
					System.out.println(str);
				}
			}else {
				System.out.println("아직 결제가 되지 않은 티켓입니다.");
			}
		} catch (Exception e) {

		}
	}
	
	public int makePayment_no() {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet r = null;
	      int payment_no = -1;

	      while (true) {
	         double randomValue = Math.random();
	         payment_no = (int) (randomValue * 899999) + 100001;
	         int success = -1;
	         try {
	            conn = getConnection();

	            String sql = "select exists (select * from payment where payment_no = ?) as success";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, Integer.toString(payment_no));
	            r = pstmt.executeQuery();
	            if (r.next()) {
	               success = r.getInt("success");
	            }
	            if (success == 0) {
	               break;
	            }
	         } catch (Exception e) {
	            e.printStackTrace();
	         } finally {
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
	      }
	      return payment_no;
	   }

	public boolean isPayed(int ticket_No) {
		boolean isPayed = false;
		try {
			conn = getConnection();
			String sql;

			sql = "SELECT isPayed FROM payment WHERE ticket_no =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ticket_No);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				if (rs.getInt("payment.isPayed") == 1) {
					isPayed = true;
				}
			}

		} catch (Exception e) {

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
		return isPayed;
	}

}