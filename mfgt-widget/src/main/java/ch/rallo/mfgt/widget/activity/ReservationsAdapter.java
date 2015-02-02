package ch.rallo.mfgt.widget.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ch.rallo.mfgt.widget.R;
import ch.rallo.mfgt.widget.bean.Reservation;
import ch.rallo.mfgt.widget.utils.ReservationDescriptionGenerator;

public class ReservationsAdapter extends ArrayAdapter<Reservation> {

	public ReservationsAdapter(Context context) {
		super(context, android.R.layout.simple_spinner_item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_row, null);

			holder = new ViewHolder();

			holder.reservationTitle = (TextView) convertView.findViewById(R.id.reservationTitle);
			holder.reservationDescription = (TextView) convertView.findViewById(R.id.reservationDescription);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		Reservation reservation = getItem(position);

		holder.reservationTitle.setText(reservation.getRegistration());
		holder.reservationDescription.setText(new ReservationDescriptionGenerator(reservation).generate());

		return convertView;
	}

	private static class ViewHolder {
		TextView reservationTitle;
		TextView reservationDescription;
	}
}
