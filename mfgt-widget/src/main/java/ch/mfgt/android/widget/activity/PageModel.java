package ch.mfgt.android.widget.activity;

import android.widget.ExpandableListView;
import android.widget.TextView;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class PageModel {

	private final LocalDate initialDate;
	private int index;
	private TextView textView;
	private ExpandableListView expandableListView;
	private ReservationsAdapter adapter;

	public PageModel(LocalDate initialDate, int index) {
		this.initialDate = initialDate;
		this.index = index;
		setIndex(index);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getText() {
		LocalDate requestedDate = initialDate.plusDays(index);
		return requestedDate.toString(DateTimeFormat.forStyle("F-"));
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

	public TextView getTextView() {
		return textView;
	}

	public ExpandableListView getExpandableListView() {
		return expandableListView;
	}

	public ReservationsAdapter getAdapter() {
		return adapter;
	}

	public void setExpandableListView(ExpandableListView expandableListView, ReservationsAdapter adapter) {
		this.expandableListView = expandableListView;
		this.adapter = adapter;
	}
}
