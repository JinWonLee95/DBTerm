package Auditorium;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AuditoriumDTO { 
	
	private String auditorium_name;
	private int seat;
	private String theater_name;
	
	public AuditoriumDTO() {
	
	}
	
	public AuditoriumDTO(String auditorium_name, int seat, 
			String theater_name) {
		this.auditorium_name = auditorium_name;
		this.seat = seat;
		this.theater_name = theater_name;
	}

	public String getAuditorium_name() {
		return auditorium_name;
	}

	public void setAuditorium_name(String auditorium_name) {
		this.auditorium_name = auditorium_name;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public String getTheater_name() {
		return theater_name;
	}

	public void setTheater_name(String theater_name) {
		this.theater_name = theater_name;
	}
	/*
	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			start_date = textFormat.format(textFormat.parse(start_date));
		} catch (ParseException e) {}
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			end_date = textFormat.format(textFormat.parse(end_date));
		} catch (ParseException e) {}
		this.end_date = end_date;
	}
	*/


	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ "+auditorium_name+ " ] 상영관의 정보====\n");
		sb.append("상영관 좌석 수 : "+seat+"\n");
		sb.append("영화관 이름 : "+theater_name+"\n");

		return sb.toString();
	}

}
