package Elevator;

import java.util.ArrayList;
import java.util.List;

public class Building {
	private List<Floor> floors;
	private ElevatorManagement elevatorManagement;
	public Building(ElevatorManagement elevatorManagement){
		this.elevatorManagement = elevatorManagement;
		floors = new ArrayList<>();
	}
	public void addFloor(Floor floor){
		floors.add(floor);
	}
	public void addAllFloor(List<Floor> floors){
		this.floors.addAll(floors);
	}
	public void removeFloor(Floor floor){
		floors.remove(floor);
	}
	public Boolean checkIfFloorExists(Integer i){
		for(Floor floor : floors){
			if(floor.getFloorNumber() == i){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;

	}

}
