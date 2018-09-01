package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.adapter.CatagoryAdapter;
import webuters.com.geet_uttarakhand20aprilpro.bean.Catagory_calass_bean;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Admin on 2/16/2016.
 */
public class CatagoryClass extends AppCompatActivity {
    ProgressDialog pDialog;
    ListView list_catagory;
    MediaPlayer mp;
    String cat_name;
    TextView cat_name_txt;
    String albumName,albumID,albumCover,singerId,firstName,catID;
    String idUrl;
   // public   String CATAGOARY_DETAILES_URL="http://demo.webuters.com/SongApi/singer.php?action_type=Catagorydetails&id=";
    String FULL_CATAGORY_URL;
   static ArrayList<Catagory_calass_bean> catagory_array_list = new ArrayList<Catagory_calass_bean>();

//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catagory_class_layout);

//        easyTracker = EasyTracker.getInstance(CatagoryClass.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(CatagoryClass.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }



        Log.e("Put Extara Catagory Calasss", "Catagory Class");

        cat_name_txt=(TextView)findViewById(R.id.cat_name_txt);
        System.out.println("message++++++++++++++++++++++" + "Catagory calss");

        Intent intent = getIntent();
        if (null != intent) {
            idUrl = intent.getStringExtra("ID");
            cat_name=intent.getStringExtra("CAT_Name");
            cat_name_txt.setText(cat_name);
           Log.e("Catagury id by put extras here++++++++++++++++++++++", idUrl);
            FULL_CATAGORY_URL= Holder.CATAGOARY_DETAILES_URL+idUrl;
           Log.e("FULL_CATAGORY_URL Catagury url recived  here++++++++++++++++++++++" , FULL_CATAGORY_URL);

        }

        new Catagory_datailes_Asyntask().execute();
    }

    private class Catagory_datailes_Asyntask extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... params) {
            String CATAGORY_DETAILES = HttpULRConnect.getData(FULL_CATAGORY_URL);

            System.out.println("Response recived>>>>>>>>>>>>>>>>>>>>>>>+++++Tushatr++" + CATAGORY_DETAILES);
           // Log.e("Tushar your App Catagory  Response is ---->", String.valueOf(CATAGORY_DETAILES));
            return CATAGORY_DETAILES;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CatagoryClass.this);
            pDialog.setMessage("Loading profile ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected void onPostExecute(String s) {
            catagory_array_list.clear();
            pDialog.dismiss();
            try {
                pDialog.dismiss();
                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray ar = new JSONArray(s);
                System.out.println("Array response is ---" + ar);
                Log.e("Array response ids---", String.valueOf(ar));
                for (int i = 0; i < ar.length(); i++)
                {

                    System.out.println("Array response is ---" + ar.length());
                    JSONObject jsonobject = ar.getJSONObject(i);

                    Catagory_calass_bean catagory_bean = new Catagory_calass_bean();

                    albumName = jsonobject.getString("AlbumName");
                    catagory_bean.setAlbumName(albumName);
                    Log.e("albumName  As it Is Here=======================>>>>>", albumName);

                    albumID=jsonobject.getString("id");
                    catagory_bean.setId(albumID);
                    Log.e("albumID is As it Is ....",albumID);

                    albumCover = jsonobject.getString("AlbumCover");
                    catagory_bean.setAlbumCover(albumCover);
                    Log.e("albumCover As it Is Here=======================>>>>>", albumCover);

                    catID = jsonobject.getString("CategoryId");
                    catagory_bean.setCategoryId(catID);
                    Log.e("catID As it Is Here=======================>>>>>", catID);


                    catagory_array_list.add(catagory_bean);
                    Log.e("catagory array is -----+", String.valueOf(catagory_array_list));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ///  See_all_catagory_Adapter catagoryAdapter = new See_all_catagory_Adapter(See_all_catagory.this, R.layout.see_all_catagory, Demo_kumsuni_catagory_array_list);

            CatagoryAdapter catagoryAdapter = new CatagoryAdapter(CatagoryClass.this, R.layout.catagory_class_layout, catagory_array_list);

            Log.e("Adapter Set here ----->>>", "Adapter set ");

            list_catagory=(ListView)findViewById(R.id.list_catagory);
            list_catagory.setAdapter(catagoryAdapter);

            list_catagory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  String songid = catagory_array_list.get(position).getId();
                    String SongImage = catagory_array_list.get(position).getAlbumCover();
                    Log.e("catagory list item clicked", "item clicked");
                    Intent i = new Intent(CatagoryClass.this, AlbumeSongs.class);
                    // String SongURL=songPath;
                    // String SongImage=SongImg;
                    Log.e("Bulr media player call here --------- >>", "Blur media player");
                    i.putExtra("SongImage123", SongImage);
                    i.putExtra("Album_id",songid);
                 //   i.putExtra("Sengaer_song_path", SongURL);
                    startActivity(i);

                    Log.e("Item Click -----tushar", "Clicked ");
//
                }
            });

        }

    }
//    @Override
//    public void onBackPressed() {
//       CatagoryMediaPlayer.mediaPlayer.stop();
//    }


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