package Practice.ElevatorDesign.Models.DTO;

import java.util.ArrayList;
import java.util.List;

import Practice.ElevatorDesign.Models.Direction;
import Practice.ElevatorDesign.Models.Elevator;

public class ElevatorRequestDTO {
	private List<Elevator> elevators;
	private Integer currentFloor;
	private Direction direction;
	
	public ElevatorRequestDTO(){
		elevators = new ArrayList<>();
	}
	
	public List<Elevator> getElevators() {
		return elevators;
	}
	public void setElevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}
	public Integer getCurrentFloor() {
		return currentFloor;
	}
	public void setCurrentFloor(Integer currentFloor) {
		this.currentFloor = currentFloor;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
