package Client;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientManagement {

	public void client_management() {

		ClientProc cp = new ClientProc();
		boolean run = true;

		while (run) {
			System.out.println();
			System.out.println("================= ȸ�� ���� ���α׷� =================");
			System.out.println("1. �� ���  2. vip");
			System.out.println("3. �� ���   4. �� ����   5. �� ���� ����");
			System.out.println("6. �ڷ�");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("�޴��� �Է��ϼ��� : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num < 7)) { // 1~6���� ���ڰ� �ԷµǸ� ���� ���� �߻�
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-6] �޴��� �������ּ���!");
			}

			switch (num) {
			case 1:
				cp.showClientList();// ȸ�� ���
				break;
			case 2:
				cp.dao.setAndShow_vip();
				break;
			case 3:
				cp.insertClient(); // ȸ�� ���
				break;
			case 4:
				cp.showClientList();
				cp.deleteClient(); // ȸ�� ����
				break;
			case 5:
				cp.showClientList();
				cp.updateClient(); // ȸ�� ����
				break;
			case 6:
				System.out.println("�ڷΰ���");
				run = false;
			}
		}

	}

}
