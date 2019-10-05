package com.example.moviecatalogue.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.moviecatalogue.MainApp;
import com.example.moviecatalogue.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImagesBannerWidget extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "com.dicoding.picodiploma.EXTRA_ITEM";
    private static final String TOAST_ACTION = "com.dicoding.picodiploma.TOAST_ACTION";
    private static String WIDGET_CLICK = "widgetsclick";
    private static String WIDGET_ID_EXTRA = "widget_id_extra";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        try {
            Intent intent = new Intent(context, StackWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.images_banner_widget);
            views.setRemoteAdapter(R.id.stack_view, intent);
            views.setEmptyView(R.id.stack_view, R.id.empty_view);

            Intent toastIntent = new Intent(context, ImagesBannerWidget.class);
            toastIntent.setAction(ImagesBannerWidget.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

            views.setOnClickPendingIntent(R.id.btn_click, getPendingSelfIntent(context, appWidgetId, WIDGET_CLICK));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        } catch (Exception e) {
            Log.d(MainApp.LOG_D, "updateAppWidget: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
            }
            if (WIDGET_CLICK.equals(intent.getAction())) {
                try {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.images_banner_widget);
                    Toast.makeText(context, "Pasang kembali Widget", Toast.LENGTH_SHORT).show();
                    int appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.stack_view);
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(MainApp.LOG_D, "onReceive: " + e.getMessage());
                }
            }
        }

    }

    protected PendingIntent getPendingSelfIntent(Context context, int appWidgetId, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        intent.putExtra(WIDGET_ID_EXTRA, appWidgetId);
        return PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
    }
}