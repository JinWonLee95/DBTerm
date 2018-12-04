package Payment;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import User.UserDAO;
import User.UserDTO;
import ticketing.TicketingDAO;
import ticketing.TicketingDTO;
import ticketing.TicketingProc;

public class PaymentProc {

	PaymentDAO dao;
	int using_point;

	public PaymentProc() {
		dao = new PaymentDAO();
	}

	public void insertPayment(String c_id) {
		Scanner scn = new Scanner(System.in);
		int p_no = showTicketingList_for_pay(c_id);
		int ticket_no = dao.getPayment(p_no).getTicket_no();
		int price = payment_process(ticket_no);
		String pay_option = pay_option(price);
		int isPayed = 1;
		PaymentDTO dto = new PaymentDTO(p_no, ticket_no, price, pay_option, isPayed);
		TicketingDAO t_dao = new TicketingDAO();
		TicketingDTO t_dto = t_dao.getTicketing(ticket_no);
		boolean r = dao.updatePayment(dto, p_no, using_point, t_dto.getPeople());

		if (r) {
			System.out.println("티켓 구매가 정상적으로 완료되었습니다.");
			dao.addPurchased(c_id, ticket_no);
		} else {
			System.out.println("티켓 구매가 정상적으로 이루지지 않았습니다.");
		}
	}

	public int payment_process(int ticket_no) {
		TicketingDAO t_dao = new TicketingDAO();
		TicketingDTO t_dto = t_dao.getTicketing(ticket_no);
		UserDAO u_dao = new UserDAO();
		UserDTO u_dto = u_dao.getUser_byTicketNo(ticket_no);

		int people = t_dto.getPeople();
		int price = 10000 * people;
		int point = u_dto.getUser_point();
		int final_price;

		Scanner scn = new Scanner(System.in);

		System.out.println("결제 가격은 " + people + "명이므로" + "10000원 * " + people + "명 = 총 " + price + "원입니다.");

		if (point < 1000) {
			System.out.println("회원님의 포인트 : " + point + " (포인트 사용은 1000 포인트 이상일 경우 사용이 가능합니다.)");
			final_price = dao.getFinal_price(ticket_no, 0);
		} else {
			System.out.println("회원님의 포인트는 총 " + point + " 포인트입니다.");
			System.out.print("포인트를 사용하시겠습니까? (Y/N) : ");

			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {

				while (true) {
					System.out.print("얼마를 사용하시겠습니까? : ");
					using_point = scn.nextInt();
					if (using_point > point || using_point > price) {
						System.out.println("입력하신 포인트가 보유하고 있는 포인트보다 크거나 결제 가격보다 큽니다. 다시 입력해 주세요.");
					} else if (using_point < 1000) {
						System.out.println("포인트는 1000 포인트 이상만 사용할 수 있습니다.");
					} else {
						final_price = dao.getFinal_price(ticket_no, using_point);
						System.out.println("총 가격은 10000 * " + people + "원 - " + using_point + " 포인트이므로 결제해야 할 가격은 "
								+ final_price + "원 입니다.");
						break;
					}
				}
			} else {
				final_price = dao.getFinal_price(ticket_no, 0);
				System.out.println("총 가격은 10000 * " + people + "원이므로 결제해야 할 가격은 " + final_price + " 입니다.");
			}
		}
		return final_price;
	}

	public String pay_option(int final_price) {
		int num = 0;
		System.out.println("1. 인터넷 결제          2. 현장 결제");
		System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
		System.out.print("결제 방법을 선택해주세요. : ");
		Scanner scn = new Scanner(System.in);

		try {
			num = scn.nextInt();
			if (!(num > 0 && num < 3)) { // 1~2외의 숫자가 입력되면 예외 강제 발생
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.out.println("입력된 값이 잘못되었습니다. [1-2] 메뉴늘 선택해주세요!");
		}

		if (num == 1) {
			System.out.println("인터넷 결제를 진행합니다.");
			System.out.println("결제할 카드번호를 입력해주세요.(- 없이) : ");
			scn.nextInt();
			System.out.println("카드의 비밀번호를 입력해주세요. : ");
			scn.nextInt();
			System.out.println("결제가 완료되었습니다.");
			return "Internet";

		} else {
			int c = 0;
			int c2 = 0;
			System.out.println("1. 카드 결제          2. 현금 결제");
			System.out.print("결제 방법을 선택해주세요. : ");
			try {
				c = scn.nextInt();
				if (!(c > 0 && c < 3)) { // 1~2외의 숫자가 입력되면 예외 강제 발생
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("입력된 값이 잘못되었습니다. [1-2] 메뉴늘 선택해주세요!");
			}

			switch (c) {
			case 1:
				System.out.println("카드를 받았습니다.");
				System.out.println("결제 되었습니다. 이용해주셔서 감사합니다.");
				break;
			case 2:
				System.out.println("현금을 지불해주세요. : ");
				while (true) {
					c2 = scn.nextInt();
					if (c2 < final_price) {
						System.out.println("손님, 지불하신 금액이 부족합니다. 지불하셔야할 금액은 " + final_price + "원 입니다.");
						System.out.print("다시 지불해주세요 : ");
					} else {
						System.out.println(c2 + "원 받았습니다.");
						if (c2 > final_price) {
							System.out.println("거스름 돈 " + (c2 - final_price) + "원을 받으십시오.");
						}
						System.out.println("결제가 완료되었습니다. 감사합니다.");
						break;
					}
				}
				break;
			}
			return "on-site";
		}
	}

	public void showPaymentList() {

		List<PaymentDTO> list = dao.getPaymentList();

		System.out.println("                             Payment List");
		System.out.println("========================================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("구분 \t  결제 번호 \t\t예매 번호\t\t결제 가격\t\t결제 방법\t\t결제 여부");
			System.out.println(
					"============================================================================================");

			for (PaymentDTO dto : list) {
				System.out.println("\t" + dto.getPayment_no() + "\t\t" + dto.getTicket_no() + "\t\t" + dto.getPrice()
						+ "\t\t" + dto.getPay_option() + "\t\t" + dto.getIsPayed());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
	}

	public int showTicketingList_for_pay(String c_id) {
		Scanner scn = new Scanner(System.in);
		List<PaymentDTO> list = dao.getPaymentList_for_pay(c_id);

		System.out.println("                             Payment List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  결제 번호 \t\t예매 번호\t\t결제 가격\t\t결제 방법\t\t결제 여부");
			System.out.println("============================================================================");

			for (PaymentDTO dto : list) {
				if (dto.getIsPayed() != 1) {
					System.out.println("\t" + dto.getPayment_no() + "\t\t" + dto.getTicket_no() + "\t\t"
							+ dto.getPrice() + "\t\t" + dto.getPay_option() + "\t\t" + dto.getIsPayed());

				}
			}
			while (true) {
				System.out.print("구매할 결제 번호를 입력해 주세요. : ");
				int p_no = scn.nextInt();

				for (PaymentDTO dto : list) {
					if (p_no == dto.getPayment_no()) {
						return p_no;
					}
				}
				System.out.println("입력한 결제 번호가 존재하지 않습니다.");
			}
		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
		return -1;
	}

	public String reInput(Scanner scn) {

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

}
