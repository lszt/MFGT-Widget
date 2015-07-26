package ch.rallo.mfgt.widget.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import ch.rallo.mfgt.widget.R;
import ch.rallo.mfgt.widget.bean.Reservation;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ReservationDescriptionGenerator {

	private final Context context;
	private final Reservation reservation;

	public ReservationDescriptionGenerator(Context context, Reservation reservation) {
		this.context = context;
		this.reservation = reservation;
	}

	public String getTitle() {
		DateTime start = DateTime.parse(reservation.getReservationStart());
		DateTime end = DateTime.parse(reservation.getReservationEnd());

		DateTimeFormatter formatter = DateTimeFormat.forStyle("MS");
		if (new LocalDate(start).equals(new LocalDate(end))) {
			formatter = DateTimeFormat.forStyle("-S");
		}

		String title = String.format("%s - %s", start.toString(formatter), end.toString(formatter));

		if (isWaiting()) {
			title += " (" + context.getString(R.string.reservation_waiting_label) + ")";
		}

		return title;
	}

	public String getDescription() {
		return reservation.getPilot();
	}

	public int getTitleTypefaceStyle() {
		return isWaiting() ? Typeface.BOLD_ITALIC : Typeface.BOLD;
	}

	public int getDescriptionTypefaceStyle() {
		return isWaiting() ? Typeface.ITALIC : Typeface.NORMAL;
	}

	private boolean isWaiting() {
		return "WAITING".equals(reservation.getReservationStatus());
	}

	public int getTextColor() {
		return isWaiting()
				? Color.parseColor(context.getString(R.string.reservation_waiting_text_color))
				: Color.parseColor(context.getString(R.string.reservation_text_color));
	}
}
