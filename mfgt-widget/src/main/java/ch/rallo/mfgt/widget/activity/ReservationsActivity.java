package ch.rallo.mfgt.widget.activity;

import android.os.Bundle;
import android.widget.ListView;
import ch.rallo.mfgt.widget.R;
import ch.rallo.mfgt.widget.bean.Reservation;
import ch.rallo.mfgt.widget.utils.RequestSingleton;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ReservationsActivity extends AbstractActivity {

	private static final Type RESERVATION_LIST_TYPE = new TypeToken<List<Reservation>>() {}.getType();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.reservations);

		ListView listView = (ListView) findViewById(R.id.reservationsListView);
		final ReservationsAdapter reservationsAdapter = new ReservationsAdapter(getApplicationContext());
		listView.setAdapter(reservationsAdapter);

		String requestUrl = getApplication().getString(R.string.reservations_url);
		StringRequest request = new StringRequest(requestUrl, new Response.Listener<String>() {
			@Override
			public void onResponse(String reservationJson) {
				List<Reservation> reservations = new Gson().fromJson(reservationJson, RESERVATION_LIST_TYPE);
				for (Reservation reservation : reservations) {
					reservationsAdapter.add(reservation);
				}
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		});

		RequestSingleton.getInstance(this).addToRequestQueue(request);
	}
}
