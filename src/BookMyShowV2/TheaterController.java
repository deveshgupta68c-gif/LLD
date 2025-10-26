package BookMyShowV2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheaterController {
	private Map<City, List<Theater>> fTheaterMap;
	private List<Theater> fTheaters;

	public TheaterController() {
		this.fTheaterMap = new HashMap<>();
		this.fTheaters = new ArrayList<>();
	}

	public List<Theater> getTheaterByCity(City city){
		if(fTheaterMap.containsKey(city)){
			return fTheaterMap.get(city);
		}
		return new ArrayList<>();
	}

	public List<Theater> getTheaters() {
		return fTheaters;
	}

	public void addTheater(Theater theater, City city){
		addTheaterToCity(theater, city);
	}

	private void addTheaterToCity(Theater theater, City city) {
		validateTheater(theater);
		if(fTheaterMap.containsKey(city)){
			fTheaterMap.get(city).add(theater);
		}else{
		fTheaterMap.put(city, new ArrayList<>());
			fTheaterMap.get(city).add(theater);
		}
		fTheaters.add(theater);
	}

	public void addTheater(Theater theater, List <City> cities){
		for(City city : cities){
			addTheaterToCity(theater, city);
		}
	}

	private void validateTheater(Theater theater) {
		if(theater == null){
			throw new IllegalArgumentException("Theater cannot be null");
		}
		if(fTheaters.contains(theater)){
			throw new IllegalArgumentException("Theater already exists");
		}
	}

	public Map<Theater, List<MovieShow>> getMovieShowsByMovieAndCityTheaterWise(City city, Movie movie) {
		Map<Theater, List<MovieShow>> movieShows = new HashMap<>();
		List<Theater> theaters = getTheaterByCity(city);
		for(Theater theater : theaters){
			for(MovieShow movieShow : theater.getMovieShows()){
				if(movieShow.getMovieEndDateTime().isAfter(LocalDateTime.now())){
					movieShows.putIfAbsent(theater, new ArrayList<>());
					movieShows.get(theater).add(movieShow);
				}
			}
		}
		return movieShows;
	}
}
