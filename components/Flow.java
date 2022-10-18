package components;

import java.time.LocalDate;

public abstract class Flow {

	private String comment;
	private int identifier;
	private double amount;
	private int targetAccountNumber;
	private boolean effect;
	private LocalDate dateOfFlow;

	// Constructor for Debit
	Flow(double amount, int targetAccountNumber, LocalDate ld) {
		this.amount = amount;
		this.targetAccountNumber = targetAccountNumber;
		this.dateOfFlow = ld;
	}

	// Constructor for Credit
	Flow(double amount, Account[] a, LocalDate ld) {
		this.amount = amount;
		this.dateOfFlow = ld;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTargetAccountNumber() {
		return targetAccountNumber;
	}

	public void setTargetAccountNumber(int targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}

	public boolean isEffect() {
		return effect;
	}

	public void setEffect(boolean effect) {
		this.effect = effect;
	}

	public LocalDate getDateOfFlow() {
		return dateOfFlow;
	}

	public void setDateOfFlow(LocalDate dateOfFlow) {
		this.dateOfFlow = dateOfFlow;
	}

	@Override
	public String toString() {
		return "Flow [Comment=" + comment + ", Identifier=" + identifier + ", Amount=" + amount
				+ ", TargetAccountNumber=" + targetAccountNumber + ", Effect=" + effect + ", Date=" + dateOfFlow + "]";
	}
}