package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.Pref;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;
import webuters.com.geet_uttarakhand20aprilpro.pojo.AlbumSongResultPOJO;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;

/**
 * Created by Tushar on 4/3/2016.
 */
public class Setting_event extends Activity {

    ImageView seeting_back;
    ListView faveratesong_list;
//    SongListBean songListBean=new SongListBean();
    //    private EasyTracker easyTracker = null;
    public static Bitmap bmp;
//    public static ArrayList<AlbumSongsBean> albumSongsBeen=new ArrayList<>();
    public static List<AlbumSongResultPOJO> albumSongResultPOJOS=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_event);
        faveratesong_list = (ListView) findViewById(R.id.faveratesong_list);
        albumSongResultPOJOS.clear();
        albumSongResultPOJOS.addAll(Pref.getsaveSongPOJOS(getApplicationContext()));
        Log.d(TagUtils.getTag(),"album song result pojo:-"+albumSongResultPOJOS.toString());
//        final List<String> lst_song_name=new ArrayList<String>();
//        final List<String> lst_song_url=new ArrayList<String>();
//        final List<String> lst_song_album=new ArrayList<String>();
//        final List<String> lst_song_album_id=new ArrayList<>();
//        SharedPreferences sp=getSharedPreferences("geet.txt", Context.MODE_PRIVATE);
//        String song_list_url=sp.getString("fav_song_url", "");
//        String song_list_name=sp.getString("fav_song_name","");
//        String song_list_album=sp.getString("fav_song_album","");
//        String song_album_id=sp.getString("fav_song_album_id","");
//        if(song_list_name.equals("")){
//
//        }
//        else{
//            String[] song_name=song_list_name.split("::");
//            String[] song_url=song_list_url.split("::");
//            String[] song_album=song_list_album.split("::");
//            String[] song_id_album=song_album_id.split("::");
//            for(int i=0;i<song_name.length;i++) {
//                if(!song_name[i].equals("")) {
//                    lst_song_name.add(song_name[i]);
//                    lst_song_album.add(song_album[i]);
//                    lst_song_url.add(song_url[i]);
//                    lst_song_album_id.add(song_id_album[i]);
//                    AlbumSongsBean asb=new AlbumSongsBean();
//                    asb.setSongName(song_name[i]);
//                    asb.setSongPath(song_url[i]);
//                    albumSongsBeen.add(asb);
//                }
//            }
//        }



        if(albumSongResultPOJOS.size()>0){
            List<String> lst_song_name=new ArrayList<>();
            for(AlbumSongResultPOJO albumSongResultPOJO:albumSongResultPOJOS){
                lst_song_name.add(albumSongResultPOJO.getSongName());
            }
            faveratesong_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lst_song_name));

        }
        else{
            Toast.makeText(getApplicationContext(),"Your Favorite Song list is empty", Toast.LENGTH_LONG).show();
        }
        seeting_back = (ImageView) findViewById(R.id.seeting_back);
        seeting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Setting_event.this, MainActivity.class);
                startActivity(i);
            }
        });
        faveratesong_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("sun","song_name:-"+lst_song_name.get(position));
//                Log.d("sun","song_url:-"+lst_song_url.get(position));
//                Log.d("sun","album_url:-"+lst_song_album.get(position));
//                Log.d("sun","album_id:-"+lst_song_album_id.get(position));


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
                try{
//                if (ContactUs.mediaPlayer != null) {
                    ContactUs.mediaPlayer.stop();
                    ContactUs.imgplay.setVisibility(View.GONE);
                    ContactUs.imgpause.setVisibility(View.VISIBLE);
                    ContactUs.music_flag=true;
                    ContactUs.reset_player=true;
//                }
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                try{
                    BluredMediaPlayer2.mediaPlayer.stop();
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
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
                    Log.d("sunil",e.toString());
                }
                try{
                    SavedSongsMusicPlayer.ssmp.finish();
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }


                //playing song code

                SharedPreferences khogen = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editr = khogen.edit();
                editr.putString("SongImage123", albumSongResultPOJOS.get(position).getSongImg());
//                Log.d("sun", "SongImage123:-" + albumID12);
                editr.putString("Sengaer_song_path", albumSongResultPOJOS.get(position).getSongPath());
//                Log.d("sun", "Sengaer_song_path:-" + AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongPath());
                editr.putString("Index", albumSongResultPOJOS.get(position).getSongPath());
//                Log.d("sun", "Index:-" + IndexNo);
                editr.commit();
                try {
                    ImageView img = new ImageView(Setting_event.this);

                    Picasso.with(Setting_event.this)
                            .load(albumSongResultPOJOS.get(position).getSongImg())
                            .into(img);
                    bmp = ((BitmapDrawable) img.getDrawable()).getBitmap();
                }
                catch (Exception e){

                }
                AppConstant.setsavedmusicplaying(getApplicationContext(),false);
                AppConstant.setmainmusicplaying(getApplicationContext(),false);
                AppConstant.setfavmusicplaying(getApplicationContext(),false);

                MainActivity.img_flag=false;
                MainActivity.blured_mediaplaying=false;
                BluredMediaPlayer2.startTime=0;
                final Intent i = new Intent(Setting_event.this, BluredMediaPlayer2.class);
                i.putExtra("SongImage123", albumSongResultPOJOS.get(position).getSongImg());
                i.putExtra("Sengaer_song_path", albumSongResultPOJOS.get(position).getSongPath());
                i.putExtra("Index", albumSongResultPOJOS.get(position).getSongPath());
                i.putExtra("backup",true);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);


            }
        });
    }

}
