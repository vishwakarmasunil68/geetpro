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
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;


public class Notificationn extends Notification {


    public static Context ctx = null;

    String channelId = "geetProID";
    String channelName = "Geet Pro";
    public static NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    public static RemoteViews contentView;
    public static Notificationn nn;


    @SuppressLint("NewApi")
    public Notificationn(Context ctx) {
        Log.d(TagUtils.getTag(), "notification showing");
        this.ctx = ctx;
        nn = this;
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
        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
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
                Bitmap bitmap = ((BitmapDrawable) NewAlbumSongsActivity.iv_album_cover.getDrawable()).getBitmap();
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            } else {
                Bitmap bitmap = Setting_event.bmp;
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            }
        } catch (Exception e) {
            Log.d("sun", e.toString());

        }

//
//            Intent i=new Intent(ctx,BlurMediaPlayer.class);
//            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(ctx, 0, i, 0);
//            builder.setContent(contentView);
//            builder.setSubText("This is a subtext");
//            builder.build();
//             notification = builder.getNotification();

        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;

        Intent intent = new Intent(ctx, BlurMediaPlayer1.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 3, intent, 0);
//            MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp, pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }


    public void setListeners(RemoteViews view) {


        //radio listener
        Intent pausee = new Intent(ctx, StartReceiver.class);
        pausee.putExtra("DO", "app");
        PendingIntent pRadio = PendingIntent.getBroadcast(ctx, 0, pausee, PendingIntent.FLAG_UPDATE_CURRENT);
        // view.setOnClickPendingIntent(R.id.btnappp, pRadio);
// songname=(TextView)view.

        //volume listener
        Intent nextt = new Intent(ctx, StartReceiver.class);
        nextt.putExtra("DO", "next");
        PendingIntent pVolume = PendingIntent.getBroadcast(ctx, 1, nextt, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnnext, pVolume);

        //reboot listener
        Intent prevv = new Intent(ctx, StartReceiver.class);
        prevv.putExtra("DO", "prev");
        PendingIntent pReboot = PendingIntent.getBroadcast(ctx, 5, prevv, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnprev, pReboot);

        //top listener
        Intent appp = new Intent(ctx, StartReceiver.class);
        //appp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        appp.putExtra("DO", "pause");
        PendingIntent pTop = PendingIntent.getBroadcast(ctx, 4, appp, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnplay, pTop);

        //app listener
        Intent app = new Intent(ctx, StartReceiver.class);
        app.putExtra("DO", "close");
        PendingIntent pApp = PendingIntent.getBroadcast(ctx, 2, app, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnclose, pApp);

//            Intent toto=new Intent(ctx, HelperActivityy.class);
//            app.putExtra("DO", "rla");
//            PendingIntent ptoto = PendingIntent.getActivity(ctx, 6, toto, PendingIntent.FLAG_UPDATE_CURRENT);
//            view.setOnClickPendingIntent(R.id.rLayout, ptoto);

    }

    public static void clearNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    public static void changeImageView1() {
        Log.d("sunil", "changing view");
        nn.changeNotification1();
    }

    public static void changeImageView2() {
        Log.d("sunil", "changing view");
        nn.changeNotification2();
    }

    public void changeNotification1() {
        Log.d("sunil", "changing notification");
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
                Bitmap bitmap = ((BitmapDrawable) NewAlbumSongsActivity.iv_album_cover.getDrawable()).getBitmap();
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            } else {
                Bitmap bitmap = Setting_event.bmp;
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            }
        } catch (Exception e) {
            Log.d("sun", e.toString());
        }

//
//            Intent i=new Intent(ctx,BlurMediaPlayer.class);
//            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(ctx, 0, i, 0);
//            builder.setContent(contentView);
//            builder.setSubText("This is a subtext");
//            builder.build();
//             notification = builder.getNotification();

        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        ;

        Intent intent = new Intent(ctx, BlurMediaPlayer1.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 3, intent, 0);
//            MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp, pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void changeNotification2() {
        Log.d("sunil", "changing notification");
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
                Bitmap bitmap = ((BitmapDrawable) NewAlbumSongsActivity.iv_album_cover.getDrawable()).getBitmap();
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            } else {
                Bitmap bitmap = Setting_event.bmp;
                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
            }
        } catch (Exception e) {
            Log.d("sun", e.toString());
            notification.icon = R.drawable.notification_img;
        }

//
//            Intent i=new Intent(ctx,BlurMediaPlayer.class);
//            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(ctx, 0, i, 0);
//            builder.setContent(contentView);
//            builder.setSubText("This is a subtext");
//            builder.build();
//             notification = builder.getNotification();

        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;

        Intent intent = new Intent(ctx, BlurMediaPlayer1.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 3, intent, 0);
//            MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp, pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

}
