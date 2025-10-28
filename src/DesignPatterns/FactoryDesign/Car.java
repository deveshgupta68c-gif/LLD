package DesignPatterns.FactoryDesign;

public class Car implements  Vehicle {
	private Boolean unlocked;
	private Boolean started;
	private Boolean driven;

	public Car() {
		this.unlocked = false;
		this.started = false;
		this.driven = false;
	}

	@Override
	public void unlock() {
		System.out.println("Unlocking car");
		this.unlocked = true;
		System.out.println("Car unlocked");
	}

	@Override
	public void start() {
		if(unlocked.equals(false)){
			throw new RuntimeException("Unlock Car first");
		}
		System.out.println("Starting car");
		this.started = true;
		System.out.println("Car started");
	}

	@Override
	public void drive() {
		if(started.equals(false)){
			throw  new RuntimeException("Start the car first");
		}
		if(driven.equals(true)){
			System.out.println("Car driven");
			return;
		}
		System.out.println("Driving car");
		this.driven = true;
		System.out.println("Car driven");
	}
}
