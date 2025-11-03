package DesignPatterns.StateDesignPattern.States;

public class ItemSelected implements VendingMachineStates{
	@Override
	public void display() {
		System.out.println("Item selected");

	}
}
