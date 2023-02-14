package test;

	import java.util.*;

	public class MoneyHangul2 {

	   public static void main(String[] args) {
	       //han1 : 입력된 숫자의 값을 한글로 변환
	      //han2 : 입력된 숫자의 해당 위치에 따라 단위 설정 4로 나눈 나머지가 값
	      //han3 : 입력된 값의 크기를 스트링 길이(length)로 확인하여 설정
	       String[] s_han1 = {"","일","이","삼","사","오","육","칠","팔","구"};
	       String[] s_han2 = {"천","","십","백"};
	       String[] s_han3 = {"","만","억","조","경"};

	       //Scanner 입력 값 -> 랩퍼 크래스를 이용하여 int형으로 형변환
	       Scanner scan = new Scanner(System.in);
	       //int inputNum = scan.nextInt();         //표준 입출력
	       System.out.print("input : ");       
	       String s_in = scan.nextLine();           //입력된 값 String
	       //int inputNum = Integer.parseInt(s_in); //랩퍼 크래스를 이용하여 int형으로 형변환
	       
	       System.out.println("input : "+ s_in);
	       
	       //입력된 숫자의 길이(자릿수)로 단위와 값을 찾는다

	       int n_cnt   = 0;                  //만 억 조 경 단위 check용으로 연속 0의 숫자 카운트
	       int n_num    = 0;                        //배열의 인덱스용으로 사용
	       int n_len    = s_in.length();            //배열의 길이
	       String s_rst = "";                       //한글로 변환할 변수
	       
	       System.out.println("length : " + n_len + " num : " + n_num);
	       
	       //입력된 숫자는 스트링 변수로 그 길이 만큼 왼쪽에서 오른쪽으로 찾는다
	       for(int i = 0; i<s_in.length(); i++) {
	          n_num = Integer.parseInt(s_in.substring(i, i+1));
	          //입력된 값이 0이 아닐 경우만 그 값의 변환과 단위를 정한다
	          //han2 : 입력된 숫자의 해당 위치에 따라 단위 설정 4로 나눈 나머지가 값
	          //       0-천 1-널 2-십 3-백의 단위
	          if (n_num != 0 ) {
	              s_rst = s_rst + s_han1[n_num];   //입력된 숫자 한글로 변환
	              n_num = n_len % 4;               //입력된 숫자의 단위 게산
	              s_rst = s_rst + s_han2[n_num];   //변환된 숫자 뒤에 단위 붙힘
	          } else {
	             n_cnt++;
	          }
	 
	          //단위를 그 위치에 따라 반영했으므로 단위 위치를 내림
	          n_len = n_len - 1;
	          
	          //만, 억, 조, 경에 해당하는 단위를 붙힘 기본(인덱스 기준 4자리 미만)은 없음
	          switch (n_len) {
	             case 4:
	                n_num = 1;
	                break;
	            case 8:
	               n_num = 2;
	                  break;
	            case 12:
	                n_num = 3;
	                break;
	            case 16:
	                n_num = 4;
	                break;
	            default:
	                n_num = 0;
	          }
	          //만억조에 해당 시 0으로 4자리 연속일 경우는 단위 제외 후, 0의 수 카운트를 1로 한다
	          if (n_num != 0) {
	             if (n_cnt == 5) {
	                n_num = 0;
	             } 
	             n_cnt = 1;
	          }
	          
	          s_rst = s_rst + s_han3[n_num];
	       }
	       //최종 원단위를 붙힘
	       s_rst = s_rst + "원";
	       System.out.println("Result : "+ s_rst );   
	   }
	}

