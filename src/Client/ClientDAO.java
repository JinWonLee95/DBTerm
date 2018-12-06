package Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

	private static ClientDAO instance = new ClientDAO();

	public static ClientDAO getInstance() {
		return instance;
	}

	public ClientDAO() {

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

	public ClientDTO getClient(String id) {
		ClientDTO dto = null;
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

				//String m_registdate = r.getDate("m_registdate").toString();
				dto = new ClientDTO(client_id, client_password,client_name,client_birth,client_address,client_number,client_point,client_purchase_count);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(r != null) try{r.close();}catch(SQLException ex) {}
	        if(pstmt != null) try{pstmt.close();}catch(SQLException ex){}
	        if(conn != null) try{conn.close();}catch(SQLException ex){}
		}

		return dto;
	}


	public List<ClientDTO> getClientList() {
		List<ClientDTO> list = new ArrayList<ClientDTO>();
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet r = null;
	    
	    try {
			conn = getConnection();

			String sql = "SELECT client_id, client_password,client_name,client_birth,client_address,client_number,client_point,client_purchase_count FROM client ORDER BY client_id DESC";

			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				String client_id = r.getString("client_id");
				String client_password = r.getString("client_password");
				String client_name = r.getString("client_name");
				String client_birth = r.getString("client_birth");
				String client_address = r.getString("client_address");
				String client_number = r.getString("client_number");
				int client_point = r.getInt("client_point");
				int client_purchase_count = r.getInt("client_purchase_count");

				//String m_registdate = r.getDate("m_registdate").toString();
				list.add(new ClientDTO(client_id, client_password,client_name,client_birth,client_address,client_number,client_point,client_purchase_count));
			}

	    }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         if(r != null) try{r.close();}catch(SQLException ex) {}
	         if(pstmt != null) try{pstmt.close();}catch(SQLException ex){}
	         if(conn != null) try{conn.close();}catch(SQLException ex){}
	      }

		return list;
	}
	
	//vip 선정 및 추가 결제 후 실행
	public void setAndShow_vip() {
		Connection conn = null;
		 PreparedStatement pstmt = null;
			boolean result = false;
			try {
				conn = getConnection();
				String sql = "truncate vip";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				
				sql = "select client_id, client_purchase_count from client where client_id != \'root\' order by client_purchase_count DESC limit 10;";
				pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					sql = "insert into vip values(?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, rs.getString("client.client_id"));
					pstmt.executeUpdate();
				}
				
				sql = "select * from vip";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				String vip_clients = "\n=========vip회원 목록=========\n";
				while(rs.next()) {
					vip_clients += rs.getString("vip.client_id")+" \n";
				}
				
				System.out.println(vip_clients);
				
			} catch(Exception e) {
				
			}
	}

}
