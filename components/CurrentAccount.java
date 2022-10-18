package components;

public class CurrentAccount extends Account {

	public CurrentAccount(String lab, Client cli) {
		super(lab, cli);
		balance = 0;
	}
}