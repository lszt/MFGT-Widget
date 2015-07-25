package ch.rallo.mfgt.widget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import ch.rallo.mfgt.widget.R;
import ch.rallo.mfgt.widget.bean.AerodromeStatus;
import ch.rallo.mfgt.widget.utils.RequestSingleton;
import ch.rallo.mfgt.widget.utils.StatusHtmlGenerator;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

public class StatusActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.status);

		final WebView webView = (WebView) findViewById(R.id.statusWebView);

		String statusUrl = getApplication().getString(R.string.status_url);
		StringRequest request = new StringRequest(statusUrl, new Response.Listener<String>() {
			@Override
			public void onResponse(String statusJson) {
				AerodromeStatus status = new Gson().fromJson(statusJson, AerodromeStatus.class);
				String html = new StatusHtmlGenerator(getApplication(), status).generate();
				webView.loadData(html, "text/html", "UTF-8");
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				webView.loadData("Failed to load status", "text/html", "UTF-8");
			}
		});

		RequestSingleton.getInstance(this).addToRequestQueue(request);
	}
}
