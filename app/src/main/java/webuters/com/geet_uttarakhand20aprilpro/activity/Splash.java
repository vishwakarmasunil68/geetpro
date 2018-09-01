package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import webuters.com.geet_uttarakhand20aprilpro.R;


//
//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Tushar on 2/4/2016.
 */
public class Splash extends Activity {
    MediaPlayer oursong;
    private ConnectionDetector cd;
    public static boolean isinternet=false;
//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        AppConstant.SetfavMusicPref(this,false);
        AppConstant.SetmainMusicpref(this,false);
        AppConstant.SetSaveMusicpref(this,false);
        AppConstant.setCallStatus(getApplicationContext(),false);
        AppConstant.setSavedCallStatus(getApplicationContext(),false);
        AppConstant.setMainCallStatus(getApplicationContext(),false);
        AppConstant.setIdlePref(this,true);
        AppConstant.setoffhookPref(this,true);
        AppConstant.setRecievePref(this,true);
        AppConstant.setsavedmusicplaying(Splash.this,false);
        AppConstant.setmainmusicplaying(Splash.this,false);
        AppConstant.setfavmusicplaying(Splash.this,false);

//        easyTracker = EasyTracker.getInstance(Splash.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(Splash.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }

        cd = new ConnectionDetector(getApplicationContext());
        StartAnimations();
//        MediaPlayer mPlayer = MediaPlayer.create(Splash.this, R.raw.geetapp);
//        mPlayer.start();
        Handler handle = new Handler();
        handle.postDelayed(new wait(),5000);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        ImageView l = (ImageView) findViewById(R.id.img_logo);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.image_e1);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        anim.reset();
        ImageView l2 = (ImageView) findViewById(R.id.image_e2);
        l2.setVisibility(View.VISIBLE);
        l2.clearAnimation();
        l2.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        anim.reset();
        ImageView l3 = (ImageView) findViewById(R.id.image_t1);
        l3.setVisibility(View.VISIBLE);
        l3.clearAnimation();
        l3.startAnimation(anim);
    }


    class wait implements  Runnable{
        @Override
        public void run() {
            if (!cd.isConnectingToInternet()) {
                isinternet=false;
                Intent intent = new Intent(Splash.this, ContactUs.class);
                startActivity(intent);
           /* Toast.makeText(Splash.this, "No Internet Connection", Toast.LENGTH_SHORT).show();*/
//            finish();
            } else {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

//
//    @Override
//    public void onStart() {
//        super.onStart();
//        EasyTracker.getInstance(this).activityStart(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EasyTracker.getInstance(this).activityStop(this);
//    }


}
