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
			System.out.println("============== 상영관 관리 프로그램 ==============");
			System.out.println("1. 상영관 목록   2. 상영관 등록");			
			System.out.println("3. 상영관 삭제   4. 뒤로");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("메뉴를 입력하세요 : ");
			
			Scanner scn = new Scanner(System.in);
			Scanner scn2 = new Scanner(System.in);
			int num=0;
			try {
				num = scn.nextInt();
				if(!(num>0 && num<5)){ //1~5외의 숫자가 입력되면 예외 강제 발생
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("입력된 값이 잘못되었습니다. [1-4] 메뉴늘 선택해주세요!");
			}
			
			switch (num) {
			case 1:
				mm.showAuditoriumList();//영화관 목록	
				break;
			case 2:
				new TheaterProc().showTheaterList();
				System.out.print("상영관을 추가할 영화관 이름을 입력해주세요. : ");
				String t_name = scn2.nextLine();
				mm.insertAuditorium(t_name); //영화관 등록
				break;
			case 3:
				mm.showAuditoriumList();
				mm.deleteAuditorium(); //영화관 삭제				
				break;
			case 4:
				System.out.println("뒤로가기");
				run = false;			
			}
		}	
		
	}
	
}
