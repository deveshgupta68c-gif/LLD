package BookMyShow;

import java.util.List;
import java.util.Set;

public class MovieShowSeats {
	private Set<String> allSeats;
	private Set<String> availableSeats;
	private Set<String> reservedSeats;
	public MovieShowSeats(Set<String> allSeats, Set<String> availableSeats, Set<String> reservedSeats) {
		this.allSeats = allSeats;
		this.availableSeats = availableSeats;
		this.reservedSeats = reservedSeats;
	}


}
