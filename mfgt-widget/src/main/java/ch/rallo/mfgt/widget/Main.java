package ch.rallo.mfgt.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import ch.rallo.mfgt.widget.activity.ReservationsActivity;
import ch.rallo.mfgt.widget.activity.StatusActivity;
import ch.rallo.mfgt.widget.activity.WebcamActivity;

public class Main extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);

			String mfgtUrl = context.getResources().getString(R.string.mfgt_url);
			views.setOnClickPendingIntent(R.id.logoButton, viewUrl(context, mfgtUrl));

			views.setOnClickPendingIntent(R.id.statusButton, getActivity(context, StatusActivity.class));
			views.setOnClickPendingIntent(R.id.webcamButton, getActivity(context, WebcamActivity.class));
			views.setOnClickPendingIntent(R.id.reservationsButton, getActivity(context, ReservationsActivity.class));

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	private PendingIntent getActivity(Context context, Class cls) {
		Intent intent = new Intent(context, cls);
		return PendingIntent.getActivity(context, 0, intent, 0);
	}

	private PendingIntent viewUrl(Context context, String uri) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		return PendingIntent.getActivity(context, 0, intent, 0);
	}
}
