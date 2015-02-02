package ch.rallo.mfgt.widget.utils;

import ch.rallo.mfgt.widget.bean.Reservation;

import static ch.rallo.mfgt.widget.utils.FormatUtils.formatDateTime;

public class ReservationDescriptionGenerator {

	private final Reservation reservation;

	public ReservationDescriptionGenerator(Reservation reservation) {
		this.reservation = reservation;
	}

	public String generate() {
		return String.format("%s - %s\n%s",
				formatDateTime(reservation.getReservationStart()),
				formatDateTime(reservation.getReservationEnd()),
				reservation.getPilot());
	}
}
