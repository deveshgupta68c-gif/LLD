package DesignPatterns.StateDesignPattern.States;

public class Dispense implements VendingMachineStates{
	@Override
	public void display() {
		System.out.println("Dispensing...");
		System.out.println("Thank you for the purchase");
	}
}
