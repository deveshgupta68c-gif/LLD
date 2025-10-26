package BookMyShowV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieController {
	private Map<City, List<Movie>> fCityMoviesMap;
	private List<Movie> fMovies;

	public MovieController() {
		this.fCityMoviesMap = new HashMap<>();
		this.fMovies = new ArrayList<>();
	}

	public List<Movie> getMovieByCity(City city){
		if(fCityMoviesMap.containsKey(city)){
			return fCityMoviesMap.get(city);
		}
		System.out.println("No movie in found in city " + city.toString());
		return new ArrayList<>();
	}

	public List<Movie> getMovies() {
		return fMovies;
	}

	public void addMovie(Movie movie, City city){
		fMovies.add(movie);
		fCityMoviesMap.computeIfAbsent(city, k -> new ArrayList<>()).add(movie);
	}

	public void addMovie(Movie movie, List<City> cities){
		fMovies.add(movie);
		cities.forEach((city)->{
			fCityMoviesMap.computeIfAbsent(city, k -> new ArrayList<>()).add(movie);
		});
	}

}
