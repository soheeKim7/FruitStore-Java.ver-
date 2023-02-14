package test;

import java.util.ArrayList;
import java.util.List;

import kr.deu.mit.FruitStoreDAOImpl;   //다른패키지라서 임포트 되어야함!!
import kr.deu.mit.FruitVO;
import kr.deu.mit.SalesVO;

public class DAOTest {

	public static void main(String[] args) {
		FruitStoreDAOImpl  dao = new FruitStoreDAOImpl();
//		FruitVO vo = new FruitVO();
		
//		vo.setFruit_name("사과");
//		vo.setPrice(1500);
//		vo.setQuantity(50);
//		dao.insertFruit(vo);
		
//		ArrayList<FruitVO> list=dao.listFruit();
//		for(FruitVO vo:list) {
//			System.out.println(vo);
//		}
		
//		vo.setFruit_code(7);
//		vo.setQuantity(-20);
//		dao.updateQuantityFruit(vo);
		
//		vo.setFruit_code(2);
//		vo.setQuantity(5);
//		dao.totalFruit(vo);
//		System.out.println(dao.totalFruit(vo));
		
//		dao.insertSales(5,20);
		
//		System.out.println(dao.totalPrice());
		
//		List<SalesVO> list=dao.listSales();
//		
//		for(SalesVO vo:list) {
//			System.out.println(vo);
//		}
		
//		List<SalesVO> list=dao.dayByTotalPrice();//		
//		for(SalesVO vo:list) {
//			System.out.println("sales_date= "+vo.getSales_date()+", total= "+ vo.getTotal()+"원");
//		}
		
//		System.out.println(dao.changemoney(728077484250L));
//		
		var str2 = "사당.교대.강남.역삼.";
//		var result2 = str2.substring(0,5);
//
////		System.out.println(result2);
//		System.out.println(str2.toString());
		
//		System.out.println(str2.length());
		
		Integer n;
		n=12354;
		String a=Integer.toString(n);
//		System.out.println(a.length());
		
//		System.out.println(a.substring(5, 6));
//		if(a.substring(4, 5).equals(4)) {
//			System.out.println(4);
//		}
//		System.out.println(Integer.parseInt(a.substring(4,5)));
		String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		String[] han2 = { "천", "", "십", "백"};
		String[] han3 = { "", "만", "억", "조"};
		
		ArrayList<String> gethan1 = new ArrayList<>();
		
		for(int i=0;i<a.length();i++) {
			int num=Integer.parseInt(a.substring(i, i+1));
			for(int j=0; j<10;j++) {
				if(num%10==j) {
					System.out.println(han1[j]);	
					gethan1.add(han1[j]);
				}
			}			
		}
		System.out.println("---------------------------");
		
		for(int i=a.length();i>0;i--) {
			for(int j=0;j<4;j++) {
				if(i%4==j) {
					System.out.println(gethan1.get(j)+han2[j]);
				}
			}
		}
		
		
		
		
		
		
		
		for(int i=0;i<a.length();i++) {
			int num=Integer.parseInt(a.substring(i, i+1));
			for(int j=0; j<10;j++) {
				if(num%10==j) {
					System.out.print(han1[j]);
					for(int ii=a.length();ii>0;ii--) {						
						for(int k=0;k<4;k++) {
							if(ii%4==k) {
								System.out.println(han2[k]);
								break;
							}
						}
					}					
				}
			}			
		}
		
		
		
		
		
		
		
//		System.out.println("------------------------------------");
//		for(String s: gethan1) {
//			System.out.println(s);
//		}
		
		
		
		
	}
	
	public static String convertNumberToHangul(String number) {
	    String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
	    String[] han2 = { "", "십", "백", "천" };
	    String[] han3 = { "", "만", "억", "조", "경" };
	    String result = "";
	    int len = number.length();
	    int nowInt = 0;
	    boolean hasHan3 = false;
	    for (int i = len; i > 0; i--) {
	      nowInt = Integer.parseInt(number.substring(len - i, len - i + 1));
	      int han2Pick = (i - 1) % 4;
	      if (nowInt > 0) {
	        result += (han1[nowInt]) + (han2[han2Pick]);
	        if (han2Pick > 0) {
	          hasHan3 = false;
	        }
	      }
	      if (!hasHan3 && han2Pick == 0) {
	        result += (han3[(i - 1) / 4]);
	        hasHan3 = true;
	      }
	    }
	    return result.toString();
	  }
	
}
