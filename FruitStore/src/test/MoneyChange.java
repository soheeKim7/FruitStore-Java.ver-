package test;

import java.util.ArrayList;

public class MoneyChange {

	public static void main(String[] args) {
		
		long money = 2234587984110000L;
		System.out.println(changemoney(money));    
		System.out.println(money);
	
	}	
	
	//long Ÿ������ �޾Ƽ�, �ѱ۷� ��ȯ���ִ� �޼ҵ�
	public static String changemoney(long money) {		
		String[] han1 = { "", "��", "��", "��", "��", "��", "��", "ĥ", "��", "��"};
		String[] han2 = { "õ", "", "��", "��"};
		String[] han3 = { "", "��", "��", "��"};

		String smoney =String.valueOf(money);   //long Ÿ�� money�� String smoney���� ��ȯ
		
		ArrayList<String> gethan1 = new ArrayList<>();    //���ڸ� ���ʿ������� �о�����, ���� ��ü�� �ѱ۷� ��ȯ�ؼ� Arraylist�� ����(han1 �迭 �̿�)
		ArrayList<String> gethan2 = new ArrayList<>();    //���ڸ� ���ʿ������� �о�����, õ��� ������  Arraylist�� ����(han2 �迭 �̿�)
		
		int len=smoney.length();  //���ڱ���
		
		if(len>16) {
			System.out.println("������ȯ�� �Ұ����մϴ�. õ�������� ������ �߰��Ͻʽÿ�");
			return "�ݾ� ���� ����";
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
//////////////////////////������ ������ ���ڸ� substring�� �� ������ 0�϶�, �ش�Ǵ� 4���� �̾ƿͼ� ������ ������ "0000"���� ���ϴ� ���//////////////////////////////
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
			}else if(smoney.length()>=5 && i==smoney.length()-5) {  //��
				boolean han3Check=true;
				if(smoney.length()>=8) 
					if(smoney.substring(smoney.length()-8, smoney.length()-4).equals("0000"))
						han3Check=false;
				if(han3Check)				
					han.append(han3[1]+" ");
			}else if(smoney.length()>=9 && i==smoney.length()-9) {   //��
				boolean han3Check=true;
				if(smoney.length()>=12) 
					if(smoney.substring(smoney.length()-12, smoney.length()-8).equals("0000"))
						han3Check=false;				
				if(han3Check)					
					han.append(han3[2]+" ");				
			}else if(smoney.length()>=13 && i==smoney.length()-13) {  //��
				boolean han3Check=true;
				if(smoney.length()>=16) 
					if(smoney.substring(smoney.length()-16, smoney.length()-12).equals("0000"))
						han3Check=false;				
				if(han3Check)	
				han.append(han3[3]+" ");
			}	
		}
		han.append("��");
		
		return han.toString();    //���� ������ String���� ������!		
	}
}	

/* 
		String yangsmoeny = money+"";
 */



////////////////////////��� �����ڸ�(�ǳ��ڸ�)���� 1->��, �ϸ��� ��� �����϶��� 1->X, �����̻��ϰ�� 1->��, �׿� ��� ��õ�Ϲ��Ͻ�->õ��� ////////////////////////////
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
	
/////////////////////////////////////////������ ������ ���ڸ� substring�� �Ѱ��� �̾ƿͼ� ���ڷ� �ٲﰪ�� 0�϶� �Է¾��ϴ� ���////////////////////////////////////	
////////////////////////////////////////�̶� ������ �������� 0�� �� �� �������� 4���� ī��Ʈ �ɶ���, ������ ���� �����ϴ� ���//////////////////////////////////// 
/*
		StringBuilder han = new StringBuilder();     
		
		//������ ���� Arraylist�� ��ġ��, han3 �迭�� �̿��Ͽ�, ��,��,�� ������ �߰��Ͽ�, �ϳ��� String���� ����� ����
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
			}else if(smoney.length()>=5 && i==smoney.length()-5) {  //��
				int count=0;
				if(smoney.length()>=8) 
					for(int j=i;i-4<j;j--) {
						if(Integer.parseInt(smoney.substring(j, j+1))==0) 
							count++;					
					}			
				if(count!=4)				
					han.append(han3[1]+" ");
			}else if(smoney.length()>=9 && i==smoney.length()-9) {   //��
				int count=0;
				if(smoney.length()>=12) 
					for(int j=i;i-4<j;j--) {
						if(Integer.parseInt(smoney.substring(j, j+1))==0) 
							count++;					
					}
				if(count!=4)				
					han.append(han3[2]+" ");				
			}else if(smoney.length()>=13 && i==smoney.length()-13) {  //��
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
		han.append("��");
		
		return han.toString();  
*/		
/////////////////////////////////////////������ ������ 0�� ""���� ��ȯ�ؼ� ������ Arraylist�� �ϳ��� �����ͼ� ""�϶� �Է� ���ϴ� ���////////////////////////////////////			
////////////////////////////////////////�̶� ������ �������� ""�� �� �� �������� 4���� ī��Ʈ �ɶ���, ������ ���� �����ϴ� ���//////////////////////////////////// 
///////////////////////////////////'��'�����°� �� ������ �� ���¿��� ���߿�, �ϸ�,��õ,�Ϲ�,�Ͻ� �϶� -> ��,õ,��,�� ���� ��ü /////////////////////////////////
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
				
		StringBuilder han = new StringBuilder();     //������ ���� ������ ��ġ�� �����ϱ�����, StringBuilder �̿�
		
		//������ ���� Arraylist�� ��ġ��, han3 �迭�� �̿��Ͽ�, ��,��,�� ������ �߰��Ͽ�, �ϳ��� String���� ����� ����
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
			}else if(smoney.length()>=5 && i==smoney.length()-5) {  //��
				int count=0;
				if(smoney.length()>=8) 
					for(int j=i;i-4<j;j--) {
						if(gethan1.get(j)=="") 
							count++;					
					}
				if(count!=4)				
					han.append(han3[1]+" ");
			}else if(smoney.length()>=9 && i==smoney.length()-9) {   //��
				int count=0;
				if(smoney.length()>=12) 
					for(int j=i;i-4<j;j--) {
						if(gethan1.get(j)=="") 
							count++;					
					}
				if(count!=4)				
					han.append(han3[2]+" ");				
			}else if(smoney.length()>=13 && i==smoney.length()-13) {  //��
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
		han.append("��");
		
		String result=han.toString();
		if(result.substring(0,2).equals("�ϸ�"))
			result=result.replace("�ϸ�", "��"); //'�ϸ�'���� �����Ҷ��� '��'���� 	
		result=result.replace("��õ", "õ"); //'��õ' �������� ���� 'õ'����
		result=result.replace("�Ϲ�", "��");
		result=result.replace("�Ͻ�", "��");
		
		return result;  
*/				


/*
 *		 //���ڸ� ���ʿ������� �о�����, ���� ��ü�� �ѱ۷� ��ȯ�ؼ� arraylist�� �����ϴ� ����(han1 �迭 �̿�)
		for(int i=0;i<smoney.length();i++) {              
			int num=Integer.parseInt(smoney.substring(i, i+1));
			for(int j=0; j<10;j++) {
				if(num%10==j) {
					gethan1.add(han1[j]);
				}
			}			
		}
		
		//���ڸ� ���ʿ������� �о�����, �ش��ϴ� ������  arraylist�� �����ϴ� ����(han2 �迭 �̿�)
		for(int i=smoney.length();i>0;i--) {
			for(int j=0;j<4;j++) {
				if(i%4==j) {
					gethan2.add(han2[j]);
				}
			}
		}		
 */
