package webuters.com.geet_uttarakhand20aprilpro.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.activity.MainActivity;

/**
 * Created by emobi8 on 11/23/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message = remoteMessage.getData().get("message");
        String title = remoteMessage.getData().get("title");
        String image = remoteMessage.getData().get("image");
        String album_id = remoteMessage.getData().get("albumid");
        String albumname = remoteMessage.getData().get("albumname");
        try {
            if (remoteMessage.getNotification().getBody() != null) {
                Log.d(TAG, "From: " + remoteMessage.getFrom());
                Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
                sendNotification(remoteMessage.getNotification().getBody(), message,
                        title, image, album_id, albumname);
            } else {
//                Log.d("sunil", "trueorfalse:-" + TrueOrFlase);
                sendNotification("", message, title, image, album_id, albumname);
            }
        } catch (Exception e) {
//            Log.d("sunil", "trueorfalse:-" + TrueOrFlase);
            sendNotification("", message, title, image, album_id, albumname);
        }
    }

    private void sendNotification(String messageBody,String message,String title,String image,String album_id,String album_name) {


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", message);
        intent.putExtra("title", title);
        intent.putExtra("album_id", album_id);
        intent.putExtra("album_name", album_name);
        Log.d("sunil","TrueorFlase:-"+message);
        Log.d("notification","notification:-message:-"+message);
        Log.d("notification","notification:-title:-"+title);
        Log.d("notification","notification:-album_id:-"+album_id);
        Log.d("notification","notification:-album_name:-"+album_name);
        SharedPreferences sp=getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("album_id",album_id);
        editor.commit();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.finallogo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(getBitmapfromUrl(image)))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL("http://23.22.9.33/Admin/GeetApp/includes/uploads/"+imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
