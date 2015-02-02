package ch.rallo.mfgt.widget.utils;

import ch.rallo.mfgt.widget.bean.AerodromeStatus;

import static ch.rallo.mfgt.widget.utils.FormatUtils.formatDateTime;

public class StatusHtmlGenerator {

	private final AerodromeStatus status;

	public StatusHtmlGenerator(AerodromeStatus status) {
		this.status = status;
	}

	public String generate() {
		return status.getMessage() + formatDateTime(status.getLastUpdateDate()) + ", " + status.getLastUpdateBy();
	}
}
