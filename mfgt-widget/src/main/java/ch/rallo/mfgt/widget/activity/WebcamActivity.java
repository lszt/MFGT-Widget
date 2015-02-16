package ch.rallo.mfgt.widget.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import ch.rallo.mfgt.widget.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class WebcamActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.webcam);

		List<Fragment> fragments = getFragments();
		WebcamPagerAdapter pagerAdapter = new WebcamPagerAdapter(getSupportFragmentManager(), fragments);
		ViewPager pager = (ViewPager) findViewById(R.id.webcamPager);
		pager.setAdapter(pagerAdapter);

		CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circles);
		circlePageIndicator.setViewPager(pager);
	}

	private List<Fragment> getFragments() {
		List<Fragment> fragments = new ArrayList<Fragment>();

		String urlString = getApplication().getString(R.string.webcams);
		String[] webCamUrls = urlString.split(",");

		for (String url : webCamUrls) {
			fragments.add(WebcamFragment.newInstance(url));
		}

		return fragments;
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.finish();
	}
}
