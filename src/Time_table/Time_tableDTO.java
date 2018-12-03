package Time_table;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Formatter;

public class Time_tableDTO {

	private String auditorium_name;
	private String show_time;
	private String movie_id;
	private String start_date;
	private String end_date;

	public Time_tableDTO(String auditorium_name, String show_time, String movie_id, 
			String start_date, String end_date) {
		this.auditorium_name = auditorium_name;
		this.show_time = show_time;
		this.movie_id = movie_id;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public String getAuditorium_name() {
		return auditorium_name;
	}

	public void setAuditorium_name(String auditorium_name) {
		this.auditorium_name = auditorium_name;
	}

	public String getShow_time() {
		return show_time;
	}

	public void setShow_time(String show_time) {
		this.show_time = show_time;
	}
	public String getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}
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

}
