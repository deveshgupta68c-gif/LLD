package BookMyShowV2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MovieShow {
	private Movie movie;
	private Screen screen;
	private LocalDateTime movieStartDateTime;
	private LocalDateTime movieEndDateTime;
	private Map<String, Seats> availableSeats;
	private Map<String, Seats> bookedSeats;
	private Map<String, Seats> lockedSeats;
	private final Map<PricingPlan, Double> pricePlan;

	public MovieShow(Movie movie,
	                  Screen screen,
	                  LocalDateTime movieStartDateTime,
	                  LocalDateTime movieEndDateTime,
	                  Map<PricingPlan, Double> pricingPlanMap
	                  ) {
		validateInputs(movie, screen, movieEndDateTime, movieStartDateTime, pricingPlanMap);
		this.movie = movie;
		this.screen = screen;
		this.movieStartDateTime = movieStartDateTime;
		this.movieEndDateTime = movieEndDateTime;
		this.availableSeats = new HashMap<>();
		screen.getSeats().forEach(seat -> availableSeats.put(seat.getName(), seat));
		this.bookedSeats = new HashMap<>();
		this.lockedSeats = new HashMap<>();
		this.pricePlan = pricingPlanMap;
	}

	private void validateInputs(Movie movie, Screen screen, LocalDateTime movieEndDateTime, LocalDateTime movieStartDateTime, Map<PricingPlan, Double> pricingPlanMap) {
		if(Objects.isNull(movie) || Objects.isNull(screen) || Objects.isNull(movieEndDateTime) || Objects.isNull(movieStartDateTime) || Objects.isNull(pricingPlanMap)){
			throw new IllegalArgumentException("Invalid inputs");
		}
		if(movieStartDateTime.isAfter(movieEndDateTime)){
			throw new IllegalArgumentException("Invalid movie start and end time");
		}
		if(pricingPlanMap.isEmpty() || pricingPlanMap.size() < 3){
			throw  new IllegalArgumentException("Invalid pricing plan");
		}
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public LocalDateTime getMovieStartDateTime() {
		return movieStartDateTime;
	}

	public void setMovieStartDateTime(LocalDateTime movieStartDateTime) {
		this.movieStartDateTime = movieStartDateTime;
	}

	public LocalDateTime getMovieEndDateTime() {
		return movieEndDateTime;
	}

	public void setMovieEndDateTime(LocalDateTime movieEndDateTime) {
		this.movieEndDateTime = movieEndDateTime;
	}

	public Map<String, Seats> getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(Map<String, Seats> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public Map<String, Seats> getLockedSeats() {
		return lockedSeats;
	}

	public void setLockedSeats(Map<String, Seats> lockedSeats) {
		this.lockedSeats = lockedSeats;
	}

	public Boolean lockTickets(List<String> seatName) {
		Boolean isLocked = true;
		for(String seat : seatName) {
			if(!availableSeats.containsKey(seat)){
				System.out.println("Seat " + seat + " not available");
				return false;
			}
		}
		for(String seat : seatName) {
			Seats seat1 = availableSeats.get(seat);
			availableSeats.remove(seat);
			lockedSeats.put(seat, seat1);
		}
		return  isLocked;
	}
	public void unlockTickets(List<String> seatName) {
		for(String seat : seatName){
			if(lockedSeats.containsKey(seat)) {
				availableSeats.put(seat, lockedSeats.get(seat));
				lockedSeats.remove(seat);
			}
		}
	}

	public List<Seats> getSeatsById(List<String> seats){
		List<Seats> seats1 = new ArrayList<>();
		for(String seat : seats){
			if(lockedSeats.containsKey(seat)){
				seats1.add(lockedSeats.get(seat));
			} else {
				throw new IllegalArgumentException("Illegal Action: Seat " + seat + " not locked! Seats should be locked first!!");
			}
		}
		return seats1;
	}
	public void bookSeats(List<String> seatName){
		for(String seat : seatName){
			if(lockedSeats.containsKey(seat)) {
				bookedSeats.put(seat, lockedSeats.get(seat));
				lockedSeats.remove(seat);
			}
		}
	}

	public Map<PricingPlan, Double> getPricePlan() {
		return pricePlan;
	}
}
