package ch.mfgt.android.widget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ch.mfgt.android.widget.R;
import ch.mfgt.android.widget.bean.Reservation;
import ch.mfgt.android.widget.utils.Preferences;
import ch.mfgt.android.widget.utils.RequestSingleton;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.joda.time.LocalDate;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ReservationsActivity extends Activity {

	private static final Type RESERVATION_LIST_TYPE = new TypeToken<List<Reservation>>() {}.getType();

	// we name the left, middle and right page
	private static final int PAGE_LEFT = 0;
	private static final int PAGE_MIDDLE = 1;
	private static final int PAGE_RIGHT = 2;

	private LayoutInflater mInflater;
	private int mSelectedPageIndex = 1;
	// we save each page in a model
	private PageModel[] mPageModel = new PageModel[3];
	private final Map<Integer, ReservationPage> reservationPages = Maps.newHashMap();
	private LocalDate initialDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initialDate = LocalDate.now();

		setContentView(R.layout.reservations);
		// initializing the model
		initPageModel();

		mInflater = getLayoutInflater();
		ReservationsPagerAdaper adapter = new ReservationsPagerAdaper();

		final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(adapter);
		// we dont want any smoothscroll. This enables us to switch the page
		// without the user notifiying this
		viewPager.setCurrentItem(PAGE_MIDDLE, false);

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mSelectedPageIndex = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_IDLE) {

					final PageModel leftPage = mPageModel[PAGE_LEFT];
					final PageModel middlePage = mPageModel[PAGE_MIDDLE];
					final PageModel rightPage = mPageModel[PAGE_RIGHT];

					final int oldLeftIndex = leftPage.getIndex();
					final int oldMiddleIndex = middlePage.getIndex();
					final int oldRightIndex = rightPage.getIndex();

					// user swiped to right direction --> left page
					if (mSelectedPageIndex == PAGE_LEFT) {

						// moving each page content one page to the right
						leftPage.setIndex(oldLeftIndex - 1);
						middlePage.setIndex(oldLeftIndex);
						rightPage.setIndex(oldMiddleIndex);

						setContent(PAGE_RIGHT);
						setContent(PAGE_MIDDLE);
						setContent(PAGE_LEFT);

						// user swiped to left direction --> right page
					} else if (mSelectedPageIndex == PAGE_RIGHT) {

						leftPage.setIndex(oldMiddleIndex);
						middlePage.setIndex(oldRightIndex);
						rightPage.setIndex(oldRightIndex + 1);

						setContent(PAGE_LEFT);
						setContent(PAGE_MIDDLE);
						setContent(PAGE_RIGHT);
					}
					viewPager.setCurrentItem(PAGE_MIDDLE, false);
				}
			}
		});
	}

	private void setContent(int index) {
		PageModel model = mPageModel[index];
		ReservationPage reservationPage = reservationPages.get(model.getIndex());
		if (reservationPage == null) {
			reservationPage = new ReservationPage(model.getIndex());
			reservationPages.put(model.getIndex(), reservationPage);
		}
		reservationPage.setTo(model);
	}

	private void initPageModel() {
		for (int i = 0; i < mPageModel.length; i++) {
			// initing the pagemodel with indexes of -1, 0 and 1
			mPageModel[i] = new PageModel(initialDate, i - 1);
		}
	}

	private class ReservationsPagerAdaper extends PagerAdapter {

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			// we only need three pages
			return 3;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.reservations_page, null);
			TextView textView = (TextView) layout.getChildAt(0);
			ExpandableListView expandableListView = (ExpandableListView) layout.getChildAt(1);

			final ReservationsAdapter adapter = new ReservationsAdapter(getApplicationContext());
			expandableListView.setAdapter(adapter);

			expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
				@Override
				public void onGroupExpand(int groupPosition) {
					String registration = adapter.getGroup(groupPosition);
					Preferences.setReservationGroupCollapsed(getApplicationContext(), registration, false);
				}
			});
			expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
				@Override
				public void onGroupCollapse(int groupPosition) {
					String registration = adapter.getGroup(groupPosition);
					Preferences.setReservationGroupCollapsed(getApplicationContext(), registration, true);
				}
			});

			PageModel currentPage = mPageModel[position];

			currentPage.setTextView(textView);
			currentPage.setExpandableListView(expandableListView, adapter);

			setContent(position);

			container.addView(layout);

			return layout;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}
	}

	public class ReservationPage {

		private final int index;
		private final LocalDate date;
		private List<Reservation> reservations;

		public ReservationPage(int index) {
			this.index = index;
			this.date = initialDate.plusDays(index);
		}

		public void setTo(PageModel pageModel) {
			pageModel.getTextView().setText(pageModel.getText());
			if (reservations != null) {
				setReservations(pageModel, reservations);
			}

			// load reservations in each case
			loadReservations(pageModel, date);
		}

		private void setReservations(PageModel pageModel, List<Reservation> reservations) {
			pageModel.getAdapter().setReservations(reservations);
			pageModel.getExpandableListView().setSelection(0);
		}

		private void loadReservations(final PageModel pageModel, LocalDate date) {
			String dateString = date.toString("yyyyMMdd");

			String requestUrl = getApplication().getString(R.string.reservations_url) + "/" + dateString;
			StringRequest request = new StringRequest(requestUrl, new Response.Listener<String>() {
				@Override
				public void onResponse(String reservationJson) {
					List<Reservation> reservations = new Gson().fromJson(reservationJson, RESERVATION_LIST_TYPE);
					ReservationPage.this.reservations = reservations;
					setReservations(pageModel, reservations);
				}
			}, new Response.ErrorListener() {
				public void onErrorResponse(VolleyError error) {
					error.printStackTrace();
				}
			});

			RequestSingleton.getInstance(ReservationsActivity.this).addToRequestQueue(request);
		}
	}
}
