package com.lokesh.CDVPushyMe.plugin;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

// Referenced classes of package com.lokesh.CDVPushyMe.plugin:
//            PushHandlerActivity

@SuppressLint("NewApi") public class PushReceiver extends BroadcastReceiver
{

    private static final String TAG = "Pushy Me Intent Service";
   @Override
    public void onReceive(Context context, Intent intent)
    {
		Log.d(TAG, "onMessage - context: " + context);
		// Extract the payload from the message
		Bundle extras = intent.getExtras();
		if (extras != null)
		{
	           // Send a notification if there is a message
               if (extras.getString("message") != null && extras.getString("message").length() != 0) {
                   createNotification(context, extras);
               }
         }
    }
    private static String getAppName(Context context)
    {
        return (String)context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
    }

	public void createNotification(Context context, Bundle extras)
    {
    	NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		String appName = getAppName(context);

		Intent notificationIntent = new Intent(context, PushyMeHandlerActivity.class);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		notificationIntent.putExtra("pushBundle", extras);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		int defaults = Notification.DEFAULT_ALL;

		if (extras.getString("defaults") != null) {
			try {
				defaults = Integer.parseInt(extras.getString("defaults"));
			} catch (NumberFormatException e) {}
		}
		
		Notification.Builder mBuilder =
			new Notification.Builder(context)
				.setDefaults(defaults)
				.setSmallIcon(context.getApplicationInfo().icon)
				.setWhen(System.currentTimeMillis())
				.setContentTitle(appName)
				.setTicker(extras.getString("title"))
				.setContentIntent(contentIntent)
				.setAutoCancel(true);

		String message = extras.getString("message");
		if (message != null) {
			mBuilder.setContentText(message);
		} else {
			mBuilder.setContentText("<missing message content>");
		}

		String msgcnt = extras.getString("msgcnt");
		if (msgcnt != null) {
			mBuilder.setNumber(Integer.parseInt(msgcnt));
		}
		int notId = 0;
		mNotificationManager.notify((String) appName, notId, mBuilder.build());
    }
}
