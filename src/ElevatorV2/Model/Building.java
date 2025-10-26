package ElevatorV2.Model;

import java.util.ArrayList;
import java.util.List;

public class Building {
	int id;
	private int floors;
	private List<Elevator> elevators;

	Building(int floors) {
		this.floors = floors;
		elevators = new ArrayList<>();
	}

	public void addElevator(Elevator elevator){
		this.elevators.add(elevator);
	}
}
