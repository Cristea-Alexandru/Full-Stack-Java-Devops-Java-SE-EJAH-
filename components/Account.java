package components;

public abstract class Account {

	protected String label;
	protected double balance;
	protected int accNumber;
	protected Client client;
	protected static int count = 0;

	Account(String lab, Client cli) {
		label = lab;
		client = cli;
		setAccNumber(++count);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(Flow f) {
		String p = f.getClass().getName();
		if (f.getClass().getName() == "components.Credit") {
			this.balance += f.getAmount();
		} else {
			this.balance -= f.getAmount();
		}
	}

	// Comment the lines 41, 43 ,44 ,45 to verify that if there's a negative amount you can't transfer
	public void setBalance(Transfer t) {
		if (this.accNumber == t.gettransfAccNum()) {
			if (this.balance >= t.getAmount()) {
				this.balance -= t.getAmount();
			} else {
				System.out.println("Insufficient funds");
			}
		} else {
			this.balance += t.getAmount();
		}
	}

	public int getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Account [Label=" + label + ", Balance=" + balance + ", AccountNumber=" + accNumber + ", Client="
				+ client + "]";
	}
}