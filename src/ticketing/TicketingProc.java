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

import Auditorium.AuditoriumDAO;
import Auditorium.AuditoriumDTO;
import Auditorium.AuditoriumProc;
import Payment.PaymentDAO;
import Payment.PaymentDTO;
import Time_table.Time_tableDAO;
import Time_table.Time_tableDTO;
import movie.MovieProc;
import theater.TheaterDTO;

class showTime_seat {
	String show_time;
	int seat;

	public showTime_seat(String show_time, int seat) {
		this.show_time = show_time;
		this.seat = seat;
	}
}

public class TicketingProc {

	TicketingDAO dao;
	static Scanner scn = new Scanner(System.in);
	AuditoriumProc ap = new AuditoriumProc();

	public TicketingProc() {
		dao = new TicketingDAO();
	}

	public void insertTicketing(String client_id) {
		String selectAuditorium = "";
		String selectTheater = "";
		MovieProc mp = new MovieProc();

		System.out.println("예매를 시작합니다.");
		System.out.print("▶예매 가능한 영화 : ");
		mp.showMovieList_for_user();
		System.out.print("▶예매할 영화 이름 : ");
		String m_name = reInput();
		if (showTicketingTheater(m_name)) {
			System.out.print("▶ 선택할 영화관 : ");
			selectTheater = reInput();
			if (showTicketingAuditorium(selectTheater, m_name)) {
				while (true) {
					System.out.print("▶ 선택할 상영관 : ");
					selectAuditorium = reInput();
					if (confirmAuditorium(selectTheater, m_name, selectAuditorium) == true) {
						break;
					} else {
						System.out.println("이 상영관은 존재하지 않습니다.");
					}
				}
				showTicketingDate(selectAuditorium);
				System.out.println("▶ 날짜를 선택하시오.");
				System.out.print("▶ 년도를 입력하시오.(YYYY) : ");
				String year = reInput();
				System.out.print("▶ 월을 입력하시오.(MM) : ");
				String month = reInput();
				System.out.print("▶ 일을 입력하시오.(dd) : ");
				String day = reInput();
				String date = year + "-" + month + "-" + day;

				if (confirm_Date(selectAuditorium, date)) {
					Time_tableDAO tt_dao = new Time_tableDAO();
					Time_tableDTO tt_dto = tt_dao.getTime_table(selectAuditorium);
					String movie_id = tt_dto.getMovie_id();
					showTime_seat ss = showTicketingShow_time(selectAuditorium, month, day, movie_id);

					String twoDigit = findMaxNumber();
					String str_ticketNumber = month + day + ss.show_time + twoDigit;
					int int_ticketNumber = Integer.parseInt(str_ticketNumber);

					TicketingDTO dto = new TicketingDTO(client_id, movie_id, selectTheater, ss.seat, int_ticketNumber);
					boolean r = dao.insertTicketing(dto, client_id);

					if (r) {
						System.out.println("티켓 등록이 정상적으로 완료되었습니다.");

					} else {
						System.out.println("티켓 등록이 정상적으로 이루지지 않았습니다.");
					}

					PaymentDAO p_dao = new PaymentDAO();

					int payment_no = dao.makePayment_no();
					int price = ss.seat * 10000;
					String pay_option = "Not yet";
					int isPayed = 0;
					PaymentDTO dto2 = new PaymentDTO(payment_no, int_ticketNumber, price, pay_option, isPayed);
					boolean r2 = p_dao.insertPayment(dto2, 0);

					if (!r2) {
						System.out.println("결제 등록이 정상적으로 이루지지 않았습니다.");
					}

				}
			}
		}
	}

	// 저장된 영화관 목록 보기
	public boolean showTicketingTheater(String m_name) {

		List<TheaterDTO> list = dao.getTicketingTheater(m_name);
		System.out.println("                             Theater List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  이름 ");
			System.out.println("============================================================================");

			for (TheaterDTO dto : list) {
				System.out.println("\t" + dto.getTheater_name());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
		if (list.size() == 0) {
			System.out.println("해당하는 영화를 상영하는 영화관이 존재하지 않습니다.");
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
			System.out.println("reg.No\t  이름 ");
			System.out.println("============================================================================");

			for (AuditoriumDTO dto : list) {
				System.out.println("\t" + dto.getAuditorium_name());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");

		if (list.size() == 0) {
			System.out.println("해당 이름의 영화관이 없거나, 해당 영화를 상영하는 상영관이 존재하지 않습니다.");
			return false;
		} else {
			return true;
		}
	}

	public boolean confirmAuditorium(String selectTheater, String selectMovie, String a_name) {
		List<AuditoriumDTO> list = dao.getTicketingAuditorium(selectTheater, selectMovie);

		if (list != null && list.size() > 0) {
			for (AuditoriumDTO dto : list) {
				if (dto.getAuditorium_name().equals(a_name)) {
					return true;
				}
			}

		}
		return false;
	}

	public void showTicketingDate(String selectedAuditorium) {

		Time_tableDTO dto = dao.getTicketingDate(selectedAuditorium);
		System.out.println("                             Date");
		System.out.println("============================================================================");
		if (dto != null) {
			System.out.println("reg.No\t  상영  시작 날짜\t\t상영 종료 날짜 ");
			System.out.println("============================================================================");
			System.out.println("\t" + dto.getStart_date() + "\t\t" + dto.getEnd_date());
		} else {
			System.out.println("저장된 데이터가 없습니다. ");
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
			System.out.println("잘못된 날짜를 입력하였습니다.");
			return false;
		}
	}

	public showTime_seat showTicketingShow_time(String selectedAuditorium, String month, String day, String movie_id) {
		List<Time_tableDTO> list = dao.getTicketingShow_time(selectedAuditorium);
		System.out.println("                             Show Time");
		System.out.println("============================================================================");
		System.out.println("reg.No\t  상영 시간\t\t좌석수");
		System.out.println("============================================================================");
		AuditoriumDAO a_dao = new AuditoriumDAO();

		for (Time_tableDTO dto : list) {
			System.out.print("\t" + dto.getShow_time() + "\t\t");
			System.out.println(a_dao.getAuditorium(selectedAuditorium).getSeat()
					- dao.getSeatSum(month, day, dto.getShow_time(), movie_id));
		}
		while (true) {
			System.out.print("▶ 선택할 상영 시간 : ");
			String show_time = reInput();
			System.out.print("▶ 예매할 인원 수 : ");
			int num = scn.nextInt();

			for (Time_tableDTO dto : list) {
				if (dto.getShow_time().equals(show_time) && num <= (a_dao.getAuditorium(selectedAuditorium).getSeat()
						- dao.getSeatSum(month, day, dto.getShow_time(), movie_id))) {
					showTime_seat ss = new showTime_seat(show_time, num);
					return ss;
				}
			}
			System.out.println("해당하는 상영시간이 없거나, 예매할 인원 수가 좌석 수 보다 많습니다.");
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
			System.out.println("reg.No\t  예매번호 \t\t고객ID\t\t영화ID\t\t영화관 이름\t\t인원");
			System.out.println("============================================================================");

			for (TicketingDTO dto : list) {
				System.out.println("\t" + dto.getTicket_number() + "\t\t" + dto.getClient_id() + "\t\t"
						+ dto.getMovie_id() + dto.getTheater_name() + dto.getPeople());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
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

	// 공백입력시 재입력
	public String reInput() {
		Scanner scn = new Scanner(System.in);
		String str = "";
		while (true) {
			str = scn.nextLine();
			if (!(str == null || str.trim().equals(""))) {
				break;
			} else {
				System.out.println("공백은 입력하실수없습니다. 올바른값을 입력해주세요!");
				System.out.print("▶");
			}
		}

		return str;
	}

	// 예매내역 출력
	public void printTicket(String id) {
		System.out.println("                                         예매 현황");
		System.out.println("============================================================================");
		System.out.println("예매 회원\t\t예매번호\t\t영화id\t\t영화관\t\t관람인원");
		System.out.println("============================================================================");

		dao.getTicketInfo(id);
		System.out.println("============================================================================");

	}

	// 티켓 발권
	public void getPrintTicket(String id) {
		printTicket(id);
		
		if (dao.regiCount == 0) {
			System.out.println("***발권 가능한 예매 내용이 없습니다.***");
		} else {
			System.out.print("발권 받으실 예매 번호를 입력하세요: ");
			int ticket_no = Integer.parseInt(reInput());

			dao.getPrintTicket(ticket_no);
		}
	}

	public void cancelArrangement(String id) {
		printTicket(id);
		
		if (dao.regiCount == 0) {
			System.out.println("취소할 예매 내역이 없습니다.");
		} else {
			System.out.println("예매 취소할 예매번호를 입력해주세요: ");
			int ticket_No = scn.nextInt();

			dao.deleteArrange(ticket_No);
		}
	}

}