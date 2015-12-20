package ch.mfgt.android.widget.utils;

import android.content.Context;
import ch.mfgt.android.widget.R;
import ch.mfgt.android.widget.bean.AerodromeStatus;
import com.google.common.base.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
		appendMessage(html, status.getMessage());
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

	private void appendMessage(StringBuilder html, String message) {
		if (!Strings.isNullOrEmpty(message)) {
			Document doc = Jsoup.parse(message);
			for (Element element : doc.body().children()) {
				if (element.isBlock() && !isEmptyElement(element)) {
					html.append(element.outerHtml());
				}
			}
		}
	}

	private boolean isEmptyElement(Element element) {
		return element.text().replaceAll("\u00A0+", " ").trim().isEmpty();
	}
}
