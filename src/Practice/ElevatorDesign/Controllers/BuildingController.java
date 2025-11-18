package Practice.ElevatorDesign.Controllers;

import Practice.ElevatorDesign.Models.Direction;
import Practice.ElevatorDesign.Service.IBuildingService;

public class BuildingController {
	private IBuildingService buildingService;

	public BuildingController(IBuildingService buildingService) {
		this.buildingService = buildingService;
	}

	public void addBuilding(int id, String name, int numberOfFloors){
		buildingService.addBuilding(id, name, numberOfFloors);
	}
	public void callLiftToFloor(int floor, int buildingId, Direction direction){
		buildingService.callLiftToFloor(floor, buildingId, direction);
	}
	public void addElevator(int id, int buildingId){
		buildingService.addElevator(id, buildingId);
	}
}
