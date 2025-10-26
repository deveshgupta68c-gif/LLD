package BookMyShowV2;

import java.util.List;
import java.util.Objects;

public class Booking {
	private int bookingId;
	private Payment payment;
	private MovieShow movieShow;
	private List<Seats> seats;

	public Booking(int bookingId, Payment payment, MovieShow movieShow, List<Seats> seats) {
		this.bookingId = bookingId;
		this.payment = payment;
		this.movieShow = movieShow;
		this.seats = seats;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if(obj instanceof  Booking){
			Booking booking = (Booking) obj;
			return this.payment.getPaymentId() == booking.payment.getPaymentId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(payment.getPaymentId());
	}

}
