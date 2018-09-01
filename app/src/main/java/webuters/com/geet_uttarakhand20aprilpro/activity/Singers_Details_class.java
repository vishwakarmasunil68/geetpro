package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.adapter.SingerAdapter;
import webuters.com.geet_uttarakhand20aprilpro.bean.SingerDEtaileBean;
import webuters.com.geet_uttarakhand20aprilpro.bean.SingerlistBean;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;


/**
 * Created by prakash on 6/6/2016.
 */
public class Singers_Details_class extends Activity{

    // int currentSongIndex;
    HorizontalScrollView horizontalScrollView;
    ListView list_latest;
    ProgressDialog pDialog;
    ImageView imae_song;
    JSONArray user = null;
    String Full_Name = "";
    TextView next;
    MediaPlayer mediaPlayer;
    ImageView image_baner;
    ImageView image_singer_list_header;
    ImageView background_image;
    String Image_Singer;
    String full_image;
    TextView text_view;
    // ImageView song_latest_item_image;
    // String Latest_Songs_Url = "http://webuters.com/SongApi/singer.php?action_type=Latest";
    String SINGERID, AlbumName, AlbumId, AlbumCover;
    public String FULL_SINGER_DETAILES_URL;
    String FULL_NAME;
    ImageView circleView;
    String Image12;
    ImageView image_share;
    //MediaPlayer mMediaPlayer;
    LinearLayout linear_media_player;
    static ArrayList<SingerDEtaileBean> singer_detailes_array = new ArrayList<SingerDEtaileBean>();
    static ArrayList<SingerlistBean> array_SingerlistBean = new ArrayList<SingerlistBean>();
    FrameLayout blur_frame;
    LinearLayout linear_media_player1;
    LinearLayout linear_latest;
    LinearLayout blur_linear;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private ConnectionDetector cd;

    String full_name;

//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latest_songs_layout);

//        easyTracker = EasyTracker.getInstance(Singers_Details_class.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(Singers_Details_class.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }

        blur_frame = (FrameLayout) findViewById(R.id.blur_frame);
        blur_linear = (LinearLayout) findViewById(R.id.linear_player);
        linear_latest = (LinearLayout) findViewById(R.id.linear_latest);
        // linear_media_player1=(LinearLayout)findViewById(R.id.linear_media_player1);
        text_view = (TextView) findViewById(R.id.text_view);
        // image_share=(ImageView)findViewById(R.id.image_share);

        background_image = (ImageView) findViewById(R.id.background_image);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.hindi);
        // Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
//         image_singer_list_header=(ImageView)findViewById(R.id.circleView);
//        image_singer_list_header.setImageBitmap(circularBitmap);
        // circleView=(ImageView)findViewById(R.id.circleView);
//        circleView.setImageBitmap(circularBitmap);
        array_SingerlistBean = new ArrayList<SingerlistBean>();
        Intent intent = getIntent();

        if (null != intent) {
            SINGERID = intent.getExtras().getString("Singer_id_put");
//            Toast.makeText(getApplicationContext(),SINGERID,Toast.LENGTH_LONG).show();
            System.out.println("Tushar your singer id Recived====>>" + SINGERID);
            Image12 = intent.getExtras().getString("ImageIs");
            full_image = intent.getExtras().getString("full");
            System.out.println("Image recived in singer detailes is ---------------- " + Image12);
            // System.out.println("Image is ----" + Image);
            // Image_Singer=intent.getExtras().getString("ImageIs");
            System.out.println("Image url is ][][][][][][][][][][][]------ ---- >>" + Image_Singer);
            //    Picasso.with(Singers_Details_class.this).load(Image12).into(circleView);
            Picasso.with(Singers_Details_class.this).load(Image12).into(background_image);
            text_view.setText(full_image);
            FULL_SINGER_DETAILES_URL = Holder.SINGERS_DETAILES + SINGERID;
            System.out.println("FULL_SINGER_DETAILES_URL Recived====>>" + FULL_SINGER_DETAILES_URL);

        }
        list_latest = (ListView) findViewById(R.id.list_latest);
        // Intent intent = new Intent(this, BlurMediaPlayer.class);
        singer_detailes_array.clear();
        cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(Singers_Details_class.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//            finish();
        } else {
            new SINGER_DETAILES_ASYTASk().execute();
        }
    }


    private class SINGER_DETAILES_ASYTASk extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Singers_Details_class.this);
            pDialog.setMessage("Loading .........");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpULRConnect.getData(FULL_SINGER_DETAILES_URL);
            Log.e("Latest Songs Response ---->", String.valueOf(content));
            if (String.valueOf(content) == "") {

                Toast.makeText(Singers_Details_class.this, "No DAta From Server Side ", Toast.LENGTH_SHORT).show();
            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            pDialog.dismiss();
            try {

                try {

                    Log.e("Post Method Call  here ....", "Method ...");
                    JSONArray ar = new JSONArray(s);
                    System.out.println("Array response is ---" + ar);
                    Log.e("Array response ids---", String.valueOf(ar));
                    for (int i = 0; i < ar.length(); i++) {
                        System.out.println("Array response is ---" + ar.length());

                        JSONObject jsonobject = ar.getJSONObject(i);

                        SingerDEtaileBean singerDEtaileBean = new SingerDEtaileBean();
                        AlbumId = jsonobject.getString("Id");
                        singerDEtaileBean.setId(AlbumId);
                        Log.e("AlbumId IS -- ::: -------------------- >>>", AlbumId);
                        AlbumName = jsonobject.getString("AlbumName");
                        singerDEtaileBean.setAlbumName(AlbumName);
                        Log.e("AlbumName As it Is Here=======================>>>>>", AlbumName);
                        AlbumCover = jsonobject.getString("AlbumCover");
                        singerDEtaileBean.setAlbumCover(AlbumCover);
                        Log.e("AlbumCover As it Is Here=======================>>>>>", AlbumCover);

                        singer_detailes_array.add(singerDEtaileBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            SingerAdapter adapter = new SingerAdapter(Singers_Details_class.this, R.layout.latest_songs_layout, singer_detailes_array);
            Log.e("Singer Adapter Set here ----->>>", "Adapter set ");
            list_latest.setAdapter(adapter);
            list_latest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    String Singer_Song_path = singer_detailes_array.get(position).getId();
                    String SongImage = singer_detailes_array.get(position).getAlbumCover();
                    String SongName_is = singer_detailes_array.get(position).getAlbumName();
                    int songIndex = position;

//                    Intent i = new Intent(Singers_Details_class.this, AlbumeSongs.class);
//                    i.putExtra("SongImage123", SongImage);
//
//                    i.putExtra("Album_id", Singer_Song_path);
//                    System.out.println("SONG position  IS   ::::: ------------------ :: _" + String.valueOf(songIndex));
//                    i.putExtra("album_name", AlbumName);
//                    startActivity(i);

                    Intent i = new Intent(Singers_Details_class.this, NewAlbumSongsActivity.class);
                    i.putExtra("Album_id", Singer_Song_path);
                    i.putExtra("SongImage123", SongImage);
                    i.putExtra("album_name", SongName_is);
                    startActivity(i);
                }
            });

        }
    }


}


