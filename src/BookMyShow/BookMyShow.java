package BookMyShow;

import java.util.ArrayList;
import java.util.List;

public class BookMyShow {
	private List<Theater> theaters;
	private List<User> users;

	public BookMyShow(){
		theaters = new ArrayList<Theater>();
		users = new ArrayList<User>();
	}

	public void addTheater(Theater theater){
		theaters.add(theater);
	}

	public void addUser(User user){
		users.add(user);
	}

	public List<Theater> getTheaters(){
		return theaters;
	}

	public List<MovieShow> getMovieShows(){
		List<MovieShow> movieShows = new ArrayList<>();
		for(Theater theater : theaters){
			movieShows.addAll(theater.getMovieShows());
		}
		return movieShows;
	}
	public MovieShowSeats getSeats(MovieShow movieShow) {
		return movieShow.getSeats();
	}
	public void addMovieShow(Theater theater, MovieShow movieShow) {
		theater.addMovieShow(movieShow);
	}
	public void bookSeat(Booking booking) {
	}
}
