package BookMyShowV2;

public class Movie {
	private int id;
	private String name;
	private int durationInMinutes;

	public Movie(int id, String name, int durationInMinutes){
		this.id = id;
		this.name = name;
		this.durationInMinutes = durationInMinutes;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}
}
