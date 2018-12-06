package User;

import java.util.InputMismatchException;
import java.util.Scanner;

import Auditorium.AuditoriumManagement;
import Client.ClientManagement;
import Payment.PaymentProc;
import movie.MovieManagement;
import movie.MovieProc;
import theater.TheaterManagement;
import ticketing.TicketingProc;

public class UserManagement {

	private static String logIn_id;

	public static void main(String[] args) {

		UserProc mm = new UserProc();
		boolean run = true;
		
		while (run) {
			System.out.println();
			System.out.println("============== 영화 예매 콘솔에 오신 것을 환영합니다! ==============");
			System.out.println("1. 로그인		2. 회원가입");
			System.out.println("3. 종료");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("메뉴를 입력하세요 : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num < 4)) { // 1~3 외의 숫자가 입력되면 예외 강제 발생
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("입력된 값이 잘못되었습니다. [1-3] 메뉴늘 선택해주세요!");
			}

			switch (num) {
			case 1:
				int r = mm.loginUser();
				if (r == 1) { // 관리자
					manager_view();
				} else if (r == 0) {
					logIn_id = mm.getLoggedId();
					user_view();
				}
				break;
			case 2:
				mm.SignUpUser();
				break;
			case 3:
				System.out.println("프로그램을 종료합니다.");
				run=false;
				break;
			}
		}

	}

	public static void manager_view() {

		AuditoriumManagement am = new AuditoriumManagement();
		TheaterManagement tm = new TheaterManagement();
		MovieManagement mm = new MovieManagement();
		ClientManagement cm = new ClientManagement();
		boolean run_manager = true;

		while (run_manager) {
			System.out.println();
			System.out.println("=================================");
			System.out.println("1.영화관");
			System.out.println("2.상영관");
			System.out.println("3.영화");
			System.out.println("4.회원");
			System.out.println("5.로그아웃");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("메뉴를 입력하세요 : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num <= 5)) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("입력된 값이 잘못되었습니다. [1-5] 메뉴늘 선택해주세요!");
			}

			switch (num) {
			case 1:
				tm.theater_management();
				break;
			case 2:
				am.auditorium_management();
				break;
			case 3:
				mm.movie_management();
				break;
			case 4:
				cm.client_management();
				break;
			case 5:
				System.out.println("로그아웃 되었습니다.");
				run_manager = false;
			}
		}
	}

	public static void user_view() {

		UserProc mm = new UserProc();
		MovieProc mp = new MovieProc();
		TicketingProc tp = new TicketingProc();
		PaymentProc pp = new PaymentProc();
		boolean run_User = true;

		while (run_User) {
			System.out.println();
			System.out.println("=================================");
			System.out.println("1.회원정보수정");
			System.out.println("2.회원 탈퇴");
			System.out.println("3.영화 검색");
			System.out.println("4.영화 예약");
			System.out.println("5.영화 결제");
			System.out.println("6.예약 현황 확인");
			System.out.println("7.예약 취소");
			System.out.println("8.티켓 발권");
			System.out.println("9.로그아웃");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("메뉴를 입력하세요 : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num < 10)) { // 1~7 외의 숫자가 입력되면 예외 강제 발생
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("입력된 값이 잘못되었습니다. [1-8] 메뉴늘 선택해주세요!");
			}

			switch (num) {
			case 1:
				mm.updateUser();
				break;
			case 2:
				boolean r = mm.deleteUser();
				if (r == true) {
					run_User = false;
					break;
				} else {
					break;
				}
			case 3:
				mp.showMovieList_for_user();
				break;
			case 4:
				tp.insertTicketing(logIn_id);
				break;
			case 5:
				pp.insertPayment(logIn_id);
				break;
			case 6:
				tp.printTicket(logIn_id);
				break;
			case 7:
				tp.cancelArrangement(logIn_id);
				break;
			case 8:
				tp.getPrintTicket(logIn_id);
				break;
			case 9:
				System.out.println("로그아웃 되었습니다.");
				run_User = false;
			}
		}
	}
}
