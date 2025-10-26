package BookMyShow;

import java.util.List;

public class User {

	private String name;
	private String email;
	private List<Booking> bookings;

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
	}
}
