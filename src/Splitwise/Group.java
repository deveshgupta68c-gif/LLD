package Splitwise;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private int id;
	List<User> users;
	List<GroupExpense> groupExpenses;

	public Group(List<User> users) {
		this.users = users;
	}

	public Group() {
		users = new ArrayList<User>();
		groupExpenses = new ArrayList<GroupExpense>();
	}

	public void setId(int i) {
		id = i;
	}

	public int getId() {
		return id;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void addUsers(List<User> users) {
		this.users.addAll(users);
	}

	public void addExpense(GroupExpense groupExpense) {
		groupExpenses.add(groupExpense);
		double amount = groupExpense.getAmount() / (groupExpense.getOwedBy().size() + 1);
		groupExpense.getOwedBy().forEach(user1 -> {
			Expense expense = new Expense(groupExpense.getOwedTo(), user1, amount);
			user1.addOwedExpense(expense);
			groupExpense.getOwedTo().addOwnedExpense(expense);
		});
	}
}
