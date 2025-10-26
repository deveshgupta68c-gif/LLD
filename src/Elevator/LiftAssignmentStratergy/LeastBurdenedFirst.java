package Elevator.LiftAssignmentStratergy;

import java.util.List;

import Elevator.Elevator;
import Elevator.ElevatorController;

public class LeastBurdenedFirst implements LiftAssignmentStratergy{
	@Override
	public ElevatorController findBestElevator(List<ElevatorController> elevatorControllerList) {
		return new ElevatorController(new Elevator());
	}
}
