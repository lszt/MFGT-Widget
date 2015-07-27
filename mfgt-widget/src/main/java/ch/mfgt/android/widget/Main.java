package ch.mfgt.android.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import ch.mfgt.android.widget.activity.ContactActivity;
import ch.mfgt.android.widget.activity.ReservationsActivity;
import ch.mfgt.android.widget.activity.StatusActivity;
import ch.mfgt.android.widget.activity.WebcamActivity;

public class Main extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);

			views.setOnClickPendingIntent(R.id.logoButton, getActivity(context, ContactActivity.class));
			views.setOnClickPendingIntent(R.id.statusButton, getActivity(context, StatusActivity.class));
			views.setOnClickPendingIntent(R.id.webcamButton, getActivity(context, WebcamActivity.class));
			views.setOnClickPendingIntent(R.id.reservationsButton, getActivity(context, ReservationsActivity.class));

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	private PendingIntent getActivity(Context context, Class cls) {
		Intent intent = new Intent(context, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return PendingIntent.getActivity(context, 0, intent, 0);
	}
}
