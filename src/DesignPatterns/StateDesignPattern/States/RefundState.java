package DesignPatterns.StateDesignPattern.States;

public class RefundState implements VendingMachineStates{

	@Override
	public void display() {
		System.out.println("Refunding the coin...");
		System.out.println("Thank you");
	}
}
