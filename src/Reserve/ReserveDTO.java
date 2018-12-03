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
		sb.append("[ "+movie_name+ " ] 영화 정보====\n");
		sb.append("영화 id : "+ movie_id +"\n");
		sb.append("영화 이름 : "+ movie_name +"\n");
		sb.append("영화 감독 : "+ movie_director +"\n");
		sb.append("영화 출연 : "+ movie_actor +"\n");
		sb.append("영화 등급 : "+ movie_rating +"\n");
		sb.append("영화 정보 : "+ movie_info +"\n");
		sb.append("영화 예매 횟수 : "+ movie_reserve_count +"\n");
		sb.append("상영관 이름 : "+ auditorium_name +"\n");

		return sb.toString();
	}
*/
}
