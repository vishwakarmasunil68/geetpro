package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import webuters.com.geet_uttarakhand20aprilpro.R;

/**
 * Created by sunil on 26-08-2016.
 */
public class Notificationn2 extends Notification {

    public static Context ctx = null;
    public static MediaPlayer mediaPlayer;
    public static NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    public static Notificationn2 nn2;
    public static RemoteViews contentView;

    String channelId = "geetProID";
    String channelName = "Geet Pro";

    @SuppressLint("NewApi")
    public Notificationn2(Context ctx) {
        this.ctx = ctx;
        nn2 = this;
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) ctx.getSystemService(ns);

        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        CharSequence tickerText = "Shortcuts";
        long when = System.currentTimeMillis();
        Builder builder = new Builder(ctx);
        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }
        Notification notification = builder.getNotification();


        notification.when = when;
        notification.tickerText = tickerText;

        notification.icon = R.drawable.final_logo_1;

        contentView = new RemoteViews(ctx.getPackageName(), R.layout.messageview1);

        setListeners(contentView);
        notification.contentView = contentView;
        try {

            if (MainActivity.img_flag) {
                Bitmap bitmap = ((BitmapDrawable) AlbumeSongs.Album_image_baner.getDrawable()).getBitmap();
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            } else {
                Bitmap bitmap = Setting_event.bmp;
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            }
        } catch (Exception e) {
            Log.d("sun", e.toString());
            notification.icon = R.drawable.final_logo_1;
        }

        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        ;

        Intent intent = new Intent(ctx, BluredMediaPlayer2.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 3, intent, 0);
//        MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp, pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }


    public void setListeners(RemoteViews view) {


        //radio listener
        Intent pausee = new Intent(ctx, StartReciever2.class);
        pausee.putExtra("DO", "app");
        PendingIntent pRadio = PendingIntent.getBroadcast(ctx, 0, pausee, PendingIntent.FLAG_UPDATE_CURRENT);
        // view.setOnClickPendingIntent(R.id.btnappp, pRadio);
// songname=(TextView)view.

        //volume listener
        Intent nextt = new Intent(ctx, StartReciever2.class);
        nextt.putExtra("DO", "next");
        PendingIntent pVolume = PendingIntent.getBroadcast(ctx, 1, nextt, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnnext, pVolume);

        //reboot listener
        Intent prevv = new Intent(ctx, StartReciever2.class);
        prevv.putExtra("DO", "prev");
        PendingIntent pReboot = PendingIntent.getBroadcast(ctx, 5, prevv, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnprev, pReboot);

        //top listener
        Intent appp = new Intent(ctx, StartReciever2.class);
        //appp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        appp.putExtra("DO", "pause");
        PendingIntent pTop = PendingIntent.getBroadcast(ctx, 4, appp, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnplay, pTop);

        //app listener
        Intent app = new Intent(ctx, StartReciever2.class);
        app.putExtra("DO", "close");
        PendingIntent pApp = PendingIntent.getBroadcast(ctx, 2, app, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnclose, pApp);

//            Intent toto=new Intent(ctx, HelperActivityy.class);
//            app.putExtra("DO", "rla");
//            PendingIntent ptoto = PendingIntent.getActivity(ctx, 6, toto, 0);
//            view.setOnClickPendingIntent(R.id.rLayout, ptoto);

    }

    public static void clearNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    public static void changeImageView1() {
        Log.d("sunil", "changing view");
        nn2.changeNotification1();
    }

    public static void changeImageView2() {
        Log.d("sunil", "changing view");
        nn2.changeNotification2();
    }


    public void changeNotification1() {
        Log.d("sunil", "change notification1");
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) ctx.getSystemService(ns);
        CharSequence tickerText = "Shortcuts";
        long when = System.currentTimeMillis();
        Builder builder = new Builder(ctx);
        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }
        Notification notification = builder.getNotification();


        notification.when = when;
        notification.tickerText = tickerText;

        notification.icon = R.drawable.final_logo_1;

        contentView = new RemoteViews(ctx.getPackageName(), R.layout.messageview1);

        setListeners(contentView);
        notification.contentView = contentView;
        try {

            if (MainActivity.img_flag) {
                Bitmap bitmap = ((BitmapDrawable) AlbumeSongs.Album_image_baner.getDrawable()).getBitmap();
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            } else {
                Bitmap bitmap = Setting_event.bmp;
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            }
        } catch (Exception e) {
            Log.d("sun", e.toString());
            notification.icon = R.drawable.final_logo_1;
        }

        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        ;

        Intent intent = new Intent(ctx, BluredMediaPlayer2.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 3, intent, 0);
//        MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp, pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }


    public void changeNotification2() {
        Log.d("sunil", "changenotification2");
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) ctx.getSystemService(ns);
        CharSequence tickerText = "Shortcuts";
        long when = System.currentTimeMillis();
        Builder builder = new Builder(ctx);
        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }
        Notification notification = builder.getNotification();


        notification.when = when;
        notification.tickerText = tickerText;

        notification.icon = R.drawable.final_logo_1;

        contentView = new RemoteViews(ctx.getPackageName(), R.layout.messageview);

        setListeners(contentView);
        notification.contentView = contentView;
        try {

            if (MainActivity.img_flag) {
                Bitmap bitmap = ((BitmapDrawable) AlbumeSongs.Album_image_baner.getDrawable()).getBitmap();
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            } else {
                Bitmap bitmap = Setting_event.bmp;
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            }
        } catch (Exception e) {
            Log.d("sun", e.toString());
            notification.icon = R.drawable.final_logo_1;
        }

        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        ;

        Intent intent = new Intent(ctx, BluredMediaPlayer2.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 3, intent, 0);
//        MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp, pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

}
