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
			System.out.println("3. 뒤로");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("메뉴를 입력하세요 : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num < 4)) { // 1~6외의 숫자가 입력되면 예외 강제 발생
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("입력된 값이 잘못되었습니다. [1-3] 메뉴늘 선택해주세요!");
			}

			switch (num) {
			case 1:
				cp.showClientList();// 회원 목록
				break;
			case 2:
				cp.dao.setAndShow_vip();
				break;
			case 3:
				System.out.println("뒤로가기");
				run = false;
			}
		}

	}

}
