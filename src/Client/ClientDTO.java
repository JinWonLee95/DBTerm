package Client;

import java.util.Formatter;

public class ClientDTO {

	private String client_id;
	private String client_password;
	private String client_name;
	private String client_birth;
	private String client_address;
	private String client_number;
	private int client_point;
	private int client_purchase_count;

	public ClientDTO() {

	}

	public ClientDTO(String client_id, String client_password, String client_name, String client_birth,
			String client_address, String client_number, int client_point, int client_purchase_count) {
		this.client_id = client_id;
		this.client_password = client_password;
		this.client_name = client_name;
		this.client_birth = client_birth;
		this.client_address = client_address;
		this.client_number = client_number;
		this.client_point = client_point;
		this.client_purchase_count = client_purchase_count;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_password() {
		return client_password;
	}

	public void setClient_password(String client_password) {
		this.client_password = client_password;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getClient_birth() {
		return client_birth;
	}

	public void setClient_birth(String client_birth) {
		this.client_birth = client_birth;
	}

	public String getClient_address() {
		return client_address;
	}

	public void setClient_address(String client_address) {
		this.client_address = client_address;
	}

	public String getClient_number() {
		return client_number;
	}

	public void setClient_number(String client_number) {
		this.client_number = client_number;
	}

	public int getClient_point() {
		return client_point;
	}

	public void setClient_point(int client_point) {
		this.client_point = client_point;
	}

	public int getClient_purchase_count() {
		return client_purchase_count;
	}

	public void setClient_purchase_count(int client_purchase_count) {
		this.client_purchase_count = client_purchase_count;
	}

	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ " + client_id + " ] 회원의 정보====\n");
		sb.append("비밀번호 : " + client_password + "\n");
		sb.append("회원 이름 : " + client_name + "\n");
		sb.append("생일 : " + client_birth + "\n");
		sb.append("주소 : " + client_address + "\n");
		sb.append("전화번호 : " + client_number + "\n");
		sb.append("포인트 : " + client_point + "\n");
		sb.append("구매 횟수 : " + client_purchase_count + "\n");

		return sb.toString();
	}

}
