package ticketing;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import Auditorium.AuditoriumDTO;
import Time_table.Time_tableDAO;
import Time_table.Time_tableDTO;
import movie.MovieProc;
import theater.TheaterDTO;

public class TicketingProc {

   TicketingDAO dao;
   static Scanner scn = new Scanner(System.in);

   public TicketingProc() {
      dao = new TicketingDAO();
   }

   // ��ȭ�� ��� ó��
   public void insertTicketing(String client_id) {

      MovieProc mp = new MovieProc();

      System.out.println("���Ÿ� �����մϴ�.");
      System.out.print("������ ������ ��ȭ : ");
      mp.showMovieList_for_user();
      System.out.print("�������� ��ȭ �̸� : ");
      String m_name = reInput(scn);
      if (showTicketingTheater(m_name)) {
         System.out.print("�� ������ ��ȭ�� : ");
         String selectTheater = reInput(scn);
         if (showTicketingAuditorium(selectTheater, m_name)) {
            System.out.print("�� ������ �󿵰� : ");
            String selectAuditorium = reInput(scn);
            showTicketingDate(selectAuditorium);
            System.out.println("�� ��¥�� �����Ͻÿ�.");
            System.out.print("�� �⵵�� �Է��Ͻÿ�. : ");
            String year = reInput(scn);
            System.out.print("�� ���� �Է��Ͻÿ�. : ");
            String month = reInput(scn);
            System.out.print("�� ���� �Է��Ͻÿ�. : ");
            String day = reInput(scn);
            String date = year + "-" + month + "-" + day;
            
            if (confirm_Date(selectAuditorium, date)) {
               showTicketingShow_time(selectAuditorium);
               //�� �Լ����� �¼� ���� �����ִ� �� ���߿� ����
               System.out.print("�� ������ �� �ð� : ");
               String show_time = reInput(scn);
               System.out.print("�������� �ο� �� : ");
               int num = scn.nextInt();
               
               Time_tableDAO tt_dao = new Time_tableDAO();
               Time_tableDTO tt_dto = tt_dao.getTime_table(selectAuditorium);
               String movie_id = tt_dto.getMovie_id();
               String twoDigit = findMaxNumber();
               String str_ticketNumber = month + day + show_time + twoDigit;
               int int_ticketNumber = Integer.parseInt(str_ticketNumber);

               TicketingDTO dto = new TicketingDTO(client_id, movie_id, selectTheater,
                     num, int_ticketNumber);
      
               boolean r = dao.insertTicketing(dto);

               if (r) {
                  System.out.println("Ƽ�� ����� ���������� �Ϸ�Ǿ����ϴ�.");
               
               } else {
                  System.out.println("Ƽ�� ����� ���������� �̷����� �ʾҽ��ϴ�.");
               }
            }
         }
      }

      /*
       * TicketingDTO dto = new TicketingDTO(client_id, m_name, num,); boolean
       * r = dao.insertTicketing(dto);
       * 
       * if(r){ System.out.println("��ȭ�� ����� ���������� �Ϸ�Ǿ����ϴ�."); }else{
       * System.out.println("��ȭ�� ����� ���������� �̷����� �ʾҽ��ϴ�."); }
       */
   }

   // ����� ��ȭ�� ��� ����
   public boolean showTicketingTheater(String m_name) {

      List<TheaterDTO> list = dao.getTicketingTheater(m_name);
      System.out.println("                             Theater List");
      System.out.println("============================================================================");
      if (list != null && list.size() > 0) {
         System.out.println("reg.No\t  �̸� ");
         System.out.println("============================================================================");

         for (TheaterDTO dto : list) {
            System.out.println("\t" + dto.getTheater_name());
         }

      } else {
         System.out.println("����� �����Ͱ� �����ϴ�. ");
      }
      System.out.println("====================================================================�� "
            + ((list == null) ? "0" : list.size()) + "��=\n");
      if (list.size() == 0) {
         System.out.println("�ش� �̸��� ��ȭ�� ���ϴ� ��ȭ���� �������� �ʽ��ϴ�.");
         return false;
      } else {
         return true;
      }
   }

   public boolean showTicketingAuditorium(String selectTheater, String selectMovie) {

      List<AuditoriumDTO> list = dao.getTicketingAuditorium(selectTheater, selectMovie);
      Scanner scn = new Scanner(System.in);
      System.out.println("                             Auditorium List");
      System.out.println("============================================================================");
      if (list != null && list.size() > 0) {
         System.out.println("reg.No\t  �̸� ");
         System.out.println("============================================================================");

         for (AuditoriumDTO dto : list) {
            System.out.println("\t" + dto.getAuditorium_name());
         }

      } else {
         System.out.println("����� �����Ͱ� �����ϴ�. ");
      }
      System.out.println("====================================================================�� "
            + ((list == null) ? "0" : list.size()) + "��=\n");

      if (list.size() == 0) {
         System.out.println("�ش� ��ȭ�� ���ϴ� �󿵰��� �������� �ʽ��ϴ�.");
         return false;
      } else {
         return true;
      }
   }

   public void showTicketingDate(String selectedAuditorium) {

      Time_tableDTO dto = dao.getTicketingDate(selectedAuditorium);
      System.out.println("                             Date");
      System.out.println("============================================================================");
      if (dto != null) {
         System.out.println("reg.No\t  ��  ���� ��¥\t\t�� ���� ��¥ ");
         System.out.println("============================================================================");
         System.out.println("\t" + dto.getStart_date() + "\t\t" + dto.getEnd_date());
      } else {
         System.out.println("����� �����Ͱ� �����ϴ�. ");
      }
   }

   public boolean confirm_Date(String selectedAuditorium, String date) {

      Time_tableDTO dto = dao.getTicketingDate(selectedAuditorium);

      Date d = transformDate(date);
      int compare1 = transformDate(dto.getStart_date()).compareTo(d);
      int compare2 = transformDate(dto.getEnd_date()).compareTo(d);
      if (compare1 <= 0 && compare2 >= 0) {
         return true;
      } else {
         System.out.println("�߸��� ��¥�� �Է��Ͽ����ϴ�.");
         return false;
      }
   }

   public void showTicketingShow_time(String selectedAuditorium) {
      List<Time_tableDTO> list = dao.getTicketingShow_time(selectedAuditorium);
      System.out.println("                             Show Time");
      System.out.println("============================================================================");
      System.out.println("reg.No\t  �� �ð�");
      System.out.println("============================================================================");

      for (Time_tableDTO dto2 : list) {
         System.out.println("\t" + dto2.getShow_time());
      }

   }

   public Date transformDate(String date) {
      SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-MM-dd");
      java.util.Date tempDate = null;
      try {
         tempDate = textFormat.parse(date);
      } catch (ParseException e) {
         e.printStackTrace();
      }

      String transDate = afterFormat.format(tempDate);
      Date d = Date.valueOf(transDate);
      return d;
   }

   public void showTicketingList() {

      List<TicketingDTO> list = dao.getTicketingList();

      System.out.println("                             Ticketing List");
      System.out.println("============================================================================");
      if (list != null && list.size() > 0) {
         System.out.println("reg.No\t  ���Ź�ȣ \t\t��ID\t\t��ȭID\t\t��ȭ�� �̸�\t\t�ο�");
         System.out.println("============================================================================");

         for (TicketingDTO dto : list) {
            System.out.println("\t" + dto.getTicket_number() + "\t\t" + dto.getClient_id() + "\t\t"
                  + dto.getMovie_id() + dto.getTheater_name() + dto.getPeople());
         }

      } else {
         System.out.println("����� �����Ͱ� �����ϴ�. ");
      }
      System.out.println("====================================================================�� "
            + ((list == null) ? "0" : list.size()) + "��=\n");
   }

   public String findMaxNumber() {

      List<TicketingDTO> list = dao.getTicketingList();
      List<Integer> numbers = new ArrayList<>();

      if (list != null && list.size() > 0) {
         for (TicketingDTO dto : list) {
            int temp = dto.getTicket_number() % 100;
            numbers.add(temp);
         }
         int m = Collections.max(numbers);
         int length = (int) (Math.log10(m) + 1);
         if (length == 2) {
            return Integer.toString(m + 1);
         } else {
            if (m == 9) {
               return "10";
            } else {
               return "0" + Integer.toString(m + 1);
            }
         }
      } else {
         return "01";
      }
   }

   public void deleteTicketing() {

      System.out.print("������ Ƽ���� ��ȣ�� �Է����ּ��� : ");
      int t_number = scn.nextInt();
      TicketingDTO dto = dao.getTicketing(t_number);
      if (dto != null) {
         System.out.println(dto.getInfo());

         System.out.println("�� Ƽ���� ������ �����Ͻðڽ��ϱ�?(Y/N)");
         String input = scn.nextLine();
         if (input.equalsIgnoreCase("y")) {
            boolean r = dao.deleteTicketing(t_number);

            if (r) {
               System.out.println(t_number + "Ƽ���� ���������� �����Ǿ����ϴ�.");
            } else {
               System.out.println("Ƽ���� ���������� ���� ���� �ʾҽ��ϴ�.");
            }
         } else {
            System.out.println("���� �۾��� ����Ͽ����ϴ�.");
         }
      } else {

         System.out.println("�Է��Ͻ� Ƽ�� ��ȣ�� �ش��ϴ� Ƽ���� �������� �ʽ��ϴ�.");
      }
   }

   // �����Է½� ���Է�
   public String reInput(Scanner scn) {

      String str = "";
      while (true) {
         str = scn.nextLine();
         if (!(str == null || str.trim().equals(""))) {
            break;
         } else {
            System.out.println("������ �Է��ϽǼ������ϴ�. �ùٸ����� �Է����ּ���!");
            System.out.print("��");
         }
      }

      return str;
   }
   
   // ���ų��� ���
   public void printTicket(String id) {
	   System.out.println("                                         ���� ��Ȳ");
	   System.out.println("============================================================================");
	   System.out.println("���� ȸ��\t\t���Ź�ȣ\t\t��ȭid\t\t��ȭ��\t\t�����ο�");
	   System.out.println("============================================================================");

	   dao.getTicketInfo(id);
	   System.out.println("============================================================================");

   }
   
   public void cancelArrangement() {
	   System.out.println("���� ����� ���Ź�ȣ�� �Է����ּ���: ");
	   int ticket_No = scn.nextInt();
	   
	   dao.deleteArrange(ticket_No);
   }
   
}