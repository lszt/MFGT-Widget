package ch.mfgt.android.widget.bean;

import android.view.View;

public class ContactItem {

	private int iconId;
	private String label;
	private View.OnClickListener clickListener;

	public ContactItem() {
	}

	public ContactItem(int iconId, String label) {
		this(iconId, label, null);
	}

	public ContactItem(int iconId, String label, View.OnClickListener clickListener) {
		this.iconId = iconId;
		this.label = label;
		this.clickListener = clickListener;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public View.OnClickListener getClickListener() {
		return clickListener;
	}

	public void setClickListener(View.OnClickListener clickListener) {
		this.clickListener = clickListener;
	}
}
