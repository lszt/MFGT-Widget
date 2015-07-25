package ch.rallo.mfgt.widget.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {

	public static String formatDateTime(String dateTimeString, String style) {
		DateTime dateTime = DateTime.parse(dateTimeString);
		DateTimeFormatter formatter = DateTimeFormat.forStyle(style);
		return dateTime.toString(formatter);
	}

	public static int compare(String dateTimeString1, String dateTimeString2) {
		DateTime dateTime1 = DateTime.parse(dateTimeString1);
		DateTime dateTime2 = DateTime.parse(dateTimeString2);
		return dateTime1.compareTo(dateTime2);
	}
}
