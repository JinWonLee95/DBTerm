package movie;

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
import Auditorium.AuditoriumProc;
import Time_table.Time_tableProc;
import theater.TheaterDAO;
import theater.TheaterProc;

public class MovieProc {

	MovieDAO dao;

	public MovieProc() {
		dao = new MovieDAO();
	}

	// 영화관 등록 처리
	public void insertMovie() {

		Scanner scn = new Scanner(System.in);
		AuditoriumProc ap = new AuditoriumProc();
		TheaterProc thp = new TheaterProc();
		Time_tableProc tp = new Time_tableProc();
		TheaterDAO td = new TheaterDAO();
		String m_id = "";
		String t_name = "";
		String a_name = "";
		
		System.out.println("영화 정보를 입력해주세요.");
		while (true) {
			System.out.print("▶ 영화 id : ");
			m_id = reInput();
			if (dao.confirmMovie(m_id) == true){
				System.out.println("존재하는 영화 id입니다.");
			}else{
				break;
			}
		}
		System.out.print("▶ 이름 : ");
		String m_name = reInput();
		System.out.print("▶ 감독 : ");
		String director = reInput();
		System.out.print("▶ 출연 : ");
		String actor = reInput();
		System.out.print("▶ 등급 : ");
		String rating = reInput();
		System.out.print("▶ 정보 : ");
		String info = reInput();
		while (true) {
			thp.showTheaterList();
			System.out.print("▶ 영화관 이름: ");
			t_name = reInput();
			if (td.confirmTheater(t_name) == true){
				break;
			}else{
				System.out.println("이 영화관은 존재하지 않습니다.");
			}
		}
		
		while (true) {
			ap.showAuditoriumListByTheater(t_name);
			System.out.print("▶ 상영관 이름: ");
			a_name = reInput();
			if (ap.confirmAuditoriumListByTheater(t_name, a_name) == true){
				break;
			}else{
				System.out.println("이 상영관은 존재하지 않습니다.");
			}
		}
		if (tp.showTime_tableExist(a_name)) {
			System.out.println("이 상영관에는 이미 상영하는 영화가 있습니다.");
		} else {
			tp.insertTime_table(a_name, m_id);
			MovieDTO dto = new MovieDTO(m_id, m_name, director, actor, rating, info, 0, t_name);
			boolean r = dao.insertMovie(dto);

			if (r) {
				System.out.println("영화 등록이 정상적으로 완료되었습니다.");
			} else {
				System.out.println("영화 등록이 정상적으로 이루지지 않았습니다.");
			}
		}
	}

	// 저장된 영화관 목록 보기
	public void showMovieList_for_manager() {

		List<MovieDTO> list = dao.getMovieList_for_manager();

		System.out.println("                             Movie List");
		System.out.println("========================================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("구분 \t  id \t\t제목\t\t감독\t\t출연\t\t등급\t\t정보\t\t예매 횟수\t\t영화관");
			System.out.println(
					"============================================================================================");

			int order = 1;
			for (MovieDTO dto : list) {
				System.out.println(order + "위\t" + dto.getMovie_id() + "\t\t" + dto.getMovie_name() + "\t\t"
						+ dto.getMovie_director() + "\t\t" + dto.getMovie_actor() + "\t\t" + dto.getMovie_rating()
						+ "\t\t" + dto.getMovie_info() + "\t\t" + dto.getMovie_reserve_count() + "\t\t"
						+ dto.getTheater_name());
				order++;
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
	}

	public void showMovieList_for_user() {

		List<MovieDTO> list = dao.getMovieList_for_user();

		System.out.println("                             Movie List");
		System.out.println(
				"===========================================================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("차트\t  제목\t\t감독\t\t출연\t\t등급\t\t정보\t\t예매횟수");
			System.out.println(
					"==========================================================================================================");

			int order = 1;
			for (MovieDTO dto : list) {
				System.out.println(order + "위\t" + dto.getMovie_name() + "\t\t" + dto.getMovie_director() + "\t\t"
						+ dto.getMovie_actor() + "\t\t" + dto.getMovie_rating() + "\t\t" + dto.getMovie_info() + "\t\t"
						+ dto.getMovie_reserve_count());
				order++;
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개========================================\n");
	}

	// 영화관 수정
	public void updateMovie() {

		Scanner scn = new Scanner(System.in);
		System.out.println("수정할 영화의 영화 id를 입력해주세요 : ");
		System.out.print("▶");
		String m_id = scn.nextLine();
		MovieDTO dto = dao.getMovie(m_id);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("수정작업을 계속하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				System.out.println("##입력을 하시지않으면 기존의 정보가 그대로 유지됩니다.");
				String id = "";
				if (id.trim().equals(""))
					id = dto.getMovie_id();
				System.out.print("▶수정할 이름 : ");
				String name = scn.nextLine();
				if (name.trim().equals(""))
					name = dto.getMovie_name();
				System.out.print("▶수정할 감독 : ");
				String dir = scn.nextLine();
				if (dir.trim().equals(""))
					dir = dto.getMovie_director();
				System.out.print("▶수정할 출연 : ");
				String actor = scn.nextLine();
				if (actor.trim().equals(""))
					actor = dto.getMovie_actor();
				System.out.print("▶수정할 등급 : ");
				String rating = scn.nextLine();
				if (rating.trim().equals(""))
					rating = dto.getMovie_rating();
				System.out.print("▶수정할 정보 : ");
				String info = scn.nextLine();
				if (info.trim().equals(""))
					info = dto.getMovie_info();
				int count = dto.getMovie_reserve_count();
				String t_name = dto.getTheater_name();

				dto = new MovieDTO(id, name, dir, actor, rating, info, count, t_name);

				boolean r = dao.updateMovie(dto, m_id);
				if (r) {
					System.out.println("영화의 정보가 다음과 같이 수정되었습니다.");
					System.out.println(dto.getInfo());
				} else {
					System.out.println("영화의 정보가 정상적으로 수정 되지 않았습니다.");
				}
			} else {
				System.out.println("수정 작업을 취소하였습니다.");
			}
		} else {
			System.out.println("입력하신 영화 id에 해당하는 영화가 존재하지 않습니다.");
		}
	}

	// 영화관 삭제
	public void deleteMovie() {

		Scanner scn = new Scanner(System.in);
		System.out.print("삭제할 영화의 영화 id를 입력해주세요 : ");
		String m_id = reInput();
		MovieDTO dto = dao.getMovie(m_id);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("위 영화의 정보를 정말로 삭제하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteMovie(m_id);

				if (r) {
					System.out.println(m_id + "영화의 정보가 정상적으로 삭제되었습니다.");
				} else {
					System.out.println("영화의 정보가 정상적으로 삭제 되지 않았습니다.");
				}
			} else {
				System.out.println("삭제 작업을 취소하였습니다.");
			}
		} else {

			System.out.println("입력하신 영화 id에 해당하는 영화가 존재하지 않습니다.");
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

}
