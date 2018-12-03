package Client;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientManagement {

	public void client_management() {

		ClientProc cp = new ClientProc();
		boolean run = true;

		while (run) {
			System.out.println();
			System.out.println("================= 회원 관리 프로그램 =================");
			System.out.println("1. 고객 목록  2. vip");
			System.out.println("3. 고객 등록   4. 고객 삭제   5. 고객 정보 수정");
			System.out.println("6. 뒤로");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("메뉴를 입력하세요 : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num < 7)) { // 1~6외의 숫자가 입력되면 예외 강제 발생
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("입력된 값이 잘못되었습니다. [1-6] 메뉴늘 선택해주세요!");
			}

			switch (num) {
			case 1:
				cp.showClientList();// 회원 목록
				break;
			case 2:
				cp.dao.setAndShow_vip();
				break;
			case 3:
				cp.insertClient(); // 회원 등록
				break;
			case 4:
				cp.showClientList();
				cp.deleteClient(); // 회원 삭제
				break;
			case 5:
				cp.showClientList();
				cp.updateClient(); // 회원 수정
				break;
			case 6:
				System.out.println("뒤로가기");
				run = false;
			}
		}

	}

}
