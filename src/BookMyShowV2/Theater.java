package BookMyShowV2;

import java.util.List;

public class Theater {
	private int id;
	private City city;
	private String name;
	private String address;
	private List<MovieShow> movieShows;

	public Theater(int id, City city, String name, String address, List<MovieShow> movieShows) {
		this.id = id;
		this.city = city;
		this.name = name;
		this.address = address;
		this.movieShows = movieShows;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<MovieShow> getMovieShows() {
		return movieShows;
	}

	public void setMovieShows(List<MovieShow> movieShows) {
		this.movieShows = movieShows;
	}

	public void addMovieShow(MovieShow movieShow){
		this.movieShows.add(movieShow);
	}

	public void removeMovieShow(MovieShow movieShow){
		this.movieShows.remove(movieShow);
	}

	public void addMovieShows(List<MovieShow> movieShows){
		this.movieShows.addAll(movieShows);
	}

	public void removeMovieShows(List<MovieShow> movieShows){
		this.movieShows.removeAll(movieShows);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Theater theater = (Theater) obj;
		return id == theater.id;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
}
