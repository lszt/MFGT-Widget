package ch.rallo.mfgt.widget.utils;

import ch.rallo.mfgt.widget.bean.Reservation;

import static ch.rallo.mfgt.widget.utils.DateUtils.formatReservationDate;

public class ReservationDescriptionGenerator {

	private final Reservation reservation;

	public ReservationDescriptionGenerator(Reservation reservation) {
		this.reservation = reservation;
	}

	public String getTitle() {
		return String.format("%s - %s",
				formatReservationDate(reservation.getReservationStart()),
				formatReservationDate(reservation.getReservationEnd()));
	}

	public String getDescription() {
		return reservation.getPilot();
	}
}
