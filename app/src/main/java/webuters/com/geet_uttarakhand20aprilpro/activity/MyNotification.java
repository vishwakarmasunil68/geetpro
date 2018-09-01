package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.adapter.Globals;
import webuters.com.geet_uttarakhand20aprilpro.adapter.Song;

/**
 * Created by Mukesh on 5/14/2016.
 */

public class MyNotification extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener{
    Notification status;
    private final String LOG_TAG = "NotificationService";
    private static final String previousIntentCode = "http://23.22.9.33.geet_uttarakhand9May.INTENT_PREVIOUS";
    private static final String nextIntentCode = "http://23.22.9.33.geet_uttarakhand9May.INTENT_NEXT";
    private static final String playpauseIntentCode = "http://23.22.9.33.geet_uttarakhand9May.INTENT_PLAYPAUSE";
    private static final String openPlayerIntentCode = "http://23.22.9.33.geet_uttarakhand9May.INTENT_OPENPLAYER";
    private ArrayList<Song> mPlaylist;
    private static final String TAG = "PlaybackService";
    private MusicBinder mBinder;
    private MediaPlayer mPlayer;
   // private ArrayList<Song> mPlaylist;
    private int mCurrentSong = 0;
    private boolean mIsPrepared = false;
    private boolean mPlaying = false;
    private NotificationManager mNM;
    Intent notificationIntent;
    Song twoSong;
    @Override
    public void onDestroy() {
        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
        stopForeground(true);
        mNM.cancel(R.string.local_service_started);
        stopSelf();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new MusicBinder();

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        mPlaylist = new ArrayList<Song>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


      ;
        if(intent != null && intent.getAction() != null) {
            Log.i(TAG, "Intent: " + intent.getAction());
            switch (intent.getAction()) {
                case previousIntentCode:
                    skipBackward();
                    break;
                case nextIntentCode:
                    skipForward();
                    break;
                case playpauseIntentCode:
                    togglePause();
                    break;
                case openPlayerIntentCode:
                    Intent openIntent = new Intent(getApplicationContext(), MainActivity.class);
                    openIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(openIntent);
                    break;
            }
        }
            return Service.START_NOT_STICKY;
    }

    public ArrayList<Song> getPlayList(){
        return mPlaylist;
    }
    public void skipForward(){
        if(mPlayer != null){
            mCurrentSong++;
            if(mCurrentSong >= mPlaylist.size()) mCurrentSong = mPlaylist.size()-1;
            skipTrack();
        }
    }

    public void skipBackward(){
        if(mPlayer != null) {
            if (mPlayer.getCurrentPosition() < 3000) {
                mCurrentSong--;
                if (mCurrentSong < 0) mCurrentSong = 0;
                skipTrack();
            } else {
                mPlayer.seekTo(0);
            }
        }
    }

    public void togglePause(){
        if(mPlayer != null){
            if(mPlayer.isPlaying()){
                mPlayer.pause();
            } else {
                if(mIsPrepared) mPlayer.start();
            }
        }

    }

    private void stopSong(){
        mPlayer.stop();
        mPlayer.reset();
        mIsPrepared = false;
        mPlaying = false;
    }

    private void skipTrack(){
        if(mPlayer != null){
            stopSong();
            playSong(mPlaylist.get(mCurrentSong));
        }
    }

    public void addToPlaylist(Song _song){
        Log.i(TAG,"adding " + _song.getmTitle() + " currently playing: " + mPlaying);
        addToPlaylist(_song, false);
    }

    public void addToPlaylist(Song _song, boolean _next){
        if(_next && mCurrentSong < mPlaylist.size()-1){
            mPlaylist.add(mCurrentSong+1, _song);
        } else {
            mPlaylist.add(_song);
        }
        if(!mPlaying){
            playSong(_song);
        }
    }

    //OnPreparedListener Methods

    @Override
    public void onPrepared(MediaPlayer mp) {
        mIsPrepared = true;
        mPlaying = true;
        mPlayer.start();
    }

    //OnCompletionListener Methods

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i(TAG,"Completing: " + mPlaylist.get(mCurrentSong).getmTitle());
        stopSong();
        mCurrentSong++;

        if(mCurrentSong < mPlaylist.size()){
            playSong(mPlaylist.get(mCurrentSong));
        }
    }


    public class MusicBinder extends Binder {
        public MyNotification getService(){
            return MyNotification.this;
        }
    }


    private void playSong(Song _song) {
        twoSong = _song;
        Log.i(TAG, "Playing: " + _song.getmTitle());
        Intent broadcastIntent = new Intent(Globals.ACTION_NEW_SONG);
        broadcastIntent.putExtra(Globals.EXTRA_SONG, _song);
        sendBroadcast(broadcastIntent);


        Intent previousIntent = new Intent(previousIntentCode);
        Intent nextIntent = new Intent(nextIntentCode);
        Intent playpauseIntent = new Intent(playpauseIntentCode);
        Intent openplayerIntent = new Intent(openPlayerIntentCode);

        PendingIntent previousPending = PendingIntent.getService(this, 0, previousIntent, 0);
        PendingIntent nextPending = PendingIntent.getService(this, 0, nextIntent, 0);
        PendingIntent playPending = PendingIntent.getService(this, 0, playpauseIntent, 0);
        PendingIntent openPending = PendingIntent.getService(this, 0, openplayerIntent, 0);

        Notification notification;
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,new Intent(this,BlurMediaPlayer.class),0);

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentTitle(_song.getmTitle())
//                .setContentText(_song.getmArtist())
//                .setLargeIcon(_song.getmCover())
//                .setAutoCancel(false)
//                .setContentIntent(openPending)
//                .addAction(R.id.status_bar_prev, null, previousPending)
//                .addAction(R.id.status_bar_play, null, playPending)
//                .addAction(R.id.status_bar_next, null, nextPending);
//
//        notification = builder.getNotification();
//        //notification.setLatestEventInfo(BackgroundService.this,"MainActivity",newtext,contentIntent);
//        mNM.notify(R.string.local_service_started,notification);
//        notificationIntent = new Intent(this,BlurMediaPlayer.class);
//        showNotification();
    }


    private void showNotification(){
        Log.d("back","shownoti");
        CharSequence text = getText(R.string.local_service_started);

        Intent previousIntent = new Intent(previousIntentCode);
        Intent nextIntent = new Intent(nextIntentCode);
        Intent playpauseIntent = new Intent(playpauseIntentCode);
        Intent openplayerIntent = new Intent(openPlayerIntentCode);

        PendingIntent previousPending = PendingIntent.getService(this, 0, previousIntent, 0);
        PendingIntent nextPending = PendingIntent.getService(this, 0, nextIntent, 0);
        PendingIntent playPending = PendingIntent.getService(this, 0, playpauseIntent, 0);
        PendingIntent openPending = PendingIntent.getService(this, 0, openplayerIntent, 0);

        Notification notification;
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,new Intent(this,BlurMediaPlayer.class),0);

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentTitle(twoSong.getmTitle())
//                .setContentText(twoSong.getmArtist())
//                .setLargeIcon(twoSong.getmCover())
//                .setAutoCancel(false)
//                .setContentIntent(openPending)
//                .addAction(R.id.status_bar_prev, null, previousPending)
//                .addAction(R.id.status_bar_play, null, playPending)
//                .addAction(R.id.status_bar_next, null, nextPending);
//
//        notification = builder.getNotification();
//        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        mNM.notify(R.string.local_service_started,notification);
    }









//    private void showNotification() {
//// Using RemoteViews to bind custom layouts into Notification
//        RemoteViews views = new RemoteViews(getPackageName(),
//                R.layout.status_bar);
//        RemoteViews bigViews = new RemoteViews(getPackageName(),
//                R.layout.status_bar_expanded);
//
//// showing default album image
//        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
//        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
//        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
//                Constants.getDefaultAlbumArt(this));
//
//        Intent notificationIntent = new Intent(this, BlurMediaPlayer.class);
//        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
//
//        Intent previousIntent = new Intent(this, MyNotification.class);
//        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
//        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
//                previousIntent, 0);
//
//        Intent playIntent = new Intent(this, MyNotification.class);
//        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
//        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
//                playIntent, 0);
//
//        Intent nextIntent = new Intent(this, MyNotification.class);
//        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
//        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
//                nextIntent, 0);
//
//        Intent closeIntent = new Intent(this, MyNotification.class);
//        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
//        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
//                closeIntent, 0);
//
//        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
//        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
//
//        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
//        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
//
//        views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
//        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
//
//        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
//        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
//
//        views.setImageViewResource(R.id.status_bar_play,
//                R.drawable.apollo_holo_dark_pause);
//        bigViews.setImageViewResource(R.id.status_bar_play,
//                R.drawable.apollo_holo_dark_pause);
//
////        views.setTextViewText(R.id.status_bar_track_name, "Song Title");
////        bigViews.setTextViewText(R.id.status_bar_track_name, "Song Title");
////
////        views.setTextViewText(R.id.status_bar_artist_name, "Artist Name");
////        bigViews.setTextViewText(R.id.status_bar_artist_name, "Artist Name");
//
//       // bigViews.setTextViewText(R.id.status_bar_album_name, "Album Name");
//      //  views.setOnClickPendingIntent(R.layout.);
//        status = new Notification.Builder(this).build();
//        status.contentView = views;
//        status.bigContentView = bigViews;
//        status.flags = Notification.FLAG_ONGOING_EVENT;
//        status.icon = R.drawable.ic_launcher;
//        status.contentIntent = pendingIntent;
//        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
//
//       // ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(0x0101,builder.build());
//
//
//        try {
//            mPlayer.setDataSource(this, Uri.parse(_song.getPath()));
//        } catch (IOException e) {
//            Log.e(TAG, "Error reading file: " + _song.getPath());
//            mPlayer.release();
//            mPlayer = null;
//        }
//        if (mPlayer != null && !mIsPrepared) {
//            mPlayer.prepareAsync();
//        }
//    }
}
