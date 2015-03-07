package ch.rallo.mfgt.widget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import ch.rallo.mfgt.widget.R;
import ch.rallo.mfgt.widget.bean.Reservation;
import ch.rallo.mfgt.widget.utils.Preferences;
import ch.rallo.mfgt.widget.utils.RequestSingleton;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ReservationsActivity extends Activity {

	private static final Type RESERVATION_LIST_TYPE = new TypeToken<List<Reservation>>() {}.getType();

	private ReservationsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new ReservationsAdapter(getApplicationContext());

		setContentView(R.layout.reservations);

		ExpandableListView listView = (ExpandableListView) findViewById(R.id.reservationsListView);
		listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				String registration = adapter.getGroup(groupPosition);
				Preferences.setReservationGroupCollapsed(getApplicationContext(), registration, false);
			}
		});
		listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				String registration = adapter.getGroup(groupPosition);
				Preferences.setReservationGroupCollapsed(getApplicationContext(), registration, true);
			}
		});
		listView.setAdapter(adapter);
	}

	@Override
	protected void onStart() {
		super.onStart();

		String requestUrl = getApplication().getString(R.string.reservations_url);
		StringRequest request = new StringRequest(requestUrl, new Response.Listener<String>() {
			@Override
			public void onResponse(String reservationJson) {
				List<Reservation> reservations = new Gson().fromJson(reservationJson, RESERVATION_LIST_TYPE);
				adapter.setReservations(reservations);
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		});

		RequestSingleton.getInstance(this).addToRequestQueue(request);
	}
}
