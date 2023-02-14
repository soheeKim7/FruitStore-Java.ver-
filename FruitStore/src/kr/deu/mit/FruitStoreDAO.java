package kr.deu.mit;

import java.util.ArrayList;
import java.util.List;

public interface FruitStoreDAO {

	//////// SQL�� ó���� �����
	//////// SQL �������� ����!//////////
	// ���ϵ��
	void insertFruit(FruitVO vo);

	// ���ϸ�� �����ֱ�
	ArrayList<FruitVO> listFruit();

	// ����������Ʈ
	void updateQuantityFruit(FruitVO vo); // ��ȯ�Ǵ� ���ϰ��� ������Ʈ�ϴ°Ŷ� ���� ���ص� �ǰ� �ؔf�� int�� ����Ǵ� ���� ���� �˼� ����

	// ���Ϻ� �Ѱ��� �˷��ֱ�
	int totalFruit(FruitVO vo);

	// �Ǹ�ó��
	// �Ǹų��� �߰� + �Ǹų��� �߰� Ű�� �˾ƿ��� + �������̺� ����(�߰�) + ������ó��
	// �ٰ��� �ؾ� ���� Ʈ����� ���� �ؾ��ϱ⶧���� �ѹ��� �������~
	void insertSales(int fruit_code, int quantity); // ���⼭�� �ǸŰ����� �ϸ�Ǵ°� �� �����Ͱ��� �ϳ��ϱ� ���� sales ��ü �ȸ���� ~!

	// ������ ó�� �����ϰ� �Ǹ�ó�� ���
	void insertSales(ArrayList<Integer> buylist);

	// �ش� �����ڵ��� �����ڵ� ��������
	Integer codeFruit(int fruit_code);

	// �ش� �����ڵ��� ���� ��������
	int quantityFruit(int fruit_code);

	// ���Ǹűݾ�
	long totalPrice(); // mysql���� ������ ¥���ֱ� �������ȿ��� �츮�� �־������ ���� ���⶧����, ()�� ����ִ»��·�! �׳� ���� �̿��ϸ� ��!

	// ���⳻������
	List<SalesVO> listSales(); // array �� ���ѵǼ� ���� ����, list�� �Ѵ����� ����Ϸ��Ҷ� �°� linked�� array�� ���������ϱ⶧���� �а� �����ϴ°� ��
								// ����!

	// ���ں� ������Ȳ
	List<SalesVO> dayByTotalPrice();

	// ���ϻ���...

	// �ѱ۷� �ݾ� ǥ��
	String changemoney(long money);
	
	//���� ȭ�� ������ ����ǥ��
	String checknum(double money);
	
	//���� ū �����ڵ� ��������
		Integer maxFruit_code();
		
	//���� ���� �����ڵ� ��������
	Integer minFruit_code();

}
