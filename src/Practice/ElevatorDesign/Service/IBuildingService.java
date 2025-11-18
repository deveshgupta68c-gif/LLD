package Practice.ElevatorDesign.Service;

import Practice.ElevatorDesign.Models.Direction;

public interface IBuildingService {
	public void addBuilding(int id, String name, int numberOfFloors);
	public void callLiftToFloor(int floor, int buildingId, Direction direction);
	public void addElevator(int id, int buildingId);
}
