package kr.deu.mit;

import java.util.Date;

public class SalesVO {
	private String fruit_name;
	private int fruit_code;
	private Date sales_date;
	private int sales_quantity;    //null값을 가져올수 없어서, 기본값의 0이라서, 어떤값이 가지는 0이 0인지 null인지 구별하기 힘듦!!!
	//private Integer sales_quantity;  //만약 이렇게 객체로 들어가게 되면, null값을 가져올수 있음
	private int total;
	
	
	public String getFruit_name() {
		return fruit_name;
	}
	public void setFruit_name(String fruit_name) {
		this.fruit_name = fruit_name;
	}
	public int getFruit_code() {
		return fruit_code;
	}
	public void setFruit_code(int fruit_code) {
		this.fruit_code = fruit_code;
	}
	public Date getSales_date() {
		return sales_date;
	}
	public void setSales_date(Date sales_date) {
		this.sales_date = sales_date;
	}
	public int getSales_quantity() {
		return sales_quantity;
	}
	public void setSales_quantity(int sales_quantity) {
		this.sales_quantity = sales_quantity;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return "fruit_name=" + fruit_name + ",      fruit_code=" + fruit_code + ",     sales_date=" + sales_date
				+ ",   sales_quantity=" + sales_quantity + ",    total=" + total;
	}
	
	

	
}
