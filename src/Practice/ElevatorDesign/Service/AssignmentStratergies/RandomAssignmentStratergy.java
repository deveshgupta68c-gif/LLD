package Practice.ElevatorDesign.Service.AssignmentStratergies;

import Practice.ElevatorDesign.Models.DTO.ElevatorRequestDTO;
import Practice.ElevatorDesign.Models.Elevator;

public class RandomAssignmentStratergy implements AssignmentStratergy{

	@Override
	public Elevator getAssignedElevator(ElevatorRequestDTO request) {
		int n = request.getElevators().size();
		int i = (int) ((Math.random()*100) % n);
		return request.getElevators().get(i);
	}

}
