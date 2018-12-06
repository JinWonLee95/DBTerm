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

import Auditorium.AuditoriumDTO;

public class Time_tableProc {

	Time_tableDAO dao;

	public Time_tableProc() {
		dao = new Time_tableDAO();
	}

	// ��ȭ�� ��� ó��
	public void insertTime_table(String a_name, String m_id) {
		String time = "";
		Scanner scn = new Scanner(System.in);
		while(true){
			System.out.println("�� ���� �ð��� �Է����ּ���.");
			System.out.print("���ð�(HHmm) : ");
			time = reInput();
			if(confirmTime_tableListByAuditorium(time, a_name) == true){
				System.out.println("�����ϴ� �� ���� �ð��Դϴ�.");
			}else{
				break;
			}
		}
		System.out.print("���� ���� ��¥ (YYYY-mm-dd): ");
		String start_date = reInput();
		System.out.print("���� ���� ��¥ (YYYY-mm-dd) : ");
		String end_date = reInput();
		Time_tableDTO dto = new Time_tableDTO(a_name, time, m_id, start_date, end_date);
		boolean r = dao.insertTime_table(dto);

		if (r) {
			System.out.println("�󿵽ð�ǥ�� ���������� �Ϸ�Ǿ����ϴ�.");
			while (true) {
				System.out.println("�� �ð��� �߰��Ͻðڽ��ϱ�?(Y/N)");
				String input = scn.nextLine();
				if (input.equalsIgnoreCase("y")) {
					String time2;
					while(true){
						System.out.println("�� ���� �ð��� �Է����ּ���.");
						System.out.print("���ð�(HHmm) : ");
						time2 = reInput();
						if(confirmTime_tableListByAuditorium(time2, a_name) == true){
							System.out.println("�����ϴ� �� ���� �ð��Դϴ�.");
						}else{
							break;
						}
					}
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

	public boolean confirmTime_tableListByAuditorium(String t_name, String a_name){
		List<Time_tableDTO> list = dao.getTime_tableListByAuditorium(a_name);
		if (list != null && list.size() > 0) {
			for (Time_tableDTO dto : list) {
				if(dto.getShow_time().equals(t_name)){
					return true;
				}
			}

		}
		return false;
	}
	
	// �����Է½� ���Է�
	public String reInput() {
		Scanner scn = new Scanner(System.in);
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
