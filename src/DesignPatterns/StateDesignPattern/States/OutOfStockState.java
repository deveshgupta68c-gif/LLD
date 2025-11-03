package DesignPatterns.StateDesignPattern.States;

public class OutOfStockState implements VendingMachineStates{
	@Override
	public void display() {
		System.out.println("Item selected is out of stock");
	}
}
