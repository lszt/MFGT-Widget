package ch.rallo.mfgt.widget.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

class WebcamPageAdapter extends FragmentPagerAdapter {

	private final List<Fragment> fragments;

	public WebcamPageAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
		super(fragmentManager);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
}