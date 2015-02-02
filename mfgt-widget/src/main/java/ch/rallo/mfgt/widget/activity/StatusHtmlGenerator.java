package ch.rallo.mfgt.widget.activity;

import ch.rallo.mfgt.widget.bean.AerodromeStatus;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class StatusHtmlGenerator {

	private final AerodromeStatus status;

	public StatusHtmlGenerator(AerodromeStatus status) {
		this.status = status;
	}

	public String generate() {
		return status.getMessage() + formatDate(status.getLastUpdateDate()) + ", " + status.getLastUpdateBy();
	}
	
	private String formatDate(String dateString) {
		DateTime dateTime = DateTime.parse(dateString);
		DateTimeFormatter formatter = DateTimeFormat.forStyle("MM");
		return dateTime.toString(formatter);
	}
}
