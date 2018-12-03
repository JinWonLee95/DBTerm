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
	

	//��ȭ�� ��� ó�� 
	public void insertClient(){			
		
		Scanner scn = new Scanner(System.in);
		
		System.out.println("���� ������ �Է����ּ���.");
		System.out.print("�����̵� : ");
		String client_id = reInput(scn);
		System.out.print("����й�ȣ : ");
		String client_password = reInput(scn);
		System.out.print("���̸� : ");	
		String client_name = reInput(scn);
		System.out.print("������ : ");	
		String client_birth = reInput(scn);
		System.out.print("���ּ� : ");	
		String client_address = reInput(scn);
		System.out.print("����ȭ��ȣ : ");	
		String client_number = reInput(scn);
		
		ClientDTO dto = new ClientDTO(client_id, client_password,client_name,client_birth,client_address,client_number,0,0);		
		boolean r = dao.insertClient(dto); 
		
		if(r){
			System.out.println("ȸ�� ������ ���������� �Ϸ�Ǿ����ϴ�.");
		}else{
			System.out.println("ȸ�� ������ ���������� �̷����� �ʾҽ��ϴ�.");
		}
		
	}	

	//
	public void showClientList(){
	
		List<ClientDTO> list = dao.getClientList();
		
		System.out.println("                             Client List");
		System.out.println("============================================================================");
		if(list!=null&&list.size()>0){			
			System.out.println("reg.No\t  id \t\t��й�ȣ\t\t�̸�\t\t����\t\t�ּ�\t\t��ȭ��ȣ\t\t����Ʈ\t\t���� Ƚ��");
			System.out.println("============================================================================");
			
			for (ClientDTO dto : list){
				System.out.println("\t" + dto.getClient_id() + "\t\t"+ dto.getClient_password() + "\t\t"+ dto.getClient_name()+ "\t\t"+ dto.getClient_birth()+ "\t\t"+ dto.getClient_address()+ "\t\t"+ dto.getClient_number()+ "\t\t"+ dto.getClient_point()+ "\t\t"+ dto.getClient_purchase_count());
			}			
	
		}else{
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "+((list==null)?"0":list.size())+"��=\n");
	}
	
	

	//��ȭ�� ����
	public void updateClient(){
		
		Scanner scn = new Scanner(System.in);
		System.out.println("������ ȸ���� ���̵� �Է����ּ��� : "); 
		System.out.print("��");
		String c_id = scn.nextLine();
		ClientDTO dto = dao.getClient(c_id);
		if (dto!=null) {
			System.out.println(dto.getInfo());
			
			System.out.println("�����۾��� ����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if(input.equalsIgnoreCase("y")){
				System.out.println("##�Է��� �Ͻ��������� ������ ������ �״�� �����˴ϴ�.");
				System.out.print("�������� ���̵� : ");
				String id = scn.nextLine();
				if(id.trim().equals("")) id=dto.getClient_id();
				System.out.print("�������� ��й�ȣ : ");
				String password = scn.nextLine();
				if(password.trim().equals("")) password=dto.getClient_password();
				System.out.print("�������� �̸� : ");
				String name = scn.nextLine();
				if(name.trim().equals("")) name=dto.getClient_name();
				System.out.print("�������� ���� : ");
				String birth = scn.nextLine();
				if(birth.trim().equals("")) birth = dto.getClient_birth();
				System.out.print("�������� �ּ� : ");
				String address = scn.nextLine();
				if(address.trim().equals("")) address=dto.getClient_address();
				System.out.print("�������� ��ȭ��ȣ : ");
				String number = scn.nextLine();
				if(number.trim().equals("")) number=dto.getClient_number();
				
				int point = dto.getClient_point();
				int count = dto.getClient_purchase_count();
				
				dto = new ClientDTO(id, password, name, birth, address, number, point, count);
				
				boolean r = dao.updateClient(dto, c_id);
				if(r){
					System.out.println("ȸ���� ������ ������ ���� �����Ǿ����ϴ�.");
					System.out.println(dto.getInfo());
				}else{
					System.out.println("ȸ���� ������ ���������� ���� ���� �ʾҽ��ϴ�.");
				}
			}else{
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		}else{
			System.out.println("�Է��Ͻ� id�� �ش��ϴ� ȸ���� �������� �ʽ��ϴ�.");
		}
	}
	

	//��ȭ�� ����
	public void deleteClient(){	
		
		Scanner scn = new Scanner(System.in);
		System.out.print("������ ȸ���� ȸ�� id�� �Է����ּ��� : ");
		String c_id = scn.nextLine();
		ClientDTO dto = dao.getClient(c_id);
		if (dto!=null) {
			System.out.println(dto.getInfo());
			
			System.out.println("�� ȸ���� ������ ������ �����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if(input.equalsIgnoreCase("y")){
				boolean r = dao.deleteClient(c_id);
				
				if(r){
					System.out.println(c_id + "ȸ���� ������ ���������� �����Ǿ����ϴ�.");
				}else{
					System.out.println("ȸ���� ������ ���������� ���� ���� �ʾҽ��ϴ�.");
				}
			}else{
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		}else{
			
			System.out.println("�Է��Ͻ� ȸ�� �̸��� �ش��ϴ� ȸ���� �������� �ʽ��ϴ�.");
		}
	}


	//�����Է½� ���Է�
	public String reInput(Scanner scn){
		
		String str="";
		while(true){
			str = scn.nextLine();
			if(!(str==null || str.trim().equals(""))){
				break;
			}else{
				System.out.println("������ �Է��ϽǼ������ϴ�. �ùٸ����� �Է����ּ���!");
				System.out.print("��");
			}
		}
		
		return str;
	}

}
