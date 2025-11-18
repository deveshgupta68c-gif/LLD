package Practice.ElevatorDesign.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Practice.ElevatorDesign.Models.DTO.ElevatorRequestDTO;
import Practice.ElevatorDesign.Models.Building;
import Practice.ElevatorDesign.Models.Direction;
import Practice.ElevatorDesign.Models.Elevator;
import Practice.ElevatorDesign.Service.AssignmentStratergies.AssignmentStratergy;

public class BuildingService implements IBuildingService{
	
	private IElevatorService elevatorService;
	private List<Building> buildings;
	private AssignmentStratergy assignmentStratergy;
	
	public BuildingService(IElevatorService elevatorService, AssignmentStratergy assignmentStratergy) {
		this.assignmentStratergy = assignmentStratergy;
		this.elevatorService = elevatorService;
		this.buildings = new ArrayList<>();
	}
	
	@Override
	public void callLiftToFloor(int floor, int buildingId, Direction direction) {
		Building building = this.getBuildingFromId(buildingId);
		
		// Validate floor is within building range
		if(floor < 0) {
			System.out.println("Invalid floor: Floor cannot be negative (" + floor + ")");
			return;
		}
		if(floor > building.getNumberOfFloors()) {
			System.out.println("Invalid floor: Floor " + floor + " exceeds building's maximum floor " + building.getNumberOfFloors());
			return;
		}
		
		List<Elevator> elevators = elevatorService.getElevatorsForBuilding(building);
		if(elevators.isEmpty()) {
			System.out.println("No elevators available in building " + buildingId);
			return;
		}
		
		ElevatorRequestDTO request = new ElevatorRequestDTO();
		request.setCurrentFloor(floor);
		request.setDirection(direction);
		request.setElevators(elevators);
		Elevator elevator = assignmentStratergy.getAssignedElevator(request);
		elevatorService.addFloorToElevator(elevator, floor, direction);
		System.out.println("Lift requested for floor : " + floor + " with elevator id : " + elevator.getId());
	}

	@Override
	public void addElevator(int id, int buildingId) {
		Building building = this.getBuildingFromId(buildingId);
		elevatorService.addElevator(id, building);
	}

	private Building getBuildingFromId(int buildingId) {
		Optional<Building> building = this.buildings.stream().filter(building1 -> building1.getId() == buildingId).findFirst();
		if(!building.isPresent()) {
			throw new RuntimeException("Building Not Found");
		}
		return building.get();
	}

	@Override
	public void addBuilding(int id, String name, int numberOfFloors) {
		Optional<Building> optionalBuilding = buildings.stream().filter(building -> building.getId() == id).findAny();
		if(optionalBuilding.isPresent()) {
			throw new IllegalArgumentException("Building with this id : " + id + " already exists, can't add new one");
		}
		Building building = new Building(id, name, numberOfFloors);
		buildings.add(building);
	}
	
}
