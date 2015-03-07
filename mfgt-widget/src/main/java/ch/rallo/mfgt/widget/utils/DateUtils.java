package ch.rallo.mfgt.widget.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {

	public static String formatDateTime(String dateTimeString) {
		DateTime dateTime = DateTime.parse(dateTimeString);
		DateTimeFormatter formatter = DateTimeFormat.forStyle("MS");
		return dateTime.toString(formatter);
	}

	public static String formatReservationDate(String dateTimeString) {
		DateTime dateTime = DateTime.parse(dateTimeString);

		DateTimeFormatter formatter;

		if (isToday(dateTime)) {
			formatter = DateTimeFormat.forStyle("-S");
		} else {
			formatter = DateTimeFormat.forStyle("MS");
		}

		return dateTime.toString(formatter);
	}

	public static boolean isToday(DateTime dateTime) {
		LocalDate today = DateTime.now(dateTime.getZone()).toLocalDate();
		LocalDate tomorrow = today.plusDays(1);

		DateTime startOfToday = today.toDateTimeAtStartOfDay(dateTime.getZone());
		DateTime startOfTomorrow = tomorrow.toDateTimeAtStartOfDay(dateTime.getZone());

		return dateTime.isAfter(startOfToday) && dateTime.isBefore(startOfTomorrow);
	}

	public static int compare(String dateTimeString1, String dateTimeString2) {
		DateTime dateTime1 = DateTime.parse(dateTimeString1);
		DateTime dateTime2 = DateTime.parse(dateTimeString2);
		return dateTime1.compareTo(dateTime2);
	}
}
