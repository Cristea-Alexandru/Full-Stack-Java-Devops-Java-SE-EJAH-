package components;

import java.time.LocalDate;

public class Debit extends Flow {

	public Debit(double amount, int targetAccountNumber, LocalDate ld) {
		super(amount, targetAccountNumber, ld);
	}
}