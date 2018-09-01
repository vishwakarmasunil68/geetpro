package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.danikula.videocache.HttpProxyCacheServer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import webuters.com.geet_uttarakhand20aprilpro.GeetApplication;
import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;
import webuters.com.geet_uttarakhand20aprilpro.pojo.AlbumSongResultPOJO;

/**
 * Created by sunil on 26-08-2016.
 */
public class BluredMediaPlayer2 extends AppCompatActivity {

    ImageView suffel, repeat, next_song_button, previous_song_button,share;
    public static ToggleButton play_pause_button;
    public static ImageView image_singer, frame_background;
    public static TextView remaining_songtime_left_text, total_song_time_text;
    FrameLayout blur_frame;
    public static SeekBar song_seekbar1;

    private ConnectionDetector cd;

    public static String songimagevalue;

//    BroadcastReceiver broadcastReceiver;

    public static MediaPlayer mediaPlayer;

    Intent ii = null;

    public static boolean broad= false;

    public static double startTime = 0;
    public static double finalTime = 0;

    public static ArrayList<AlbumSongResultPOJO> albumSongsBeen= (ArrayList<AlbumSongResultPOJO>) Setting_event.albumSongResultPOJOS;

    public static String song_pathvalue ;
    String song_invalue ;
    public static int currentsongindex=0;
    private static final String TAG="sunil";

    public static Handler myHandler = new Handler();

    private boolean isShuffle = false;
    private boolean isRepeat = false;

    Notificationn2 nn;

    public static LinearLayout blur_linear,pb_ll;

    public static BluredMediaPlayer2 bm2;

    public static TelephonyManager mgr;
    boolean back_flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blur_media_player);
        bm2=this;
        SettingToolbar();
        initViews();

        gettingPrefValues();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            back_flag=bundle.getBoolean("backup");
        }

//        Setting_Broadcast();




        play_pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayPauseMethod();
            }
        });

        previous_song_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sunil","current:-"+currentsongindex);
                Log.d("sunil","size:-"+Setting_event.albumSongResultPOJOS.size());
                Log.d(TAG,"prev song");
                previousSong();
            }
        });

        next_song_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sunil","current:-"+currentsongindex);
                Log.d("sunil","size:-"+Setting_event.albumSongResultPOJOS.size());
                Log.d(TAG,"next song");
                nextSong();
            }
        });
        if(!AppConstant.getFavMusicPref(this)) {
            Log.d(TagUtils.getTag(),"starting fav music player");
            startTime=0;
            PlayMusic(song_pathvalue,songimagevalue);
        }

        song_seekbar1.setClickable(false);
        song_seekbar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                seekChange(view);
                return false;
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent =
                        new Intent(Intent.ACTION_SEND);
                String path = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/abc.mp3";
                shareIntent.setType("audio/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + path));
                startActivity(Intent.createChooser(shareIntent, "Share Sound File"));
                shareIntent.setType("text/plain");
            }
        });

        suffel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShuffle) {
                    isShuffle = false;

                    Toast.makeText(BluredMediaPlayer2.this, "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    // btnShuffle.setImageResource(R.drawable.btn_shuffle);
                } else {
                    // make repeat to true
                    isShuffle = true;
                    Collections.shuffle(new AlbumeSongs().AlBUM_LIST_ARRAY);

                    for(int i=0;i<Setting_event.albumSongResultPOJOS.size();i++){
                        if(song_pathvalue.equals(Setting_event.albumSongResultPOJOS.get(i).getSongPath())){
                            currentsongindex=i;
                            Log.d("sunil","current_song_index:-"+currentsongindex);
                        }
                    }

                    Toast.makeText(BluredMediaPlayer2.this, "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                }
            }
        });
        mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("sunil","stop");
//        unregisterReceiver(broadcastReceiver);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        registerReceiver(broadcastReceiver,new IntentFilter("broadCastName"));
//    }

    public static void unregister_phonestate(){
        Log.d("sunil","unregister_phonestate");
        bm2.unregister();
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
                    Log.d("sunil","bm2 ringing");
                    try {
                        AppConstant.setSavedCallStatus(getApplicationContext(), true);

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

                        if (AppConstant.getSavedCallStatus(getApplicationContext())) {
                            Log.d("sunil","bm2 idle");
                            Log.d("sunil","idle");
                            AppConstant.setRecievePref(getApplicationContext(),true);
                            AppConstant.setIdlePref(getApplicationContext(),false);
                            AppConstant.setoffhookPref(getApplicationContext(),true);

                            AppConstant.setSavedCallStatus(getApplicationContext(), false);
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
                    Log.d("sunil","bm2 offhook");
                    try {

                        AppConstant.setRecievePref(getApplicationContext(),true);
                        AppConstant.setIdlePref(getApplicationContext(),true);
                        AppConstant.setoffhookPref(getApplicationContext(),false);

                        AppConstant.setSavedCallStatus(getApplicationContext(), true);
                        PlayPauseMethod();

                    } catch (Exception e) {
                        Log.d("sunil", e.toString());
                    }
                }
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    public void PlayPauseMethod(){
        if((mediaPlayer!=null)){
            if(mediaPlayer.isPlaying()){
                play_pause_button.setChecked(true);
                mediaPlayer.pause();
                AppConstant.setIsPlaying(getApplicationContext(),false);
                Notificationn2.changeImageView2();
            }
            else{
                play_pause_button.setChecked(false);
                mediaPlayer.start();
                AppConstant.setIsPlaying(getApplicationContext(),true);
                Notificationn2.changeImageView1();
            }
        }else {
            broad=false;
        }
    }


    private void seekChange(View v){
        // if(mediaPlayer.isPlaying()){
        SeekBar sb = (SeekBar)v;
        mediaPlayer.seekTo(sb.getProgress());
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

    public void nextSong(){
        Log.d(TagUtils.getTag(),"saved song mp");
        if((currentsongindex+1)!=Setting_event.albumSongResultPOJOS.size()){
            song_pathvalue=Setting_event.albumSongResultPOJOS.get(currentsongindex+1).getSongPath();
            songimagevalue=Setting_event.albumSongResultPOJOS.get(currentsongindex+1).getSongImg();
            PlayMusic(song_pathvalue,songimagevalue);
            BluredMediaPlayer2.startTime=0;
            myHandler.postDelayed(UpdateSongTime, 1000);
        }
    }

    public void previousSong(){
        if(currentsongindex!=0){
            BluredMediaPlayer2.startTime=0;
            song_pathvalue=Setting_event.albumSongResultPOJOS.get(currentsongindex-1).getSongPath();
            songimagevalue=Setting_event.albumSongResultPOJOS.get(currentsongindex-1).getSongImg();
            PlayMusic(song_pathvalue,songimagevalue);
            myHandler.postDelayed(UpdateSongTime, 1000);
        }
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
//                        nextSong();
//                    } else if (action.equals("prev")) {
//                        previousSong();
//                    } else if (action.equals("apppp")) {
//                        //Your code
//                        Log.d(TAG,"appppp");
//                    } else if (action.equals("close")) {
//                        //Your code
//                        try{
//                            NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                            nMgr.cancelAll();
//                        }
//                        catch (Exception e){
//                            Log.d(TAG,e.toString());
//                        }
//                        try{
//                            Notificationn.clearNotification();
//                        }
//                        catch (Exception e){
//                            Log.d(TAG,e.toString());
//                        }
//                        try{
//                            mediaPlayer.stop();
//                            finish();
////                            PlayPauseMethod();
//                        }
//                        catch (Exception e){
//                            Log.d(TAG,"catched notification");
//                        }
//                    }
//                }
//                catch (Exception e){
//
//                }
//
//            }
//        };
//    }


    public void gettingPrefValues(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        songimagevalue = prefs.getString("SongImage123", null);
        song_pathvalue = prefs.getString("Sengaer_song_path", null);
        song_invalue = prefs.getString("Index", null);


        Picasso.with(BluredMediaPlayer2.this).load(String.valueOf(songimagevalue)).into(image_singer);
        Picasso.with(BluredMediaPlayer2.this).load(String.valueOf(songimagevalue)).into(frame_background);
    }

    public void SettingToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        setTitle("BlurMediaPlayer");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
    public void PlayMusic(final String idUrl, final String song_image_value) {
        blur_linear.setVisibility(View.VISIBLE);
//        blur_linear.setBackgroundColor(Color.parseColor("#BDBDBD"));
        pb_ll.setVisibility(View.VISIBLE);
        new CountDownTimer(100, 100) {

            public void onTick(long millisUntilFinished) {

                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                playMusic1(idUrl,song_image_value);
            }

        }.start();
    }

    public void showNotification(){
        //Notificationn.ctx = BlurMediaPlayer.this;
        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion>= Build.VERSION_CODES.JELLY_BEAN) {
            if(AppConstant.getFavMusicPref(this)){

                Log.d("sunil","bm2 if");
                AppConstant.SetSaveMusicpref(this,false);
                AppConstant.SetmainMusicpref(this,false);
                AppConstant.SetfavMusicPref(this,true);
            }
            else {
                Log.d("sunil","bm2 else");
                AppConstant.SetSaveMusicpref(this, false);
                AppConstant.SetmainMusicpref(this, false);
                AppConstant.SetfavMusicPref(this, true);

                AppConstant.setMainCallStatus(getApplicationContext(), false);
                AppConstant.setCallStatus(getApplicationContext(), false);
                AppConstant.setSavedCallStatus(getApplicationContext(), false);


                AppConstant.setIdlePref(this, true);
                AppConstant.setoffhookPref(this, true);
                AppConstant.setRecievePref(this, true);

                try{
                    NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nMgr.cancelAll();
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                try{
                    Notificationn.clearNotification();
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                try{
                    SavedSongNotification.clearNotification();
                }
                catch (Exception e){

                }
                try{
                    SavedSongsMusicPlayer.mediaPlayer.stop();
                }
                catch (Exception e){

                }
                try{
                    SavedSongsMusicPlayer.ssmp.finish();
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
            nn = new Notificationn2(this);
        }
        else{
            new AlertDialog.Builder(BluredMediaPlayer2.this)
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
        if(back_flag){

        }
        else{
            Intent intent=new Intent(BluredMediaPlayer2.this,MainActivity.class);
            intent.putExtra("backup1",true);
            startActivity(intent);
            finish();
        }
    }

    public void playMusic1(String idUrl,String songimagevalue){

        try{
            if(mediaPlayer!=null)
                mediaPlayer.release();
        }
        catch (Exception e){

        }

        mediaPlayer = new MediaPlayer();
        showNotification();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            //(Environment.getExternalStorageDirectory().getPath()+"/term.mp3")
            Picasso.with(BluredMediaPlayer2.this).load(String.valueOf(songimagevalue)).into(image_singer);
            Picasso.with(BluredMediaPlayer2.this).load(String.valueOf(songimagevalue)).into(frame_background);


            Log.d(TagUtils.getTag(),"original url:-"+idUrl);
            HttpProxyCacheServer proxy = GeetApplication.getProxy(this);
            String proxyUrl = proxy.getProxyUrl(idUrl);
            Log.d(TagUtils.getTag(),"proxy url:-"+proxyUrl);
            mediaPlayer.setDataSource(proxyUrl);

        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }



        try {
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextSong();
                    Log.d("sunil","song completed");
                }
            });

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("khogenprep","prepared music");
//                    Toast.makeText(getApplicationContext(),"prepared",Toast.LENGTH_SHORT).show();
                    try{
                        SavedSongsMusicPlayer.ssmp.unregister();
                    }
                    catch (Exception e){

                    }
                    try{
                        BlurMediaPlayer1.bmp1.unregister();
                    }
                    catch (Exception e){

                    }


                    mediaPlayer.start();
                    blur_linear.setVisibility(View.VISIBLE);
                    AppConstant.setIsPlaying(getApplicationContext(),true);
                    pb_ll.setVisibility(View.GONE);
                    finalTime = mediaPlayer.getDuration();
                    play_pause_button.setChecked(false);
                    total_song_time_text.setText(String.format("%d : %d ",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                    );
                    for(int i = 0; i< Setting_event.albumSongResultPOJOS.size(); i++){
                        if(song_pathvalue.equals(Setting_event.albumSongResultPOJOS.get(i).getSongPath())){
                            currentsongindex=i;
                            Log.d("sunil","current_song_index:-"+currentsongindex);
                        }
                    }



                    myHandler.postDelayed(UpdateSongTime, 1000);

                    AppConstant.setsavedmusicplaying(BluredMediaPlayer2.this,false);
                    AppConstant.setmainmusicplaying(BluredMediaPlayer2.this,false);
                    AppConstant.setfavmusicplaying(BluredMediaPlayer2.this,true);
                }
            });
        }  catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }catch(IOException t){

        }
    }

}

