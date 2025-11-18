package Practice.ElevatorDesign.Service;

import java.util.List;

import Practice.ElevatorDesign.Models.Building;
import Practice.ElevatorDesign.Models.Direction;
import Practice.ElevatorDesign.Models.Elevator;

public interface IElevatorService {
	public void pushButton(int targetFloorNumber, int elevatorId);
	public void addElevator(int id, Building building);
	public List<Elevator> getElevatorsForBuilding(Building building);
	public void addFloorToElevator(Elevator elevator, int floor, Direction direction);
	public void moveAllElevators();
}
