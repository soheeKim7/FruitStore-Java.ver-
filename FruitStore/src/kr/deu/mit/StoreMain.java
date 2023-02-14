package kr.deu.mit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreMain {

	public static void main(String[] args) {
		// �����԰�, ����ľ�, �Ǹ��ϱ�, ����Ȯ��
		
		Scanner in = new Scanner(System.in);
		
		FruitStoreDAO dao = new FruitStoreDAOImpl();
		
		int menuNum;						
		do {
			//�޴�����ϱ�
			System.out.println("1. �����Է�");
			System.out.println("2. ����ľ�");
			System.out.println("3. �Ǹ��ϱ�");
			System.out.println("4. ����Ȯ��");
			System.out.print("�޴��� �������ּ���.(0�� ����): ");			
			//����� �Է¹ް� �ش�޴� �����ϱ�
			menuNum=in.nextInt();
			System.out.println();
			if(menuNum==1) {
				System.out.println("�����Է� ����Դϴ�.");
				//1. ���ϸ�Ϻ����ֱ�
				int addChoice=-1;
				do {
					System.out.println("���ϸ�� �Դϴ�.");
					List<FruitVO> list=dao.listFruit();
					for(FruitVO vo : list) {
						System.out.println(vo);
					}					
					
					System.out.println();
					//2. �������� �߰����� �Է¹ް�
					//3-1. ������ ���
					//	   �԰���� �ް�, DBó��(������Ʈ) -> ��������� ����: �����ڵ�,����
					//3-2. �߰��� ���
					//	   �����̸�,����,���� �ް�, DBó��(�μ�Ʈ,����) -> ��������� ����: 
					System.out.println("�߰��� ���û����� �����ּ���.");
					System.out.println("1. ������ ǰ�񿡼� �����߰� �� ���");
					System.out.println("2. ���ο� ǰ���� �߰��� ���");
					System.out.println("3. �����޴��� �̵�");
					addChoice=in.nextInt();
					
					if(addChoice==1) {
						System.out.print("�߰��� �����ڵ带 �Է����ּ���. : ");
						int fruit_code=in.nextInt();
						System.out.print("�߰��� ������ �Է����ּ���. : ");
						int quantity=in.nextInt();											
						FruitVO vo = new FruitVO();
						vo.setFruit_code(fruit_code);
						vo.setQuantity(quantity);
						dao.updateQuantityFruit(vo);
						System.out.println();
					}else if(addChoice==2) {
						System.out.print("�߰��� �����̸��� �Է����ּ���. : ");
						String fruit_name=in.next();
						System.out.print("�߰��� ������ �Է����ּ���. : ");
						int price=in.nextInt();
						System.out.print("�߰��� ������ �Է����ּ���. : ");
						int quantity=in.nextInt();
						FruitVO vo = new FruitVO();
						vo.setFruit_name(fruit_name);
						vo.setPrice(price);
						vo.setQuantity(quantity);
						dao.insertFruit(vo);
						System.out.println();
					}
				}while(addChoice!=3);
				
			}else if(menuNum==2) {
				System.out.println("����ľ� ����Դϴ�.");		
				List<FruitVO> list=dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println(vo);
				}	
				System.out.println();
			}else if(menuNum==3) {
				System.out.println("�Ǹ��ϱ� ����Դϴ�.");
				//1. ���ϸ�Ϻ����ֱ� -> (DB) ���ϸ�Ϻ����ֱ�
				System.out.println("���ϸ�� �Դϴ�.");
				List<FruitVO> list=dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println(vo);
				}
				System.out.println();
				//2. ����ڰ� ����(�����ڵ�,����) 
				ArrayList<Integer> buylist = new ArrayList<>();	
				do {
					boolean error=true;
					int fruit_code;
					int quantity;
					do {
						error=true;
						System.out.print("������ �����ڵ带 �������ּ���. : ");								
						fruit_code=in.nextInt();   
						
						System.out.print("������ ������ �Է����ּ���. : ");
						quantity=in.nextInt();
						
						if(dao.codeFruit(fruit_code)==null) {
							System.out.println("�Է��� �����ڵ尡 ��� �����ϴ�. \n�ٽ� �Է����ּ���.\n");
							error=false;
						}else {							
							if(quantity>dao.quantityFruit(fruit_code)) {
								System.out.println("�Է��� ������ ������ ���� ����� Ů�ϴ�. \n�ٽ� �Է����ּ���.\n");
								error=false;
							}
						}
					}while(!error);
					
					buylist.add(fruit_code);
					buylist.add(quantity);
					System.out.print("�߰��� ��ٱ��Ͽ� ��ڽ��ϱ�?(1:�߰� 2:���) ");				
				}while(in.nextInt()!=2);
				System.out.println();
				
//				for(Integer temp: buylist) {
//					System.out.println(temp);
//				}
				
				FruitVO vo = new FruitVO();
				int sum=0;
				for(int i=0; i<buylist.size();i += 2) {
					vo.setFruit_code(buylist.get(i));
					vo.setQuantity(buylist.get(i+1));
					sum += dao.totalFruit(vo);				
				}
				System.out.println("�� ���űݾ��� "+ sum+"("+dao.changemoney(sum)+")"+"�Դϴ�.");
				System.out.print("�����Ͻðڽ��ϱ�?(1:����, 2:���) ");
				if(in.nextInt()==1) {
					dao.insertSales(buylist);
				}
			}else if(menuNum==4) {
				System.out.println("����Ȯ�� ����Դϴ�.");
				int salesCheck=-1;
				do {
					System.out.println("1. ��ü ���⳻�� Ȯ���ϱ�");
					System.out.println("2. ���ں� ���⳻�� Ȯ���ϱ�");
					System.out.println("3. �� ����ݾ� Ȯ���ϱ�");
					System.out.println("4. �����޴��� �̵�");
					salesCheck=in.nextInt();
					System.out.println();
					
					if(salesCheck==1) {
						List<SalesVO> list=dao.listSales();				
						for(SalesVO vo:list) {
							System.out.println(vo);
						}
						System.out.println();
					}else if(salesCheck==2) {
						List<SalesVO> list=dao.dayByTotalPrice();
						for(SalesVO vo:list) {
							System.out.println("sales_date= "+vo.getSales_date()+", total= "+ vo.getTotal()+"��");
						}
					}else if(salesCheck==3)
						System.out.println("�Ѹ���ݾ��� "+dao.totalPrice()+"("+dao.changemoney(dao.totalPrice())+")"+"�Դϴ�.\n");
				}while(salesCheck!=4);
			}
		}while(menuNum!=0);
			System.out.println("�̿����ּż� �����մϴ�.");
	}
	

}





/*
 * 				System.out.println("������ ���Ϲ�ȣ�� �������ּ��� : ");
				int fruit_code=in.nextInt();   //���Ϲ�ȣ �Է�
				System.out.println("������ ������ �Է����ּ��� : ");
				int quantity=in.nextInt();
				
				//3. ��� ���ſ��� Ȯ�� (����̸� 2�� ����)   ->��ٱ��ϴ� ����  �ѹ��� �Ѱ��ϸ� ���ð�������
				
				//4. ���ұݾ� �ȳ� -> (DB) ���Ϻ� �Ѱ��ݾ˷��ֱ�
				FruitVO vo = new FruitVO();
				vo.setFruit_code(fruit_code);
				vo.setQuantity(quantity);
				System.out.println("�� ���űݾ��� "+dao.totalFruit(vo)+"�Դϴ�");
				System.out.println("�����Ͻðڽ��ϱ�?(1:����, 2:���)");
				if(in.nextInt()==1) {
					//5. �ǸſϷ� -> (DB) �Ǹ�ó��
					dao.insertSales(fruit_code, quantity);
				}			
 */
