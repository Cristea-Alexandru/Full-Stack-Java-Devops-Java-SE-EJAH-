package main;

import components.Client;
import components.Account;
import components.CurrentAccount;
import components.SavingsAccount;
import components.Flow;
import components.Credit;
import components.Debit;
import components.Transfer;
import java.util.*;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;

public class main {
	// Display
	public static void main(String[] args) {

		System.out.println("Client Array:\n");
		Client[] clientsArray = fillArray(3);
		printArray(clientsArray);

		System.out.println("\n");
		System.out.println("Accounts with Clients:\n");
		Account[] clientsAccounts = fillArray2(clientsArray);
		printArray2(clientsAccounts);

		System.out.println("\n");
		System.out.println("Accounts Hashtable:\n");
		Hashtable<Integer, Account> accs = adaptation(clientsAccounts);
		printHash(accs);

		System.out.println("\n");
		System.out.println("Flows:\n");
		Flow[] flows = loadFlows(clientsAccounts);
		for (int i = 0; i < flows.length; i++) {
			System.out.println(flows[i].toString());
		}
		if (updateBalance(flows, accs)) {
			System.out.println("\n");
			System.out.println("Updated & Ordered Accounts:\n");
			sortAccounts(accs);
		}
	}

	// Flow Creation
	public static Flow[] loadFlows(Account[] arr) {
		LocalDate localDate = LocalDate.now().plusDays(2);
		Flow[] flows = new Flow[1];
		flows[0] = new Debit(50, 1, localDate);
		flows = addCreditFlow(flows, arr);
		flows = addSavingsFlow(flows, arr);
		flows = addTransferFlow(flows, arr, 1, 2, 50);
		return flows;
	}

	// Accounts
	public static Hashtable<Integer, Account> adaptation(Account[] a) {
		Hashtable<Integer, Account> acc = new Hashtable<Integer, Account>();
		for (int i = 0; i < a.length; i++) {
			acc.put(a[i].getAccNumber(), a[i]);
		}
		return acc;
	}

	// Display the accounts
	public static void printHash(Map<Integer, Account> h) {
		h.entrySet().stream().forEach(System.out::println);
	}

	public static Client[] fillArray(int numberOfClients) {
		Client[] client = new Client[numberOfClients];
		for (int i = 0; i < numberOfClients; i++) {
			client[i] = new Client("name" + (i + 1), "firstname" + (i + 1));
		}
		return client;
	}

	// Display the client array
	public static void printArray(Client[] arr) {
		Arrays.stream(arr).forEach(System.out::println);
	}

	public static Account[] fillArray2(Client[] arr) {
		Account[] accounts = new Account[arr.length * 2];
		for (int i = 0; i < arr.length; i++) {
			accounts[i] = new CurrentAccount("label" + i, arr[i]);
		}
		int count = 0;
		for (int i = arr.length; i < accounts.length; i++) {
			accounts[i] = new SavingsAccount("label" + i, arr[count]);
			count++;
		}
		return accounts;
	}

	public static void printArray2(Account[] arr) {
		Arrays.stream(arr).forEach(System.out::println);
	}

	// Add Credit Flow to all Current Accounts
	public static Flow[] addCreditFlow(Flow[] arr, Account[] acc) {
		LocalDate localDate = LocalDate.now().plusDays(2);
		int currentAccountCount = 0;
		for (int i = 0; i < acc.length; i++) {
			if (acc[i].getClass().getName() == "components.CurrentAccount") {
				currentAccountCount++;
			}
		}
		Flow[] flows = new Flow[arr.length + currentAccountCount];
		for (int i = 0; i < arr.length; i++) {
			flows[i] = arr[i];
		}
		int count = arr.length;
		for (int i = 0; i < acc.length; i++) {
			if (acc[i].getClass().getName() == "components.CurrentAccount") {
				flows[count] = new Credit(100.50, acc[i].getAccNumber(), localDate);
				count++;
			}
		}
		return flows;
	}

	// Add Credit Flow to all Savings Accounts
	public static Flow[] addSavingsFlow(Flow[] arr, Account[] acc) {
		LocalDate localDate = LocalDate.now().plusDays(2);
		int savingsAccountCount = 0;
		for (int i = 0; i < acc.length; i++) {
			if (acc[i].getClass().getName() == "components.SavingsAccount") {
				savingsAccountCount++;
			}
		}
		Flow[] flows = new Flow[arr.length + savingsAccountCount];
		for (int i = 0; i < arr.length; i++) {
			flows[i] = arr[i];
		}
		int count = arr.length;
		for (int i = 0; i < acc.length; i++) {
			if (acc[i].getClass().getName() == "components.SavingsAccount") {
				flows[count] = new Credit(1500, acc[i].getAccNumber(), localDate);
				count++;
			}
		}
		return flows;
	}

	// Transfer 50 from Account 1 to Account 2
	public static Flow[] addTransferFlow(Flow[] arr, Account[] acc, int issAccount, int targetAccount, double amount) {
		LocalDate localDate = LocalDate.now().plusDays(2);
		Flow[] flows = new Flow[arr.length + 2];
		for (int i = 0; i < arr.length; i++) {
			flows[i] = arr[i];
		}
		flows[arr.length] = new Transfer(amount, targetAccount, issAccount, localDate);
		flows[arr.length + 1] = new Transfer(amount, issAccount, issAccount, localDate);
		return flows;
	}

	// Recieve and Sort by Account balance
	public static void sortAccounts(Hashtable<Integer, Account> acc) {
		List<Account> list = new ArrayList<Account>(acc.values());
		Collections.sort(list, new Comparator<Account>() {
			@Override
			public int compare(Account o1, Account o2) {
				return Double.compare(o1.getBalance(), o2.getBalance());
			}
		});
		list.stream().forEach(System.out::println);
	}

	// Update the Account balance
	public static boolean updateBalance(Flow[] arr, Hashtable<Integer, Account> acc) {
		double[] balanceCollection = new double[acc.size()];
		List<Double> balanceList = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getClass().getName() != "components.Transfer") {
				acc.get(arr[i].getTargetAccountNumber()).setBalance(arr[i]);
			} else {
				acc.get(arr[i].getTargetAccountNumber()).setBalance((Transfer) arr[i]);
			}
		}

		for (int i = 0; i < acc.size(); i++) {
			balanceList.add(acc.get(i + 1).getBalance());
		}

		Predicate<Double> isNegative = i -> i > 0;

		List<Double> positiveBalanceList = balanceList.stream().filter(isNegative).collect(Collectors.toList());
		if (positiveBalanceList.size() != balanceList.size()) {
			// Error: negative Account
			System.out.println("ERROR: There is an Account with negative balance!!!");
			return false;

		}
		return true;
	}
}