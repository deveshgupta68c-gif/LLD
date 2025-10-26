package BookMyShow;

import java.util.List;

public class Booking {
	private User user;
	private MovieShow movieShow;
	private List<String> seats;

	public Booking(User user, MovieShow movieShow, List<String> seats) {
		this.user = user;
		this.movieShow = movieShow;
		this.seats = seats;
	}


	public MovieShow getMovieShow() {
		return movieShow;
	}

	public List<String> getSeats() {
		return seats;
	}

	public User getUser() {
		return user;
	}
}
