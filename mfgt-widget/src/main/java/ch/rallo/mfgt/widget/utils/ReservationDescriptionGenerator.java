package ch.rallo.mfgt.widget.utils;

import ch.rallo.mfgt.widget.bean.Reservation;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ReservationDescriptionGenerator {

	private final Reservation reservation;

	public ReservationDescriptionGenerator(Reservation reservation) {
		this.reservation = reservation;
	}

	public String getTitle() {
		DateTime start = DateTime.parse(reservation.getReservationStart());
		DateTime end = DateTime.parse(reservation.getReservationEnd());

		DateTimeFormatter formatter = DateTimeFormat.forStyle("MS");
		if (new LocalDate(start).equals(new LocalDate(end))) {
			formatter = DateTimeFormat.forStyle("-S");
		}

		return String.format("%s - %s", start.toString(formatter), end.toString(formatter));
	}

	public String getDescription() {
		return reservation.getPilot();
	}
}
