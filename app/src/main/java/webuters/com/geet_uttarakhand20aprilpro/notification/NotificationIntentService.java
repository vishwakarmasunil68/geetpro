package webuters.com.geet_uttarakhand20aprilpro.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.activity.MainActivity;


public class NotificationIntentService extends IntentService {
	// Sets an ID for the notification, so it can be updated
	//public static final int notifyID = 9001;
	NotificationCompat.Builder builder;

	public NotificationIntentService() {
		super("GcmIntentService");
	}


	@Override
	protected void onHandleIntent(Intent intent) {
	Bundle extras = intent.getExtras();
		// String message = extras.getString("message");  
		
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) 
		{
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) 
			{
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) 
			{
				sendNotification("Deleted messages on server: "+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) 
			{
				sendNotification("message");
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
		Intent resultIntent = new Intent(this, MainActivity.class);
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
				resultIntent, PendingIntent.FLAG_ONE_SHOT);
	        	
		NotificationCompat.Builder mNotifyBuilder;
		NotificationManager mNotificationManager;
	        
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		mNotifyBuilder = new NotificationCompat.Builder(this)
		.setContentTitle(getResources().getString(R.string.app_name))
		.setContentText("You've received new message.")
		.setSmallIcon(R.drawable.ic_launcher);
	        // Set pending intent
		mNotifyBuilder.setContentIntent(resultPendingIntent);
		
		// Set Vibrate, Sound and Light	        
		int defaults = 0;
		defaults = defaults | Notification.DEFAULT_LIGHTS;
		defaults = defaults | Notification.DEFAULT_VIBRATE;
		defaults = defaults | Notification.DEFAULT_SOUND;
		
		mNotifyBuilder.setDefaults(defaults);
		mNotifyBuilder.setContentText(msg);
	        // Set autocancel
		mNotifyBuilder.setAutoCancel(true);
		// Post a notification	        
		
		Long tsLong = System.currentTimeMillis()/1000;
		int notifyID = tsLong.intValue();
			
		mNotificationManager.notify(notifyID, mNotifyBuilder.build());
	        
	}
}
