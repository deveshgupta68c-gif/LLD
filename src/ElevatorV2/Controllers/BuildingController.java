package ElevatorV2.Controllers;

import java.util.List;

import ElevatorV2.Model.Building;
import ElevatorV2.Model.Elevator;

public class BuildingController {
	List<Building>  buildings;

	public void addBuilding(Building building){
		buildings.add(building);
	}

	public void addElevator(Building building, Elevator elevator){
		building.addElevator(elevator);
	}
}
