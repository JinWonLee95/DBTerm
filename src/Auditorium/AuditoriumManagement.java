package Auditorium;
import java.util.InputMismatchException;
import java.util.Scanner;

import Time_table.Time_tableProc;
import theater.TheaterManagement;
import theater.TheaterProc;


public class AuditoriumManagement {	
	
	public void auditorium_management() {
		
		AuditoriumProc mm = new AuditoriumProc();
		Time_tableProc tp = new Time_tableProc();
		boolean run = true;
		
		while (run) {
			System.out.println();
			System.out.println("============== �󿵰� ���� ���α׷� ==============");
			System.out.println("1. �󿵰� ���   2. �󿵰� ���");			
			System.out.println("3. �󿵰� ����   4. �ڷ�");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("�޴��� �Է��ϼ��� : ");
			
			Scanner scn = new Scanner(System.in);
			Scanner scn2 = new Scanner(System.in);
			int num=0;
			try {
				num = scn.nextInt();
				if(!(num>0 && num<5)){ //1~5���� ���ڰ� �ԷµǸ� ���� ���� �߻�
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-4] �޴��� �������ּ���!");
			}
			
			switch (num) {
			case 1:
				mm.showAuditoriumList();//��ȭ�� ���	
				break;
			case 2:
				new TheaterProc().showTheaterList();
				System.out.print("�󿵰��� �߰��� ��ȭ�� �̸��� �Է����ּ���. : ");
				String t_name = scn2.nextLine();
				mm.insertAuditorium(t_name); //��ȭ�� ���
				break;
			case 3:
				mm.showAuditoriumList();
				mm.deleteAuditorium(); //��ȭ�� ����				
				break;
			case 4:
				System.out.println("�ڷΰ���");
				run = false;			
			}
		}	
		
	}
	
}
