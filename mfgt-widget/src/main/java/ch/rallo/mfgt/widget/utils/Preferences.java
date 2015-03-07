package ch.rallo.mfgt.widget.utils;

import android.content.Context;
import android.content.SharedPreferences;
import ch.rallo.mfgt.widget.R;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

import java.util.Set;

public final class Preferences {

	private static final String COLLAPSED_RESERVATION_GROUPS = "collapsed_reservation_groups";

	private Preferences() {
	}

	public static boolean isReservationGroupCollapsed(Context context, String registration) {
		SharedPreferences sharedPref = getSharedPreferences(context);
		Set<String> headers = getCollapsedReservationGroups(sharedPref);
		return headers.contains(registration);
	}

	public static void setReservationGroupCollapsed(Context context, String registration, boolean collapsed) {
		SharedPreferences sharedPref = getSharedPreferences(context);
		Set<String> collapsedGroups = getCollapsedReservationGroups(sharedPref);
		if (collapsed) {
			collapsedGroups.add(registration);
		} else {
			collapsedGroups.remove(registration);
		}
		saveCollapsedReservationGroups(sharedPref, collapsedGroups);
	}

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
	}

	private static Set<String> getCollapsedReservationGroups(SharedPreferences sharedPref) {
		String collapsedGroups = sharedPref.getString(COLLAPSED_RESERVATION_GROUPS, "");
		return Sets.newHashSet(Splitter.on(',').omitEmptyStrings().trimResults().split(collapsedGroups));
	}

	private static void saveCollapsedReservationGroups(SharedPreferences sharedPref, Set<String> collapsedGroups) {
		String joined = Joiner.on(',').skipNulls().join(collapsedGroups);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(COLLAPSED_RESERVATION_GROUPS, joined);
		editor.commit();
	}
}
