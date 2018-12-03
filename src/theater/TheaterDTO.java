package theater;

import java.util.Formatter;

public class TheaterDTO { 
	
	private String theater_name;
	private String theater_address;
	private String theater_number;
	
	/*
	private String room_name;
	private int seat_number;
	
	private int show_times;
	//상영시간표에 영화 ID가 또?
	private String show_start_date;
	private String show_end_date;
	
	
	private String movie_id;
	private String movie_title;
	private String movie_director;
	private String movie_star;
	private int movie_class;
	private String movie_info;
	private int movie_reserve_count;
	private int movie_reserve_clientID;
	*/
	

	public TheaterDTO() {
	
	}
	
	public TheaterDTO(String theater_name, String theater_address, 
			String theater_number) {
		this.theater_name = theater_name;
		this.theater_address = theater_address;
		this.theater_number = theater_number;
	}

	public String getTheater_name() {
		return theater_name;
	}

	public void setTheater_name(String theater_name) {
		this.theater_name = theater_name;
	}

	public String getTheater_address() {
		return theater_address;
	}

	public void setTheater_address(String theater_address) {
		this.theater_address = theater_address;
	}

	public String getTheater_number() {
		return theater_number;
	}

	public void setTheater_number(String theater_number) {
		this.theater_number = theater_number;
	}

	
/*
	public void setRegistdate(String registdate) {
		SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			registdate = textFormat.format(textFormat.parse(registdate));
		} catch (ParseException e) {}
		this.registdate = registdate;
		
	}
*/

	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ "+theater_name+ " ] 영화관의 정보====\n");
		sb.append("영화관 주소 : "+theater_address+"\n");
		sb.append("영화관 전화번호 : "+theater_number+"\n");

		return sb.toString();
	}

}
