package Elevator.LiftAssignmentStratergy;


import java.util.List;

import Elevator.ElevatorController;

public interface LiftAssignmentStratergy {
	ElevatorController findBestElevator(List<ElevatorController> elevatorControllerList);
}
