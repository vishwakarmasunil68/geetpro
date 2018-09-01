package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.List;

import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;

/**
 * Created by emobi8 on 8/12/2016.
 */
public class NotificationReturnSlot extends Activity {
    List<String> song_list=ContactUs.filepathlst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        String action = (String) getIntent().getExtras().get("DO");
        Log.d(TagUtils.getTag(),"action:-"+action);
//        if (action.equals("volume")) {
//            Log.i(TagUtils.getTag(), "volume");
//            //Your code
//        } else if (action.equals("stopNotification")) {
//            //Your code
//            Log.i(TagUtils.getTag(), "stopNotification");
//        }
        if(action.equals("play")){
            if(ContactUs.music_flag) {
                action = "play";
                ContactUs.music_flag=false;
            }
            else{
                action="pause";
                ContactUs.music_flag=true;
            }
        }
            switch (action){
                case "volume":
                    Log.d(TagUtils.getTag(),"volume");
                    if (ContactUs.mediaPlayer != null) {
                        ContactUs.mediaPlayer.stop();
                        ContactUs.imgplay.setVisibility(View.GONE);
                        ContactUs.imgpause.setVisibility(View.VISIBLE);
                        ContactUs.music_flag=true;
                        ContactUs.reset_player=true;
                    }
                    try {
                        NotificationPanel.notificationCancel();
                    }
                    catch (Exception e){
                        Log.d("sunil",e.toString());
                    }
                    try {
                        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        nMgr.cancelAll();
                        BlurMediaPlayer1.mediaPlayer.stop();
                    }
                    catch (Exception e){
                        Log.d("sunil",e.toString());
                    }
                    try {
                        BlurMediaPlayer1.mediaPlayer.stop();
                    }
                    catch (Exception e){
                        Log.d("sunil",e.toString());
                    }
                    break;
                case "stop":
                    Log.d(TagUtils.getTag(),"stop");
                    if (ContactUs.mediaPlayer != null) {
                        ContactUs.mediaPlayer.stop();
                    }
                    break;
                case "play":
                    Log.d(TagUtils.getTag(),"play");
                    ContactUs.imgplay.setVisibility(View.VISIBLE);
                    ContactUs.imgpause.setVisibility(View.GONE);
//                    if (NotificationMediaPlayer.mediaPlayer != null) {
                    ContactUs.mediaPlayer.start();
//                    }
                    break;
                case "pause":
                    ContactUs.imgplay.setVisibility(View.GONE);
                    ContactUs.imgpause.setVisibility(View.VISIBLE);
                    Log.d(TagUtils.getTag(),"pause");
//                    if (NotificationMediaPlayer.mediaPlayer != null) {
                    ContactUs.mediaPlayer.pause();
//                    }
                    break;
                case "previous":
                    if(ContactUs.pos==0){

                    }
                    else {
                        ContactUs.pos = ContactUs.pos - 1;
                        String path1 = song_list.get(ContactUs.pos);
                        if (ContactUs.mediaPlayer != null) {
                            ContactUs.mediaPlayer.stop();
                        }
                        try {

                            ContactUs.mediaPlayer = new MediaPlayer();
                            ContactUs.mediaPlayer.setDataSource(path1);
                            ContactUs.mediaPlayer.prepare();
                            ContactUs.mediaPlayer.start();
                            File file=new File(path1);
                            ContactUs.text_songname.setText(file.getName());
//                            NotificationPanel.flag=true;
                            ContactUs.music_flag=false;
                            ContactUs.imgplay.setVisibility(View.VISIBLE);
                            ContactUs.imgpause.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Log.d(TagUtils.getTag(), e.toString());
                        }
                        Log.d(TagUtils.getTag(), "previous");
                    }
                    break;
                case "next":
//                    if(NotificationMediaPlayer.pos==(song_list.size()-1)){

                    try {
                        ContactUs.pos = ContactUs.pos + 1;
                        String path = song_list.get(ContactUs.pos);
                        if (ContactUs.mediaPlayer != null) {
                            ContactUs.mediaPlayer.stop();
                        }
                        ContactUs.mediaPlayer = new MediaPlayer();
                        ContactUs.mediaPlayer.setDataSource(path);
                        ContactUs.mediaPlayer.prepare();
                        ContactUs.mediaPlayer.start();

                        File file=new File(path);
                        ContactUs.text_songname.setText(file.getName());
                        ContactUs.imgplay.setVisibility(View.VISIBLE);
                        ContactUs.imgpause.setVisibility(View.GONE);
//                        NotificationPanel.flag=true;
                        ContactUs.music_flag=false;
                    } catch (Exception e) {
                        Log.d(TagUtils.getTag(), e.toString());
                    }
                    Log.d(TagUtils.getTag(), "next");
//                }
                    break;
            }
        finish();
    }
}