package Practice.ElevatorDesign.Models;

import java.util.Collections;
import java.util.PriorityQueue;

public class Elevator {
	private int id;
	private int buildingId;
	private Direction direction;
	private PriorityQueue<Integer> upstream;
	private PriorityQueue<Integer> downstream;
	private int currentFloor;
	
	public Elevator(int id, int buildingId){
		this.setBuildingId(buildingId);
		this.id = id;
		this.setDirection(Direction.IDLE);
		this.setUpstream(new PriorityQueue<>());
		this.setDownstream(new PriorityQueue<>(Collections.reverseOrder()));
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public PriorityQueue<Integer> getUpstream() {
		return upstream;
	}

	private void setUpstream(PriorityQueue<Integer> upstream) {
		this.upstream = upstream;
	}

	public PriorityQueue<Integer> getDownstream() {
		return downstream;
	}

	private void setDownstream(PriorityQueue<Integer> downstream) {
		this.downstream = downstream;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getId() {
		return this.id;
	}
	
	
}
