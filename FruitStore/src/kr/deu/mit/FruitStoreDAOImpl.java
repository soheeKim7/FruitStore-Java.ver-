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
	
	@Override  //���ϵ��
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
	
	@Override  //���ϸ�� �����ֱ�
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
	
	//DB����
	void dbConn() {		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");
		} catch (Exception e) {
			e.printStackTrace();
		} 			
	}
		
	//DB�ݱ�
	void dbClose() {
		if(result!=null) try { result.close(); } catch (SQLException e) { e.printStackTrace(); }
		if(pstmt !=null) try { pstmt.close();  } catch (SQLException e) { e.printStackTrace(); }
		if(conn  !=null) try { conn.close();   } catch (SQLException e) { e.printStackTrace(); }
	}

	@Override  //���� ������Ʈ
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

	@Override  //���Ϻ� �Ѱ��� �˷��ֱ�
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

	@Override  //�Ѱ��� ������ �Ǹ�ó��
	public void insertSales(int fruit_code,int quantity) {
		dbConn();
		try { conn.setAutoCommit(false); } catch (SQLException e1) { e1.printStackTrace(); }   
		
		try {
			pstmt=conn.prepareStatement("insert into sales(sales_quantity) value(?)");
			pstmt.setInt(1, quantity);
			pstmt.executeUpdate();
			
			result=pstmt.executeQuery("select last_insert_id()");   //�Էµ� Ű�� Ȯ��
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
			System.out.println("�ǸŽ���");
			try { conn.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }  //�߰��� ������ ����� �ѹ�
			e.printStackTrace();
		} finally {
			dbClose();	
		}
	}
	
	@Override  //�������� ���� �Ǹ�ó��
	public void insertSales(ArrayList<Integer> buylist) {
		dbConn();
		try { conn.setAutoCommit(false); } catch (SQLException e1) { e1.printStackTrace();}   
		
		for(int i=0; i<buylist.size();i+=2) {
			try {
				pstmt=conn.prepareStatement("insert into sales(sales_quantity) value(?)");
				pstmt.setInt(1, buylist.get(i+1));
				pstmt.executeUpdate();
				
				result=pstmt.executeQuery("select last_insert_id()");   //�Էµ� Ű�� Ȯ��
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
				System.out.println("�ǸŽ���");
				try { conn.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }  //�߰��� ������ ����� �ѹ�
				e.printStackTrace();
			} 
		}
		try { conn.commit(); } catch (SQLException e) {	e.printStackTrace(); } finally { dbClose(); }
	}
	
	@Override  //�ش� �����ڵ��� �����ڵ� ��������
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

	@Override  //�ش� �����ڵ��� ���� ��������
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

	@Override   //�� �Ǹűݾ�
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
			result=pstmt.executeQuery();  //������ �����ؼ� ����� ��������
			result.next();  //����� ù��° ���� ����Ű��
			totalPrice=result.getLong(1); //ù��° �Ӽ����� longŸ������ ��ȯ�ؼ� �о�´�.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}		
		return totalPrice;
	}

	@Override  //���⳻�� ����
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

	@Override  //���ں� ������Ȳ
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
	
	@Override  //�ѱ۷� �ݾ� ǥ��
	public String changemoney(long money) {
		
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
		moneyHan += " ��";

		if(moneyHan.substring(0,2).equals("�ϸ�"))
			moneyHan=moneyHan.replace("�ϸ�", "��");
		moneyHan=moneyHan.replace("��õ", "õ");
		moneyHan=moneyHan.replace("�Ϲ�", "��");
		moneyHan=moneyHan.replace("�Ͻ�", "��");
		moneyHan=moneyHan.replace(" ��", "");
		moneyHan=moneyHan.replace(" ��", "");			
				
		return moneyHan;    		
	}

	@Override
	public String checknum(double money) {
		DecimalFormat dc = new DecimalFormat("###,###,###,###,###,###");
		String num=dc.format(money);
		return num;
	}
	
	@Override  //���� ū �����ڵ� ��������
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

	@Override  //���� ���� �����ڵ� ��������
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




/*/////////////////////////////////////////////////<try catch ��ġ�� ��!>///////////////////////////////////////////////////////
		//1. DB����
		//1-1. JDBC ����̹� �ε�
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");       //�� �ش� ����̹� �̸� com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver �� �ٲ�!!!!
			System.out.println("����̹� �ε�����");
		} catch (Exception e) {
			System.out.println("����̹� �ε�����");      // ���н� 1. ����̹� �̸� �߸� ��  /2. ���̹����� �ش� ����̹��� �߰� ���ؼ�
			e.printStackTrace();
		}		
		//1-2. �����ؼ� Connection ��ü����
		Connection conn=null;
		try {
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");  
			//���� ��ǻ�� ip�� ��Ī�ϴ� ip = 127.0.0.1 / ��Ʈ��/�⺻��Ű��
			//���ϰ��� Ŀ�ؼ� ��ü!! �׷��� Ŀ�ؼ� ��ü�� �޴´�
			//���� ����Ŭ�̸� �ٸ��� �ּ� �޴´�! Ư�� �⺻��Ű�� ������ ���ʿ� �����ؼ� �ϱ� ������ mydb������ ���°� ����
			//jdbc:oracle:thin:@localhost:1521:xe
			//xe->����Ŭ ���鶧 ���̴� sid ��! �׷��� ��� ����Ŭ�� �������� �ƴϴ�! sql�н�1�� p8�� ����, �˻��ϴ� ���� ����!
			System.out.println("���Ἲ��");
		} catch (SQLException e) {
			System.out.println("�������");
			e.printStackTrace();
		}    
				
		//2. �����۾�  
		//2-1. Ŀ�ؼ� ��ü�� ������ Statement ��ü����
		Statement stmt=null;
		try {
			stmt=conn.createStatement();
			System.out.println("������Ʈ��Ʈ ��ü ����");
		} catch (SQLException e) {
			System.out.println("������Ʈ��Ʈ ��ü ����");
			e.printStackTrace();
		}
		//2-2. ������Ʈ��Ʈ ��ü�� ������ query �۾�
		try {
			String name=vo.getFruit_name();
			int price=vo.getPrice();
			int quantity=vo.getQuantity();
					
			String query=("insert into fruit(fruit_name,price,quantity) values ('"+name+"',"+price+ ","+quantity+")");
			System.out.println("����Ȯ��"+query);
			int count=stmt.executeUpdate(query);
			System.out.println("�������� ���� ó�������� ���� "+count);
		} catch (SQLException e) {
			System.out.println("�������� ����");
			e.printStackTrace();
		}  
		// ���� int �� �޾Ƽ� ó���ϸ�, �� �ش������� ó���� ���� ����!! �� ���� 
		// �ش������� �Է½� ;�� �����ϰ�!!
		//����,����,����, c,u,d
		//     select ���� ����� ResultSet ��ü�� �޾Ƽ� �۾�
		
		// Ŀ�ؼ�->������Ʈ��Ʈ-> ����Ʈ ������ ��ü ���� (���°� �ݴ��!!)
*/

/*////////////////////////////////////////<try catch ��ġ�� ��!>///////////////////////////////////////////
 * //3. ����� DB���� ����
		//  ResultSet,Statement,Connection ��ü �ݾ��ֱ� 
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// ���� ������Ʈ��Ʈ ������ �ΰ��϶� ������ ����, ������ �׵� Ŀ�ؼ��� ����. �׷��� ������ ����. 
		// �׸��� ������Ʈ��Ʈ�� �ִµ� ������ ���� Ŀ�ؼǵ� �ȴ����� �Ǿ�����.
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
 */

/*/////////////////////////<try catch ��ģ ��!> !! ��ü �������� ��ġ�� �ȵ�!! �����ؾ���////////////////////////////////////////////
 * //1. DB����
		//1-1. JDBC ����̹� �ε�
		//1-2. �����ؼ� Connection ��ü����
		//2. �����۾�  
		//2-1. Ŀ�ؼ� ��ü�� ������ Statement ��ü����
		//2-2. ������Ʈ��Ʈ ��ü�� ������ query �۾�
		Connection conn=null;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");      //�� �ش� ����̹� �̸� com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver �� �ٲ�!!!!
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");  
			//���� ��ǻ�� ip�� ��Ī�ϴ� ip = 127.0.0.1 / ��Ʈ��/�⺻��Ű��
			//���ϰ��� Ŀ�ؼ� ��ü!! �׷��� Ŀ�ؼ� ��ü�� �޴´�
			stmt=conn.createStatement();
			
			String name=vo.getFruit_name();
			int price=vo.getPrice();
			int quantity=vo.getQuantity();					
			String query=("insert into fruit(fruit_name,price,quantity) values ('"+name+"',"+price+ ","+quantity+")");
			stmt.executeUpdate(query);
			
		} catch (Exception e) {
			System.out.println("�������");      
			e.printStackTrace();
		}		
		
		// ���� int �� �޾Ƽ� ó���ϸ�, �� �ش������� ó���� ���� ����!! �� ���� 
		// �ش������� �Է½� ;�� �����ϰ�!!
		//����,����,����, c,u,d
		//     select ���� ����� ResultSet ��ü�� �޾Ƽ� �۾�
		//���� ������Ʈ��Ʈ ��������  ��ŷ�� ����� �ִ� => SQL injection �̶�� ��ŷ�� ����� �ִ� 
		//������������ �� ���� ������ �ִµ�, �׷��� ��ŷ��.. ������ .. ��찡 �߻��Ѵ�.
		//�׷��� ���� ������Ʈ��Ʈ �Ⱦ���!!!
		
		// Ŀ�ؼ�->������Ʈ��Ʈ-> ����Ʈ ������ ��ü ���� (���°� �ݴ��!!)	
 * 
 */
/*////////////////<try catch ��ġ��, ���������� ��ŷ ���� Ŀ�ؼǿ� �ִ� �����Ͱ��� �����ֱ�(�������彺����Ʈ��Ʈ!!)>////////////////////////
 		Connection conn=null;
		PreparedStatement stmt=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");      //�� �ش� ����̹� �̸� com.mysql.jdbc.Driver -> com.mysql.cj.jdbc.Driver �� �ٲ�!!!!
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");  
			//���� ��ǻ�� ip�� ��Ī�ϴ� ip = 127.0.0.1 / ��Ʈ��/�⺻��Ű��
			//���ϰ��� Ŀ�ؼ� ��ü!! �׷��� Ŀ�ؼ� ��ü�� �޴´�
			stmt=conn.prepareStatement("insert into fruit(fruit_name,price,quantity) values (?,?,?)");						
			stmt.setString(1, vo.getFruit_name());    //? ä���
			stmt.setInt(2, vo.getPrice());
			stmt.setInt(3, vo.getQuantity());
			// �̷��� ������ �ϰԵǸ� ���ȿ� ���� ������ Ÿ�Ե��� �������� ������ ������������ ���� ����! �׷��� ��ŷ���!!
			
			stmt.executeUpdate();	//����,����,�����ÿ��� executeUpdate()��   ->��ȯ�� int ó���� ���� ����
									//read(select)�ÿ��� executeQuery()�� �̿�	->��ȯ�� ResultSet ��ü�� ������� �����ش�.	
		} catch (Exception e) {
			System.out.println("DB�������");      
			e.printStackTrace();
		}		
		
		// ���� int �� �޾Ƽ� ó���ϸ�, �� �ش������� ó���� ���� ����!! �� ���� 
		// �ش������� �Է½� ;�� �����ϰ�!!
		//����,����,����, c,u,d   //crud
		//     r,select ���� ����� ResultSet ��ü�� �޾Ƽ� �۾�
		//���� ������Ʈ��Ʈ ��������  ��ŷ�� ����� �ִ� => SQL injection �̶�� ��ŷ�� ����� �ִ� 
		//������������ �� ���� ������ �ִµ�, �׷��� ��ŷ��.. ������ .. ��찡 �߻��Ѵ�.
		//�׷��� ���� ������Ʈ��Ʈ �Ⱦ���!!!
		
		// Ŀ�ؼ�->������Ʈ��Ʈ-> ����Ʈ ������ ��ü ���� (���°� �ݴ��!!)		
				
		//3. ����� DB���� ����
		//  ResultSet,Statement,Connection ��ü �ݾ��ֱ� 
		if(stmt!=null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
		// ���� ������Ʈ��Ʈ ������ �ΰ��϶� ������ ����, ������ �׵� Ŀ�ؼ��� ����. �׷��� ������ ����. 
		// �׸��� ������Ʈ��Ʈ�� �ִµ� ������ ���� Ŀ�ؼǵ� �ȴ����� �Ǿ�����.
		if(conn!=null)try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
 *
 */
 	
/*
 * 			while(rs.next()) {         //next() �������� ����Ŵ, ������ �����డ��Ű�� �� �����̸� true ������ false
//				int code=rs.getInt(1);      // ���⼭ 1�� ���� �ƴ϶� �� ���������� �Ӽ��� ���� ��! 1�̴ϱ� �� �ڵ尪�� ����!!
				int code=rs.getInt("fruit_code");   //���⼭ rs.getInt() �޼ҵ尡 �ȿ� ���� ���� �ε����� �����̸�(����) �� ����! 
				//������ ���⼭ ���ϰ��� ��ü�� int �̱⶧����(getInt) �׷��� int code�� ������!! 
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

/*    /////////////////////////////////////////���� �ѱۺ�ȭ ó���� �ߴ���, 2�� 3õ.. 0�� �̷���/////////////////////////////////////////////////////////
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
			result=cheoneok+"õ"+baeckeok+"��"+sipeok+"��"+eok+"�� "+cheonman+"õ"+baeckman+"��"+sipman+"��"+man+"�� "+cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=10000000000L)
			result=baeckeok+"��"+sipeok+"��"+eok+"�� "+cheonman+"õ"+baeckman+"��"+sipman+"��"+man+"�� "+cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=1000000000) 
			result=sipeok+"��"+eok+"�� "+cheonman+"õ"+baeckman+"��"+sipman+"��"+man+"�� "+cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=100000000) 
			result=eok+"�� "+cheonman+"õ"+baeckman+"��"+sipman+"��"+man+"�� "+cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=10000000) 
			result=cheonman+"õ"+baeckman+"��"+sipman+"��"+man+"�� "+cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=1000000) 
			result=baeckman+"��"+sipman+"��"+man+"�� "+cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=100000) 
			result=sipman+"��"+man+"�� "+cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=10000) 
			result=man+"�� "+cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=1000) 
			result=cheon+"õ"+baeck+"��"+sip+"�ʿ�";
		else if(money>=100) 
			result=baeck+"��"+sip+"�ʿ�";
		else if(money>=10) 
			result=sip+"�ʿ�";
		
		return result;
	}
 */
 

