package BookMyShow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Theater {
	public String name;
	public String address;
	public String city;
	public String state;
	public List<MovieShow> shows;

	public Theater(String name, String address, String city, String state) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.shows = new ArrayList<>();
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}

	public List<MovieShow> getMovieShows() {
		return this.shows;
	}

	public void addMovieShow(MovieShow movieShow) {
		shows.add(movieShow);
	}
}
