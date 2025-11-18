package Practice.ElevatorDesign.Service.AssignmentStratergies;

import Practice.ElevatorDesign.Models.DTO.ElevatorRequestDTO;
import Practice.ElevatorDesign.Models.Elevator;

public interface AssignmentStratergy {
	public Elevator getAssignedElevator(ElevatorRequestDTO request);
}
