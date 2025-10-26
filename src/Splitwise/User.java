package Splitwise;

import java.util.HashMap;
import java.util.Map;

public class User {

	private String name;
	private String email;
	private String password;
	private Double balance;
	private Map<User, Double> userBalanceMap;
	private int id;

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		userBalanceMap = new HashMap<>();
		balance = 0.0;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int i) {
		this.id = i;
	}


	public void addBalance(Double amount) {
		balance += amount;
	}

	public void addOwedExpense(Expense expense) {
		balance -= expense.getAmount();
		userBalanceMap.put(expense.getPaidBy(), userBalanceMap.getOrDefault(expense.getPaidBy(),0.0) - expense.getAmount());
	}
	public void addOwnedExpense(Expense expense) {
		balance += expense.getAmount();
		userBalanceMap.put(expense.getReceiver(), userBalanceMap.getOrDefault(expense.getReceiver(), 0.0) + expense.getAmount());
	}

	public Map<User, Double> getUserBalanceMap() {
		return userBalanceMap;
	}

	public void setUserBalanceMap(Map<User, Double> userBalanceMap) {
		this.userBalanceMap = userBalanceMap;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBalance() {
		return balance;
	}

	public void settleAmount(User receiver, Double amount) {
		if(userBalanceMap.get(receiver) == null) {
			throw new RuntimeException("Receiver not found");
		}
		this.balance += amount;
		this.userBalanceMap.put(receiver, this.userBalanceMap.getOrDefault(receiver, 0.0) + amount);
	}
}
