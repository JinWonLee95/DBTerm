package Client;
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


public class ClientProc { 
	
	ClientDAO dao;
	
	public ClientProc() {
		dao = new ClientDAO();
	}
	

	//영화관 등록 처리 
	public void insertClient(){			
		
		Scanner scn = new Scanner(System.in);
		
		System.out.println("고객의 정보를 입력해주세요.");
		System.out.print("▶아이디 : ");
		String client_id = reInput(scn);
		System.out.print("▶비밀번호 : ");
		String client_password = reInput(scn);
		System.out.print("▶이름 : ");	
		String client_name = reInput(scn);
		System.out.print("▶생일 : ");	
		String client_birth = reInput(scn);
		System.out.print("▶주소 : ");	
		String client_address = reInput(scn);
		System.out.print("▶전화번호 : ");	
		String client_number = reInput(scn);
		
		ClientDTO dto = new ClientDTO(client_id, client_password,client_name,client_birth,client_address,client_number,0,0);		
		boolean r = dao.insertClient(dto); 
		
		if(r){
			System.out.println("회원 가입이 정상적으로 완료되었습니다.");
		}else{
			System.out.println("회원 가입이 정상적으로 이루지지 않았습니다.");
		}
		
	}	

	//
	public void showClientList(){
	
		List<ClientDTO> list = dao.getClientList();
		
		System.out.println("                             Client List");
		System.out.println("============================================================================");
		if(list!=null&&list.size()>0){			
			System.out.println("reg.No\t  id \t\t비밀번호\t\t이름\t\t생일\t\t주소\t\t전화번호\t\t포인트\t\t구매 횟수");
			System.out.println("============================================================================");
			
			for (ClientDTO dto : list){
				System.out.println("\t" + dto.getClient_id() + "\t\t"+ dto.getClient_password() + "\t\t"+ dto.getClient_name()+ "\t\t"+ dto.getClient_birth()+ "\t\t"+ dto.getClient_address()+ "\t\t"+ dto.getClient_number()+ "\t\t"+ dto.getClient_point()+ "\t\t"+ dto.getClient_purchase_count());
			}			
	
		}else{
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "+((list==null)?"0":list.size())+"개=\n");
	}
	
	

	//영화관 수정
	public void updateClient(){
		
		Scanner scn = new Scanner(System.in);
		System.out.println("수정할 회원의 아이디를 입력해주세요 : "); 
		System.out.print("▶");
		String c_id = scn.nextLine();
		ClientDTO dto = dao.getClient(c_id);
		if (dto!=null) {
			System.out.println(dto.getInfo());
			
			System.out.println("수정작업을 계속하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if(input.equalsIgnoreCase("y")){
				System.out.println("##입력을 하시지않으면 기존의 정보가 그대로 유지됩니다.");
				System.out.print("▶수정할 아이디 : ");
				String id = scn.nextLine();
				if(id.trim().equals("")) id=dto.getClient_id();
				System.out.print("▶수정할 비밀번호 : ");
				String password = scn.nextLine();
				if(password.trim().equals("")) password=dto.getClient_password();
				System.out.print("▶수정할 이름 : ");
				String name = scn.nextLine();
				if(name.trim().equals("")) name=dto.getClient_name();
				System.out.print("▶수정할 생일 : ");
				String birth = scn.nextLine();
				if(birth.trim().equals("")) birth = dto.getClient_birth();
				System.out.print("▶수정할 주소 : ");
				String address = scn.nextLine();
				if(address.trim().equals("")) address=dto.getClient_address();
				System.out.print("▶수정할 전화번호 : ");
				String number = scn.nextLine();
				if(number.trim().equals("")) number=dto.getClient_number();
				
				int point = dto.getClient_point();
				int count = dto.getClient_purchase_count();
				
				dto = new ClientDTO(id, password, name, birth, address, number, point, count);
				
				boolean r = dao.updateClient(dto, c_id);
				if(r){
					System.out.println("회원의 정보가 다음과 같이 수정되었습니다.");
					System.out.println(dto.getInfo());
				}else{
					System.out.println("회원의 정보가 정상적으로 수정 되지 않았습니다.");
				}
			}else{
				System.out.println("수정 작업을 취소하였습니다.");
			}
		}else{
			System.out.println("입력하신 id에 해당하는 회원이 존재하지 않습니다.");
		}
	}
	

	//영화관 삭제
	public void deleteClient(){	
		
		Scanner scn = new Scanner(System.in);
		System.out.print("삭제할 회원의 회원 id를 입력해주세요 : ");
		String c_id = scn.nextLine();
		ClientDTO dto = dao.getClient(c_id);
		if (dto!=null) {
			System.out.println(dto.getInfo());
			
			System.out.println("위 회원의 정보를 정말로 삭제하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if(input.equalsIgnoreCase("y")){
				boolean r = dao.deleteClient(c_id);
				
				if(r){
					System.out.println(c_id + "회원의 정보가 정상적으로 삭제되었습니다.");
				}else{
					System.out.println("회원의 정보가 정상적으로 삭제 되지 않았습니다.");
				}
			}else{
				System.out.println("삭제 작업을 취소하였습니다.");
			}
		}else{
			
			System.out.println("입력하신 회원 이름에 해당하는 회원이 존재하지 않습니다.");
		}
	}


	//공백입력시 재입력
	public String reInput(Scanner scn){
		
		String str="";
		while(true){
			str = scn.nextLine();
			if(!(str==null || str.trim().equals(""))){
				break;
			}else{
				System.out.println("공백은 입력하실수없습니다. 올바른값을 입력해주세요!");
				System.out.print("▶");
			}
		}
		
		return str;
	}

}
