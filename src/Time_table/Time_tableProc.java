package Time_table;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Time_tableProc {

	Time_tableDAO dao;

	public Time_tableProc() {
		dao = new Time_tableDAO();
	}

	// ��ȭ�� ��� ó��
	public void insertTime_table(String a_name, String m_id) {
		Scanner scn = new Scanner(System.in);
		System.out.println("�� �ð��� �Է����ּ���.");
		System.out.print("���ð� : ");
		String time = reInput(scn);
		System.out.print("���� ���� ��¥ : ");
		String start_date = reInput(scn);
		System.out.print("���� ���� ��¥ : ");
		String end_date = reInput(scn);
		Time_tableDTO dto = new Time_tableDTO(a_name, time, m_id, start_date, end_date);
		boolean r = dao.insertTime_table(dto);

		if (r) {
			System.out.println("�󿵽ð�ǥ�� ���������� �Ϸ�Ǿ����ϴ�.");
			while (true) {
				System.out.println("�� �ð��� �߰��Ͻðڽ��ϱ�?(Y/N)");
				String input = scn.nextLine();
				if (input.equalsIgnoreCase("y")) {
					System.out.print("���ð� : ");
					String time2 = reInput(scn);
					Time_tableDTO dto2 = new Time_tableDTO(a_name, time2, m_id, start_date, end_date);
					boolean r2 = dao.insertTime_table(dto2);

					if (r2) {
						System.out.println("�󿵽ð�ǥ�� ���������� �Ϸ�Ǿ����ϴ�.");
					} else {
						System.out.println("�󿵽ð�ǥ�� ���������� �̷����� �ʾҽ��ϴ�.");
					}
				} else {
					System.out.println("�� �ð�ǥ �۾��� ����Ͽ����ϴ�.");
					break;
				}
			}
		} else {
			System.out.println("�󿵽ð�ǥ�� ���������� �̷����� �ʾҽ��ϴ�.");
		}
	}

	public boolean showTime_tableExist(String a_name) {
		if(dao.showTime_tableExist(a_name)){
			return true;
		}
		return false;
		
	}

	// ����� ��ȭ�� ��� ����
	public void showTime_tableList() {

		List<Time_tableDTO> list = dao.getTime_tableList();

		System.out.println("                           Time table List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  �󿵰� \t\t�� �ð�\t\t��ȭID\t\t�� ���� ��¥\t\t�� ���� ��¥");
			System.out.println("============================================================================");

			for (Time_tableDTO dto : list) {
				System.out.println("\t" + dto.getAuditorium_name() + "\t\t" + dto.getShow_time() + "\t\t"
						+ dto.getMovie_id() + "\t\t" + dto.getStart_date() + "\t\t" + dto.getEnd_date());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
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

}
