package webuters.com.geet_uttarakhand20aprilpro.activity;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import webuters.com.geet_uttarakhand20aprilpro.R;


/**
 * Created by khagendra on 20-06-2016.
 */
public class BackgroundService extends Service {

    public static Activity refActivity;

    private NotificationManager mNM;
    Bundle b;
    Intent notificationIntent;
    private final IBinder mBinder = new LocalBinder();
    private String newtext;

    public static final long NOTIFY_INTERVAL = 10*1000;
    // run on another Thread to avoid crash
    public Handler mHandler = new Handler();
    public static Timer mTimer = null;

    public class LocalBinder extends Binder{

        //this part left
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        Log.d("back","oncreate");
        if(mTimer !=null){
            mTimer.cancel();
        }
        else{
            mTimer = new Timer();
        }
        //schedule task
       // mTimer.scheduleAtFixedRate(new TimerDisplayTimerTask(),0,NOTIFY_INTERVAL);



        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        newtext = "BackGroundApp Service Running";

        Notification notification;
       // = new Notification(R.mipmap.ic_launcher,newtext,System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(BackgroundService.this,0,new Intent(BackgroundService.this,BlurMediaPlayer.class),0);
        Notification.Builder builder = new Notification.Builder(BackgroundService.this);
        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("Geet Notification");
        builder.setContentText("Music is playing.");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(contentIntent);
        builder.setOngoing(true);
        builder.setSubText("Click here to open app");  //API level 16
        builder.setNumber(100);
        builder.build();

        notification = builder.getNotification();
        //notification.setLatestEventInfo(BackgroundService.this,"MainActivity",newtext,contentIntent);
        mNM.notify(R.string.local_service_started,notification);
        notificationIntent = new Intent(this,BlurMediaPlayer.class);
        showNotification();
    }

    public static void stopTimer(){
        try{
            if(mTimer!=null){
                Log.d("life","timer stopper");
                mTimer.cancel();
                mTimer = null;
            }

        }catch(Exception e){

        }
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d("life","stopservice");
        return super.stopService(name);
    }

    public static void referrenceActivity(Activity mActivity){
        refActivity = mActivity;
    }

    private void takeScreenshot() {
        Process sh = null;
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        try {
            sh = Runtime.getRuntime().exec("su", null,null);
            OutputStream os = sh.getOutputStream();
            os.write(("/system/bin/screencap -p " + Environment.getExternalStorageDirectory()+"/img.png").getBytes("ASCII"));
            os.flush();
            os.close();
            sh.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bitmap screen = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + File.separator + "img.png");

//my code for saving
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        screen.compress(Bitmap.CompressFormat.JPEG, 15, bytes);

//you can create a new file name "test.jpg" in sdcard folder.

        File f = new File(Environment.getExternalStorageDirectory()+ File.separator +  now+ ".jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//write the bytes in file

        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fo.write(bytes.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
// remember close de FileOutput

        try {
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    class TimerDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("back","running");
                    //takeScreenshot();
                    Toast.makeText(getApplicationContext(), "Screenshot: "+getDateTime(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        private String getDateTime(){
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("back","onstartcommand");
        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        mNM.cancel(R.string.local_service_started);
        stopSelf();
    }

    private void showNotification(){
        Log.d("back","shownoti");
        CharSequence text = getText(R.string.local_service_started);

        Notification notification;
                //= new Notification(R.mipmap.ic_launcher,text,System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,new Intent(this,BlurMediaPlayer.class),0);

        Notification.Builder builder = new Notification.Builder(BackgroundService.this);
        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("Geet Notification");
        builder.setContentText("Music is playing.");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(contentIntent);
        builder.setOngoing(true);
        builder.setSubText("Click here to open app.");   //API level 16
        builder.setNumber(100);
        builder.build();

        notification = builder.getNotification();
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        mNM.notify(R.string.local_service_started,notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
