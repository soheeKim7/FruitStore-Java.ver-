package test;

import java.util.ArrayList;

public class MoneyChangeSimple {

	public static void main(String[] args) {
		
		long money = 33231234551L;
		System.out.println(changemoney(money));    
		System.out.println(money);

	}
	
	public static String changemoney(long money) {
		
		String[] han1 = { "", "��", "��", "��", "��", "��", "��", "ĥ", "��", "��"};
		String[] han2 = { "õ", "", "��", "��"};
		String[] han3 = { "", "��", "��", "��"};

		String smoney=money+"";
		String moneyHan="";
				
		int len=smoney.length(); 
		
		if(len>16) {
			System.out.println("������ȯ�� �Ұ����մϴ�. õ�������� ������ �߰��Ͻʽÿ�");
			return "�ݾ� ���� ����";
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
		moneyHan += "��";
				
		moneyHan=moneyHan.replace(" ��", "");
		moneyHan=moneyHan.replace(" ��", "");			
				
		return moneyHan;    
	}
	
}


/*///////////////////////////////////ù�ڸ��� ���ڸ��� ���� 1�� �Ϸ� ǥ�� �ϴ� ���//////////////////////////////////////////////////////
 *	if(num==1) {
				if(i!=0 &i!=(smoney.length()-1))
					moneyHan += han1[0];
			}else
	//////////////////////////////////1���θ� �Ϸ� �ް�, ���߿� �ϸ��� ���� �����̻��϶��� ǥ��, ��� ��õ,�Ϲ�,�Ͻ��� õ������� ǥ���ϴ� ���///////////////////////////////////////		
	if(moneyHan.substring(0,2).equals("�ϸ�"))
			moneyHan=moneyHan.replace("�ϸ�", "��");
		moneyHan=moneyHan.replace("��õ", "õ");
		moneyHan=moneyHan.replace("�Ϲ�", "��");
		moneyHan=moneyHan.replace("�Ͻ�", "��");		
			
 *
 */




//////////////////////////////////////������ �̻� �����ڵ� �ִٰ� �־��! 1->'��'��ȯ���� ������������ �����ؼ� ��ȭ���ְ� §��!!!//////////////////////////
///////////////////////////�׷��� ������ �̻� �־����� �����ؼ� §�ٸ�, �� ��ȯ���� ������� �ٲٰ� ���߿� ���� ���ִ°� �����ڵ� ¥�� ����!!//////////////////
/*
		String[] han1 = { "", "��", "��", "��", "��", "��", "��", "ĥ", "��", "��"};
		String[] han2 = { "õ", "", "��", "��"};
		String[] han3 = { "", "��", "��", "��"};
		
		String smoney=money+"";
		String moneyHan="";
				
		int len=smoney.length(); 
		
		if(len>16) {
			System.out.println("������ȯ�� �Ұ����մϴ�. õ�������� ������ �߰��Ͻʽÿ�");
			return "�ݾ� ���� ����";
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
		moneyHan += "��";
		
		moneyHan=moneyHan.replace(" ��", "");
		moneyHan=moneyHan.replace(" ��", "");			
				
		return moneyHan; 
*/