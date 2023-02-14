package modification;

public class FruitVO {
	//코드    (DB속성명과 동일하게 보통 함)
		private int fruit_code;
		
		//이름
		private String fruit_name;
				
		//수량
		private int quantity;
		
		public int getFruit_code() {
			return fruit_code;
		}

		public void setFruit_code(int fruit_code) {
			this.fruit_code = fruit_code;
		}

		public String getFruit_name() {
			return fruit_name;
		}

		public void setFruit_name(String fruit_name) {
			this.fruit_name = fruit_name;
		}


		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return "fruit_code=" + fruit_code + ",   fruit_name=" + fruit_name + ",      quantity="
					+ quantity;
		}
}
