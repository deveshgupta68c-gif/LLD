package BookMyShowV2;

import java.util.ArrayList;
import java.util.List;

public class BookingController {
	List<Booking> bookings;

	public BookingController() {
		bookings = new ArrayList<>();
	}

	public void bookTickets(List<String> seats, MovieShow movieShow, Payment payment) {
		List<Seats> seats1 = movieShow.getSeatsById(seats);
		validatePayment(movieShow, payment, seats1);
		movieShow.bookSeats(seats);
		Booking booking = new Booking(bookings.size() + 1, payment, movieShow, seats1);
		bookings.add(booking);
	}

	private void validatePayment(MovieShow movieShow, Payment payment, List<Seats> seats) {
		Double totalAmnt = 0.0;
		for(Seats seat : seats) {
			totalAmnt += movieShow.getPricePlan().get(seat.getPricingPlan());
		}
		if(totalAmnt > payment.getAmount()){
			throw new IllegalArgumentException("Payment amount is less than total amount. Cannot Book Seats");
		}
	}
}
