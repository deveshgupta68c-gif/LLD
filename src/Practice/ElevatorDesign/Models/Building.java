package Practice.ElevatorDesign.Models;

public class Building {
	private final int id;
	private String name;
	private int numberOfFloors;
	
	public Building(int id, String name, int numberOfFloors){
		this.id = id;
		this.setName(name);
		this.setNumberOfFloors(numberOfFloors);
	}
	
	@Override
	public int hashCode() {
		return this.getId();
	}
	
	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
}
