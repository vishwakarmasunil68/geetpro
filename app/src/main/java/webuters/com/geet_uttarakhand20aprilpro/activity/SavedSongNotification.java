package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.File;

import webuters.com.geet_uttarakhand20aprilpro.R;

/**
 * Created by sunil on 31-08-2016.
 */
public class SavedSongNotification extends Notification{

    Context context;


    public static NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    public static RemoteViews contentView;
    public static SavedSongNotification ssnot;


    public SavedSongNotification(Context context) {
        this.context=context;
        ssnot=this;
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) context.getSystemService(ns);
        CharSequence tickerText = "Shortcuts";
        long when = System.currentTimeMillis();
        Notification.Builder builder = new Notification.Builder(context);

        Notification notification =builder.getNotification();

//        builder.setSmallIcon(getNotificationIcon());

        notification.when=when;
        notification.tickerText=tickerText;

        notification.icon= R.drawable.final_logo_1;

        contentView=new RemoteViews(context.getPackageName(), R.layout.messageview1);

        setListeners(contentView);
        notification.contentView = contentView;

        try {
            File f = new File(ContactUs.song_image_list.get(SavedSongsMusicPlayer.currentsongindex));
            Log.d("sunil","exist:-"+f.exists());
            Log.d("sunil","path:-"+f.toString());
            if (f.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(f.toString());
                SavedSongsMusicPlayer.image_singer.setImageBitmap(myBitmap);
                contentView.setImageViewBitmap(R.id.btnappp, myBitmap);
            }
        }
        catch (Exception e){
//            SavedSongsMusicPlayer.image_singer.setImageDrawable(context.getResources().getDrawable(R.drawable.finallogo));
        }
//        try {
//
//////            if(MainActivity.img_flag){
////                Bitmap bitmap = ((BitmapDrawable) AlbumeSongs.Album_image_baner.getDrawable()).getBitmap();
////                builder.setSmallIcon(R.drawable.finallogo);
////                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
//////            }
//////            else {
//                Bitmap bitmap = Setting_event.bmp;
//                builder.setSmallIcon(R.drawable.finallogo);
//                contentView.setImageViewBitmap(R.id.btnappp, bitmap);
//

//            SavedSongNotification.contentView.setImageViewBitmap(R.id.btnplay,icon);
////            }
//        }
//        catch (Exception e){
//            Log.d("sun", e.toString());
//            notification.icon= R.drawable.notification_img;
//        }
//
//            Intent intent1=new Intent(context,BluredMediaPlayer2.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 6, intent, 0);
//            remoteView.setOnClickPendingIntent(R.id.btnappp,pendingIntent);


        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;

        Intent intent=new Intent(context,SavedSongsMusicPlayer.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 3, intent, 0);
//            MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp,pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID,notification);
    }


    public void setListeners(RemoteViews view){


        //radio listener
        Intent pausee=new Intent(context,SavedSongReciever.class);
        pausee.putExtra("DO", "app");
        PendingIntent pRadio = PendingIntent.getBroadcast(context, 0, pausee, PendingIntent.FLAG_UPDATE_CURRENT);
        // view.setOnClickPendingIntent(R.id.btnappp, pRadio);
// songname=(TextView)view.

        //volume listener
        Intent nextt=new Intent(context,SavedSongReciever.class);
        nextt.putExtra("DO", "next");
        PendingIntent pVolume = PendingIntent.getBroadcast(context, 1, nextt, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnnext, pVolume);


        //reboot listener
        Intent prevv=new Intent(context,SavedSongReciever.class);
        prevv.putExtra("DO", "prev");
        PendingIntent pReboot = PendingIntent.getBroadcast(context, 5, prevv, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnprev, pReboot);


        //top listener
        Intent appp=new Intent(context,SavedSongReciever.class);
        //appp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        appp.putExtra("DO", "pause");
        PendingIntent pTop = PendingIntent.getBroadcast(context, 4, appp, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnplay, pTop);

        //app listener
        Intent app=new Intent(context, SavedSongReciever.class);
        app.putExtra("DO", "close");
        PendingIntent pApp = PendingIntent.getBroadcast(context, 2, app, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnclose, pApp);

//            Intent toto=new Intent(context, HelperActivityy.class);
//            app.putExtra("DO", "rla");
//            PendingIntent ptoto = PendingIntent.getActivity(context, 6, toto, 0);
//            view.setOnClickPendingIntent(R.id.rLayout, ptoto);

    }
    public static void clearNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }


    public static void changeImageView1(){
        Log.d("sunil","changing view");
        ssnot.changeNotification1();
    }
    public static void changeImageView2(){
        Log.d("sunil","changing view");
        ssnot.changeNotification2();
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.final_logo_1 : R.drawable.final_logo_1;
    }
    public void changeNotification1() {
        Log.d("sunil","changing notification");
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) context.getSystemService(ns);
        CharSequence tickerText = "Shortcuts";
        long when = System.currentTimeMillis();
        Notification.Builder builder = new Notification.Builder(context);

        Notification notification =builder.getNotification();
//        builder.setSmallIcon(getNotificationIcon());

        notification.when=when;
        notification.tickerText=tickerText;

        notification.icon= R.drawable.final_logo_1;

        contentView=new RemoteViews(context.getPackageName(), R.layout.messageview1);

        setListeners(contentView);
        notification.contentView = contentView;

        try {
            File f = new File(ContactUs.song_image_list.get(SavedSongsMusicPlayer.currentsongindex));
            Log.d("sunil","exist:-"+f.exists());
            Log.d("sunil","path:-"+f.toString());
            if (f.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(f.toString());
                SavedSongsMusicPlayer.image_singer.setImageBitmap(myBitmap);
                contentView.setImageViewBitmap(R.id.btnappp, myBitmap);
            }
        }
        catch (Exception e){
//            SavedSongsMusicPlayer.image_singer.setImageDrawable(context.getResources().getDrawable(R.drawable.finallogo));
        }

//        try {
//            Bitmap bitmap = Setting_event.bmp;
//            builder.setSmallIcon(R.drawable.finallogo);
//            contentView.setImageViewBitmap(R.id.btnappp, bitmap);
//
//        }
//        catch (Exception e){
//            Log.d("sun", e.toString());
//            notification.icon= R.drawable.notification_img;
//        }

        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;;

        Intent intent=new Intent(context,SavedSongsMusicPlayer.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 3, intent, 0);
//            MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp,pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID,notification);
    }

    public Bitmap scaleImage (Drawable image, float scaleFactor) {

        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return null;
        }

        Bitmap b = ((BitmapDrawable)image).getBitmap();

        int sizeX = Math.round(image.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(image.getIntrinsicHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

//        image = new BitmapDrawable(getResources(), bitmapResized);

        return bitmapResized;

    }

    public void changeNotification2() {
        Log.d("sunil","changing notification");
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) context.getSystemService(ns);
        CharSequence tickerText = "Shortcuts";
        long when = System.currentTimeMillis();
        Notification.Builder builder = new Notification.Builder(context);

        Notification notification =builder.getNotification();
//        builder.setSmallIcon(getNotificationIcon());

        notification.when=when;
        notification.tickerText=tickerText;

        notification.icon= R.drawable.final_logo_1;

        contentView=new RemoteViews(context.getPackageName(), R.layout.messageview);

        setListeners(contentView);
        notification.contentView = contentView;


        try {
            File f = new File(ContactUs.song_image_list.get(SavedSongsMusicPlayer.currentsongindex));
            Log.d("sunil","exist:-"+f.exists());
            Log.d("sunil","path:-"+f.toString());
            if (f.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(f.toString());
                SavedSongsMusicPlayer.image_singer.setImageBitmap(myBitmap);
                contentView.setImageViewBitmap(R.id.btnappp, myBitmap);
            }
        }
        catch (Exception e){
//            SavedSongsMusicPlayer.image_singer.setImageDrawable(context.getResources().getDrawable(R.drawable.finallogo));
        }

//        try {
//
//            Bitmap bitmap = Setting_event.bmp;
//            builder.setSmallIcon(R.drawable.finallogo);
//            contentView.setImageViewBitmap(R.id.btnappp, bitmap);
//
//        }
//        catch (Exception e){
//            Log.d("sun", e.toString());
//            notification.icon= R.drawable.notification_img;
//        }
        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;;

        Intent intent=new Intent(context,SavedSongsMusicPlayer.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 3, intent, 0);
//            MainActivity.blured_mediaplaying=true;
        contentView.setOnClickPendingIntent(R.id.btnappp,pendingIntent);

        CharSequence contentTitle = "From Shortcuts";

        mNotificationManager.notify(NOTIFICATION_ID,notification);
    }




}
