package test;

	import java.util.*;

	public class MoneyHangul2 {

	   public static void main(String[] args) {
	       //han1 : �Էµ� ������ ���� �ѱ۷� ��ȯ
	      //han2 : �Էµ� ������ �ش� ��ġ�� ���� ���� ���� 4�� ���� �������� ��
	      //han3 : �Էµ� ���� ũ�⸦ ��Ʈ�� ����(length)�� Ȯ���Ͽ� ����
	       String[] s_han1 = {"","��","��","��","��","��","��","ĥ","��","��"};
	       String[] s_han2 = {"õ","","��","��"};
	       String[] s_han3 = {"","��","��","��","��"};

	       //Scanner �Է� �� -> ���� ũ������ �̿��Ͽ� int������ ����ȯ
	       Scanner scan = new Scanner(System.in);
	       //int inputNum = scan.nextInt();         //ǥ�� �����
	       System.out.print("input : ");       
	       String s_in = scan.nextLine();           //�Էµ� �� String
	       //int inputNum = Integer.parseInt(s_in); //���� ũ������ �̿��Ͽ� int������ ����ȯ
	       
	       System.out.println("input : "+ s_in);
	       
	       //�Էµ� ������ ����(�ڸ���)�� ������ ���� ã�´�

	       int n_cnt   = 0;                  //�� �� �� �� ���� check������ ���� 0�� ���� ī��Ʈ
	       int n_num    = 0;                        //�迭�� �ε��������� ���
	       int n_len    = s_in.length();            //�迭�� ����
	       String s_rst = "";                       //�ѱ۷� ��ȯ�� ����
	       
	       System.out.println("length : " + n_len + " num : " + n_num);
	       
	       //�Էµ� ���ڴ� ��Ʈ�� ������ �� ���� ��ŭ ���ʿ��� ���������� ã�´�
	       for(int i = 0; i<s_in.length(); i++) {
	          n_num = Integer.parseInt(s_in.substring(i, i+1));
	          //�Էµ� ���� 0�� �ƴ� ��츸 �� ���� ��ȯ�� ������ ���Ѵ�
	          //han2 : �Էµ� ������ �ش� ��ġ�� ���� ���� ���� 4�� ���� �������� ��
	          //       0-õ 1-�� 2-�� 3-���� ����
	          if (n_num != 0 ) {
	              s_rst = s_rst + s_han1[n_num];   //�Էµ� ���� �ѱ۷� ��ȯ
	              n_num = n_len % 4;               //�Էµ� ������ ���� �Ի�
	              s_rst = s_rst + s_han2[n_num];   //��ȯ�� ���� �ڿ� ���� ����
	          } else {
	             n_cnt++;
	          }
	 
	          //������ �� ��ġ�� ���� �ݿ������Ƿ� ���� ��ġ�� ����
	          n_len = n_len - 1;
	          
	          //��, ��, ��, �濡 �ش��ϴ� ������ ���� �⺻(�ε��� ���� 4�ڸ� �̸�)�� ����
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
	          //�������� �ش� �� 0���� 4�ڸ� ������ ���� ���� ���� ��, 0�� �� ī��Ʈ�� 1�� �Ѵ�
	          if (n_num != 0) {
	             if (n_cnt == 5) {
	                n_num = 0;
	             } 
	             n_cnt = 1;
	          }
	          
	          s_rst = s_rst + s_han3[n_num];
	       }
	       //���� �������� ����
	       s_rst = s_rst + "��";
	       System.out.println("Result : "+ s_rst );   
	   }
	}

