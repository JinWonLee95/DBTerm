package Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Time_table.Time_tableDTO;

public class PaymentDAO {

	private static PaymentDAO instance = new PaymentDAO();

	public static PaymentDAO getInstance() {
		return instance;
	}

	public PaymentDAO() {

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

	public boolean insertPayment(PaymentDTO dto, int using_point) {
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		boolean result = false;

		try {
			conn = getConnection();
			conn2 = getConnection();

			String sql = "insert into payment VALUES (?,?,?,?,?)";
			String sql2 = "UPDATE client SET client_point = client_point - ?, "
					+ "client_purchase_count = client_purchase_count + 1 WHERE client_id "
					+ "in (select client_id from ticketing where ticket_number = ?);";
			
			pstmt = conn.prepareStatement(sql);
			pstmt2 = conn2.prepareStatement(sql2);
			
			pstmt.setString(1, Integer.toString(dto.getPayment_no()));
			pstmt.setString(2, Integer.toString(dto.getTicket_no()));
			pstmt.setString(3, Integer.toString(dto.getPrice()));
			pstmt.setString(4, dto.getPay_option());
			pstmt.setString(5, Integer.toString(dto.getIsPayed()));
			pstmt2.setString(1, Integer.toString(using_point));
			pstmt2.setString(2, Integer.toString(dto.getTicket_no()));
			
			int r = pstmt.executeUpdate();
			int r2 = pstmt2.executeUpdate();
			if (r > 0 && r2 > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close();} catch (SQLException sqle) {}
			if (conn != null)try {conn.close();} catch (SQLException sqle) {}
			if (pstmt2 != null) try { pstmt2.close();} catch (SQLException sqle) {}
			if (conn2 != null)try {conn2.close();} catch (SQLException sqle) {}
		}
		return result;
	}

	public PaymentDTO getPayment(String p_no) {
		PaymentDTO dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT payment_no, ticket_no, price, pay_option,"
					+ " isPayed FROM payment WHERE payment_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_no);
			r = pstmt.executeQuery();

			if (r.next()) {
				int payment_no = r.getInt("payment_no");
				int ticket_no = r.getInt("ticket_no");
				int price = r.getInt("price");
				String pay_option = r.getString("pay_option");
				int isPayed = r.getInt("isPayed");

				dto = new PaymentDTO(payment_no, ticket_no, price, pay_option, isPayed);
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

	public List<PaymentDTO> getPaymentList() {
		List<PaymentDTO> list = new ArrayList<PaymentDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT payment_no, ticket_no, price, pay_option,"
					+ " isPayed FROM payment ORDER BY payment_no ASC";
			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				int payment_no = r.getInt("payment_no");
				int ticket_no = r.getInt("ticket_no");
				int price = r.getInt("price");
				String pay_option = r.getString("pay_option");
				int isPayed = r.getInt("isPayed");

				list.add(new PaymentDTO(payment_no, ticket_no, price, pay_option, isPayed));
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

	public boolean updatePayment(PaymentDTO dto, String p_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "UPDATE payment SET payment_no=?, ticket_no=?,"
					+ " price=?, pay_option=?, isPayed=? WHERE payment_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Integer.toString(dto.getPayment_no()));
			pstmt.setString(2, Integer.toString(dto.getTicket_no()));
			pstmt.setString(3, Integer.toString(dto.getPrice()));
			pstmt.setString(4, dto.getPay_option());
			pstmt.setString(5, Integer.toString(dto.getIsPayed()));
			pstmt.setString(6, p_no);
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

	public boolean deletePayment(String p_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = getConnection();

			String sql = "DELETE FROM payment WHERE payment_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_no);
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

	public int getFinal_price(int ticket_no, int using_point) {
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet r = null;
		int final_price = 1;

		try {
			conn = getConnection();
			
			String sql = "select (10000 * (select people " + "from ticketing where ticket_number = ? ) "
					+ "- ?) as final_price;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(ticket_no));
			pstmt.setString(2, Integer.toString(using_point));
			r = pstmt.executeQuery();
			if (r.next()) {
				final_price = r.getInt("final_price");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close();} catch (SQLException ex) {}
			if (conn != null) try { conn.close(); } catch (SQLException ex) {}
		}
		return final_price;
	}
}


