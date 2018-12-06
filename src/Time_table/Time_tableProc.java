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

	// 영화관 등록 처리
	public void insertTime_table(String a_name, String m_id) {
		String time = "";
		Scanner scn = new Scanner(System.in);
		while(true){
			System.out.println("상영 시작 시간을 입력해주세요.");
			System.out.print("▶시간(HHmm) : ");
			time = reInput();
			if(confirmTime_tableListByAuditorium(time, a_name) == true){
				System.out.println("존재하는 상영 시작 시간입니다.");
			}else{
				break;
			}
		}
		System.out.print("▶상영 시작 날짜 (YYYY-mm-dd): ");
		String start_date = reInput();
		System.out.print("▶상영 종료 날짜 (YYYY-mm-dd) : ");
		String end_date = reInput();
		Time_tableDTO dto = new Time_tableDTO(a_name, time, m_id, start_date, end_date);
		boolean r = dao.insertTime_table(dto);

		if (r) {
			System.out.println("상영시간표가 정상적으로 완료되었습니다.");
			while (true) {
				System.out.println("상영 시간을 추가하시겠습니까?(Y/N)");
				String input = scn.nextLine();
				if (input.equalsIgnoreCase("y")) {
					String time2;
					while(true){
						System.out.println("상영 시작 시간을 입력해주세요.");
						System.out.print("▶시간(HHmm) : ");
						time2 = reInput();
						if(confirmTime_tableListByAuditorium(time2, a_name) == true){
							System.out.println("존재하는 상영 시작 시간입니다.");
						}else{
							break;
						}
					}
					Time_tableDTO dto2 = new Time_tableDTO(a_name, time2, m_id, start_date, end_date);
					boolean r2 = dao.insertTime_table(dto2);

					if (r2) {
						System.out.println("상영시간표가 정상적으로 완료되었습니다.");
					} else {
						System.out.println("상영시간표가 정상적으로 이루지지 않았습니다.");
					}
				} else {
					System.out.println("상영 시간표 작업을 취소하였습니다.");
					break;
				}
			}
		} else {
			System.out.println("상영시간표가 정상적으로 이루지지 않았습니다.");
		}
	}

	public boolean showTime_tableExist(String a_name) {
		if(dao.showTime_tableExist(a_name)){
			return true;
		}
		return false;
		
	}

	// 저장된 영화관 목록 보기
	public void showTime_tableList() {

		List<Time_tableDTO> list = dao.getTime_tableList();

		System.out.println("                           Time table List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  상영관 \t\t상영 시간\t\t영화ID\t\t상영 시작 날짜\t\t상영 종료 날짜");
			System.out.println("============================================================================");

			for (Time_tableDTO dto : list) {
				System.out.println("\t" + dto.getAuditorium_name() + "\t\t" + dto.getShow_time() + "\t\t"
						+ dto.getMovie_id() + "\t\t" + dto.getStart_date() + "\t\t" + dto.getEnd_date());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
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

}
