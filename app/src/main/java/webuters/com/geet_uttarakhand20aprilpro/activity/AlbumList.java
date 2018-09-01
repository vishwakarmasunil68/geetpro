package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.adapter.AlbumListAdapter;
import webuters.com.geet_uttarakhand20aprilpro.bean.SongListBean;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;

/**
 * Created by Tushar on 2/27/2016.
 */
public class AlbumList extends Activity {
  static ArrayList<SongListBean> song_Array_list;
    ProgressDialog pDialog;
    ListView album_list_layout_list1;
//  public  String Albumname, createdDate, firstName, lastName, songPath, songId, songImg;
    //ImageView album_cover;

//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list_layout);

//        easyTracker = EasyTracker.getInstance(AlbumList.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(AlbumList.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }


        // album_list_layout = (ListView) findViewById(R.id.album_list_layout);
        new AlbumListAsynTask().execute();
    }


    private class AlbumListAsynTask extends AsyncTask<String, String, String> {

        String id,AlbumName,AlbumCover,CreatedDate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AlbumList.this);
            pDialog.setMessage("Loading .........");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpULRConnect.getData(Holder.ALBUM_LIST);
            Log.e("ALBUM  LIST Response ---->", String.valueOf(content));

            if(String.valueOf(content)==null)
            {
                Toast.makeText(AlbumList.this, "Connection is Breaking ", Toast.LENGTH_SHORT).show();
            }

            return content;

        }

        @Override
        protected void onPostExecute(String s) {
            //   song_Array_list.clear();
            pDialog.dismiss();
            try {

                Log.e("Album Post Method Call here ....", "Method ...");
                JSONArray ar = new JSONArray(s);
                song_Array_list = new ArrayList<SongListBean>();
                System.out.println("Array response is ---" + ar);
                Log.e("Album Array response ids---", String.valueOf(ar));
                for (int i = 0; i < ar.length(); i++) {
                    Log.e("Album Array  is ---", String.valueOf(ar.length()));

                    JSONObject jsonobject = ar.getJSONObject(i);

                    SongListBean songListBean = new SongListBean();

                    id = jsonobject.getString("id");

                    songListBean.setId(id);
//                    String.valueOf(ar).indexOf(i);
                    Log.e("ALBUM ID Is =======================>>>>>", id);
                    Log.e("Array id  Index is ---->>", String.valueOf(String.valueOf(ar).indexOf(i)));


                    AlbumCover = jsonobject.getString("AlbumCover");
                    songListBean.setAlbumCover(AlbumCover);
                    Log.e(" AlbumCover As it Is Here=======================>>>>>", AlbumCover);


                    AlbumName = jsonobject.getString("AlbumName");
                    songListBean.setAlbumName(AlbumName);
                    Log.e("Album name As it Is Here=======================>>>>>", AlbumName);
//                    AlbumListAdapter adapter = new AlbumListAdapter(AlbumList.this, R.layout.album_list_layout, song_Array_list);
                //    Log.e("Adapter set  here --(*(*(*(*(*(*(*(*(--", String.valueOf(adapter));
                    song_Array_list.add(songListBean);
                    Log.e("song_Array_list set  here --NJNIJNIJMNIMNIMNIKMN--", String.valueOf(song_Array_list));


//                    Log.d("sun","id:-"+songListBean.getId()+"\t"+"Name:-"+songListBean.getAlbumName());
                }
            }

            catch (JSONException e)

            {
                e.printStackTrace();
            }
                    Log.e("Album [][][][[][][][][][[]Array list is ----- >>>", String.valueOf(song_Array_list));

            for(SongListBean slb:song_Array_list){
                Log.d("sunil","id:-"+slb.getId()+"\t"+"Name:-"+slb.getAlbumName());
            }

            AlbumListAdapter adapter = new AlbumListAdapter(AlbumList.this, R.layout.album_list_layout, song_Array_list);
               //     AlbumListAdapter adapter = new AlbumListAdapter(AlbumList.this, R.layout.album_list_layout, song_Array_list);
                    album_list_layout_list1 = (ListView) findViewById(R.id.album_list_layout_list1);

                    Log.e("Album Adapter Set here ----->>>", "Adapter set ");

                   album_list_layout_list1.setAdapter(adapter);


                    album_list_layout_list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            Log.e("List Item Click -----tushar", "Clicked ");


//                    String latest_songs_bean12 = album_list_aaray.get(position).getId();
                            String latest_songs_bean123 = song_Array_list.get(position).getAlbumCover();
                            int songIndex = position;

                            Intent i = new Intent(getApplicationContext(), AlbumeSongs.class);

                            String string = String.valueOf(song_Array_list);

                            i.putExtra("songIndex", songIndex);
                            Log.e("put Index is --->", String.valueOf(songIndex));
                            String latest_songs_bean12 = song_Array_list.get(position).getId();

                            i.putExtra("Album_id", latest_songs_bean12);
                            Log.e("ALBUM_ID put is ----", latest_songs_bean12);
                            //   String latest_songs_bean123 = album_list_aaray.get(position).getAlbumName();
                            i.putExtra("SongImage123", latest_songs_bean123);
                            System.out.println(" Album_cover_image is ----" + latest_songs_bean123);


                            startActivity(i);

                        }
                    });
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
