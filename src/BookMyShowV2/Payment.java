package BookMyShowV2;

public class Payment {
	private final int paymentId;
	private final double amount;

	public Payment(int paymentId, double amount) {
		this.paymentId = paymentId;
		this.amount = amount;
	}
	public int getPaymentId() {
		return  paymentId;
	}

	public double getAmount() {
		return amount;
	}
}
