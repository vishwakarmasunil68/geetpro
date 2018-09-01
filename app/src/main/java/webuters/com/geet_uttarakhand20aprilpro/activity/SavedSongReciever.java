package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import webuters.com.geet_uttarakhand20aprilpro.R;

/**
 * Created by sunil on 31-08-2016.
 */
public class SavedSongReciever extends BroadcastReceiver {
    Context context;
    SavedSongNotification nn;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        String action = intent.getStringExtra("DO");
        Log.d("sunil","action:-"+action);

        if(action.equalsIgnoreCase("close")){
            try {
                NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nMgr.cancelAll();
                SavedSongsMusicPlayer.mediaPlayer.stop();
                SavedSongsMusicPlayer.ssmp.finish();

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

//                SavedSongNotification.contentView.setViewVisibility(R.id.btnplay, View.GONE);
            }
            else{
                if(action.equalsIgnoreCase("next")){
                    Log.d("sunil","next");
                    nextSong();
                }
                else{
                    if(action.equalsIgnoreCase("prev")){
                        Log.d("sunil","prev");
                        if(SavedSongsMusicPlayer.currentsongindex!=0){
                            SavedSongsMusicPlayer.currentsongindex=SavedSongsMusicPlayer.currentsongindex-1;
                            playMusic(ContactUs.filepathlst.get(SavedSongsMusicPlayer.currentsongindex));
                            SavedSongsMusicPlayer.myHandler.postDelayed(SavedSongsMusicPlayer.UpdateSongTime, 1000);
                        }
                    }
                }
            }
        }


//        Intent oi = new Intent("savebroadcast");
//        oi.putExtra("message",action);
//        context.sendBroadcast(oi);
//        }


        //Notificationn nnn= new Notificationn(context);
    }
    public void PlayPauseMethod(){
        Log.d("sunil","pause");
        try {
//                    BluredMediaPlayer2.mediaPlayer.pause();
            if((SavedSongsMusicPlayer.mediaPlayer!=null)){
                if(SavedSongsMusicPlayer.mediaPlayer.isPlaying()){
                    SavedSongsMusicPlayer.play_pause_button.setChecked(true);
                    SavedSongsMusicPlayer.mediaPlayer.pause();
                    AppConstant.setIsPlaying(context,false);
                    SavedSongNotification.changeImageView2();
                }
                else{
                    SavedSongsMusicPlayer.play_pause_button.setChecked(false);
                    SavedSongsMusicPlayer.mediaPlayer.start();
                    AppConstant.setIsPlaying(context,true);
                    SavedSongNotification.changeImageView1();
                }
            }else {
                SavedSongsMusicPlayer.broad=false;
            }
            Log.d("sunil", "pausing2");
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }


    public void nextSong(){
        if((SavedSongsMusicPlayer.currentsongindex+1)!=ContactUs.filepathlst.size()){
            SavedSongsMusicPlayer.currentsongindex=SavedSongsMusicPlayer.currentsongindex+1;
            playMusic(ContactUs.filepathlst.get(SavedSongsMusicPlayer.currentsongindex));
            SavedSongsMusicPlayer.myHandler.postDelayed(SavedSongsMusicPlayer.UpdateSongTime, 1000);
        }
    }

    public void showNotification(){
        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion>= Build.VERSION_CODES.JELLY_BEAN) {

            if(AppConstant.getSaveMusicPref(context)){
                AppConstant.SetSaveMusicpref(context,true);
                AppConstant.SetmainMusicpref(context,false);
                AppConstant.SetfavMusicPref(context,false);
            }
            else{
                AppConstant.SetSaveMusicpref(context,true);
                AppConstant.SetmainMusicpref(context,false);
                AppConstant.SetfavMusicPref(context,false);

                AppConstant.setMainCallStatus(context, false);
                AppConstant.setCallStatus(context, false);
                AppConstant.setSavedCallStatus(context, false);

                AppConstant.setIdlePref(context,true);
                AppConstant.setoffhookPref(context,true);
                AppConstant.setRecievePref(context,true);
                try{
                    NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
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

            nn= new SavedSongNotification(context);
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
            if(SavedSongsMusicPlayer.mediaPlayer!=null)
                SavedSongsMusicPlayer.mediaPlayer.release();
        }
        catch (Exception e){

        }

        SavedSongsMusicPlayer.mediaPlayer= MediaPlayer.create(context, Uri.parse(idUrl));


        SavedSongsMusicPlayer.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                AppConstant.setIsPlaying(context,true);
                SavedSongsMusicPlayer.finalTime = mediaPlayer.getDuration();
                SavedSongsMusicPlayer.play_pause_button.setChecked(false);
                SavedSongsMusicPlayer.total_song_time_text.setText(String.format("%d : %d ",
                        TimeUnit.MILLISECONDS.toMinutes((long) SavedSongsMusicPlayer.finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) SavedSongsMusicPlayer.finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) SavedSongsMusicPlayer.finalTime)))
                );
                showNotification();
                Log.d("sunil","ss current index:-"+SavedSongsMusicPlayer.currentsongindex);
                AppConstant.setsavedmusicplaying(context,true);
                AppConstant.setmainmusicplaying(context,false);
                AppConstant.setfavmusicplaying(context,false);
//                for(int i=0;i<Setting_event.albumSongsBeen.size();i++){
//                    if(song_pathvalue.equals(Setting_event.albumSongsBeen.get(i).getSongPath())){
//                        currentsongindex=i;
//                        Log.d("sunil","current_song_index:-"+currentsongindex);
//                    }
//                }

                try {
                    File f = new File(ContactUs.song_image_list.get(SavedSongsMusicPlayer.currentsongindex));
                    Log.d("sunil","exist:-"+f.exists());
                    Log.d("sunil","path:-"+f.toString());
                    if (f.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(f.toString());
                        SavedSongsMusicPlayer.image_singer.setImageBitmap(myBitmap);
                    }
                }
                catch (Exception e){
                    SavedSongsMusicPlayer.image_singer.setImageDrawable(context.getResources().getDrawable(R.drawable.finallogo));
                }


//                gettingSongImagePath();
                SavedSongsMusicPlayer.myHandler.postDelayed(SavedSongsMusicPlayer.UpdateSongTime, 1000);
            }
        });
    }
}
