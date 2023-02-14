package test;

import java.util.ArrayList;

public class MoneyChange {

	public static void main(String[] args) {
		
		long money = 2234587984110000L;
		System.out.println(changemoney(money));    
		System.out.println(money);
	
	}	
	
	//long 타입으로 받아서, 한글로 변환해주는 메소드
	public static String changemoney(long money) {		
		String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		String[] han2 = { "천", "", "십", "백"};
		String[] han3 = { "", "만", "억", "조"};

		String smoney =String.valueOf(money);   //long 타입 money을 String smoney으로 변환
		
		ArrayList<String> gethan1 = new ArrayList<>();    //숫자를 왼쪽에서부터 읽었을때, 숫자 자체를 한글로 변환해서 Arraylist로 저장(han1 배열 이용)
		ArrayList<String> gethan2 = new ArrayList<>();    //숫자를 왼쪽에서부터 읽었을때, 천백십 단위를  Arraylist로 저장(han2 배열 이용)
		
		int len=smoney.length();  //문자길이
		
		if(len>16) {
			System.out.println("단위변환이 불가능합니다. 천조이후의 단위를 추가하십시오");
			return "금액 단위 부족";
		}
		
		for(int i=0;i<smoney.length();i++) {
			int num=Integer.parseInt(smoney.substring(i, i+1));		
			
			gethan1.add(han1[num]);				
			
			int rest=len%4;
			if(num!=0) 
				gethan2.add(han2[rest]);				
			else 
				gethan2.add(han2[1]);
			
			len--;
		}	
//////////////////////////만억조 단위를 문자를 substring로 딱 구간이 0일때, 해당되는 4개를 뽑아와서 문자의 내용을 "0000"으로 비교하는 방식//////////////////////////////
		StringBuilder han = new StringBuilder();     
				
		for(int i=0;i<smoney.length();i++) {
			han.append(gethan1.get(i)+gethan2.get(i));
			if(smoney.length()>=0 && i==smoney.length()-1) {
				boolean han3Check=true;
				if(smoney.length()>=4) 
					if(smoney.substring(smoney.length()-4, smoney.length()).equals("0000"))
						han3Check=false;				
				if(han3Check)	
					han.append(han3[0]+" ");
			}else if(smoney.length()>=5 && i==smoney.length()-5) {  //만
				boolean han3Check=true;
				if(smoney.length()>=8) 
					if(smoney.substring(smoney.length()-8, smoney.length()-4).equals("0000"))
						han3Check=false;
				if(han3Check)				
					han.append(han3[1]+" ");
			}else if(smoney.length()>=9 && i==smoney.length()-9) {   //억
				boolean han3Check=true;
				if(smoney.length()>=12) 
					if(smoney.substring(smoney.length()-12, smoney.length()-8).equals("0000"))
						han3Check=false;				
				if(han3Check)					
					han.append(han3[2]+" ");				
			}else if(smoney.length()>=13 && i==smoney.length()-13) {  //조
				boolean han3Check=true;
				if(smoney.length()>=16) 
					if(smoney.substring(smoney.length()-16, smoney.length()-12).equals("0000"))
						han3Check=false;				
				if(han3Check)	
				han.append(han3[3]+" ");
			}	
		}
		han.append("원");
		
		return han.toString();    //최종 합쳐진 String으로 리턴함!		
	}
}	

/* 
		String yangsmoeny = money+"";
 */



////////////////////////모든 일의자리(맨끝자리)에서 1->일, 일만원 경우 만원일때만 1->X, 만원이상일경우 1->일, 그외 모든 일천일백일십->천백십 ////////////////////////////
/*		if(num==1) {
			if(smoney.length()>=0 && i==smoney.length()-1)
				gethan1.add(han1[num]);
			else if(smoney.length()>=5 && i==smoney.length()-5) {
				if(smoney.length()!=5)
					gethan1.add(han1[num]);
			}
			else if(smoney.length()>=9 && i==smoney.length()-9)
				gethan1.add(han1[num]);
			else if(smoney.length()>=13 && i==smoney.length()-13)
				gethan1.add(han1[num]);
			else
				gethan1.add(han1[0]);
		}else
			gethan1.add(han1[num]);	
*/		
	
/////////////////////////////////////////만억조 단위를 문자를 substring로 한개씩 뽑아와서 숫자로 바뀐값이 0일때 입력안하는 방식////////////////////////////////////	
////////////////////////////////////////이때 만억조 단위별로 0이 딱 그 구간에서 4개로 카운트 될때만, 만억조 단위 생략하는 방식//////////////////////////////////// 
/*
		StringBuilder han = new StringBuilder();     
		
		//위에서 얻은 Arraylist를 합치고, han3 배열을 이용하여, 만,억,조 단위를 추가하여, 하나의 String으로 만드는 과정
		for(int i=0;i<smoney.length();i++) {
			han.append(gethan1.get(i)+gethan2.get(i));
			if(smoney.length()>=0 && i==smoney.length()-1) {
				int count=0;
				if(smoney.length()>=4) 
					for(int j=i;i-4<j;j--) {
						if(Integer.parseInt(smoney.substring(j, j+1))==0) 
							count++;					
					}				
				if(count!=4)	
					han.append(han3[0]+" ");
			}else if(smoney.length()>=5 && i==smoney.length()-5) {  //만
				int count=0;
				if(smoney.length()>=8) 
					for(int j=i;i-4<j;j--) {
						if(Integer.parseInt(smoney.substring(j, j+1))==0) 
							count++;					
					}			
				if(count!=4)				
					han.append(han3[1]+" ");
			}else if(smoney.length()>=9 && i==smoney.length()-9) {   //억
				int count=0;
				if(smoney.length()>=12) 
					for(int j=i;i-4<j;j--) {
						if(Integer.parseInt(smoney.substring(j, j+1))==0) 
							count++;					
					}
				if(count!=4)				
					han.append(han3[2]+" ");				
			}else if(smoney.length()>=13 && i==smoney.length()-13) {  //조
				int count=0;
				if(smoney.length()>=16) 
					for(int j=i;i-4<j;j--) {
						if(Integer.parseInt(smoney.substring(j, j+1))==0) 
							count++;					
					}
				if(count!=4)	
				han.append(han3[3]+" ");
			}	
		}
		han.append("원");
		
		return han.toString();  
*/		
/////////////////////////////////////////만억조 단위를 0을 ""으로 변환해서 저장한 Arraylist를 하나씩 가져와서 ""일때 입력 안하는 방식////////////////////////////////////			
////////////////////////////////////////이때 만억조 단위별로 ""이 딱 그 구간에서 4개로 카운트 될때만, 만억조 단위 생략하는 방식//////////////////////////////////// 
///////////////////////////////////'일'찍히는거 다 찍히게 한 상태에서 나중에, 일만,일천,일백,일십 일때 -> 만,천,백,십 으로 대체 /////////////////////////////////
/*
		for(int i=0;i<smoney.length();i++) {
			int num=Integer.parseInt(smoney.substring(i, i+1));			
			gethan1.add(han1[num]);				
			int rest=len%4;
			if(num!=0) 
				gethan2.add(han2[rest]);				
			else 
				gethan2.add(han2[1]);
		
			len--;
		}
				
		StringBuilder han = new StringBuilder();     //위에서 얻은 값들을 합치고 수정하기위해, StringBuilder 이용
		
		//위에서 얻은 Arraylist를 합치고, han3 배열을 이용하여, 만,억,조 단위를 추가하여, 하나의 String으로 만드는 과정
		for(int i=0;i<smoney.length();i++) {
			han.append(gethan1.get(i)+gethan2.get(i));
			if(smoney.length()>=0 && i==smoney.length()-1) {
				int count=0;
				if(smoney.length()>=4) 
					for(int j=i;i-4<j;j--) {
						if(gethan1.get(j)=="") 
							count++;					
					}
				if(count!=4)	
					han.append(han3[0]+" ");
			}else if(smoney.length()>=5 && i==smoney.length()-5) {  //만
				int count=0;
				if(smoney.length()>=8) 
					for(int j=i;i-4<j;j--) {
						if(gethan1.get(j)=="") 
							count++;					
					}
				if(count!=4)				
					han.append(han3[1]+" ");
			}else if(smoney.length()>=9 && i==smoney.length()-9) {   //억
				int count=0;
				if(smoney.length()>=12) 
					for(int j=i;i-4<j;j--) {
						if(gethan1.get(j)=="") 
							count++;					
					}
				if(count!=4)				
					han.append(han3[2]+" ");				
			}else if(smoney.length()>=13 && i==smoney.length()-13) {  //조
				int count=0;
				if(smoney.length()>=16) 
					for(int j=i;i-4<j;j--) {
						if(gethan1.get(j)=="") 
							count++;					
					}
				if(count!=4)	
				han.append(han3[3]+" ");
			}	
		}
		han.append("원");
		
		String result=han.toString();
		if(result.substring(0,2).equals("일만"))
			result=result.replace("일만", "만"); //'일만'으로 시작할때만 '만'으로 	
		result=result.replace("일천", "천"); //'일천' 존재하지 않음 '천'으로
		result=result.replace("일백", "백");
		result=result.replace("일십", "십");
		
		return result;  
*/				


/*
 *		 //숫자를 왼쪽에서부터 읽었을때, 숫자 자체를 한글로 변환해서 arraylist로 저장하는 과정(han1 배열 이용)
		for(int i=0;i<smoney.length();i++) {              
			int num=Integer.parseInt(smoney.substring(i, i+1));
			for(int j=0; j<10;j++) {
				if(num%10==j) {
					gethan1.add(han1[j]);
				}
			}			
		}
		
		//숫자를 왼쪽에서부터 읽었을때, 해당하는 단위를  arraylist로 저장하는 과정(han2 배열 이용)
		for(int i=smoney.length();i>0;i--) {
			for(int j=0;j<4;j++) {
				if(i%4==j) {
					gethan2.add(han2[j]);
				}
			}
		}		
 */
