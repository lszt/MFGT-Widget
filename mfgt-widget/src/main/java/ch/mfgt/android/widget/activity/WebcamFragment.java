package ch.mfgt.android.widget.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import ch.mfgt.android.widget.R;
import ch.mfgt.android.widget.utils.RequestSingleton;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class WebcamFragment extends Fragment {

	public static final String URL = "URL";

	public static WebcamFragment newInstance(String url) {
		WebcamFragment fragment = new WebcamFragment();
		Bundle bundle = new Bundle(1);
		bundle.putString(URL, url);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		String url = getArguments().getString(URL);
		View view = inflater.inflate(R.layout.webcam_fragment, container, false);
		ImageView imageView = (ImageView) view.findViewById(R.id.webcamFragmentImageView);
		loadImage(imageView, url);

		return view;
	}

	private void loadImage(final ImageView imageView, String imageUrl) {
		ImageRequest request = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
			}
		}, 0, 0, null, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				imageView.setImageResource(R.drawable.icon);
			}
		});

		RequestSingleton.getInstance(getActivity()).addToRequestQueue(request);
	}
}
