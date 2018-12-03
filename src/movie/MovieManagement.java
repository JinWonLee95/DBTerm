package movie;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MovieManagement {	
	
	public void movie_management() {
		
		MovieProc mm = new MovieProc();
		boolean run = true;
		
		while (run) {
			System.out.println();
			System.out.println("============== 영화 관리 프로그램 ==============");
			System.out.println("1. 영화 목록");			
			System.out.println("2. 영화 등록   3. 영화 삭제   4. 영화 정보 수정");
			System.out.println("5. 뒤로");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("메뉴를 입력하세요 : ");
			
			Scanner scn = new Scanner(System.in);
			int num=0;
			try {
				num = scn.nextInt();
				if(!(num>0 && num<6)){ //1~5외의 숫자가 입력되면 예외 강제 발생
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("입력된 값이 잘못되었습니다. [1-5] 메뉴늘 선택해주세요!");
			}
			
			switch (num) {
			case 1:
				mm.showMovieList_for_manager();//영화 목록		
				break;
			case 2:
				mm.insertMovie(); //영화관 등록
				break;
			case 3:
				mm.showMovieList_for_manager();
				mm.deleteMovie(); //영화관 삭제				
				break;
			case 4:
				mm.showMovieList_for_manager();
				mm.updateMovie(); //영화관 수정
				break;
			case 5:
				System.out.println("뒤로가기");
				run = false;		
			}
		}	
		
	}
	
}
