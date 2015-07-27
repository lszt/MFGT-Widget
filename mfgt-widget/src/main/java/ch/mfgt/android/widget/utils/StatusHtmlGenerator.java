package ch.mfgt.android.widget.utils;

import android.content.Context;
import ch.mfgt.android.widget.R;
import ch.mfgt.android.widget.bean.AerodromeStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static ch.mfgt.android.widget.utils.DateUtils.formatDateTime;

public class StatusHtmlGenerator {

	private final Context context;
	private final AerodromeStatus status;

	public StatusHtmlGenerator(Context context, AerodromeStatus status) {
		this.context = context;
		this.status = status;
	}

	public String generate() {
		StringBuilder html = new StringBuilder();
		html.append("<span style=\"font-weight: bold; font-size: 1.3em;\">")
				.append(getStatusLabel(status.getStatus()))
				.append("</span><br><br>");
		if (hasMessage(status)) {
			html.append(status.getMessage());
		}
		html.append(formatDateTime(status.getLastUpdateDate(), "FS"))
				.append(", ")
				.append(status.getLastUpdateBy());
		return html.toString();
	}

	private String getStatusLabel(String status) {
		switch (status) {
			case "open":
				return context.getString(R.string.status_open);
			case "restricted":
				return context.getString(R.string.status_restricted);
			case "closed":
				return context.getString(R.string.status_closed);
			default:
				return "";
		}
	}

	private boolean hasMessage(AerodromeStatus status) {
		Document doc = Jsoup.parse(status.getMessage());
		String text = doc.text().trim();
		return text.length() == 0;
	}
}
