package Splitwise;

import java.util.List;

public class GroupExpense {
	private Group group;
	private List<Expense> expenses;
	private User owedTo;
	private List<User> owedBy;
	private double amount;
	GroupExpense(User owedTo, List<User> owedBy, double amount, Group group) {
		this.owedTo = owedTo;
		this.owedBy = owedBy;
		this.amount = amount;
		this.group = group;
	}

	public User getOwedTo() {
		return owedTo;
	}

	public void setOwedTo(User owedTo) {
		this.owedTo = owedTo;
	}

	public List<User> getOwedBy() {
		return owedBy;
	}

	public void setOwedBy(List<User> owedBy) {
		this.owedBy = owedBy;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
