package test;
public class MoneyHan {

	public static void main(String[] args) {
		long money = 100000000L;
		System.out.println(changemoney(money));    
		System.out.println(money);		
	}

	private static String changemoney(long money) {
		String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		String[] han2 = { "", "십", "백", "천"};
		String[] han3 = { " 원" ,"만 ", "억 ", "조 " };
		
		String lastHan="";
		
		String[] stringArrayMoney=(money+"").split(""); //숫자를 문자로 변환후 다시 한글자씩 문자열의 배열로 저장
		//자리수가 같다면 +1안함 딱맞으니까
		int lengthMoney=stringArrayMoney.length;
		int unitMoney=lengthMoney/4-1; //원단위 4자리수
		
		if( lengthMoney %4 != 0)
			unitMoney++;	//금액이 원 만 억... 으로 딱 안떨어지면 한 단위가 더 있음으로 
		                    // ex>7자리이면 ..만...원(2개)   , 4자리이면 ...원(1개)  으로 표기되어야함		
		//예외처리
		if(unitMoney>=han3.length) {
			System.out.println("변환불가 han3에 높은 단위를 추가해 주세요");
			return "금액오류";
		}
		
		for(int i =unitMoney ; i>=0 ;i--) {  //금액의 한글단위는 4자리수 임으로 자리수 만큼 반복			
			int j=3;
			if(i==unitMoney) {  //첫번째 단위는 4자리수가 아닐수 있음으로
				j=lengthMoney-(unitMoney*4)-1; //-1은 index는 0부터 시작
			}
			
			boolean isUnit=false; //4자리에 값이 존재하는지 ex>0000 -> false , 값이 하나라도 있으면 true 
			
			for( ; j>=0; j--) {  //천백십일 단위 숫자 확인
							
				String hanNum=han1[Integer.parseInt(stringArrayMoney[(lengthMoney-1)-(4*i+j)])];//한글변환 높은 자리수 부터 출력
				lastHan+=hanNum;
								
				if(!hanNum.equals("")) { //0이 아니고
					isUnit=true;
					if(j!=0) //마지막 단위는 없음 (천백십까지만 일 불필요)
						lastHan+=han2[j];
				}
			}
			if(isUnit)
				lastHan+=han3[i];			
		}		
		if(!lastHan.contains(" 원"))
			lastHan+=han3[0];
						
		lastHan=lastHan.replace("  ", " ");
		
		return lastHan;
	}
}

/*
 * if(lastHan.substring(0,2).equals("일만"))
			lastHan=lastHan.replace("일만", "만"); //'일만'으로 시작할때만 '만'으로 	
		
		lastHan=lastHan.replace("일천", "천"); //'일천' 존재하지 않음 '천'으로
		lastHan=lastHan.replace("일백", "백"); //'일백' 존재하지 않음 '백'으로
		lastHan=lastHan.replace("일십", "십");
 */



