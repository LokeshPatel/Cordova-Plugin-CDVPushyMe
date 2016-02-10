package com.lokesh.CDVPushyMe.plugin;

import me.pushy.sdk.Pushy;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This class echoes a string called from JavaScript.
 */
public class CDVPlushyMePlugin extends CordovaPlugin {

	public static final String PROPERTY_REG_ID_PushyMe = "registration_PushyMe_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String TAG = "Push Me Service";
	  public static final String PREFS_NAME = "PushyMeData";
	public CallbackContext getBackContext;
	public Context appContext =null;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	appContext = cordova.getActivity().getApplicationContext();
    	getBackContext = callbackContext; 
    	if (action.equals("pushyMeTokenID")) {
        	final SharedPreferences prefs = getPushyMePreferences(appContext);
    		String registrationId = prefs.getString(PROPERTY_REG_ID_PushyMe, "");	
            if(registrationId.isEmpty())
            new registerForPushNotificationsAsync().execute();
            else
            getBackContext.success(registrationId); 	
            return true;
        }
        
        if (action.equals("installPlugin")) {
        	getPushyMelistenEvent(appContext);
            return true;
        }
        return false;
    }
    
    private class registerForPushNotificationsAsync extends AsyncTask<Void, Void, Exception>
    {
    	protected Exception doInBackground(Void... params)
    	{
    		try
    		{
    			String registrationId = Pushy.register(appContext);
    			sendRegistrationIdToBackendServer(registrationId,appContext);
    		}
    		catch( Exception exc )
    		{
    			// Return exc to onPostExecute
    			return exc;
    		}
    		return null;
    	}

    	@Override
    	protected void onPostExecute(Exception exc)
    	{
    		// Failed?
    		if ( exc != null )
    		{
    			getBackContext.error(exc.getMessage().toString());
    			return;
    		}
    		else
    		{
    			getBackContext.success();
    		}		
    	}

    	void sendRegistrationIdToBackendServer(String registrationId,Context appContext) throws Exception
    	{
    	   storeRegistrationId(appContext, registrationId, PROPERTY_REG_ID_PushyMe);
    	   getBackContext.success(registrationId);
    	}
    }
    
    private void storeRegistrationId(Context context, String regId,String property_Reg_ID) {
	    final SharedPreferences prefs = getPushyMePreferences(context);
	    String appVersion = getVersionID(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(property_Reg_ID, regId);
	    editor.putString(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
    
    private SharedPreferences getPushyMePreferences(Context context) {
        return  context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
	}
    
    private String getVersionID(Context context)
    {
    	PackageManager manager = context.getPackageManager();
 	    PackageInfo info = null;
 	    String getid ="";
 		try {
 			info = manager.getPackageInfo(
 			    context.getPackageName(), 0);
 			getid = info.versionName;
 		} catch (NameNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	       return getid;
    }
    
    public void getPushyMelistenEvent(Context context)
    {
    	final SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
	    String registrationId = prefs.getString(PROPERTY_REG_ID_PushyMe,"");
	    if(!registrationId.isEmpty())
	    {
    	    Pushy.listen(context);
	    }
      else
	    {
    	 cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
            	new registerForPushNotificationsAsync().execute();	
            }
           });
	    }
	}
}

