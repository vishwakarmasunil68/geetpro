package webuters.com.geet_uttarakhand20aprilpro.activity;


import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import webuters.com.geet_uttarakhand20aprilpro.adapter.Globals;
import webuters.com.geet_uttarakhand20aprilpro.adapter.Song;
import webuters.com.geet_uttarakhand20aprilpro.bean.SingerDEtaileBean;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Tushar Dhiman on 3/5/2016.
 */
public class BlurMediaPlayer extends AppCompatActivity implements MediaPlayer.OnCompletionListener, ServiceConnection {

    public static ToggleButton play_pause_button;
    private ImageView iv;
    private boolean mBound = false;
    private MyNotification.MusicBinder mBinder;
    boolean broad = false;
    //  private MusicReceiver mReceiver;
    // public SeekBar song_seekbar = null;
    public static SeekBar song_seekbar1;

    public static MediaPlayer mediaPlayer;
    public static double startTime = 0;
    public static double finalTime = 0;
    public static Handler myHandler = new Handler();
    Context ctx;
    public String AlbumName;

    public static TextView remaining_songtime_left_text, total_song_time_text;
    public static int oneTimeOnly = 0;
    public static String idUrl;
    TextView text_song_title;
    ImageView suffel, repeat, next_song_button, previous_song_button;
    String SongName;
    RelativeLayout relativ;
    int SINGER_ID_RECIVED;
    String SINGER_SONG_URL;
    String Singer_Image_upload;
    String Recived_Array;
    public static ImageView image_singer, frame_background;
    ArrayList<SingerDEtaileBean> singer_detailes_array;
    FrameLayout blur_frame;
    private static final float BLUR_RADIUS = 25f;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    //    NotificationManager mNotificationManager;
//    Notification mNotification = null;
//    final int NOTIFICATION_ID = 1;
    private Handler seekHandler;
    private Runnable run;
    ImageView share;
    String SongImage;
    public static int currentSongIndex = 0;
    int p, p1;
    ArrayList<String> myList = new ArrayList<>();
    private ConnectionDetector cd;
    ProgressBar progress;
    Intent ii = null;
    Notificationn nn;
    ProgressDialog blDialog;
    private ProgressBar spinner;

    //    private EasyTracker easyTracker = null;
    public static String songimagevalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.blur_media_player);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
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


        spinner.setVisibility(View.VISIBLE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        songimagevalue = prefs.getString("SongImage123", null);
        String song_pathvalue = prefs.getString("Sengaer_song_path", null);
        String song_invalue = prefs.getString("Index", null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        setTitle("BlurMediaPlayer");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //showNotification();


        Log.d("lifecycle", "oncreate");
        ii = new Intent(BlurMediaPlayer.this, BackgroundService.class);

        cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(BlurMediaPlayer.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("array list is ---" + singer_detailes_array);

            previous_song_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });


            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    try {
                        String action = intent.getStringExtra("message");
                        Toast.makeText(getApplication(), "" + action, Toast.LENGTH_SHORT).show();
                        broad = true;
                        if (action.equals("pause")) {
                            //Your code
                            NotificationManager mgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                            Log.d("hell", "pause button notification");
                            if (BlurMediaPlayer.mediaPlayer != null) {
                                if (BlurMediaPlayer.mediaPlayer.isPlaying()) {
                                    BlurMediaPlayer.mediaPlayer.pause();
                                    BlurMediaPlayer.play_pause_button.setChecked(true);

                                } else {
                                    BlurMediaPlayer.mediaPlayer.start();
                                    BlurMediaPlayer.play_pause_button.setChecked(false);
                                }
                            }

                        } else if (action.equals("next")) {
                            //Your code
                            next_song();

                        } else if (action.equals("prev")) {
                            //Your code

                            ///
                            if (AlbumeSongs.AlBUM_LIST_ARRAY.size() != 0) {

                                Log.e("next button clicked", "next clicked");
                                System.out.println("Singer size is " + AlbumeSongs.AlBUM_LIST_ARRAY.size() + "index is " + currentSongIndex);
                                Log.e("Size is -", String.valueOf(AlbumeSongs.AlBUM_LIST_ARRAY.size()));
                                if (currentSongIndex > 0) {
                                    Picasso.with(BlurMediaPlayer.this).load(String.valueOf(SongImage)).into(image_singer);
                                    playSong(currentSongIndex - 1, "Garwali");

                                    currentSongIndex = currentSongIndex - 1;
                                }
                            }
                        } else if (action.equals("apppp")) {
                            //Your code
                            Intent to = new Intent(getBaseContext(), BlurMediaPlayer.class);
                            to.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getBaseContext().startActivity(to);
                        } else if (action.equals("close")) {
                            //Your code
                            try {

                                nn.clearNotification();
                                mediaPlayer.pause();
                                BlurMediaPlayer.play_pause_button.setChecked(true);
                                stopService(ii);  //Is this causing crash?
                                // finish();
                            } catch (Exception e) {
                                Log.d("TAg", "catched notification");
                            }
                        }
                    } catch (Exception e) {

                    }

                }
            };

            registerReceiver(broadcastReceiver, new IntentFilter("broadCastName"));


            if ((mediaPlayer != null && broad == false)) {
                if (mediaPlayer.isPlaying()) {
                    play_pause_button.setChecked(false);
                } else {
                    play_pause_button.setChecked(true);
                }
            } else {
                broad = false;
            }


            song_seekbar1.setClickable(false);
            song_seekbar1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    seekChange(view);
                    return false;
                }
            });
            play_pause_button.setEnabled(true);

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

            // int progress = 0;
            PhoneStateListener phoneStateListener = new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    if (state == TelephonyManager.CALL_STATE_RINGING) {
                        mediaPlayer.pause();
                    } else if (state == TelephonyManager.CALL_STATE_IDLE) {
//                        try {
//                          mediaPlayer.prepare();  //try catch
//                           mediaPlayer.start();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

                    }
                    super.onCallStateChanged(state, incomingNumber);
                }
            };
            TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (mgr != null) {
                mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            }

            SharedPreferences khogen = PreferenceManager.getDefaultSharedPreferences(this);
            SongImage = khogen.getString("SongImage123", null);
            idUrl = khogen.getString("Sengaer_song_path", null);
            Picasso.with(BlurMediaPlayer.this).load(String.valueOf(SongImage)).into(image_singer);
            Picasso.with(BlurMediaPlayer.this).load(String.valueOf(SongImage)).into(frame_background);

            spinner.setVisibility(View.GONE);

//            Intent intent = getIntent();
//            if (null != intent) {
//
//              //  Recived_Array = intent.getExtras().getString("singer_detailes_put");
//                System.out.println("Recived_Array is ----->. " + Recived_Array);
//                //String index = (String) intent.getExtras().get(String.valueOf(currentSongIndex));
//               // Log.e("RECIVE INDEX ;;;;", String.valueOf(index));
//                SongImage = intent.getExtras().getString("SongImage123");
//             //   SINGER_SONG_URL = intent.getExtras().getString("Song_ID");
//                System.out.println("SINGER ID RECIVED HERE ------- :: ::: ------ >" + SINGER_SONG_URL);
//                SINGER_ID_RECIVED = intent.getExtras().getInt("Singer_id_put");
//                System.out.println("SINGER ID RECIVED AS  :::::------------------------->> ----->>>--::" + SINGER_ID_RECIVED);
//               // Singer_Image_upload = intent.getExtras().getString("Singer_Image");
//                System.out.println("Recived  Singer Image  here :::###############----@@@@@@@@@@@@@@ ---- " + Singer_Image_upload);
//                idUrl = intent.getStringExtra("Sengaer_song_path");
//                System.out.println("recived idurl is -------" + idUrl);
//               // SongName = intent.getStringExtra("Song_name_put");
//                System.out.println("SongName recive here is ------ >>" + SongName);
////            text_song_title.setText(SongName);
//                System.out.println("set image into Image View is ---------::: ---  >>" + Singer_Image_upload);
//                Log.e("Song Image Recived Here ------ >", SongImage);
//                Picasso.with(BlurMediaPlayer.this).load(String.valueOf(SongImage)).into(image_singer);
//                Picasso.with(BlurMediaPlayer.this).load(String.valueOf(SongImage)).into(frame_background);
//            }
            System.out.println("value 1" + idUrl);
            suffel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isShuffle) {
                        isShuffle = false;

                        Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                        // btnShuffle.setImageResource(R.drawable.btn_shuffle);
                    } else {
                        // make repeat to true
                        isShuffle = true;
                        Collections.shuffle(new AlbumeSongs().AlBUM_LIST_ARRAY);
                        Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                        // make shuffle to false
                        isRepeat = false;
                    }
                }
            });
            repeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isRepeat) {
                        isRepeat = false;
                        mediaPlayer.setLooping(false);
                        Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                        //  repeat.setImageResource(R.drawable.btn_repeat);
                    } else {
                        // make repeat to true
                        isRepeat = true;
                        mediaPlayer.setLooping(true);
                        Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                        // make shuffle to false
                        isShuffle = false;
                    }
                }
            });
            next_song_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next_song();

                }
            });
            System.out.println("Full Activity On Create callstart here" + idUrl);
            System.out.println("Recived Latest Song URL is  in Full Activity++++++++++++++++++++++" + idUrl);

            Log.e("Media player blank ", String.valueOf(mediaPlayer));

            if (mediaPlayer != null && broad == false) {
                if (mediaPlayer.isPlaying()) {
                    Log.e(" if media player value", String.valueOf(mediaPlayer));
                    System.out.println("IF Check here ---" + "if check");
                    BlurMediaPlayer.mediaPlayer.reset();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    playMusic();
//                    mediaPlayer = MediaPlayer.create(this, Uri.parse(idUrl));
//                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(this);
                } else if (!mediaPlayer.isPlaying()) {
                    Log.e(" if media player value", String.valueOf(mediaPlayer));
                    System.out.println("IF Check here ---" + "if check");
                    BlurMediaPlayer.mediaPlayer.reset();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    playMusic();
//                    mediaPlayer = MediaPlayer.create(this, Uri.parse(idUrl));
//                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(this);
                    play_pause_button.setChecked(false);
                }
            } else {
                if (mediaPlayer == null && broad == false) {
                    Log.e("Else media player value", String.valueOf(mediaPlayer));
                    System.out.println("ELSE Check here ---" + "ELSE check");
                    playMusic();
//                    mediaPlayer = MediaPlayer.create(this, Uri.parse(idUrl));
//                    mediaPlayer.start();//here
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            mediaPlayer.stop();
//                            mediaPlayer.release();
//
//                        }
//                    });
                } else {
                    broad = false;
                }
            }
            previous_song_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("Current Index is -----", String.valueOf(currentSongIndex));
                    Log.e("Previous clicked -----", "clicked");


                    ///
                    if (AlbumeSongs.AlBUM_LIST_ARRAY.size() != 0) {

                        Log.e("next button clicked", "next clicked");
                        System.out.println("Singer size is " + AlbumeSongs.AlBUM_LIST_ARRAY.size() + "index is " + currentSongIndex);
                        Log.e("Size is -", String.valueOf(AlbumeSongs.AlBUM_LIST_ARRAY.size()));
                        if (currentSongIndex < (AlbumeSongs.AlBUM_LIST_ARRAY.size() - 1)) {
//                        String SongImage = AlbumeSongs.AlBUM_LIST_ARRAY.get(currentSongIndex + 1).getAlbumCover();
//                       System.out.println("Image on previous button is :"+ AlbumeSongs.AlBUM_LIST_ARRAY.get(currentSongIndex - 1).getAlbumCover());
                            Picasso.with(BlurMediaPlayer.this).load(String.valueOf(SongImage)).into(image_singer);
                            playSong(currentSongIndex - 1, "Garwali");
                            currentSongIndex = currentSongIndex - 1;
                        }
                    }

                }
            });

            System.out.println("Full Activity On Create callstart here" + idUrl);
            System.out.println("Recived Latest Song URL is  in Full Activity++++++++++++++++++++++" + idUrl);

            Log.e("Media player blank ", String.valueOf(mediaPlayer));

            play_pause_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mediaPlayer.isPlaying())

                    {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
//
                    }
                }
            });

            Log.e("Full Activity On Create", "Full Activity");

            if (mediaPlayer != null && broad == false) {
                if (mediaPlayer.isPlaying() || !(mediaPlayer.isPlaying())) {

                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

//                }
                    total_song_time_text.setText(String.format("%d : %d ",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                    );

                    remaining_songtime_left_text.setText(String.format("%d : %d ",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                    );
                    // song_seekbar1.setProgress((int)startTime);
                    myHandler.postDelayed(UpdateSongTime, 1000);
                    total_song_time_text.setEnabled(true);
                }

            }
        }
    }


    int i = 0;

    public void playSong(int currentSongIndex, String listverfy) {
        //mediaPlayer =  new MediaPlayer();
        try {
            mediaPlayer.reset();
            try {

                if (listverfy.equalsIgnoreCase("Singer")) {
                    //  System.out.println("Singer url is" + idUrl + "path value is " + AlbumeSongs.AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath() + "current index is " + currentSongIndex + "size is " + Singers_Details_class.singer_detailes_array.size());
                    //mediaPlayer = MediaPlayer.create(BlurMediaPlayer.this, Uri.parse(new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath()));
                    idUrl = new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath();
                    playMusic();
                }
                if (listverfy.equalsIgnoreCase("Garwali")) {
                    //  System.out.println(" Garwali url is" + idUrl + "path value is " + AlbumeSongs.AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath() + "current index is " + currentSongIndex + "size is " + Singers_Details_class.singer_detailes_array.size());
                    Uri uri = Uri.parse(new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath());
                    //   mediaPlayer = MediaPlayer.create(BlurMediaPlayer.this, Uri.parse(new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath()));

                    if (uri != null) {
                        try {
                            idUrl = new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath();
                            playMusic();
                            //mediaPlayer = MediaPlayer.create(BlurMediaPlayer.this, Uri.parse(new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath()));
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (listverfy.equalsIgnoreCase("Kumauni")) {
                    // System.out.println("Kumauni url is" + idUrl + "path value is " + AlbumeSongs.AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath() + "current index is " + currentSongIndex + "size is " + Singers_Details_class.singer_detailes_array.size());
                    // mediaPlayer = MediaPlayer.create(BlurMediaPlayer.this, Uri.parse(new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath()));
                    idUrl = new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath();
                    playMusic();

                }

                if (listverfy.equalsIgnoreCase("Junsari")) {
                    System.out.println("Junsari url is" + idUrl + "path value is " + AlbumeSongs.AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath() + "current index is " + currentSongIndex + "size is " + Singers_Details_class.singer_detailes_array.size());
                    // mediaPlayer = MediaPlayer.create(BlurMediaPlayer.this, Uri.parse(new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath()));
                    idUrl = new AlbumeSongs().AlBUM_LIST_ARRAY.get(currentSongIndex).getSongPath();
                    playMusic();

                }
                // mediaPlayer.start();


            } catch (IllegalArgumentException e)

            {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private Runnable UpdateSongTime = new Runnable()

    {
        public void run() {


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


    //  call permission..here...is...

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
//INCOMING call
//do all necessary action to pause the audio
                if (mediaPlayer != null) {//check mp
                    //setPlayerButton(true, false, true);

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }

            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
//Not IN CALL
//do anything if the phone-state is idle
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
//A call is dialing, active or on hold
//do all necessary action to pause the audio
//do something here
                if (mediaPlayer != null) {//check mp
                    //  setPlayerButton(true, false, true);

                    if (mediaPlayer.isPlaying()) {

                        mediaPlayer.pause();
                    }
                }
            }
            super.onCallStateChanged(state, incomingNumber);

        }
    };

    public void onCompletion(MediaPlayer arg0) {

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            if (currentSongIndex < (AlbumeSongs.AlBUM_LIST_ARRAY.size() - 1))
                playSong(currentSongIndex + 1, "Kumauni");
            else playSong(0, "Kumauni");
        }
    }

    private void seekChange(View v) {
        // if(mediaPlayer.isPlaying()){
        SeekBar sb = (SeekBar) v;
        mediaPlayer.seekTo(sb.getProgress());
//            Toast.makeText(getApplicationContext(),"progress"+sb,Toast.LENGTH_LONG).show();
        //  }
    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();

        *//*Intent oi = new Intent(this, MainActivity.class);
        oi.setAction(Intent.ACTION_MAIN);
        oi.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(oi);*//*
        Log.e("back................", " back pressed");
       // mediaPlayer.pause();
    }*/

    //    @Override
//    protected void onPause() {
//        super.onPause();
//
//        //startService();
//
//            if(mBound){
//                unbindService(this);
//                mBound = false;
//                mBinder = null;
//            }
//
//            unregisterReceiver(mReceiver);
//
//            super.onPause();
//
//    }
    public void startService() {
        Intent serviceIntent = new Intent(BlurMediaPlayer.this, MyNotification.class);
        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(serviceIntent);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mediaPlayer.reset();
//    }

    public void next_song() {
        Log.d(TagUtils.getTag(), "next clicked");
        if (AlbumeSongs.AlBUM_LIST_ARRAY.size() != 0) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();

                Log.e("next button clicked", "next clicked");
                System.out.println("Singer size is " + AlbumeSongs.AlBUM_LIST_ARRAY.size() + "index is " + currentSongIndex);
                Log.e("Size is -", String.valueOf(AlbumeSongs.AlBUM_LIST_ARRAY.size()));
                if (currentSongIndex < (AlbumeSongs.AlBUM_LIST_ARRAY.size() - 1)) {
                    String SongImage = AlbumeSongs.AlBUM_LIST_ARRAY.get(currentSongIndex + 1).getSongImg();
                    System.out.println("Image on next button is :" + AlbumeSongs.AlBUM_LIST_ARRAY.get(currentSongIndex + 1).getSongImg());
                    Picasso.with(BlurMediaPlayer.this).load(String.valueOf(SongImage)).into(image_singer);
                    Picasso.with(BlurMediaPlayer.this).load(String.valueOf(SongImage)).into(frame_background);
                    playSong(currentSongIndex + 1, "Garwali");
                    currentSongIndex = currentSongIndex + 1;
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            finalTime = mediaPlayer.getDuration();
                            startTime = mediaPlayer.getCurrentPosition();

                            total_song_time_text.setText(String.format("%d : %d ",
                                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                            );

                            remaining_songtime_left_text.setText(String.format("%d : %d ",
                                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                            );
                            // song_seekbar1.setProgress((int)startTime);
                            myHandler.postDelayed(UpdateSongTime, 1000);
                        }
                    }
                }
                Log.e("CURRENTSONGINDEX", String.valueOf(currentSongIndex));
            } else {
                // play first song
                Log.e("CURRENTSONGINDEX", "in else");
                playSong(0, "Singer");
                currentSongIndex = 0;
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mBound = true;
        mBinder = (MyNotification.MusicBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mBound = false;
        mBinder = null;
    }

    private class MusicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().compareTo(Globals.ACTION_NEW_SONG) == 0) {
                Log.i("TAG", "Recieved New Song Broadcast");
                Song song = intent.getParcelableExtra(Globals.EXTRA_SONG);
//                TextView songView = (TextView) findViewById(R.id.tv_currentSong);
//                TextView artistView = (TextView) findViewById(R.id.tv_currentArtist);
//                songView.setText(song.getmTitle());
//                artistView.setText(song.getmArtist());
            }
        }
       /*public void setUpAsForeground(String text) {
            PendingIntent pi =
                    PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), BlurMediaPlayer.class),
                            PendingIntent.FLAG_UPDATE_CURRENT);
            mNotification = new Notification();
            mNotification.tickerText = text;
//            mNotification.icon = R.drawable.ic_mshuffle_icon;
            mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
            mNotification.setLatestEventInfo(getApplicationContext(), getResources().getString(R.string.app_name), text, pi);

           startForeground(NOTIFICATION_ID, mNotification);
        }*/
    }


    public void showNotification() {
        //Notificationn.ctx = BlurMediaPlayer.this;
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
            nn = new Notificationn(this);
        } else {
            new AlertDialog.Builder(BlurMediaPlayer.this)
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

    public void playMusic() {
        //Notificationn.mNotificationManager.notify(Notificationn.NOTIFICATION_ID, Notificationn.notification);
        try {
            if (mediaPlayer != null)
                mediaPlayer.release();
        } catch (Exception e) {

        }
        mediaPlayer = new MediaPlayer();
        showNotification();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            //(Environment.getExternalStorageDirectory().getPath()+"/term.mp3")

            Log.d(TagUtils.getTag(), "original url:-" + idUrl);
            HttpProxyCacheServer proxy = GeetApplication.getProxy(this);
            String proxyUrl = proxy.getProxyUrl(idUrl);
            Log.d(TagUtils.getTag(), "proxy url:-" + proxyUrl);
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
                    next_song();
                }
            });

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("khogenprep", "prepared music");
                    Toast.makeText(getApplicationContext(), "prepared", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                    finalTime = mediaPlayer.getDuration();
                    total_song_time_text.setText(String.format("%d : %d ",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                    );
                }
            });
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException t) {

        }

    }

    /*@Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onpaused");
        BackgroundService.referrenceActivity(BlurMediaPlayer.this);
        startService(ii);
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "resume");


        stopService(ii);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5 && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.moveTaskToBack(true);
//            Intent oi = new Intent(this, MainActivity.class);
//            oi.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            oi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(oi);
            finish();
            //onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
//        Intent setIntent = new Intent(Intent.ACTION_MAIN);
//        setIntent.addCategory(Intent.CATEGORY_HOME);
//        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(setIntent);
        finish();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                //this.moveTaskToBack(true);

                Toast.makeText(getApplicationContext(),"back",Toast.LENGTH_SHORT).show();
                Intent oi = new Intent(BlurMediaPlayer.this, MainActivity.class);
                oi.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(oi);
                return true;
            default:
                Toast.makeText(getApplicationContext(),"default",Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }*/
    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("lifecycle","destroy");
//        //nn.clearNotification();
//
//    }

//
//    public class StartReceiver extends BroadcastReceiver {
//        Context ctx;
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            this.ctx=context ;
//		/*Instead of starting the apps i decided to start the
//		  the main activity. Also by starting the main activity I start
//		  the service for notifications too.
//		 */
//            //startChatApps("Î�Î± Î¾ÎµÎºÎ¹Î½Î®ÏƒÏ‰ Ï„Î¹Ï‚ chat apps?");

//
//
//
//
//
//
//
////        Intent activity=new Intent(ctx, BlurMediaPlayer.class);
////        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        ctx.startActivity(activity);
////
////        //show the notification
////        new Notificationn(context);
//        }
//
//    }
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
