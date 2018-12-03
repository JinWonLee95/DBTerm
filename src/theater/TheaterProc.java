package theater;

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

import Auditorium.AuditoriumProc;

public class TheaterProc {

	TheaterDAO dao;

	public TheaterProc() {
		dao = new TheaterDAO();
	}

	// 영화관 등록 처리
	public void insertTheater() {

		Scanner scn = new Scanner(System.in);
		AuditoriumProc ap = new AuditoriumProc();
		System.out.println("영화관 정보를 입력해주세요.");
		System.out.print("▶이름 : ");
		String name = reInput(scn);
		System.out.print("▶주소 : ");
		String address = reInput(scn);
		System.out.print("▶전화번호 : ");
		String number = reInput(scn);

		TheaterDTO dto = new TheaterDTO(name, address, number);
		boolean r = dao.insertTheater(dto);

		if (r) {
			System.out.println("영화관 등록이 정상적으로 완료되었습니다.");
			System.out.println(name + " 영화관에 상영관을 추가하십시오.");
			ap.insertAuditorium(name);
			
			while (true) {
				System.out.print("상영관을 더 추가하시겠습니까?(Y/N) :");
				String input = scn.nextLine();
				if (input.equalsIgnoreCase("y")) {
					ap.insertAuditorium(name);
				} else {
					System.out.println("상영관 추가 작업을 취소하였습니다.");
					break;
				}
			}
		} else {
			System.out.println("영화관 등록이 정상적으로 이루지지 않았습니다.");
		}

	}

	// 저장된 영화관 목록 보기
	public void showTheaterList() {

		List<TheaterDTO> list = dao.getTheaterList();

		System.out.println("                             Theater List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  이름 \t\t주소\t\t연락처");
			System.out.println("============================================================================");

			for (TheaterDTO dto : list) {
				System.out.println("\t" + dto.getTheater_name() + "\t\t" + dto.getTheater_address() + "\t\t"
						+ dto.getTheater_number());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
	}

	// 영화관 수정
	public void updateTheater() {

		Scanner scn = new Scanner(System.in);
		System.out.println("수정할 영화관의 영화관 이름을 입력해주세요 : ");
		System.out.print("▶");
		String t_name = scn.nextLine();
		TheaterDTO dto = dao.getTheater(t_name);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("수정작업을 계속하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				System.out.println("##입력을 하시지않으면 기존의 정보가 그대로 유지됩니다.");
				System.out.print("▶수정할 이름 : ");
				String name = scn.nextLine();
				if (name.trim().equals(""))
					name = dto.getTheater_name();
				System.out.print("▶수정할 주소 : ");
				String address = scn.nextLine();
				if (address.trim().equals(""))
					address = dto.getTheater_address();
				System.out.print("▶수정할 전화번호 : ");
				String number = scn.nextLine();
				if (number.trim().equals(""))
					number = dto.getTheater_number();

				dto = new TheaterDTO(name, address, number);

				boolean r = dao.updateTheater(dto, t_name);
				if (r) {
					System.out.println("영화관의 정보가 다음과 같이 수정되었습니다.");
					System.out.println(dto.getInfo());
				} else {
					System.out.println("영화관의 정보가 정상적으로 수정 되지 않았습니다.");
				}
			} else {
				System.out.println("수정 작업을 취소하였습니다.");
			}
		} else {
			System.out.println("입력하신 영화관 이름에 해당하는 영화관이 존재하지 않습니다.");
		}
	}

	// 영화관 삭제
	public void deleteTheater() {

		Scanner scn = new Scanner(System.in);
		System.out.print("삭제할 영화관의 영화관 이름을 입력해주세요 : ");
		String t_name = scn.nextLine();
		TheaterDTO dto = dao.getTheater(t_name);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("위 영화관의 정보를 정말로 삭제하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteTheater(t_name);

				if (r) {
					System.out.println(t_name + "영화관의 정보가 정상적으로 삭제되었습니다.");
				} else {
					System.out.println("영화관의 정보가 정상적으로 삭제 되지 않았습니다.");
				}
			} else {
				System.out.println("삭제 작업을 취소하였습니다.");
			}
		} else {

			System.out.println("입력하신 영화관 이름에 해당하는 영화관이 존재하지 않습니다.");
		}
	}

	// 공백입력시 재입력
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
