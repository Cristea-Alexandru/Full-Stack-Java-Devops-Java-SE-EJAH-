package components;

import java.time.LocalDate;

public class Transfer extends Flow {

	private int transfAccNum;

	public Transfer(double amount, int targetAccountNumber, int accN, LocalDate ld) {
		super(amount, targetAccountNumber, ld);
		this.transfAccNum = accN;
	}

	public int gettransfAccNum() {
		return this.transfAccNum;
	}
}