package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.deu.mit.FruitStoreDAOImpl;   //다른패키지라서 임포트 되어야함!!
import kr.deu.mit.FruitVO;
import kr.deu.mit.SalesVO;

public class DAOTest2 {

	public static void main(String[] args) {
		FruitStoreDAOImpl  dao = new FruitStoreDAOImpl();
		
		System.out.println(111);
//		int a=1;
//		int b=4;
//		int count=0;
//		int i=0;
//		for(i=a; i<=b;i++){
//			for(int j=1;j<=a;j++){
//				if(i%j==0){
//					count++;
//					if(count==2){
//						System.out.println(i);						
//					}
//				}
//			}
//			count=0;		
//		}
		
		
		
		
		
		
		
		
		
//		Date date = new Date();  //현재날짜 가져오기
//		
//		System.out.println(date); //출력
//		
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//자바.text   /("yyyy mm dd")  (a hh:mm:ss)  hh는 12시간제 HH는 24시간제
//		System.out.println(simpleDateFormat.format(date));
//		
//		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy년 MM월 dd일");  //mm은 분!! MM은 월!!
//		try {
//			date=simpleDateFormat2.parse("2023년 12월 1일"); // 예외가 발생되도록 나온 메소드 그래서 예외처리
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		System.out.println(simpleDateFormat.format(date));
//		
		
		
//		dao.insertSales(29, 10);
//		
//		String a ="가나다";
//		System.out.println(a.substring(1));
//		double comrate=(int)(Math.random()*6);
//		System.out.println(comrate);
		
//		boolean check=true;
//		int comcode=0;
//		
////		System.out.println("최대코드는 "+dao.maxFruit_code());
////		System.out.println("최소코드는 "+dao.minFruit_code());
//		
//		do{
//			comcode=(int)(Math.random()*(dao.maxFruit_code()-dao.minFruit_code()+1)+dao.minFruit_code());
//			check=true;
//			//(int)(Marh.random()*(최대값-최소값+1)+최소값)
//			System.out.println("처음 뽑힌 수: "+comcode);
//			if(dao.codeFruit(comcode)==null){
//				System.out.println("null인 코드: "+comcode);
//				check=false;
//			}
//		}while(check==false);
//		
//		System.out.println("결론적 뽑힌 수: "+comcode);
		
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
//		System.out.println(dao.quantityFruit(2));
//		System.out.println(dao.codeFruit(18));
//		
//		var str2 = "사당.교대.강남.역삼.";
////		var result2 = str2.substring(0,5);
////
//////		System.out.println(result2);
////		System.out.println(str2.toString());
//		
////		System.out.println(str2.length());
//		
//		Integer n;
//		n=12354;
//		String a=Integer.toString(n);
//		System.out.println(a.length());
		
//		System.out.println(a.substring(5, 6));
//		if(a.substring(4, 5).equals("4")) {
//			System.out.println(4);
//		}
//		System.out.println(Integer.parseInt(a.substring(4,5)));
//		String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
//		String[] han2 = { "천","", "십", "백"};
//		String[] han3 = { "", "만", "억", "조"};
//		
//		n=2000000000;
////		n=14800006789;
//		
//		String a=Integer.toString(n);
//		System.out.println("####################################3");
//		System.out.println(Integer.parseInt(a.substring(1, 5)));
//		
//		
//		ArrayList<String> gethan1 = new ArrayList<>();
//		ArrayList<String> gethan2 = new ArrayList<>();
//		int len=a.length();
//		StringBuilder han = new StringBuilder();
//		
//		for(int i=0;i<a.length();i++) {
//			int num=Integer.parseInt(a.substring(i, i+1));
//			gethan1.add(han1[num]);
//			int rest=len%4;
//			if(num!=0) {
//				gethan2.add(han2[rest]);				
//			}else {
//				gethan2.add(han2[1]);
//			}
//			len--;
			
//			int man;
//			int eok;
//			if(a.length()==8) {
//				man=Integer.parseInt((a.substring(0,1)));
//			}else if(a.length()==12) {
//			}
			
			
//			han.append(gethan1.get(i)+gethan2.get(i));
//						
//			if(i<=4 && i==a.length()-1) {
//				han.append(han3[0]);
//			}else if(i<=8 && i==a.length()-5) {
//				if(i>=8)
//				if(!a.substring(a.length()-8, a.length()-4).equals("0000")) 
//					han.append(han3[1]+" ");
//			}else if(i<=12 && i==a.length()-9) {
////				if(!a.substring(a.length()-12, a.length()-8).equals("0000"))
//					han.append(han3[2]+" ");
//			}else if(i<=16 && i==a.length()-13) {
//				han.append(han3[3]+" ");				
//			}
//		}
//				
//		han.append(" 원");
//		System.out.println(han.toString());
//				
//		System.out.println("**********************");
//		System.out.println(dao.changemoney(20000000));
//		
//		
//		System.out.println("-------------------");
//		for(int i=0;i<a.length();i++) {
//			System.out.println(i+gethan1.get(i));
//		}
//		
//		for(int i=0;i<a.length();i++) {
//			System.out.println(i+gethan2.get(i));
//		}
				
		
//		for(int i=0;i<a.length();i++) {
//			System.out.print(gethan1.get(i)+gethan2.get(i));
//			if(i<=4 && i==a.length()-1) {
//				System.out.print(han3[0]);
//			}else if(i<=8 && i==a.length()-5) {
//				System.out.print(han3[1]+" ");
//			}else if(i<=12 && i==a.length()-9) {
//				System.out.print(han3[2]+" ");
//			}else if(i<=16 && i==a.length()-13) {
//				System.out.print(han3[3]+" ");
//			}			
//		}
//		System.out.println(" 원");
//		
//							
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		System.out.println();
//		System.out.println(dao.changemoney(23000000000L));
		
						
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