package com.lokesh.CDVPushyMe.plugin;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class PushyMeHandlerActivity extends Activity
{
	private static String TAG = "Pushy Me Handler Activity"; 

	/*
	 * this activity will be started if the user touches a notification that we own. 
	 * We send it's data off to the push plugin for processing.
	 * If needed, we boot up the main activity to kickstart the application. 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		forceMainActivityReload();
    }
  /**
	 * Forces the main activity to re-launch if it's unloaded.
	 */
	private void forceMainActivityReload()
	{
		PackageManager pm = getPackageManager();
		Intent launchIntent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());    
		launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);		
		startActivity(launchIntent);
		finish();
	}

  @Override
  protected void onResume() {
    super.onResume();
    final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.cancelAll();
  }

}
