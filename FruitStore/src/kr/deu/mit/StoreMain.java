package kr.deu.mit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreMain {

	public static void main(String[] args) {
		// 과일입고, 재고파악, 판매하기, 매출확인
		
		Scanner in = new Scanner(System.in);
		
		FruitStoreDAO dao = new FruitStoreDAOImpl();
		
		int menuNum;						
		do {
			//메뉴출력하기
			System.out.println("1. 과일입력");
			System.out.println("2. 재고파악");
			System.out.println("3. 판매하기");
			System.out.println("4. 매출확인");
			System.out.print("메뉴를 선택해주세요.(0은 종료): ");			
			//사용자 입력받고 해당메뉴 실행하기
			menuNum=in.nextInt();
			System.out.println();
			if(menuNum==1) {
				System.out.println("과일입력 기능입니다.");
				//1. 과일목록보여주기
				int addChoice=-1;
				do {
					System.out.println("과일목록 입니다.");
					List<FruitVO> list=dao.listFruit();
					for(FruitVO vo : list) {
						System.out.println(vo);
					}					
					
					System.out.println();
					//2. 선택할지 추가할지 입력받고
					//3-1. 선택일 경우
					//	   입고수량 받고, DB처리(업데이트) -> 보내줘야할 내용: 과일코드,수량
					//3-2. 추가일 경우
					//	   과일이름,가격,수량 받고, DB처리(인서트,삽입) -> 보내줘야할 내용: 
					System.out.println("추가할 선택사항을 눌러주세요.");
					System.out.println("1. 기존에 품목에서 수량추가 할 경우");
					System.out.println("2. 새로운 품목을 추가할 경우");
					System.out.println("3. 상위메뉴로 이동");
					addChoice=in.nextInt();
					
					if(addChoice==1) {
						System.out.print("추가할 과일코드를 입력해주세요. : ");
						int fruit_code=in.nextInt();
						System.out.print("추가할 수량을 입력해주세요. : ");
						int quantity=in.nextInt();											
						FruitVO vo = new FruitVO();
						vo.setFruit_code(fruit_code);
						vo.setQuantity(quantity);
						dao.updateQuantityFruit(vo);
						System.out.println();
					}else if(addChoice==2) {
						System.out.print("추가할 과일이름을 입력해주세요. : ");
						String fruit_name=in.next();
						System.out.print("추가할 가격을 입력해주세요. : ");
						int price=in.nextInt();
						System.out.print("추가할 수량을 입력해주세요. : ");
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
				System.out.println("재고파악 기능입니다.");		
				List<FruitVO> list=dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println(vo);
				}	
				System.out.println();
			}else if(menuNum==3) {
				System.out.println("판매하기 기능입니다.");
				//1. 과일목록보여주기 -> (DB) 과일목록보여주기
				System.out.println("과일목록 입니다.");
				List<FruitVO> list=dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println(vo);
				}
				System.out.println();
				//2. 사용자가 선택(과일코드,개수) 
				ArrayList<Integer> buylist = new ArrayList<>();	
				do {
					boolean error=true;
					int fruit_code;
					int quantity;
					do {
						error=true;
						System.out.print("구매할 과일코드를 선택해주세요. : ");								
						fruit_code=in.nextInt();   
						
						System.out.print("구매할 수량을 입력해주세요. : ");
						quantity=in.nextInt();
						
						if(dao.codeFruit(fruit_code)==null) {
							System.out.println("입력한 과일코드가 재고에 없습니다. \n다시 입력해주세요.\n");
							error=false;
						}else {							
							if(quantity>dao.quantityFruit(fruit_code)) {
								System.out.println("입력한 구매할 수량이 현재 재고보다 큽니다. \n다시 입력해주세요.\n");
								error=false;
							}
						}
					}while(!error);
					
					buylist.add(fruit_code);
					buylist.add(quantity);
					System.out.print("추가로 장바구니에 담겠습니까?(1:추가 2:취소) ");				
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
				System.out.println("총 구매금액은 "+ sum+"("+dao.changemoney(sum)+")"+"입니다.");
				System.out.print("구매하시겠습니까?(1:구매, 2:취소) ");
				if(in.nextInt()==1) {
					dao.insertSales(buylist);
				}
			}else if(menuNum==4) {
				System.out.println("매출확인 기능입니다.");
				int salesCheck=-1;
				do {
					System.out.println("1. 전체 매출내역 확인하기");
					System.out.println("2. 일자별 매출내역 확인하기");
					System.out.println("3. 총 매출금액 확인하기");
					System.out.println("4. 상위메뉴로 이동");
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
							System.out.println("sales_date= "+vo.getSales_date()+", total= "+ vo.getTotal()+"원");
						}
					}else if(salesCheck==3)
						System.out.println("총매출금액은 "+dao.totalPrice()+"("+dao.changemoney(dao.totalPrice())+")"+"입니다.\n");
				}while(salesCheck!=4);
			}
		}while(menuNum!=0);
			System.out.println("이용해주셔서 감사합니다.");
	}
	

}





/*
 * 				System.out.println("구매할 과일번호를 선택해주세요 : ");
				int fruit_code=in.nextInt();   //과일번호 입력
				System.out.println("구매할 수량을 입력해주세요 : ");
				int quantity=in.nextInt();
				
				//3. 계속 구매여부 확인 (계속이면 2번 수행)   ->장바구니는 생략  한번에 한과일만 선택가능으로
				
				//4. 지불금액 안내 -> (DB) 과일별 총가격알려주기
				FruitVO vo = new FruitVO();
				vo.setFruit_code(fruit_code);
				vo.setQuantity(quantity);
				System.out.println("총 구매금액은 "+dao.totalFruit(vo)+"입니다");
				System.out.println("구매하시겠습니까?(1:구매, 2:취소)");
				if(in.nextInt()==1) {
					//5. 판매완료 -> (DB) 판매처리
					dao.insertSales(fruit_code, quantity);
				}			
 */
