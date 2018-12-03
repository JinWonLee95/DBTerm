package Reserve;
public class ReserveDTO { 


	private String user_id;
	private String movie_id;
	private int reserve_number;
	private int number_of_people;

	public ReserveDTO() {
	
	}
	
	public ReserveDTO(String user_id, String movie_id, int reserve_number, 
			int number_of_people) {
		this.user_id = user_id;
		this.reserve_number = reserve_number;
		this.number_of_people = number_of_people;
		this.movie_id = movie_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}

	public int getReserve_number() {
		return reserve_number;
	}

	public void setReserve_number(int reserve_number) {
		this.reserve_number = reserve_number;
	}

	public int getNumber_of_people() {
		return number_of_people;
	}

	public void setNumber_of_people(int number_of_people) {
		this.number_of_people = number_of_people;
	}
/*
	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ "+movie_name+ " ] ��ȭ ����====\n");
		sb.append("��ȭ id : "+ movie_id +"\n");
		sb.append("��ȭ �̸� : "+ movie_name +"\n");
		sb.append("��ȭ ���� : "+ movie_director +"\n");
		sb.append("��ȭ �⿬ : "+ movie_actor +"\n");
		sb.append("��ȭ ��� : "+ movie_rating +"\n");
		sb.append("��ȭ ���� : "+ movie_info +"\n");
		sb.append("��ȭ ���� Ƚ�� : "+ movie_reserve_count +"\n");
		sb.append("�󿵰� �̸� : "+ auditorium_name +"\n");

		return sb.toString();
	}
*/
}
