package Elevator;

public class Elevator {
	private Integer currentFloor;
	private Direction fDirection;

	public Elevator(){
		currentFloor = 0;
		fDirection = Direction.IDLE;
	}
	public int move(Integer floor){
		if(floor.equals(null)){
			System.out.println("Error: Null Floor received as input");
		}
		if(floor == currentFloor){
			fDirection = Direction.IDLE;
		} else if(floor > currentFloor){
			currentFloor++;
			fDirection = Direction.UP;
		} else{
			currentFloor--;
			fDirection = Direction.DOWN;
		}
		return currentFloor;
	}

	public void setDirection(Direction direction) {
		fDirection = direction;
	}

	public Direction getDirection() {
		return fDirection;
	}
}
