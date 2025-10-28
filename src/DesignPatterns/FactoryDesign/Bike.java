package DesignPatterns.FactoryDesign;

public class Bike implements Vehicle{
	private Boolean unlocked;
	private Boolean started;
	private Boolean driven;

	public Bike() {
		this.unlocked = false;
		this.started = false;
		this.driven = false;
	}

	@Override
	public void unlock() {
		System.out.println("Unlocking bike");
		this.unlocked = true;
		System.out.println("Bike unlocked");
	}

	@Override
	public void start() {
		if(unlocked.equals(false)) {
			throw new RuntimeException("Unlock bike first");
		}
		this.started = true;
		System.out.println("Bike started");
	}

	@Override
	public void drive() {
		if(started.equals(false)){
			throw  new RuntimeException("Start the bike first");
		}
		if(driven.equals(true)){
			System.out.println("Bike driven");
			return;
		}
		System.out.println("Driving bike");
		this.driven = true;
		System.out.println("Bike driven");
	}
}
