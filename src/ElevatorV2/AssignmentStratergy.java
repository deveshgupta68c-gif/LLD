package ElevatorV2;

import java.util.List;

import ElevatorV2.DTO.ElevatorRequest;
import ElevatorV2.Model.Elevator;

public interface AssignmentStratergy {
	public Elevator getElevatorForRequest(List<Elevator> elevators, ElevatorRequest elevatorRequest);
}
