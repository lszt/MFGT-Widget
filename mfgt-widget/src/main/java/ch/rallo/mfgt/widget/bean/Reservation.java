package ch.rallo.mfgt.widget.bean;

import com.google.gson.annotations.SerializedName;

public class Reservation {

	@SerializedName("Registration")
	private String registration;

	@SerializedName("Pilot")
	private String pilot;

	@SerializedName("ReservationStart")
	private String reservationStart;

	@SerializedName("ReservationEnd")
	private String reservationEnd;

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getPilot() {
		return pilot;
	}

	public void setPilot(String pilot) {
		this.pilot = pilot;
	}

	public String getReservationStart() {
		return reservationStart;
	}

	public void setReservationStart(String reservationStart) {
		this.reservationStart = reservationStart;
	}

	public String getReservationEnd() {
		return reservationEnd;
	}

	public void setReservationEnd(String reservationEnd) {
		this.reservationEnd = reservationEnd;
	}
}
