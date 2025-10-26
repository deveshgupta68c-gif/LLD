package Splitwise;

import java.util.List;

public class Expense {
	private User paidBy;
	private User receiver;
	private double amount;

	public Expense(User paidBy, User receiver, double amount) {
		this.paidBy = paidBy;
		this.receiver = receiver;
		this.amount = amount;
	}

	public User getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(User paidBy) {
		this.paidBy = paidBy;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
