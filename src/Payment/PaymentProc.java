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
		TicketingProc tp = new TicketingProc();
		TicketingDAO t_dao = new TicketingDAO();
		TicketingDTO t_dto = new TicketingDTO();
		tp.showTicketingList_for_pay(c_id);
		System.out.print("������ Ƽ�� ��ȣ�� �Է��� �ּ���. : ");
		int t_no = scn.nextInt();
		t_dto = t_dao.getTicketing(t_no);

		int payment_no = dao.makePayment_no();
		int price = payment_process(t_dto.getTicket_number());
		String pay_option = pay_option(price);
		int isPayed = 1;
		PaymentDTO dto = new PaymentDTO(payment_no, t_dto.getTicket_number(), price, pay_option, isPayed);
		boolean r = dao.insertPayment(dto, using_point);

		if (r) {
			System.out.println("Ƽ�� ���Ű� ���������� �Ϸ�Ǿ����ϴ�.");
		} else {
			System.out.println("Ƽ�� ���Ű� ���������� �̷����� �ʾҽ��ϴ�.");
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

		System.out.println("���� ������ " + people + "���̹Ƿ�" + "10000�� * " + people + "�� = �� " + price + "���Դϴ�.");

		if (point < 1000) {
			System.out.println("ȸ������ ����Ʈ : " + point + " (����Ʈ ����� 1000 ����Ʈ �̻��� ��� ����� �����մϴ�.)");
			final_price = dao.getFinal_price(ticket_no, 0);
		} else {
			System.out.println("ȸ������ ����Ʈ�� �� " + point + " ����Ʈ�Դϴ�.");
			System.out.print("����Ʈ�� ����Ͻðڽ��ϱ�? (Y/N) : ");

			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				
				while (true) {
					System.out.print("�󸶸� ����Ͻðڽ��ϱ�? : ");
					using_point = scn.nextInt();
					if (using_point > point || using_point > price) {
						System.out.println("�Է��Ͻ� ����Ʈ�� �����ϰ� �ִ� ����Ʈ���� ũ�ų� ���� ���ݺ��� Ů�ϴ�. �ٽ� �Է��� �ּ���.");
					} else {
						final_price = dao.getFinal_price(ticket_no, using_point);
						System.out.println("�� ������ 10000 * " + people + "�� - " + point + " ����Ʈ�̹Ƿ� �����ؾ� �� ������ "
								+ final_price + "�� �Դϴ�.");
						break;
					}
				}
			} else {
				final_price = dao.getFinal_price(ticket_no, 0);
				System.out.println("�� ������ 10000 * " + people + "���̹Ƿ� �����ؾ� �� ������ " + final_price + " �Դϴ�.");
			}
		}
		return final_price;
	}

	public String pay_option(int final_price) {
		int num = 0;
		System.out.println("1. ���ͳ� ����          2. ���� ����");
		System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
		System.out.print("���� ����� �������ּ���. : ");
		Scanner scn = new Scanner(System.in);

		try {
			num = scn.nextInt();
			if (!(num > 0 && num < 3)) { // 1~2���� ���ڰ� �ԷµǸ� ���� ���� �߻�
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-2] �޴��� �������ּ���!");
		}

		if (num == 1) {
			System.out.println("���ͳ� ������ �����մϴ�.");
			System.out.println("������ ī���ȣ�� �Է����ּ���.(- ����) : ");
			scn.nextInt();
			System.out.println("ī���� ��й�ȣ�� �Է����ּ���. : ");
			scn.nextInt();
			System.out.println("������ �Ϸ�Ǿ����ϴ�.");
			return "���ͳ� ����";

		} else {
			int c = 0;
			int c2 = 0;
			System.out.println("1. ī�� ����          2. ���� ����");
			System.out.print("���� ����� �������ּ���. : ");
			try {
				c = scn.nextInt();
				if (!(c > 0 && c < 3)) { // 1~2���� ���ڰ� �ԷµǸ� ���� ���� �߻�
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-2] �޴��� �������ּ���!");
			}

			switch (c) {
			case 1:
				System.out.println("ī�带 �޾ҽ��ϴ�.");
				System.out.println("���� �Ǿ����ϴ�. �̿����ּż� �����մϴ�.");
				break;
			case 2:
				System.out.println("������ �������ּ���. : ");
				while (true) {
					c2 = scn.nextInt();
					if (c2 < final_price) {
						System.out.println("�մ�, �����Ͻ� �ݾ��� �����մϴ�. �����ϼž��� �ݾ��� " + final_price + "�� �Դϴ�.");
						System.out.print("�ٽ� �������ּ��� : ");
					} else {
						System.out.println(c2 + "�� �޾ҽ��ϴ�.");
						if (c2 > final_price) {
							System.out.println("�Ž��� �� " + (c2 - final_price) + "���� �����ʽÿ�.");
						}
						System.out.println("������ �Ϸ�Ǿ����ϴ�. �����մϴ�.");
						break;
					}
				}
				break;
			}
			return "���� ����";
		}
	}

	public void showPaymentList() {

		List<PaymentDTO> list = dao.getPaymentList();

		System.out.println("                             Payment List");
		System.out.println("========================================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("���� \t  ���� ��ȣ \t\t���� ��ȣ\t\t���� ����\t\t���� ���\t\t���� ����");
			System.out.println(
					"============================================================================================");

			for (PaymentDTO dto : list) {
				System.out.println("\t" + dto.getPayment_no() + "\t\t" + dto.getTicket_no() + "\t\t" + dto.getPrice()
						+ "\t\t" + dto.getPay_option() + "\t\t" + dto.getIsPayed());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
	}

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

}
