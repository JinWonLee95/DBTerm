package Auditorium;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class AuditoriumProc {

	AuditoriumDAO dao;
	String a = "";
	Scanner scn = new Scanner(System.in);

	public AuditoriumProc() {
		dao = new AuditoriumDAO();
	}

	// 영화관 등록 처리
	public void insertAuditorium(String t_name) {

		System.out.println("상영관 정보를 입력해주세요.");
		System.out.print("▶이름 : ");
		String a_name = reInput();
		System.out.print("▶좌석 수 : ");
		int seat = scn.nextInt();
		a = a_name;
		AuditoriumDTO dto = new AuditoriumDTO(a_name, seat, t_name);
		boolean r = dao.insertAuditorium(dto);

		if (r) {
			System.out.println("상영관 등록이 정상적으로 완료되었습니다.");
		} else {
			System.out.println("상영관 등록이 정상적으로 이루지지 않았습니다.");
		}

	}

	// 저장된 영화관 목록 보기
	public void showAuditoriumList() {

		List<AuditoriumDTO> list = dao.getAuditoriumList();

		System.out.println("                             Auditorium List");
		System.out.println("============================================================================");

		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  상영관 이름 \t\t좌석 수\t\t영화관 이름");
			System.out.println("============================================================================");

			for (AuditoriumDTO dto : list) {
				System.out.println(
						"\t" + dto.getAuditorium_name() + "\t\t" + dto.getSeat() + "\t\t" + dto.getTheater_name());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}

		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");

	}

	public void showAuditoriumListByTheater(String t_name) {

		List<AuditoriumDTO> list = dao.getAuditoriumListByTheater(t_name);

		System.out.println("                             Auditorium List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  상영관 이름 \t\t좌석 수\t\t영화관 이름");
			System.out.println("============================================================================");

			for (AuditoriumDTO dto : list) {
				System.out.println(
						"\t" + dto.getAuditorium_name() + "\t\t" + dto.getSeat() + "\t\t" + dto.getTheater_name());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
	}
	
	public boolean confirmAuditoriumListByTheater(String t_name, String a_name){
		List<AuditoriumDTO> list = dao.getAuditoriumListByTheater(t_name);
		if (list != null && list.size() > 0) {
			for (AuditoriumDTO dto : list) {
				if(dto.getAuditorium_name().equals(a_name)){
					return true;
				}
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		return false;
	}

	// 영화관 삭제
	public void deleteAuditorium() {

		System.out.print("삭제할 상영관의 상영관 이름을 입력해주세요 : ");
		String a_name = scn.nextLine();
		AuditoriumDTO dto = dao.getAuditorium(a_name);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("위 상영관의 정보를 정말로 삭제하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteAuditorium(a_name);

				if (r) {
					System.out.println(a_name + "상영관의 정보가 정상적으로 삭제되었습니다.");
				} else {
					System.out.println("상영관의 정보가 정상적으로 삭제 되지 않았습니다.");
				}
			} else {
				System.out.println("삭제 작업을 취소하였습니다.");
			}
		} else {

			System.out.println("입력하신 상영관 이름에 해당하는 상영관이 존재하지 않습니다.");
		}
	}

	// 공백입력시 재입력
	public String reInput() {

		String str;
		Scanner scn = new Scanner(System.in);
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
