package test;
public class MoneyHan {

	public static void main(String[] args) {
		long money = 100000000L;
		System.out.println(changemoney(money));    
		System.out.println(money);		
	}

	private static String changemoney(long money) {
		String[] han1 = { "", "��", "��", "��", "��", "��", "��", "ĥ", "��", "��"};
		String[] han2 = { "", "��", "��", "õ"};
		String[] han3 = { " ��" ,"�� ", "�� ", "�� " };
		
		String lastHan="";
		
		String[] stringArrayMoney=(money+"").split(""); //���ڸ� ���ڷ� ��ȯ�� �ٽ� �ѱ��ھ� ���ڿ��� �迭�� ����
		//�ڸ����� ���ٸ� +1���� �������ϱ�
		int lengthMoney=stringArrayMoney.length;
		int unitMoney=lengthMoney/4-1; //������ 4�ڸ���
		
		if( lengthMoney %4 != 0)
			unitMoney++;	//�ݾ��� �� �� ��... ���� �� �ȶ������� �� ������ �� �������� 
		                    // ex>7�ڸ��̸� ..��...��(2��)   , 4�ڸ��̸� ...��(1��)  ���� ǥ��Ǿ����		
		//����ó��
		if(unitMoney>=han3.length) {
			System.out.println("��ȯ�Ұ� han3�� ���� ������ �߰��� �ּ���");
			return "�ݾ׿���";
		}
		
		for(int i =unitMoney ; i>=0 ;i--) {  //�ݾ��� �ѱ۴����� 4�ڸ��� ������ �ڸ��� ��ŭ �ݺ�			
			int j=3;
			if(i==unitMoney) {  //ù��° ������ 4�ڸ����� �ƴҼ� ��������
				j=lengthMoney-(unitMoney*4)-1; //-1�� index�� 0���� ����
			}
			
			boolean isUnit=false; //4�ڸ��� ���� �����ϴ��� ex>0000 -> false , ���� �ϳ��� ������ true 
			
			for( ; j>=0; j--) {  //õ����� ���� ���� Ȯ��
							
				String hanNum=han1[Integer.parseInt(stringArrayMoney[(lengthMoney-1)-(4*i+j)])];//�ѱۺ�ȯ ���� �ڸ��� ���� ���
				lastHan+=hanNum;
								
				if(!hanNum.equals("")) { //0�� �ƴϰ�
					isUnit=true;
					if(j!=0) //������ ������ ���� (õ��ʱ����� �� ���ʿ�)
						lastHan+=han2[j];
				}
			}
			if(isUnit)
				lastHan+=han3[i];			
		}		
		if(!lastHan.contains(" ��"))
			lastHan+=han3[0];
						
		lastHan=lastHan.replace("  ", " ");
		
		return lastHan;
	}
}

/*
 * if(lastHan.substring(0,2).equals("�ϸ�"))
			lastHan=lastHan.replace("�ϸ�", "��"); //'�ϸ�'���� �����Ҷ��� '��'���� 	
		
		lastHan=lastHan.replace("��õ", "õ"); //'��õ' �������� ���� 'õ'����
		lastHan=lastHan.replace("�Ϲ�", "��"); //'�Ϲ�' �������� ���� '��'����
		lastHan=lastHan.replace("�Ͻ�", "��");
 */



