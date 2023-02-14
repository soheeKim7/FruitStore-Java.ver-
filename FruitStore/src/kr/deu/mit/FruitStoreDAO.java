package kr.deu.mit;

import java.util.ArrayList;
import java.util.List;

public interface FruitStoreDAO {

	//////// SQL로 처리할 내용들
	//////// SQL 쿼리별로 구현!//////////
	// 과일등록
	void insertFruit(FruitVO vo);

	// 과일목록 보여주기
	ArrayList<FruitVO> listFruit();

	// 수량업데이트
	void updateQuantityFruit(FruitVO vo); // 반환되는 리턴값은 업데이트하는거라 굳이 안해도 되고 해봣자 int로 수행되는 행의 개수 알수 있음

	// 과일별 총가격 알려주기
	int totalFruit(FruitVO vo);

	// 판매처리
	// 판매내용 추가 + 판매내용 추가 키값 알아오기 + 교차테이블 갱신(추가) + 재고수정처리
	// 다같이 해야 같이 트랜잭션 으로 해야하기때문에 한번에 묶어야해~
	void insertSales(int fruit_code, int quantity); // 여기서는 판매개수만 하면되는게 그 데이터값이 하나니까 굳이 sales 객체 안만들고 ~!

	// 여러번 처리 가능하게 판매처리 기능
	void insertSales(ArrayList<Integer> buylist);

	// 해당 과일코드의 과일코드 가져오기
	Integer codeFruit(int fruit_code);

	// 해당 과일코드의 수량 가져오기
	int quantityFruit(int fruit_code);

	// 총판매금액
	long totalPrice(); // mysql에서 쿼리가 짜여있구 그쿼리안에서 우리가 넣어줘야할 값이 없기때문에, ()은 비어있는상태로! 그냥 쿼리 이용하면 됨!

	// 매출내역보기
	List<SalesVO> listSales(); // array 로 국한되서 받지 말고, list로 한다음에 사용하려할때 맞게 linked나 array로 설정가능하기때문에 넓게 설정하는게 더
								// 좋아!

	// 일자별 매출현황
	List<SalesVO> dayByTotalPrice();

	// 과일삭제...

	// 한글로 금액 표시
	String changemoney(long money);
	
	//숫자 화폐 단위로 끊어표시
	String checknum(double money);
	
	//가장 큰 과일코드 가져오기
		Integer maxFruit_code();
		
	//가장 작은 과일코드 가져오기
	Integer minFruit_code();

}
