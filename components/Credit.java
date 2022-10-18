package components;

import java.time.LocalDate;

public class Credit extends Flow {

	public Credit(double amount, int targetAccountNumber, LocalDate ld) {
		super(amount, targetAccountNumber, ld);
	}

	public Credit(double amount, Account[] a, LocalDate ld) {
		super(amount, a, ld);
	}
}