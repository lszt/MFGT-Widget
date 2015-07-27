package ch.mfgt.android.widget.bean;

import com.google.gson.annotations.SerializedName;

public class AerodromeStatus {

	private String status;
	private String message;
	@SerializedName("last_update_date")
	private String lastUpdateDate;
	@SerializedName("last_update_by")
	private String lastUpdateBy;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
}
