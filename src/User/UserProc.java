package User;

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


public class UserProc {

	UserDAO dao;
	static String loggedId;

	public String getLoggedId() {
		return loggedId;
	}

	public void setLoggedId(String loggedId) {
		this.loggedId = loggedId;
	}

	public UserProc() {
		dao = new UserDAO();
	}

	// 영화관 등록 처리
	public void SignUpUser() {

		Scanner scn = new Scanner(System.in);

		System.out.println("고객의 정보를 입력해주세요.");
		System.out.print("▶아이디 : ");
		String client_id = reInput();
		System.out.print("▶비밀번호 : ");
		String client_password = reInput();
		System.out.print("▶이름 : ");
		String client_name = reInput();
		System.out.print("▶생일 : ");
		String client_birth = reInput();
		System.out.print("▶주소 : ");
		String client_address = reInput();
		System.out.print("▶전화번호 : ");
		String client_number = reInput();

		UserDTO dto = new UserDTO(client_id, client_password, client_name, client_birth, client_address, client_number,
				0, 0);
		boolean r = dao.signUpUser(dto);

		if (r) {
			System.out.println("회원 가입이 정상적으로 완료되었습니다.");
		} else {
			System.out.println("회원 가입이 정상적으로 이루지지 않았습니다.");
		}

	}

	public int loginUser() {
		Scanner scn = new Scanner(System.in);

		System.out.print("▶아이디 : ");
		String client_id = reInput();
		System.out.print("▶비밀번호 : ");
		String client_password = reInput();
		boolean r = dao.loginUser(client_id, client_password);
		
		System.out.println(client_id +" "+ client_password);
		
		if (client_id.equals("root") && client_password.equals("root")) {
			if (r) {
				System.out.println("관리자님 안녕하세요!");
				return 1;
				//관리자 로그인 1 리턴
			} else {
				System.out.println("로그인이 이루어지지 않았습니다.");
				return -1;
			}
		} else {
			if (r) {
				setLoggedId(client_id);
				System.out.println("로그인이 정상적으로 완료되었습니다.");
				return 0;
				//고객 로그인 0리턴
			} else {
				System.out.println("로그인이 이루어지지 않았습니다.");
				return -1;
			}
		}
	}

	public void updateUser() {

		Scanner scn = new Scanner(System.in);

		UserDTO dto = dao.getUser(getLoggedId());
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("수정작업을 계속하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				System.out.println("##입력을 하시지않으면 기존의 정보가 그대로 유지됩니다.");

				System.out.print("▶수정할 비밀번호 : ");
				String password = scn.nextLine();
				if (password.trim().equals(""))
					password = dto.getUser_password();
				System.out.print("▶수정할 이름 : ");
				String name = scn.nextLine();
				if (name.trim().equals(""))
					name = dto.getUser_name();
				System.out.print("▶수정할 생일 : ");
				String birth = scn.nextLine();
				if (birth.trim().equals(""))
					birth = dto.getUser_birth();
				System.out.print("▶수정할 주소 : ");
				String address = scn.nextLine();
				if (address.trim().equals(""))
					address = dto.getUser_address();
				System.out.print("▶수정할 전화번호 : ");
				String number = scn.nextLine();
				if (number.trim().equals(""))
					number = dto.getUser_number();

				String id = dto.getUser_id();
				int point = dto.getUser_point();
				int count = dto.getUser_purchase_count();

				dto = new UserDTO(id, password, name, birth, address, number, point, count);

				boolean r = dao.updateUser(dto, loggedId);
				if (r) {
					System.out.println("회원의 정보가 다음과 같이 수정되었습니다.");
					System.out.println(dto.getInfo());
				} else {
					System.out.println("회원의 정보가 정상적으로 수정 되지 않았습니다.");
				}
			} else {
				System.out.println("수정 작업을 취소하였습니다.");
			}
		}
	}

	public boolean deleteUser() {

		Scanner scn = new Scanner(System.in);

		UserDTO dto = dao.getUser(getLoggedId());
		if (dto != null) {
			System.out.println("정말로 회원 탈퇴를 하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteUser(loggedId);

				if (r) {
					System.out.println(loggedId + "회원탈퇴가 정상적으로 이루어졌습니다.");
					return true;
				} else {
					System.out.println("회원탈퇴가 이루어지지 않았습니다.");
				}
			} else {
				System.out.println("회원탈퇴 작업을 취소하였습니다.");
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
