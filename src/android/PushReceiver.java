package com.lokesh.CDVPushyMe.plugin;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

// Referenced classes of package com.lokesh.CDVPushyMe.plugin:
//            PushHandlerActivity

@SuppressLint("NewApi") public class PushReceiver extends BroadcastReceiver
{

    private static final String TAG = "Pushy Me Intent Service";
	private static final String MY_PREFS_NAME = "saveNotification";
	private static final int MODE_PRIVATE = 0;
	private static final String MESSAGE_KEY = "msg_";
	private static final String MESSAGE_COUNT_KEY = "totalCount";
	private static final String EVENT_FOR_REMOVE_VALUE ="removeValue";
	private static final String EVENT_FOR_GET_ALL_VALUE ="getAllValue";
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
        int count = saveNotificationOnSharedPreferences (message,context);
		if (message != null) {
			mBuilder.setContentText(message);
		} else {
			mBuilder.setContentText("<missing message content>");
		}

	  /*String msgcnt = extras.getString("msgcnt");
		if (msgcnt != null) {
			mBuilder.setNumber(Integer.parseInt(msgcnt));
		}*/
        mBuilder.setNumber(count);
		int notId = 0;
		mNotificationManager.notify((String) appName, notId, mBuilder.build());

    }

	private int saveNotificationOnSharedPreferences (String message,Context context)
	{  int getCountMessage =0;
		try {
		SharedPreferences sharedPreferences =  context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
        getCountMessage = sharedPreferences.getInt(MESSAGE_COUNT_KEY, 0);
		getCountMessage = getCountMessage +1;
		String messageKey = MESSAGE_KEY +getCountMessage;
		editor.putString(messageKey, message);
		editor.putInt(MESSAGE_COUNT_KEY,getCountMessage );
		editor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return getCountMessage;
	}

	public JSONObject getNotificationOnSharedPreferences (Context context)
	{
		SharedPreferences sharedPreferences =  context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
		JSONObject jsonObject = convertSharedPreferencesToJsonObject(sharedPreferences,EVENT_FOR_GET_ALL_VALUE,"");
		return jsonObject;
	}

	public void removeNotificationOnSharedPreferences (Context context)
	{
		try {
			SharedPreferences sharedPreferences =  context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
			sharedPreferences.edit().clear().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeNotificationWithIDOnSharedPreferences (Context context,String removeMsgKey)
	{
		try {
			SharedPreferences sharedPreferences =  context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
			JSONObject jsonObject = convertSharedPreferencesToJsonObject(sharedPreferences,EVENT_FOR_REMOVE_VALUE,removeMsgKey);
			sharedPreferences.edit().clear().commit();
			addJsonValueInSharedPreferences(jsonObject,sharedPreferences);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JSONObject convertSharedPreferencesToJsonObject (SharedPreferences sharedPreferences ,String eventFor ,String removeMsgKey )
	{
		JSONObject jsonObject = new JSONObject();
		if(EVENT_FOR_REMOVE_VALUE.equalsIgnoreCase(eventFor)) {
			try {
				int getCountMessage = sharedPreferences.getInt(MESSAGE_COUNT_KEY, 0);
				for (int i = 1; i <= getCountMessage; i++) {
					String messageKey = MESSAGE_KEY + i;
					if (!messageKey.equalsIgnoreCase(removeMsgKey))
						jsonObject.put(String.valueOf(i), sharedPreferences.getString(messageKey, null));
				}
				addJsonValueInSharedPreferences(jsonObject,sharedPreferences);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				int getCountMessage = sharedPreferences.getInt(MESSAGE_COUNT_KEY,0);
				for(int i=1;i<=getCountMessage;i++) {
					String messageKey = MESSAGE_KEY +i;
					jsonObject.put(messageKey, sharedPreferences.getString(messageKey, null));
				}
				jsonObject.put(MESSAGE_COUNT_KEY, getCountMessage);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	private void addJsonValueInSharedPreferences (JSONObject jsonObject,SharedPreferences sharedPreferences)
	{
		try {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			int getCountMessage = jsonObject.length();
			Iterator<String> iter = jsonObject.keys();
			int i=1;
			while (iter.hasNext()) {
				String messageKey = MESSAGE_KEY +i;
				String key = iter.next();
				try {
					editor.putString(messageKey, jsonObject.getString(key));
				} catch (JSONException e) {
					// Something went wrong!
				}
				i++;
			}
			editor.putInt(MESSAGE_COUNT_KEY,getCountMessage);
			editor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
