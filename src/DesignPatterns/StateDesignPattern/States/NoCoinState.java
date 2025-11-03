package DesignPatterns.StateDesignPattern.States;

public class NoCoinState implements  VendingMachineStates {
	@Override
	public void display() {
		System.out.println("Enter a coin to proceed");
	}
}
