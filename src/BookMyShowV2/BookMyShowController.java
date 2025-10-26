package BookMyShowV2;

import java.util.List;
import java.util.Map;

public class BookMyShowController {
	private MovieController fMovieController;
	private TheaterController fTheaterController;
	private BookingController fBookingController;

	public BookMyShowController() {
		fMovieController = new MovieController();
		fTheaterController = new TheaterController();
		fBookingController = new BookingController();
	}

	public void addMovie(Movie movie, City city){
		fMovieController.addMovie(movie, city);
	}

	public void addTheater(Theater theater, City city){
		fTheaterController.addTheater(theater, city);
	}

	public void addMovie(Movie movie, List<City> cities){
		fMovieController.addMovie(movie, cities);
	}

	public void addTheater(Theater theater, List<City> cities){
		fTheaterController.addTheater(theater, cities);
	}

	public List<Movie> getMovieByCity(City city){
		return fMovieController.getMovieByCity(city);
	}

	public  List<Theater> getTheaterByCity(City city){
		return fTheaterController.getTheaterByCity(city);
	}

	public Map<Theater, List<MovieShow>> getMovieShowsByMovieAndCityTheaterWise(City city, Movie movie){
		return fTheaterController.getMovieShowsByMovieAndCityTheaterWise(city, movie);
	}

	public Boolean lockTickets(List<String> seats, MovieShow movieShow){
		return movieShow.lockTickets(seats);
	}

	public void bookTickets(List<String> seats, MovieShow movieShow, Payment payment){
		fBookingController.bookTickets(seats, movieShow, payment);
	}
}
