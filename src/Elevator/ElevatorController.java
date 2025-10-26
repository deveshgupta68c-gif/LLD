package Elevator;

import java.util.Collections;
import java.util.PriorityQueue;

public class ElevatorController {
	private Elevator fElevator;
	private PriorityQueue<Integer> upQueue;
	private PriorityQueue<Integer> downQueue;

	public ElevatorController(Elevator elevator){
		this.fElevator = elevator;
		upQueue = new PriorityQueue<>();
		downQueue = new PriorityQueue<>(Collections.reverseOrder());
	}

	public void queueFloor(Integer source, Integer target){
		if(source > target){
			downQueue.offer(source);
		}
	}

	public void moveElevator(){
		if(upQueue.isEmpty() && downQueue.isEmpty()){
			fElevator.setDirection(Direction.IDLE);
		} else if(fElevator.getDirection().equals(Direction.UP)){
			if(upQueue.isEmpty()){
				moveDown();
			} else{
				moveUp();
			}
		} else {
			if(downQueue.isEmpty()){
				moveUp();
			} else {
				moveDown();
			}
		}
	}

	private void moveUp() {
		int nextFloor = fElevator.move(upQueue.peek());
		while(!upQueue.isEmpty() && upQueue.peek() == nextFloor){
			downQueue.poll();
		}
	}

	private void moveDown() {
		int nextFloor = fElevator.move(downQueue.peek());
		if(!downQueue.isEmpty() && nextFloor == downQueue.peek()){
			downQueue.poll();
		}
	}
}
