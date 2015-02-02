package ch.rallo.mfgt.widget.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FormatUtils {

	public static String formatDateTime(String dateTimeString) {
		DateTime dateTime = DateTime.parse(dateTimeString);
		DateTimeFormatter formatter = DateTimeFormat.forStyle("MM");
		return dateTime.toString(formatter);
	}
}
