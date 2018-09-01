package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;

/**
 * Created by emobi8 on 8/12/2016.
 */
public class NotificationPanel {
    private Context parent;
    private static NotificationManager nManager;
    private NotificationCompat.Builder nBuilder;
    private RemoteViews remoteView;
    public static boolean flag = true;

    public NotificationPanel(Context parent) {
        // TODO Auto-generated constructor stub
        this.parent = parent;
        nBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(parent)
                .setContentTitle("Parking Meter")
                .setSmallIcon(R.drawable.finallogo)
                .setOngoing(true);

        remoteView = new RemoteViews(parent.getPackageName(), R.layout.notificationview);

        Intent intent = new Intent(parent, ContactUs.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(parent, 4, intent, 0);
        remoteView.setOnClickPendingIntent(R.id.btnappp, pendingIntent);
        //set the button listeners
        setListeners(remoteView);
        nBuilder.setContent(remoteView);

        nManager = (NotificationManager) parent.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(2, nBuilder.build());
    }

    public void setListeners(RemoteViews view) {
        //listener 1
        Intent volume = new Intent(parent, NotificationReturnSlot.class);
        volume.putExtra("DO", "volume");
        PendingIntent btn1 = PendingIntent.getActivity(parent, 0, volume, 0);
        view.setOnClickPendingIntent(R.id.btnclose, btn1);

        //listener 2
//            if(NotificationMediaPlayer.music_flag) {
        Intent play = new Intent(parent, NotificationReturnSlot.class);
        play.putExtra("DO", "play");
        PendingIntent btn2 = PendingIntent.getActivity(parent, 1, play, 0);
        view.setOnClickPendingIntent(R.id.btnplay, btn2);
//                NotificationMediaPlayer.music_flag=false;
//            }
//            else{
//                NotificationMediaPlayer.music_flag=true;
//                Intent pause = new Intent(parent, NotificationReturnSlot.class);
//                pause.putExtra("DO", "pause");
//                PendingIntent btn2 = PendingIntent.getActivity(parent, 1, pause, 0);
//                view.setOnClickPendingIntent(R.id.btn2, btn2);
//            }

        //listener 3
        Intent previous = new Intent(parent, NotificationReturnSlot.class);
        previous.putExtra("DO", "previous");
        PendingIntent previous1 = PendingIntent.getActivity(parent, 2, previous, 0);
        view.setOnClickPendingIntent(R.id.btnprev, previous1);

        //listener 4
        Intent next = new Intent(parent, NotificationReturnSlot.class);
        next.putExtra("DO", "next");
        PendingIntent next1 = PendingIntent.getActivity(parent, 3, next, 0);
        view.setOnClickPendingIntent(R.id.btnnext, next1);
    }

    public static void notificationCancel() {
        try {
            nManager.cancel(2);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TagUtils.getTag(), e.toString());
        }
    }
}