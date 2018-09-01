package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunil on 26-08-2016.
 */
public class StartReciever2 extends BroadcastReceiver {
    Context context;
    Notificationn2 nn;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        String action = intent.getStringExtra("DO");

        if(action.equalsIgnoreCase("close")){
            try {
                NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nMgr.cancelAll();
                BluredMediaPlayer2.mediaPlayer.stop();
                BluredMediaPlayer2.bm2.finish();
                AppConstant.setsavedmusicplaying(context,false);
                AppConstant.setmainmusicplaying(context,false);
                AppConstant.setfavmusicplaying(context,false);
            }
            catch (Exception e){
                Log.d("sunil",e.toString());
            }
        }
        else {
            if (action.equalsIgnoreCase("pause")) {
                PlayPauseMethod();
            }
            else{
                if(action.equalsIgnoreCase("next")){
                    nextSong();
                }
                else{
                    if(action.equalsIgnoreCase("prev")){
                        if(BluredMediaPlayer2.currentsongindex!=0){
                            BluredMediaPlayer2.startTime=0;
                            BluredMediaPlayer2.song_pathvalue=Setting_event.albumSongResultPOJOS.get(BluredMediaPlayer2.currentsongindex-1).getSongPath();
                            playMusic(BluredMediaPlayer2.song_pathvalue);
                            BluredMediaPlayer2.myHandler.postDelayed(BluredMediaPlayer2.UpdateSongTime, 1000);
                        }
                    }
                }
            }
        }

//
//        Intent oi = new Intent("broadCastName");
//        oi.putExtra("message",action);
//        context.sendBroadcast(oi);
    }

    public void PlayPauseMethod(){
        try {
//                    BluredMediaPlayer2.mediaPlayer.pause();
            if((BluredMediaPlayer2.mediaPlayer!=null)){
                if(BluredMediaPlayer2.mediaPlayer.isPlaying()){
                    BluredMediaPlayer2.play_pause_button.setChecked(true);
                    BluredMediaPlayer2.mediaPlayer.pause();
                    AppConstant.setIsPlaying(context,false);
                    Notificationn2.changeImageView2();
                }
                else{
                    BluredMediaPlayer2.play_pause_button.setChecked(false);
                    BluredMediaPlayer2.mediaPlayer.start();
                    AppConstant.setIsPlaying(context,true);
                    Notificationn2.changeImageView1();
                }
            }else {
                BluredMediaPlayer2.broad=false;
            }
            Log.d("sunil", "pausing2");
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }


    public void nextSong(){
        if((BluredMediaPlayer2.currentsongindex+1)!=Setting_event.albumSongResultPOJOS.size()){
            BluredMediaPlayer2.startTime=0;
            BluredMediaPlayer2.song_pathvalue=Setting_event.albumSongResultPOJOS.get(BluredMediaPlayer2.currentsongindex+1).getSongPath();
            playMusic(BluredMediaPlayer2.song_pathvalue);
            BluredMediaPlayer2.myHandler.postDelayed(BluredMediaPlayer2.UpdateSongTime, 1000);
        }
    }

    public void showNotification(){
        //Notificationn.ctx = BlurMediaPlayer.this;
        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion>= Build.VERSION_CODES.JELLY_BEAN) {
            if(AppConstant.getFavMusicPref(context)){
                AppConstant.SetSaveMusicpref(context,false);
                AppConstant.SetmainMusicpref(context,false);
                AppConstant.SetfavMusicPref(context,true);
            }
            else {
                AppConstant.SetSaveMusicpref(context, false);
                AppConstant.SetmainMusicpref(context, false);
                AppConstant.SetfavMusicPref(context, true);


                AppConstant.setMainCallStatus(context, false);
                AppConstant.setCallStatus(context, false);
                AppConstant.setSavedCallStatus(context, false);


                AppConstant.setIdlePref(context, true);
                AppConstant.setoffhookPref(context, true);
                AppConstant.setRecievePref(context, true);

                try{
                    NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    nMgr.cancelAll();
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
                    Notificationn2.clearNotification();
                }
                catch (Exception e){

                }
                try{
                    Notificationn.clearNotification();
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
            }
            nn = new Notificationn2(context);
        }
        else{
            new AlertDialog.Builder(context)
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


    public void playMusic(String idUrl){
        try{
            if(BluredMediaPlayer2.mediaPlayer!=null)
                BluredMediaPlayer2.mediaPlayer.release();
        }
        catch (Exception e){

        }

        BluredMediaPlayer2.mediaPlayer = new MediaPlayer();
        showNotification();
        BluredMediaPlayer2.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            //(Environment.getExternalStorageDirectory().getPath()+"/term.mp3")
            BluredMediaPlayer2.mediaPlayer.setDataSource((idUrl));

        } catch (IllegalArgumentException e) {
            Toast.makeText(context.getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(context.getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(context.getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }



        try {
            BluredMediaPlayer2.mediaPlayer.prepare();

            BluredMediaPlayer2.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextSong();
                    Log.d("sunil","song completed");
                }
            });

            BluredMediaPlayer2.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("khogenprep","prepared music");
                    Toast.makeText(context.getApplicationContext(),"prepared",Toast.LENGTH_SHORT).show();
                    BluredMediaPlayer2.mediaPlayer.start();
                    AppConstant.setIsPlaying(context,true);
                    BluredMediaPlayer2.blur_linear.setVisibility(View.VISIBLE);
                    BluredMediaPlayer2.pb_ll.setVisibility(View.GONE);
                    BluredMediaPlayer2.finalTime = BluredMediaPlayer2.mediaPlayer.getDuration();

                    BluredMediaPlayer2.play_pause_button.setChecked(false);

                    BluredMediaPlayer2.total_song_time_text.setText(String.format("%d : %d ",
                            TimeUnit.MILLISECONDS.toMinutes((long) BluredMediaPlayer2.finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) BluredMediaPlayer2.finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) BluredMediaPlayer2.finalTime)))
                    );
                    for(int i = 0; i< Setting_event.albumSongResultPOJOS.size(); i++){
                        if(BluredMediaPlayer2.song_pathvalue.equals(Setting_event.albumSongResultPOJOS.get(i).getSongPath())){
                            BluredMediaPlayer2.currentsongindex=i;
                            Log.d("sunil","current_song_index:-"+BluredMediaPlayer2.currentsongindex);
                        }
                    }

                    AppConstant.setsavedmusicplaying(context,false);
                    AppConstant.setmainmusicplaying(context,false);
                    AppConstant.setfavmusicplaying(context,true);


                    BluredMediaPlayer2.myHandler.postDelayed(BluredMediaPlayer2.UpdateSongTime, 1000);
                }
            });
        }  catch (IllegalStateException e) {
            Toast.makeText(context.getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }catch(IOException t){

        }
    }
}
