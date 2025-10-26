package BookMyShow;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieShow {
	private Movie movie;
	private Theater theater;
	private Timestamp time;
	private double price;
	private Set<String> availableSeats;
	private Set<String> temporaryUnavailableSeats;
	private Set<String> bookedSeats;

	public Set<String> getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(Set<String> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public MovieShow(Movie movie, Theater theater, Timestamp time, double price, List<String> availableSeats) {
		this.movie = movie;
		this.theater = theater;
		this.time = time;
		this.price = price;
		this.availableSeats = new HashSet<>(availableSeats);
		this.bookedSeats = new HashSet<>();
		this.temporaryUnavailableSeats = new HashSet<>();
	}

	public MovieShowSeats getSeats() {
		return new MovieShowSeats(availableSeats, temporaryUnavailableSeats, bookedSeats);
	}
}
