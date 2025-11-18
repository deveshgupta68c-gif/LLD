package Practice.ElevatorDesign.Controllers;

import Practice.ElevatorDesign.Service.IElevatorService;

public class ElevatorController {
	private IElevatorService elevatorService;

	public ElevatorController(IElevatorService elevatorService) {
		this.elevatorService = elevatorService;
	}
	public void pushButton(int targetFloorNumber, int elevatorId){
		elevatorService.pushButton(targetFloorNumber, elevatorId);
	}
	public void moveAllElevators(){
		elevatorService.moveAllElevators();
	}
}
