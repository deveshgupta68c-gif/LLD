package BookMyShowV2;

import java.util.List;
import java.util.Map;

public class Screen {
	private int id;
	List<Seats> seats;

	public Screen(int id, List<Seats> seats){
		this.id = id;
		this.seats = seats;
	}

	public List<Seats> getSeats() {
		return seats;
	}
}
