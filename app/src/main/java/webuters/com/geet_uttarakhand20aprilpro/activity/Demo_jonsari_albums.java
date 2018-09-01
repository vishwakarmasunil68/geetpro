package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.adapter.CatagoryAdapter;
import webuters.com.geet_uttarakhand20aprilpro.bean.Catagory_calass_bean;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Tushar on 4/8/2016.
 */
public class Demo_jonsari_albums extends AppCompatActivity {
    ProgressDialog pDialog;
    ListView list_catagory;
    CatagoryAdapter catagoryAdapter;
    MediaPlayer mp;
    String cat_name;
    TextView cat_name_txt;
    String albumName,albumID,albumCover,singerId,firstName,catID;
    String idUrl;

    EditText etSearchh;
    Button btnSearchh;
    Boolean boolSearch = false;
    static ArrayList<Catagory_calass_bean> demo_jonsari_catagory_array_list = new ArrayList<Catagory_calass_bean>();
//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catagory_class_layout);


        cat_name_txt=(TextView)findViewById(R.id.cat_name_txt);
        System.out.println("message++++++++++++++++++++++" + "Catagory calss");

//        Intent intent = getIntent();
//        if (null != intent) {
//            idUrl = intent.getStringExtra("ID");
//            cat_name=intent.getStringExtra("CAT_Name");
//            cat_name_txt.setText(cat_name);
//            Log.e("Catagury id by put extras here++++++++++++++++++++++" , idUrl);
//            FULL_CATAGORY_URL=Holder.CATAGOARY_DETAILES_URL+idUrl;
//            Log.e("FULL_CATAGORY_URL Catagury url recived  here++++++++++++++++++++++" , FULL_CATAGORY_URL);
//
//        }


        etSearchh = (EditText) findViewById(R.id.etSearchh);
        btnSearchh = (Button) findViewById(R.id.btnSearchh);

        final InputMethodManager immo = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        etSearchh.setRawInputType(InputType.TYPE_CLASS_TEXT);
        etSearchh.setTextIsSelectable(true);
        btnSearchh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolSearch){
                    cat_name_txt.setVisibility(View.GONE);
                    etSearchh.setVisibility(View.VISIBLE);
                    boolSearch = true;
                    etSearchh.requestFocus();
                    immo.showSoftInput(etSearchh, InputMethodManager.SHOW_IMPLICIT);
                }else{
                    cat_name_txt.setVisibility(View.VISIBLE);
                    etSearchh.setVisibility(View.GONE);
                    //etSearchh.setText("");
                    boolSearch = false;
                    immo.hideSoftInputFromWindow(etSearchh.getWindowToken(), 0);
                }


            }
        });

        etSearchh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //singer_list_adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String filtered = etSearchh.getText().toString().toLowerCase(Locale.getDefault());
                catagoryAdapter.filter(filtered);
            }
        });

        etSearchh.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus)
                {
                    v.setFocusableInTouchMode(true);
                    v.requestFocus();
                    etSearchh.setHint("Search");
                }
                else
                    etSearchh.setHint("Type here");
            }
        });



        new Catagory_datailes_Asyntask().execute();
    }

    private class Catagory_datailes_Asyntask extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... params) {

//            String CATAGORY_DETAILES = HttpULRConnect.getData("http://demo.webuters.com/SongApi/singer.php?action_type=Catagorydetails&id=3");
            String CATAGORY_DETAILES = HttpULRConnect.getData("http://23.22.9.33/SongApi/singer.php?action_type=categoryDetails&id=3&slimit=0&elimit=100");
            System.out.println("Response recived>>>>>>>>>>>>>>>>>>>>>>>+++++Tushatr++" + CATAGORY_DETAILES);
             Log.e("your", String.valueOf(CATAGORY_DETAILES));
            return CATAGORY_DETAILES;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Demo_jonsari_albums.this);
            pDialog.setMessage("Loading profile ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected void onPostExecute(String s) {
            demo_jonsari_catagory_array_list.clear();
            pDialog.dismiss();
            try {
                pDialog.dismiss();
                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray ar = new JSONArray(s);
                System.out.println("Array response is ---" + ar);
                Log.e("Array response ids---", String.valueOf(ar));
                for (int i = 0; i < ar.length(); i++) {

                    System.out.println("Array response is ---" + ar.length());
                    JSONObject jsonobject = ar.getJSONObject(i);

                    Catagory_calass_bean catagory_bean = new Catagory_calass_bean();
                    albumID = jsonobject.getString("id");
                    catagory_bean.setId(albumID);
                    Log.e("albumID is As it Is ....", albumID);
                    albumName = jsonobject.getString("AlbumName");
                    catagory_bean.setAlbumName(albumName);
                    Log.e("albumName  As it Is Here=======================>>>>>", albumName);



                    albumCover = jsonobject.getString("AlbumCover");
                    catagory_bean.setAlbumCover(albumCover);
                    Log.e("albumCover As it Is Here=======================>>>>>", albumCover);

                    catID = jsonobject.getString("CategoryId");
                    catagory_bean.setCategoryId(catID);
                    Log.e("catID As it Is Here=======================>>>>>", catID);


                    demo_jonsari_catagory_array_list.add(catagory_bean);
                    Log.e("catagory array is -----+", String.valueOf(demo_jonsari_catagory_array_list));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            /// http://demo.webuters.com/SongApi/singer.php?action_type=Albumdetails&id=


            ///  See_all_catagory_Adapter catagoryAdapter = new See_all_catagory_Adapter(See_all_catagory.this, R.layout.see_all_catagory, Demo_kumsuni_catagory_array_list);

             catagoryAdapter = new CatagoryAdapter(Demo_jonsari_albums.this, R.layout.catagory_class_layout, demo_jonsari_catagory_array_list);

            Log.e("Adapter Set here ----->>>", "Adapter set ");

            list_catagory = (ListView) findViewById(R.id.list_catagory);
            list_catagory.setAdapter(catagoryAdapter);

            list_catagory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String albumID12 = demo_jonsari_catagory_array_list.get(position).getId();
                    String AlbumImage = demo_jonsari_catagory_array_list.get(position).getAlbumCover();
                    String album_name = demo_jonsari_catagory_array_list.get(position).getAlbumName();
                    Log.e("catagory list item clicked", "item clicked");
                    Intent i = new Intent(Demo_jonsari_albums.this, AlbumeSongs.class);

                    i.putExtra("SongImage123", AlbumImage);
                    i.putExtra("album_name", album_name);
                    i.putExtra("Album_id", albumID12);
                    //   i.putExtra("Sengaer_song_path", SongURL);
                    startActivity(i);

                    Log.e("Item Click -----tushar", "Clicked ");
//
                }
            });

        }
    }

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
//    @Override
//    public void onBackPressed() {
//       CatagoryMediaPlayer.mediaPlayer.stop();
//    }
