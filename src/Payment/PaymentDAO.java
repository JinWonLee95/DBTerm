package Payment;

import java.sql.Connection;
import java.sql.DriverManager;

public class PaymentDAO {

	private static PaymentDAO instance = new PaymentDAO();
	
	public static PaymentDAO getInstance() {
		return instance;
	}
	
	public PaymentDAO() {
		
	}
	
	private Connection getConnection() throws Exception{
		Connection conn = null;
		String jdbcUrl = "jdbc:mysql://localhost:3306/db_term?serverTimezone=UTC&useSSL=false";
		String dbId = "root";
		String dbPass = "1%dmlghkrfbf";

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		return conn;
	}
	
}
