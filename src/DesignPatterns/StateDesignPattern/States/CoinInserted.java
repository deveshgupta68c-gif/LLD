package DesignPatterns.StateDesignPattern.States;

public class CoinInserted implements VendingMachineStates {
	@Override
	public void display() {
		System.out.println("Coin Inserted");
		System.out.println("Select An Item");
	}
}
