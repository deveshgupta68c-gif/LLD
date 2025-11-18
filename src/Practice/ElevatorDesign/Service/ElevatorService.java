package Practice.ElevatorDesign.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Practice.ElevatorDesign.Models.Building;
import Practice.ElevatorDesign.Models.Direction;
import Practice.ElevatorDesign.Models.Elevator;

public class ElevatorService implements IElevatorService{
	Map<Integer, Elevator> idElevatorMap;
	Map<Building, List<Elevator>> buildingElevatorMap;
	Map<Elevator, Building> elevatorBuildingMap;
	
	public ElevatorService() {
		this.idElevatorMap = new HashMap<>();
		this.buildingElevatorMap = new HashMap<>();
		this.elevatorBuildingMap = new HashMap<>();
	}
	
	@Override
	public void pushButton(int targetFloorNumber, int elevatorId) {
		Boolean isValidRequest = validatePushButtonRequest(targetFloorNumber, elevatorId);
		Elevator elevator = idElevatorMap.get(elevatorId);
		if(isValidRequest) {
			if(elevator.getDirection() == Direction.IDLE) {
				if(elevator.getCurrentFloor() > targetFloorNumber) {
					elevator.getDownstream().add(targetFloorNumber);
					elevator.setDirection(Direction.DOWN);
				} else if(elevator.getCurrentFloor() < targetFloorNumber) {
					elevator.getUpstream().add(targetFloorNumber);
					elevator.setDirection(Direction.UP);
				} else {
					System.out.println("Lift is already at " + targetFloorNumber);
				}
			}
		}
	}

	private Boolean validatePushButtonRequest(int targetFloorNumber, int elevatorId) {
		Boolean ans = true;
		Elevator elevator = idElevatorMap.get(elevatorId);
		if(Objects.isNull(elevator)) {
			System.out.println("Couldn't find elevator with elevatorID : " + elevatorId);
			return false;
		}
		
		Building building = elevatorBuildingMap.get(elevator);
		if(targetFloorNumber > building.getNumberOfFloors()) {
			return false;
		}
		
		return ans;
	}

	@Override
	public void addElevator(int id, Building building) {
		validateAddElevator(id);
		Elevator elevator = new Elevator(id, building.getId());
		idElevatorMap.put(id, elevator);
		if(!buildingElevatorMap.containsKey(building)) {
			buildingElevatorMap.put(building, new ArrayList<>());
		}
		buildingElevatorMap.get(building).add(elevator);
		elevatorBuildingMap.put(elevator, building);
		System.out.println("Successfully added elevator to building id : " + building.getId());
	}

	private void validateAddElevator(int id) {
		if(idElevatorMap.containsKey(id)) {
			throw new IllegalArgumentException("Elevator with this Id already Exists");
		}
	}

	@Override
	public List<Elevator> getElevatorsForBuilding(Building building) {
		return buildingElevatorMap.getOrDefault(building, new ArrayList<>());
	}

	@Override
	public void addFloorToElevator(Elevator elevator, int floor, Direction direction) {
		if(elevator.getDirection() == Direction.IDLE) {
			if(elevator.getCurrentFloor() - floor > 0) {
				elevator.setDirection(Direction.UP);
				elevator.getUpstream().add(floor);
			} else {
				elevator.setDirection(Direction.DOWN);
				elevator.getDownstream().add(floor);
			}
		} else if(elevator.getDirection() == Direction.DOWN) {
			if(elevator.getCurrentFloor() - floor > 0) {
				elevator.getDownstream().add(floor);
			} else {
				elevator.getUpstream().add(floor);
			}
		} else {
			if(elevator.getCurrentFloor() - floor > 0) {
				elevator.getDownstream().add(floor);
			} else {
				elevator.getUpstream().add(floor);
			}
		}
	}

	@Override
	public void moveAllElevators() {
		idElevatorMap.entrySet().forEach(this::moveElevator);
		
	}
	
	private void moveElevator(Map.Entry<Integer, Elevator> entry) {
		Elevator elevator = entry.getValue();
		if(elevator.getDirection() == Direction.UP) {
			if(elevator.getUpstream().isEmpty()) {
				if(elevator.getDownstream().isEmpty()) {
					elevator.setDirection(Direction.IDLE);
				} else {
					elevator.setDirection(Direction.DOWN);
				}
			} else {
				Integer nextFloor  = elevator.getUpstream().poll();
				elevator.setCurrentFloor(nextFloor);
			}
		} else if(elevator.getDirection() == Direction.DOWN) {
			if(elevator.getDownstream().isEmpty()) {
				if(elevator.getUpstream().isEmpty()) {
					elevator.setDirection(Direction.IDLE);
				} else {
					elevator.setDirection(Direction.UP);
				}
			} else {
				Integer nextFloor  = elevator.getDownstream().poll();
				elevator.setCurrentFloor(nextFloor);
			}
		}
	}
	
	// Helper method to validate floor for a specific elevator
	private boolean isValidFloor(Elevator elevator, int floor) {
		Building building = elevatorBuildingMap.get(elevator);
		if(building == null) {
			System.out.println("Elevator " + elevator.getId() + " is not associated with any building");
			return false;
		}
		return isValidFloorForBuilding(floor, building);
	}
	
	// Helper method to validate floor range for a building
	private boolean isValidFloorForBuilding(int floor, Building building) {
		if(building == null) {
			return false;
		}
		if(floor < 0) {
			System.out.println("Floor cannot be negative: " + floor);
			return false;
		}
		if(floor > building.getNumberOfFloors()) {
			System.out.println("Floor " + floor + " exceeds building's maximum floor " + building.getNumberOfFloors());
			return false;
		}
		return true;
	}
	
}
