package ch.rallo.mfgt.widget.activity;

import android.app.Activity;

public abstract class AbstractActivity extends Activity {

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
