package ch.rallo.mfgt.widget.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import ch.rallo.mfgt.widget.R;
import ch.rallo.mfgt.widget.bean.Reservation;
import ch.rallo.mfgt.widget.utils.DateUtils;
import ch.rallo.mfgt.widget.utils.Preferences;
import ch.rallo.mfgt.widget.utils.ReservationDescriptionGenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ReservationsAdapter extends BaseExpandableListAdapter {

	private Context context;
	private final List<String> groupHeaders = Lists.newArrayList();
	private final Map<String, List<Reservation>> reservationsGroupedByRegistration = Maps.newHashMap();

	public ReservationsAdapter(Context context) {
		this.context = context;
	}

	public void setReservations(List<Reservation> reservations) {
		groupHeaders.clear();
		reservationsGroupedByRegistration.clear();
		
		addReservations(reservations);
		
		super.notifyDataSetChanged();
	}

	@Override
	public Reservation getChild(int groupPosition, int childPosititon) {
		return this.reservationsGroupedByRegistration.get(this.groupHeaders.get(groupPosition)).get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.reservation_row, null);
		}

		TextView reservationTitle = (TextView) convertView.findViewById(R.id.reservationTitle);
		TextView reservationDescription = (TextView) convertView.findViewById(R.id.reservationDescription);

		Reservation reservation = getChild(groupPosition, childPosition);

		ReservationDescriptionGenerator descGenerator = new ReservationDescriptionGenerator(context, reservation);

		reservationTitle.setTypeface(null, descGenerator.getTitleTypefaceStyle());
		reservationDescription.setTypeface(null, descGenerator.getDescriptionTypefaceStyle());

		int textColor = descGenerator.getTextColor();
		reservationTitle.setTextColor(textColor);
		reservationDescription.setTextColor(textColor);

		reservationTitle.setText(descGenerator.getTitle());
		reservationDescription.setText(descGenerator.getDescription());

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.reservationsGroupedByRegistration.get(this.groupHeaders.get(groupPosition)).size();
	}

	@Override
	public String getGroup(int groupPosition) {
		return this.groupHeaders.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.groupHeaders.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String headerTitle = getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.reservation_group, null);
		}

		TextView groupHeader = (TextView) convertView.findViewById(R.id.reservationGroupHeader);
		groupHeader.setTypeface(null, Typeface.BOLD);
		groupHeader.setText(headerTitle);

		ExpandableListView expandableListView = (ExpandableListView) parent;
		if (Preferences.isReservationGroupCollapsed(context, headerTitle)) {
			expandableListView.collapseGroup(groupPosition);
		} else {
			expandableListView.expandGroup(groupPosition);
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private void addReservations(List<Reservation> reservations) {
		for (Reservation reservation : reservations) {
			String registration = reservation.getRegistration();

			if (!groupHeaders.contains(registration)) {
				groupHeaders.add(registration);
			}

			List<Reservation> reservationsOfRegistration = getReservationsOfRegistration(reservationsGroupedByRegistration, registration);
			reservationsOfRegistration.add(reservation);
		}

		Collections.sort(groupHeaders);
		sortReservationLists(reservationsGroupedByRegistration);
	}

	private List<Reservation> getReservationsOfRegistration(Map<String, List<Reservation>> listData, String registration) {
		List<Reservation> reservationsOfRegistration = listData.get(registration);
		if (reservationsOfRegistration == null) {
			reservationsOfRegistration = Lists.newArrayList();
			listData.put(registration, reservationsOfRegistration);
		}
		return reservationsOfRegistration;
	}

	private void sortReservationLists(Map<String, List<Reservation>> reservationsGroupedByRegistration) {
		for (List<Reservation> reservations : reservationsGroupedByRegistration.values()) {
			Collections.sort(reservations, new Comparator<Reservation>() {
				@Override
				public int compare(Reservation res1, Reservation res2) {
					return DateUtils.compare(res1.getReservationStart(), res2.getReservationStart());
				}
			});
		}
	}
}
