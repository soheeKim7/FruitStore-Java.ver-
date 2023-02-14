package kr.deu.mit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FruitStoreDAOImpl implements FruitStoreDAO {
	
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet result=null;
	
	@Override  //과일등록
	public void insertFruit(FruitVO vo) {		
		dbConn();		
		try {					
			pstmt=conn.prepareStatement("insert into fruit(fruit_name,price,quantity) values (?,?,?)");						
			pstmt.setString(1, vo.getFruit_name());    
			pstmt.setInt(2, vo.getPrice());
			pstmt.setInt(3, vo.getQuantity());			
			pstmt.executeUpdate();	
		} catch (Exception e) {    
			e.printStackTrace();
		} finally {				
			dbClose();
		}
	}
	
	@Override  //과일목록 보여주기
	public ArrayList<FruitVO> listFruit() {
		ArrayList<FruitVO> list = new ArrayList<>();
		dbConn();
		try {
			pstmt=conn.prepareStatement("select * from fruit order by fruit_code");
			result=pstmt.executeQuery();
			while(result.next()) {         
				FruitVO vo = new FruitVO();
				vo.setFruit_code(result.getInt("fruit_code"));
				vo.setFruit_name(result.getString("fruit_name"));
				vo.setPrice(result.getInt("price"));
				vo.setQuantity(result.getInt("quantity"));
				list.add(vo);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {				
			dbClose();
		}	
		return list;
	}	
	
	//DB연결
	void dbConn() {		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");
		} catch (Exception e) {
			e.printStackTrace();
		} 			
	}
		
	//DB닫기
	void dbClose() {
		if(result!=null) try { result.close(); } catch (SQLException e) { e.printStackTrace(); }
		if(pstmt !=null) try { pstmt.close();  } catch (SQLException e) { e.printStackTrace(); }
		if(conn  !=null) try { conn.close();   } catch (SQLException e) { e.printStackTrace(); }
	}

	@Override  //수량 업데이트
	public void updateQuantityFruit(FruitVO vo) {
		dbConn();
		try {
			pstmt=conn.prepareStatement("update fruit set quantity=quantity+? where fruit_code=?");
			pstmt.setInt(1, vo.getQuantity());
			pstmt.setInt(2, vo.getFruit_code());			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();				
		}					
	}

	@Override  //과일별 총가격 알려주기
	public int totalFruit(FruitVO vo) {
		dbConn();
		int total=-1;    
		try {
			pstmt=conn.prepareStatement("select price*? from fruit where fruit_code=?");
			pstmt.setInt(1, vo.getQuantity());
			pstmt.setInt(2, vo.getFruit_code());    
			result=pstmt.executeQuery();
			result.next(); 
			total=result.getInt(1);   
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();	
		}
		return total;
	}

	@Override  //한개의 과일을 판매처리
	public void insertSales(int fruit_code,int quantity) {
		dbConn();
		try { conn.setAutoCommit(false); } catch (SQLException e1) { e1.printStackTrace(); }   
		
		try {
			pstmt=conn.prepareStatement("insert into sales(sales_quantity) value(?)");
			pstmt.setInt(1, quantity);
			pstmt.executeUpdate();
			
			result=pstmt.executeQuery("select last_insert_id()");   //입력된 키값 확인
			result.next();
			int key=result.getInt(1);			
		
			pstmt.close();  
			pstmt=conn.prepareStatement("insert into fruit_has_sales values(?,?)");  
			pstmt.setInt(1, fruit_code);
			pstmt.setInt(2, key);			
			pstmt.executeUpdate();
									
			pstmt.close();
			pstmt=conn.prepareStatement("update fruit set quantity=quantity-? where fruit_code=?");  
			pstmt.setInt(1, quantity);
			pstmt.setInt(2, fruit_code);			
			pstmt.executeUpdate();
			
			conn.commit();   
			
		} catch (SQLException e) {
			System.out.println("판매실패");
			try { conn.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }  //중간에 문제가 생기면 롤백
			e.printStackTrace();
		} finally {
			dbClose();	
		}
	}
	
	@Override  //여러개의 과일 판매처리
	public void insertSales(ArrayList<Integer> buylist) {
		dbConn();
		try { conn.setAutoCommit(false); } catch (SQLException e1) { e1.printStackTrace();}   
		
		for(int i=0; i<buylist.size();i+=2) {
			try {
				pstmt=conn.prepareStatement("insert into sales(sales_quantity) value(?)");
				pstmt.setInt(1, buylist.get(i+1));
				pstmt.executeUpdate();
				
				result=pstmt.executeQuery("select last_insert_id()");   //입력된 키값 확인
				result.next();
				int key=result.getInt(1);
				
				pstmt.close(); 
				pstmt=conn.prepareStatement("insert into fruit_has_sales values(?,?)");  
				pstmt.setInt(1, buylist.get(i));
				pstmt.setInt(2, key);				
				pstmt.executeUpdate();
			
				pstmt.close();
				pstmt=conn.prepareStatement("update fruit set quantity=quantity-? where fruit_code=?");  
				pstmt.setInt(1, buylist.get(i+1));
				pstmt.setInt(2, buylist.get(i));			
				pstmt.executeUpdate();				
				
			} catch (SQLException e) {
				System.out.println("판매실패");
				try { conn.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }  //중간에 문제가 생기면 롤백
				e.printStackTrace();
			} 
		}
		try { conn.commit(); } catch (SQLException e) {	e.printStackTrace(); } finally { dbClose(); }
	}
	
	@Override  //해당 과일코드의 과일코드 가져오기
	public Integer codeFruit(int fruit_code) {
		dbConn();
		Integer code=-1;
		try {
			pstmt=conn.prepareStatement("select fruit_code from fruit where fruit_code=?");
			pstmt.setInt(1, fruit_code);
			result=pstmt.executeQuery();
			result.next();
			code=result.getInt(1);
		} catch (SQLException e) {
			code=null;
		}finally {
			dbClose();
		}
		return code;
	}

	@Override  //해당 과일코드의 수량 가져오기
	public int quantityFruit(int fruit_code) {
		dbConn();
		int quantity=-1;
		try {
			pstmt=conn.prepareStatement("select quantity from fruit where fruit_code=?");
			pstmt.setInt(1, fruit_code);
			result=pstmt.executeQuery();
			result.next();
			quantity=result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return quantity;
	}

	@Override   //총 판매금액
	public long totalPrice() {
		dbConn();
		long totalPrice=-1;
		try {
			String st ="select sum(price*sales_quantity) " + 
					"from fruit join" + 
					"(select  fruit_fruit_code, sales_date, sales_quantity " + 
					"from fruit_has_sales join sales on fruit_has_sales.sales_sales_code=sales.sales_code) t1 " + 
					"on fruit.fruit_code=t1.fruit_fruit_code";
			pstmt=conn.prepareStatement(st);
			result=pstmt.executeQuery();  //쿼리를 수행해서 결과를 가져오고
			result.next();  //결과에 첫번째 행을 가르키고
			totalPrice=result.getLong(1); //첫번째 속성값을 long타입으로 변환해서 읽어온다.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}		
		return totalPrice;
	}

	@Override  //매출내역 보기
	public List<SalesVO> listSales() {
		ArrayList<SalesVO> list = new ArrayList<>();
		dbConn();
		try {
			pstmt=conn.prepareStatement("select fruit_name,fruit_code,sales_date, sales_quantity,price*sales_quantity " + 
					"from fruit join" + 
					"(select  fruit_fruit_code, sales_date, sales_quantity " + 
					"from fruit_has_sales join sales on fruit_has_sales.sales_sales_code=sales.sales_code) t1 " + 
					"on fruit.fruit_code=t1.fruit_fruit_code");
			result=pstmt.executeQuery();
			while(result.next()) {
				SalesVO vo = new SalesVO();
				vo.setFruit_name(result.getString("fruit_name"));
				vo.setFruit_code(result.getInt("fruit_code"));
				vo.setSales_date(result.getDate("sales_date"));
				vo.setSales_quantity(result.getInt("sales_quantity"));
				vo.setTotal(result.getInt("price*sales_quantity"));
				list.add(vo);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}			
		return list;
	}

	@Override  //일자별 매출현황
	public List<SalesVO> dayByTotalPrice() {
		dbConn();
		ArrayList<SalesVO> list = new ArrayList<>();
		try {
			pstmt=conn.prepareStatement("select date_format(sales_date,'%Y-%m-%d') sales_date2,sum(price*sales_quantity) sales_price " + 
					"from fruit join " + 
					"(select  fruit_fruit_code, sales_date, sales_quantity " + 
					"from fruit_has_sales join sales on fruit_has_sales.sales_sales_code=sales.sales_code) t1 " + 
					"on fruit.fruit_code=t1.fruit_fruit_code " + 
					"group by 1");
			result=pstmt.executeQuery();
			while(result.next()) {
				SalesVO vo= new SalesVO();
				vo.setSales_date(result.getDate("sales_date2"));
				vo.setTotal(result.getInt("sales_price"));
				list.add(vo);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;		
	}	
	
	@Override  //한글로 금액 표시
	public String changemoney(long money) {
		
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
		moneyHan += " 원";

		if(moneyHan.substring(0,2).equals("일만"))
			moneyHan=moneyHan.replace("일만", "만");
		moneyHan=moneyHan.replace("일천", "천");
		moneyHan=moneyHan.replace("일백", "백");
		moneyHan=moneyHan.replace("일십", "십");
		moneyHan=moneyHan.replace(" 만", "");
		moneyHan=moneyHan.replace(" 억", "");			
				
		return moneyHan;    		
	}

	@Override
	public String checknum(double money) {
		DecimalFormat dc = new DecimalFormat("###,###,###,###,###,###");
		String num=dc.format(money);
		return num;
	}
	
	@Override  //가장 큰 과일코드 가져오기
	public Integer maxFruit_code() {
		dbConn();
		Integer code=-1;
		try {
			pstmt=conn.prepareStatement("select max(fruit_code) from fruit");
			result=pstmt.executeQuery();
			result.next();
			code=result.getInt(1);
		} catch (SQLException e) {
			code=null;
		}finally {
			dbClose();
		}
		return code;
	}

	@Override  //가장 작은 과일코드 가져오기
	public Integer minFruit_code() {
		dbConn();
		Integer code=-1;
		try {
			pstmt=conn.prepareStatement("select min(fruit_code) from fruit");
			result=pstmt.executeQuery();
			result.next();
			code=result.getInt(1);
		} catch (SQLException e) {
			code=null;
		}finally {
			dbClose();
		}
		return code;
	}
	
}




/*/////////////////////////////////////////////////<try catch 합치기 전!>///////////////////////////////////////////////////////
		//1. DB연결
		//1-1. JDBC 드라이버 로드
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");       //그 해당 드라이버 이름 com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver 로 바뀜!!!!
			System.out.println("드라이버 로딩성공");
		} catch (Exception e) {
			System.out.println("드라이버 로딩실패");      // 실패시 1. 드라이버 이름 잘못 됨  /2. 라이버리를 해당 드라이버를 추가 안해서
			e.printStackTrace();
		}		
		//1-2. 연결해서 Connection 객체생성
		Connection conn=null;
		try {
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");  
			//본인 컴퓨터 ip를 지칭하는 ip = 127.0.0.1 / 포트값/기본스키마
			//리턴값이 커넥션 객체!! 그래서 커넥션 객체로 받는다
			//만약 오라클이면 다르게 주소 받는다! 특히 기본스키마 설정은 애초에 설정해서 하기 때문에 mydb같은거 쓰는거 없음
			//jdbc:oracle:thin:@localhost:1521:xe
			//xe->오라클 만들때 보이는 sid 값! 그래서 모든 오라클의 고정값이 아니다! sql학습1의 p8에 보면, 검색하는 내용 나옴!
			System.out.println("연결성공");
		} catch (SQLException e) {
			System.out.println("연결실패");
			e.printStackTrace();
		}    
				
		//2. 쿼리작업  
		//2-1. 커넥션 객체를 가지고 Statement 객체생성
		Statement stmt=null;
		try {
			stmt=conn.createStatement();
			System.out.println("스테이트먼트 객체 성공");
		} catch (SQLException e) {
			System.out.println("스테이트먼트 객체 실패");
			e.printStackTrace();
		}
		//2-2. 스테이트먼트 객체를 가지고 query 작업
		try {
			String name=vo.getFruit_name();
			int price=vo.getPrice();
			int quantity=vo.getQuantity();
					
			String query=("insert into fruit(fruit_name,price,quantity) values ('"+name+"',"+price+ ","+quantity+")");
			System.out.println("쿼리확인"+query);
			int count=stmt.executeUpdate(query);
			System.out.println("쿼리수행 성공 처리된행의 개수 "+count);
		} catch (SQLException e) {
			System.out.println("쿼리수행 실패");
			e.printStackTrace();
		}  
		// 만약 int 로 받아서 처리하면, 그 해당쿼리가 처리된 행의 개수!! 가 나옴 
		// 해당쿼리를 입력시 ;를 제외하고!!
		//삽입,삭제,수정, c,u,d
		//     select 문의 결과는 ResultSet 객체로 받아서 작업
		
		// 커넥션->스테이트먼트-> 리절트 순으로 객체 생성 (끊는건 반대로!!)
*/

/*////////////////////////////////////////<try catch 합치기 전!>///////////////////////////////////////////
 * //3. 사용후 DB연결 끊기
		//  ResultSet,Statement,Connection 객체 닫아주기 
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 보통 스테이트먼트 오류는 널값일때 에러가 나고, 하지만 그뒤 커넥션은 닫힘. 그래서 문제는 없음. 
		// 그리고 스테이트먼트가 있는데 오류가 나면 커넥션도 안닫히게 되어있음.
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
 */

/*/////////////////////////<try catch 합친 후!> !! 객체 닫을때는 합치면 안돼!! 따로해야함////////////////////////////////////////////
 * //1. DB연결
		//1-1. JDBC 드라이버 로드
		//1-2. 연결해서 Connection 객체생성
		//2. 쿼리작업  
		//2-1. 커넥션 객체를 가지고 Statement 객체생성
		//2-2. 스테이트먼트 객체를 가지고 query 작업
		Connection conn=null;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");      //그 해당 드라이버 이름 com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver 로 바뀜!!!!
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");  
			//본인 컴퓨터 ip를 지칭하는 ip = 127.0.0.1 / 포트값/기본스키마
			//리턴값이 커넥션 객체!! 그래서 커넥션 객체로 받는다
			stmt=conn.createStatement();
			
			String name=vo.getFruit_name();
			int price=vo.getPrice();
			int quantity=vo.getQuantity();					
			String query=("insert into fruit(fruit_name,price,quantity) values ('"+name+"',"+price+ ","+quantity+")");
			stmt.executeUpdate(query);
			
		} catch (Exception e) {
			System.out.println("연결실패");      
			e.printStackTrace();
		}		
		
		// 만약 int 로 받아서 처리하면, 그 해당쿼리가 처리된 행의 개수!! 가 나옴 
		// 해당쿼리를 입력시 ;를 제외하고!!
		//삽입,삭제,수정, c,u,d
		//     select 문의 결과는 ResultSet 객체로 받아서 작업
		//저기 스테이트먼트 쿼리에서  해킹의 우려가 있다 => SQL injection 이라는 해킹에 우려가 있다 
		//서브쿼리같은 걸 따로 넣을수 있는데, 그럴때 해킹이.. 가능한 .. 경우가 발생한다.
		//그래서 요즘에 스테이트먼트 안쓴다!!!
		
		// 커넥션->스테이트먼트-> 리절트 순으로 객체 생성 (끊는건 반대로!!)	
 * 
 */
/*////////////////<try catch 합치고, 서브쿼리로 해킹 방어로 커넥션에 넣는 데이터값들 정해주기(프리페어드스테이트먼트!!)>////////////////////////
 		Connection conn=null;
		PreparedStatement stmt=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");      //그 해당 드라이버 이름 com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver 로 바뀜!!!!
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");  
			//본인 컴퓨터 ip를 지칭하는 ip = 127.0.0.1 / 포트값/기본스키마
			//리턴값이 커넥션 객체!! 그래서 커넥션 객체로 받는다
			stmt=conn.prepareStatement("insert into fruit(fruit_name,price,quantity) values (?,?,?)");						
			stmt.setString(1, vo.getFruit_name());    //? 채우기
			stmt.setInt(2, vo.getPrice());
			stmt.setInt(3, vo.getQuantity());
			// 이렇게 설정을 하게되면 저안에 들어가는 값들의 타입들이 정해지기 때문에 서브쿼리들이 들어가지 못함! 그래서 해킹방어!!
			
			stmt.executeUpdate();	//삽입,삭제,수정시에는 executeUpdate()를   ->반환값 int 처리된 행의 개수
									//read(select)시에는 executeQuery()를 이용	->반환값 ResultSet 객체를 결과값을 돌려준다.	
		} catch (Exception e) {
			System.out.println("DB연결실패");      
			e.printStackTrace();
		}		
		
		// 만약 int 로 받아서 처리하면, 그 해당쿼리가 처리된 행의 개수!! 가 나옴 
		// 해당쿼리를 입력시 ;를 제외하고!!
		//삽입,삭제,수정, c,u,d   //crud
		//     r,select 문의 결과는 ResultSet 객체로 받아서 작업
		//저기 스테이트먼트 쿼리에서  해킹의 우려가 있다 => SQL injection 이라는 해킹에 우려가 있다 
		//서브쿼리같은 걸 따로 넣을수 있는데, 그럴때 해킹이.. 가능한 .. 경우가 발생한다.
		//그래서 요즘에 스테이트먼트 안쓴다!!!
		
		// 커넥션->스테이트먼트-> 리절트 순으로 객체 생성 (끊는건 반대로!!)		
				
		//3. 사용후 DB연결 끊기
		//  ResultSet,Statement,Connection 객체 닫아주기 
		if(stmt!=null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
		// 보통 스테이트먼트 오류는 널값일때 에러가 나고, 하지만 그뒤 커넥션은 닫힘. 그래서 문제는 없음. 
		// 그리고 스테이트먼트가 있는데 오류가 나면 커넥션도 안닫히게 되어있음.
		if(conn!=null)try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
 *
 */
 	
/*
 * 			while(rs.next()) {         //next() 다음행을 가르킴, 리턴은 다음행가르키는 게 성공이면 true 없으면 false
//				int code=rs.getInt(1);      // 여기서 1은 행이 아니라 그 찍혀나오는 속성인 열의 값! 1이니까 그 코드값만 나옴!!
				int code=rs.getInt("fruit_code");   //여기서 rs.getInt() 메소드가 안에 들어가는 값이 인덱스와 열의이름(문자) 로 가능! 
				//하지만 여기서 리턴값이 자체가 int 이기때문에(getInt) 그래서 int code로 받은겨!! 
				String name=rs.getString("fruit_name");
				int price=rs.getInt("price");
				int quantity=rs.getInt("quantity");
				//System.out.println(code+" "+name+" "+price+" "+quantity);
				
				FruitVO vo = new FruitVO();
				vo.setFruit_code(code);
				vo.setFruit_name(name);
				vo.setPrice(price);
				vo.setQuantity(quantity);
				list.add(vo);
								
			}
 */

/*    /////////////////////////////////////////숫자 한글변화 처음에 했던거, 2백 3천.. 0백 이런식/////////////////////////////////////////////////////////
 * public String changemoney(long money) {
		long cheoneok=money/100000000000L;
		long baeckeok=(money%100000000000L)/10000000000L;
		long sipeok=(money%10000000000L)/1000000000;
		long eok=(money%1000000000)/100000000;
		long cheonman=(money%100000000)/10000000;
		long baeckman=(money%10000000)/1000000;
		long sipman=(money%1000000)/100000;
		long man=(money%100000)/10000;
		long cheon=(money%10000)/1000;
		long baeck=(money%1000)/100;
		long sip=(money%100)/10;
		String result="";
		
		if(money>=100000000000L)
			result=cheoneok+"천"+baeckeok+"백"+sipeok+"십"+eok+"억 "+cheonman+"천"+baeckman+"백"+sipman+"십"+man+"만 "+cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=10000000000L)
			result=baeckeok+"백"+sipeok+"십"+eok+"억 "+cheonman+"천"+baeckman+"백"+sipman+"십"+man+"만 "+cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=1000000000) 
			result=sipeok+"십"+eok+"억 "+cheonman+"천"+baeckman+"백"+sipman+"십"+man+"만 "+cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=100000000) 
			result=eok+"억 "+cheonman+"천"+baeckman+"백"+sipman+"십"+man+"만 "+cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=10000000) 
			result=cheonman+"천"+baeckman+"백"+sipman+"십"+man+"만 "+cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=1000000) 
			result=baeckman+"백"+sipman+"십"+man+"만 "+cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=100000) 
			result=sipman+"십"+man+"만 "+cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=10000) 
			result=man+"만 "+cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=1000) 
			result=cheon+"천"+baeck+"백"+sip+"십원";
		else if(money>=100) 
			result=baeck+"백"+sip+"십원";
		else if(money>=10) 
			result=sip+"십원";
		
		return result;
	}
 */
 

