package ticketing;

import java.util.Formatter;

public class TicketingDTO { 
   
   private String client_id;
   private String movie_id;
   private String theater_name;
   private int people;
   private int ticket_number;
   
   public TicketingDTO() {
   
   }
   
   public TicketingDTO(String client_id, String movie_id, String theater_name,
   int people, int ticket_number) {
      this.client_id = client_id;
      this.movie_id = movie_id;
      this.theater_name = theater_name;
      this.people = people;
      this.ticket_number = ticket_number;
   }

   public String getClient_id() {
      return client_id;
   }

   public void setClient_id(String client_id) {
      this.client_id = client_id;
   }

   public String getMovie_id() {
      return movie_id;
   }

   public void setMovie_id(String movie_id) {
      this.movie_id = movie_id;
   }

   public int getPeople() {
      return people;
   }

   public void setPeople(int people) {
      this.people = people;
   }

   public int getTicket_number() {
      return ticket_number;
   }

   public void setTicket_number(int ticket_number) {
      this.ticket_number = ticket_number;
   }

   public String getTheater_name() {
      return theater_name;
   }

   public void setTheater_name(String theater_name) {
      this.theater_name = theater_name;
   }

   public String getInfo() {
      StringBuffer sb = new StringBuffer();
      sb.append("\r\n");
      sb.append("[ "+ticket_number+ " ] 티켓의 정보====\n");
      sb.append("회원 ID : "+client_id+"\n");
      sb.append("영화 ID : "+movie_id+"\n");
      sb.append("인원 : "+people+"\n");

      return sb.toString();
   }

}