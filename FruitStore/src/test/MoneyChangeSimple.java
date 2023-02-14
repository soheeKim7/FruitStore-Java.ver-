package test;

import java.util.ArrayList;

public class MoneyChangeSimple {

	public static void main(String[] args) {
		
		long money = 33231234551L;
		System.out.println(changemoney(money));    
		System.out.println(money);

	}
	
	public static String changemoney(long money) {
		
		String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		String[] han2 = { "천", "", "십", "백"};
		String[] han3 = { "", "만", "억", "조"};

		String smoney=money+"";
		String moneyHan="";
				
		int len=smoney.length(); 
		
		if(len>16) {
			System.out.println("단위변환이 불가능합니다. 천조이후의 단위를 추가하십시오");
			return "금액 단위 부족";
		}
		
		for(int i=0;i<smoney.length();i++) {
			int num=Integer.parseInt(smoney.substring(i, i+1));		
					
			moneyHan += han1[num];			
			
			int rest=len%4;
			if(num!=0) 				
				moneyHan += han2[rest];			
			else 
				moneyHan += han2[1];	
			len--;
			
			switch(len) {
				case 4:
					num=1;
					break;
				case 8:
					num=2;
					break;
				case 12:
					num=3;
					break;
				default:
					num=0;				
			}
			if(num!=0)
				moneyHan += han3[num]+" ";
			else
				moneyHan += han3[num];		
		}	
		moneyHan += "원";
				
		moneyHan=moneyHan.replace(" 만", "");
		moneyHan=moneyHan.replace(" 억", "");			
				
		return moneyHan;    
	}
	
}


/*///////////////////////////////////첫자리와 끝자리에 오는 1만 일로 표기 하는 방식//////////////////////////////////////////////////////
 *	if(num==1) {
				if(i!=0 &i!=(smoney.length()-1))
					moneyHan += han1[0];
			}else
	//////////////////////////////////1전부를 일로 받고, 나중에 일만의 경우는 만원이상일때만 표기, 모든 일천,일백,일십은 천백십으로 표기하는 방식///////////////////////////////////////		
	if(moneyHan.substring(0,2).equals("일만"))
			moneyHan=moneyHan.replace("일만", "만");
		moneyHan=moneyHan.replace("일천", "천");
		moneyHan=moneyHan.replace("일백", "백");
		moneyHan=moneyHan.replace("일십", "십");		
			
 *
 */




//////////////////////////////////////조단위 이상 에러코드 있다고 넣었어도! 1->'일'변환값을 조단위까지만 생각해서 변화해주게 짠것!!!//////////////////////////
///////////////////////////그래서 조단위 이상 넣었을걸 생각해서 짠다면, 일 변환값을 원래대로 바꾸고 나중에 일을 빼주는게 수정코드 짜기 쉽다!!//////////////////
/*
		String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		String[] han2 = { "천", "", "십", "백"};
		String[] han3 = { "", "만", "억", "조"};
		
		String smoney=money+"";
		String moneyHan="";
				
		int len=smoney.length(); 
		
		if(len>16) {
			System.out.println("단위변환이 불가능합니다. 천조이후의 단위를 추가하십시오");
			return "금액 단위 부족";
		}
		
		for(int i=0;i<smoney.length();i++) {
			int num=Integer.parseInt(smoney.substring(i, i+1));		
			if(num==1) {
				if(smoney.length()>=0 && i==smoney.length()-1)
					moneyHan += han1[num];
				else if(smoney.length()>=5 && i==smoney.length()-5) {
					if(smoney.length()!=5)
						moneyHan += han1[num];
				}
				else if(smoney.length()>=9 && i==smoney.length()-9)
					moneyHan += han1[num];
				else if(smoney.length()>=13 && i==smoney.length()-13)
					moneyHan += han1[num];
				else
					moneyHan += han1[0];
			}else
				moneyHan += han1[num];			
			
			int rest=len%4;
			if(num!=0) 
				moneyHan += han2[rest];			
			else 
				moneyHan += han2[1];	
			len--;
			
			switch(len) {
				case 4:
					num=1;
					break;
				case 8:
					num=2;
					break;
				case 12:
					num=3;
					break;
				default:
					num=0;				
			}
			if(num!=0)
				moneyHan += han3[num]+" ";
			else
				moneyHan += han3[num];		
		}	
		moneyHan += "원";
		
		moneyHan=moneyHan.replace(" 만", "");
		moneyHan=moneyHan.replace(" 억", "");			
				
		return moneyHan; 
*/