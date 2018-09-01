package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.util.concurrent.TimeUnit;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;

/**
 * Created by sunil on 31-08-2016.
 */
public class SavedSongsMusicPlayer extends AppCompatActivity {
    ImageView suffel, repeat, next_song_button, previous_song_button,share;
    public static ToggleButton play_pause_button;
    public static ImageView image_singer, frame_background;
    public static TextView remaining_songtime_left_text, total_song_time_text;
    FrameLayout blur_frame;
    public static SeekBar song_seekbar1;
    public static LinearLayout blur_linear,pb_ll;

    //    BroadcastReceiver broadcastReceiver;
    public static boolean broad= false;


    public static MediaPlayer mediaPlayer;

    String song_path="";

    public static double startTime = 0;
    public static double finalTime = 0;

    public static Handler myHandler = new Handler();

    public static int currentsongindex=0;



    private static final String tag="sunil";

    SavedSongNotification ssn;
    public static SavedSongsMusicPlayer ssmp;
    TelephonyManager mgr;
    boolean bundle_flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blur_media_player);
        ssmp=this;

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){

            bundle_flag=bundle.getBoolean("back");
            Log.d("bundle",bundle_flag+"");
        }

        initViews();
        SettingToolbar();
        gettingStaticValues();
//        Setting_Broadcast();
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            try{
                if(extra.getBoolean("song")){
                    currentsongindex=ContactUs.filepathlst.size()-1;
                }
            }
            catch (Exception e){

            }
        }

        play_pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayPauseMethod();
            }
        });



        if(!AppConstant.getsavedmusicplaying(this)) {
            Log.d(TagUtils.getTag(),"current song index:-"+currentsongindex);
            playMusic(ContactUs.filepathlst.get(currentsongindex));
        }


        song_seekbar1.setClickable(false);
        song_seekbar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                seekChange(view);
                return false;
            }
        });

        next_song_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSong();
            }
        });
        previous_song_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousSong();
            }
        });



        mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    public static void unregister_phonestate(){
        Log.d("sunil","unregister_phonestate");
        ssmp.unregister();
    }
    public void unregister(){
        Log.d("sunil","unregister");
        mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                //Incoming call: Pause music

                if(AppConstant.getRecievingPref(getApplicationContext())&&AppConstant.getIsPlaying(getApplicationContext())) {
                    Log.d("sunil","ringing");
                    Log.d("sunil","ssmp ringing");
                    try {
                        AppConstant.setCallStatus(getApplicationContext(), true);

                        AppConstant.setRecievePref(getApplicationContext(),false);
                        AppConstant.setIdlePref(getApplicationContext(),true);
                        AppConstant.setoffhookPref(getApplicationContext(),true);
                        PlayPauseMethod();
                    } catch (Exception e) {
                        Log.d("sunil", e.toString());
                    }
                }
            } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                //Not in call: Play musics

                try {
                    if(AppConstant.getIdlePref(getApplicationContext())) {

                        if (AppConstant.getCallStatus(getApplicationContext())) {
                            Log.d("sunil","ssmp idle");
                            Log.d("sunil","idle");
                            AppConstant.setRecievePref(getApplicationContext(),true);
                            AppConstant.setIdlePref(getApplicationContext(),false);
                            AppConstant.setoffhookPref(getApplicationContext(),true);

                            AppConstant.setCallStatus(getApplicationContext(), false);
                            PlayPauseMethod();
                        }
                    }
                }
                catch (Exception e){

                }
            } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                //A call is dialing, active or on hold
                if(AppConstant.getoffhookPref(getApplicationContext())&&AppConstant.getIsPlaying(getApplicationContext())) {
                    Log.d("sunil", "offhook");
                    try {
                        Log.d("sunil","ssmp hook");
                        AppConstant.setRecievePref(getApplicationContext(),true);
                        AppConstant.setIdlePref(getApplicationContext(),true);
                        AppConstant.setoffhookPref(getApplicationContext(),false);

                        AppConstant.setCallStatus(getApplicationContext(), true);
                        PlayPauseMethod();

                    } catch (Exception e) {
                        Log.d("sunil", e.toString());
                    }
                }
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };
    public void previousSong(){
        if(currentsongindex!=0){
            currentsongindex=currentsongindex-1;
            String song_pathvalue=ContactUs.filepathlst.get(currentsongindex);
            playMusic(song_pathvalue);
            myHandler.postDelayed(UpdateSongTime, 1000);
        }
    }

    public void nextSong(){
        if((currentsongindex+1)!=ContactUs.filepathlst.size()){
            currentsongindex=currentsongindex+1;
            String song_pathvalue=ContactUs.filepathlst.get(currentsongindex);
            playMusic(song_pathvalue);
            myHandler.postDelayed(UpdateSongTime, 1000);
        }
    }

    private void seekChange(View v){
        // if(mediaPlayer.isPlaying()){
        SeekBar sb = (SeekBar)v;
        mediaPlayer.seekTo(sb.getProgress());
    }
    public void gettingStaticValues(){
        currentsongindex=ContactUs.pos;
    }

    public void PlayPauseMethod(){
        if((mediaPlayer!=null)){
            if(mediaPlayer.isPlaying()){
                play_pause_button.setChecked(true);
                mediaPlayer.pause();
                AppConstant.setIsPlaying(getApplicationContext(),false);
                SavedSongNotification.changeImageView2();
            }
            else{
                play_pause_button.setChecked(false);
                mediaPlayer.start();
                AppConstant.setIsPlaying(getApplicationContext(),true);
                SavedSongNotification.changeImageView1();
            }
        }else {
            broad=false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("sunil","stop");
//        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            File f = new File(ContactUs.song_image_list.get(currentsongindex));
            Log.d("sunil","exist:-"+f.exists());
            Log.d("sunil","path:-"+f.toString());
            if (f.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(f.toString());
                image_singer.setImageBitmap(myBitmap);
            }
        }
        catch (Exception e){
//                    image_singer.setImageBitmap(myBitmap);
        }
//        registerReceiver(broadcastReceiver,new IntentFilter("savebroadcast"));
    }

    public void initViews(){
        previous_song_button = (ImageView) findViewById(R.id.previous_song_button);
        play_pause_button = (ToggleButton) findViewById(R.id.play_pause_button);
        image_singer = (ImageView) findViewById(R.id.image_singer);
        repeat = (ImageView) findViewById(R.id.repeat);
        suffel = (ImageView) findViewById(R.id.suffel);
        frame_background = (ImageView) findViewById(R.id.frame_background);
        remaining_songtime_left_text = (TextView) findViewById(R.id.remaining_songtime_left_text);
        total_song_time_text = (TextView) findViewById(R.id.total_song_time_text);
        blur_frame = (FrameLayout) findViewById(R.id.blur_frame);
        next_song_button = (ImageView) findViewById(R.id.next_song_button);
        song_seekbar1 = (SeekBar) findViewById(R.id.song_seekbar);
        share = (ImageView) findViewById(R.id.share);
        blur_linear= (LinearLayout) findViewById(R.id.blur_linear);
        pb_ll= (LinearLayout) findViewById(R.id.pb_ll);
    }

    public void SettingToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        setTitle("BlurMediaPlayer");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

//    public void Setting_Broadcast(){
//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                try {
//                    String action = intent.getStringExtra("message");
//                    Toast.makeText(getApplication(),""+action,Toast.LENGTH_SHORT).show();
//                    broad = true;
//                    if (action.equals("pause")) {
////                        PlayPauseMethod();
//                    } else if (action.equals("next")) {
//
//                    } else if (action.equals("prev")) {
//
//                    } else if (action.equals("apppp")) {
//                        //Your code
//                        Log.d("sunil","appppp");
//                    } else if (action.equals("close")) {
//                        //Your code
//
//                    }
//                }
//                catch (Exception e){
//
//                }
//
//            }
//        };
//    }


    public void playMusic(String song_path){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        play_pause_button.setChecked(false);
        mediaPlayer=MediaPlayer.create(this, Uri.parse(song_path));


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                AppConstant.setIsPlaying(getApplicationContext(),true);
                finalTime = mediaPlayer.getDuration();
//                gettingSongImagePath();

                AppConstant.setsavedmusicplaying(SavedSongsMusicPlayer.this,true);
                AppConstant.setmainmusicplaying(SavedSongsMusicPlayer.this,false);
                AppConstant.setfavmusicplaying(SavedSongsMusicPlayer.this,false);

                Log.d("sunil","ss current index:-"+currentsongindex);

                total_song_time_text.setText(String.format("%d : %d ",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                );
                try {
                    File f = new File(ContactUs.song_image_list.get(currentsongindex));
                    Log.d("sunil","exist:-"+f.exists());
                    Log.d("sunil","path:-"+f.toString());
                    if (f.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(f.toString());
                        image_singer.setImageBitmap(myBitmap);
                    }
                }

                catch (Exception e){
//                    image_singer.setImageBitmap(myBitmap);
                }

                try{
                    BluredMediaPlayer2.bm2.unregister();
                }
                catch (Exception e){

                }
                try{
                    BlurMediaPlayer1.bmp1.unregister();
                }
                catch (Exception e){

                }
                showNotification();

                myHandler.postDelayed(UpdateSongTime, 1000);
            }
        });

    }

    public void gettingSongImagePath(){
        ContentResolver musicResolve = getContentResolver();
        Uri smusicUri = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor music =musicResolve.query(smusicUri,null         //can also use where clause
                ,null, null, null);



        music.moveToFirst();            //i put only one song in my external storage to keep things simple
        int x=music.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM_ART);
        String thisArt = music.getString(x);


        Bitmap bm= BitmapFactory.decodeFile(thisArt);
        ImageView image=(ImageView)findViewById(R.id.image_singer);
        image.setImageBitmap(bm);
    }

    public void showNotification(){
        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion>= Build.VERSION_CODES.JELLY_BEAN) {

            if(AppConstant.getSaveMusicPref(this)){
                Log.d("sunil","ssmpif");
                AppConstant.SetSaveMusicpref(this,true);
                AppConstant.SetmainMusicpref(this,false);
                AppConstant.SetfavMusicPref(this,false);
            }
            else{
                Log.d("sunil","ssmpelse");
                AppConstant.SetSaveMusicpref(this,true);
                AppConstant.SetmainMusicpref(this,false);
                AppConstant.SetfavMusicPref(this,false);

                AppConstant.setMainCallStatus(getApplicationContext(), false);
                AppConstant.setCallStatus(getApplicationContext(), false);
                AppConstant.setSavedCallStatus(getApplicationContext(), false);

                AppConstant.setIdlePref(this,true);
                AppConstant.setoffhookPref(this,true);
                AppConstant.setRecievePref(this,true);
                try{
                    NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nMgr.cancelAll();
                }
                catch (Exception e){
                    Log.d(tag,e.toString());
                }
                try{
                    Notificationn.clearNotification();
                }
                catch (Exception e){
                    Log.d(tag,e.toString());
                }
                try{
                    Notificationn2.clearNotification();
                }
                catch (Exception e){

                }
                try{
                    BluredMediaPlayer2.mediaPlayer.stop();
                }
                catch (Exception e){

                }
                try{
                    BluredMediaPlayer2.bm2.finish();
                }
                catch (Exception e){

                }
                try{
                    BlurMediaPlayer1.mediaPlayer.stop();
                }
                catch (Exception e){

                }
                try{
                    BlurMediaPlayer1.bmp1.finish();
                }
                catch (Exception e){

                }
            }

            ssn = new SavedSongNotification(this);
        }
        else{
            new AlertDialog.Builder(SavedSongsMusicPlayer.this)
                    .setTitle("Update your phone")
                    .setMessage("Your phone is outdated. You may not be able to use some features. Please update your phone.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            dialog.cancel();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(bundle_flag){

        }
        else{
            Intent intent=new Intent(SavedSongsMusicPlayer.this,MainActivity.class);
            intent.putExtra("backup",true);
            startActivity(intent);
            finish();
        }
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result", "ok");
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
    }

    public static Runnable UpdateSongTime = new Runnable()

    {
        public  void run() {


            startTime = mediaPlayer.getCurrentPosition();
            song_seekbar1.setMax(mediaPlayer.getDuration());
            song_seekbar1.setProgress(mediaPlayer.getCurrentPosition());
            // seekHandler.postDelayed(run, 1000);
            myHandler.postDelayed(this, 1000);
            remaining_songtime_left_text.setText(String.format("%d: %d ",

                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );

        }
    };
}
