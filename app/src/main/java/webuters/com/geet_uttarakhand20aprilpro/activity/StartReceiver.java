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

import com.danikula.videocache.HttpProxyCacheServer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import webuters.com.geet_uttarakhand20aprilpro.GeetApplication;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;


/**
 * Created by khagendra on 27-06-2016.
 */

public class StartReceiver extends BroadcastReceiver {
    Context context;
    Notificationn nn;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        String action = intent.getStringExtra("DO");

        if(action.equalsIgnoreCase("close")){
            try {
                NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nMgr.cancelAll();
                BlurMediaPlayer1.mediaPlayer.stop();
                BlurMediaPlayer1.bmp1.finish();

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
                        Log.d(TagUtils.getTag(),"prev song");
                        if(BlurMediaPlayer1.playing_position!=0){
//                            BlurMediaPlayer1.song_pathvalue=AlBUM_LIST_ARRAY.get(BlurMediaPlayer1.currentsongindex-1).getSongPath();
                            playMusic(BlurMediaPlayer1.album.getAlbumSongResultPOJOS().get(BlurMediaPlayer1.playing_position-1).getSongPath());
                            BlurMediaPlayer1.myHandler.postDelayed(BlurMediaPlayer1.UpdateSongTime, 1000);
                        }
                    }
                }
            }
        }

//        if(action.equalsIgnoreCase("next")){
//            try {
//                if ((BlurMediaPlayer1.currentsongindex + 1) != AlBUM_LIST_ARRAY.size()) {
//                    song_pathvalue = AlBUM_LIST_ARRAY.get(currentsongindex + 1).getSongPath();
//                    PlayMusic(song_pathvalue);
//                    myHandler.postDelayed(UpdateSongTime, 1000);
//                }
//            }
//            catch (Exception e){
//                Log.d("sunil",e.toString());
//            }
//        }
//        if(action.equalsIgnoreCase(""))

       /* Notificationn.contentView.setImageViewResource(R.id.btnplay,R.drawable.nextfwd);
        Notificationn.notification.contentView = Notificationn.contentView;
        Notificationn.mNotificationManager.notify(Notificationn.NOTIFICATION_ID,Notificationn.notification);
*/

//        if(action.equalsIgnoreCase("app")){
//            Intent it = new Intent(context, BlurMediaPlayer.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // You need this if starting
//            //  the activity from a service
//            intent.setAction(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            context.startActivity(intent);
//        }
//         else{
            Intent oi = new Intent("broadCastName");
            oi.putExtra("message",action);
            context.sendBroadcast(oi);
//        }


        //Notificationn nnn= new Notificationn(context);
    }

    public void PlayPauseMethod(){
        try {
//                    BlurMediaPlayer1.mediaPlayer.pause();
            if((BlurMediaPlayer1.mediaPlayer!=null)){
                if(BlurMediaPlayer1.mediaPlayer.isPlaying()){
                    BlurMediaPlayer1.play_pause_button.setChecked(true);
                    BlurMediaPlayer1.mediaPlayer.pause();

                    AppConstant.setIsPlaying(context,false);
                    Notificationn.changeImageView2();
                }
                else{
                    BlurMediaPlayer1.play_pause_button.setChecked(false);
                    BlurMediaPlayer1.mediaPlayer.start();
                    AppConstant.setIsPlaying(context,true);
                    Notificationn.changeImageView1();
                }
            }else {
                BlurMediaPlayer1.broad=false;
            }
            Log.d("sunil", "pausing");
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }

    public void nextSong(){
        if((BlurMediaPlayer1.playing_position+1)!=BlurMediaPlayer1.album.getAlbumSongResultPOJOS().size()){
//            BlurMediaPlayer1.song_pathvalue=BlurMediaPlayer1.album.getAlbumSongResultPOJOS().get(BlurMediaPlayer1.playing_position+1).getSongPath();
            playMusic(BlurMediaPlayer1.album.getAlbumSongResultPOJOS().get(BlurMediaPlayer1.playing_position+1).getSongPath());
            BlurMediaPlayer1.myHandler.postDelayed(BlurMediaPlayer1.UpdateSongTime, 1000);
        }
        Log.d(TagUtils.getTag(),"next song");
    }

    public void showNotification(){
        //Notificationn.ctx = BlurMediaPlayer.this;
        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion>= Build.VERSION_CODES.JELLY_BEAN) {

            if(AppConstant.getMainMusicPref(context)){
                AppConstant.SetSaveMusicpref(context,false);
                AppConstant.SetmainMusicpref(context,true);
                AppConstant.SetfavMusicPref(context,false);
            }
            else {
                AppConstant.SetSaveMusicpref(context, false);
                AppConstant.SetmainMusicpref(context, true);
                AppConstant.SetfavMusicPref(context, false);


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

            nn = new Notificationn(context);
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


    public void playMusic(final String idUrl){
        try{
            if(BlurMediaPlayer1.mediaPlayer!=null)
                BlurMediaPlayer1.mediaPlayer.release();
        }
        catch (Exception e){

        }

        BlurMediaPlayer1.mediaPlayer = new MediaPlayer();
        showNotification();
        BlurMediaPlayer1.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            //(Environment.getExternalStorageDirectory().getPath()+"/term.mp3")
            Log.d(TagUtils.getTag(), "original url:-" + idUrl);
            HttpProxyCacheServer proxy = GeetApplication.getProxy(context.getApplicationContext());
            String proxyUrl = proxy.getProxyUrl(idUrl);
            Log.d(TagUtils.getTag(), "proxy url:-" + proxyUrl);
            BlurMediaPlayer1.mediaPlayer.setDataSource(proxyUrl);

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
            BlurMediaPlayer1.mediaPlayer.prepare();

            BlurMediaPlayer1.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextSong();
                    Log.d("sunil","song completed");
                }
            });

            BlurMediaPlayer1.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("khogenprep","prepared music");
                    Toast.makeText(context.getApplicationContext(),"prepared",Toast.LENGTH_SHORT).show();
                    AppConstant.setIsPlaying(context,true);
                    BlurMediaPlayer1.mediaPlayer.start();
                    BlurMediaPlayer1.blur_linear.setVisibility(View.VISIBLE);
                    BlurMediaPlayer1.pb_ll.setVisibility(View.GONE);
                    BlurMediaPlayer1.finalTime = BlurMediaPlayer1.mediaPlayer.getDuration();

                    BlurMediaPlayer1.play_pause_button.setChecked(false);

                    BlurMediaPlayer1.startTime=0;

                    BlurMediaPlayer1.total_song_time_text.setText(String.format("%d : %d ",
                            TimeUnit.MILLISECONDS.toMinutes((long) BlurMediaPlayer1.finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) BlurMediaPlayer1.finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) BlurMediaPlayer1.finalTime)))
                    );
                    for(int i=0;i<BlurMediaPlayer1.album.getAlbumSongResultPOJOS().size();i++){
                        if(idUrl.equals(BlurMediaPlayer1.album.getAlbumSongResultPOJOS().get(i).getSongPath())){
                            BlurMediaPlayer1.playing_position=i;
                            Log.d("sunil","current_song_index:-"+BlurMediaPlayer1.playing_position);
                        }
                    }

                    AppConstant.setsavedmusicplaying(context,false);
                    AppConstant.setmainmusicplaying(context,true);
                    AppConstant.setfavmusicplaying(context,false);

                    BlurMediaPlayer1.myHandler.postDelayed(BlurMediaPlayer1.UpdateSongTime, 1000);
                }
            });
        }  catch (IllegalStateException e) {
            Toast.makeText(context.getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }catch(IOException t){

        }
    }
}
