package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TimingLogger;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.Pref;
import webuters.com.geet_uttarakhand20aprilpro.Utils.StringUtils;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;
import webuters.com.geet_uttarakhand20aprilpro.adapter.AlbumRecyclerAdapter;
import webuters.com.geet_uttarakhand20aprilpro.adapter.CatagoryAdapter;
import webuters.com.geet_uttarakhand20aprilpro.adapter.LatestSongAdapter;
import webuters.com.geet_uttarakhand20aprilpro.adapter.ViewPagerAdapter;
import webuters.com.geet_uttarakhand20aprilpro.bean.AlbumListBean;
import webuters.com.geet_uttarakhand20aprilpro.bean.Catagory_calass_bean;
import webuters.com.geet_uttarakhand20aprilpro.bean.CategoryGetSetUrl;
import webuters.com.geet_uttarakhand20aprilpro.connection.NetworkCheckClass;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;
import webuters.com.geet_uttarakhand20aprilpro.pojo.AlbumPOJO;
import webuters.com.geet_uttarakhand20aprilpro.pojo.LatestSongPOJO;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


public class MainActivity extends Activity{

    RecyclerView rv_garwali, rv_kumauni, rv_jaunsari, rv_singers;
    Timer timer;
    int page = 0;
    List<AlbumListBean> main_album_list = new ArrayList<>();
    public static Set<String> listItem = new HashSet<>();
    public ProgressDialog pDialog;
    public static TextView catagory_see_all, see_all_singers, see_all_album;
    static ArrayList<CategoryGetSetUrl> catagory_array_list1 = new ArrayList<CategoryGetSetUrl>();
    String CatagoryName, CatId;


    String Singer_First_Name, Singer_Last_Name, id, Latest_Image_Id, Latest_Song_Name, Detailes_caragory;
    ImageView toolbar_main;
    TextView toolbar_title, latest_song_title_main, title_singer, garwali_title, title_jonsari, title_kumaoni, garwali_see_all;
    boolean flag = false;
    LinearLayout linear_full;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<Catagory_calass_bean> itemInfoList = new ArrayList<>();
    LinearLayout linear;
    LinearLayout media_player_play;
    ImageView back_arrow;
    private ConnectionDetector cd;
    public static String CATAGOARY_URL = "http://23.22.9.33/SongApi/singer.php?action_type=Catagory";
    Boolean v = false;
    String currentVersion = "";
    private static final int REQUEST_WRITE_STORAGE = 112;
    EditText edit_searchmain;
    Button btn_searchmain;
    Boolean boolSearch = false;
    ListView list_search;
    String FULL_NAME;

    String album_id_not = "";
    ArrayList<Catagory_calass_bean> add_allthree_arraylist = new ArrayList<Catagory_calass_bean>();
    CatagoryAdapter catagoryAdapter;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    public static boolean img_flag;

    public static boolean blured_mediaplaying = false;
    boolean nav_flag = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
//                showRewardedVideo();
            }
        }.start();

        rv_garwali = findViewById(R.id.rv_garwali);
        rv_kumauni = findViewById(R.id.rv_kumauni);
        rv_jaunsari = findViewById(R.id.rv_jaunsari);
        rv_singers = findViewById(R.id.rv_singers);

        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-18871205-2");
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("backup")) {
                Intent intent = new Intent(MainActivity.this, SampleActivity.class);
                intent.putExtra("backup", true);
                startActivity(intent);
            }
        }
        if (extras != null) {
            if (extras.getBoolean("backup1")) {
                Intent intent = new Intent(MainActivity.this, SampleActivity.class);
                intent.putExtra("backup1", true);
                startActivity(intent);
            }
        }
        if (extras != null) {
            if (extras.getBoolean("backup2")) {
                Intent intent = new Intent(MainActivity.this, AlbumeSongs.class);
                intent.putExtra("Album_id", extras.getString("album_back_id"));
                intent.putExtra("SongImage123", extras.getString("album_back_cover"));
                startActivity(intent);
            }
        }
        Log.d("notification", "running");
        if (extras != null) {
            String message = extras.getString("message");
            String title = extras.getString("title");
            String albumid = extras.getString("album_id");
            String albumname = extras.getString("album_name");

            Log.d("notification", "message:-" + message);
            Log.d("notification", "albumid:-" + albumid);
            Log.d("notification", "albumname:-" + albumname);
            Log.d("notification", "title:-" + title);
//            Toast.makeText(getApplicationContext(), "album_id:-" + albumid, Toast.LENGTH_LONG).show();

        }
        SharedPreferences sp = getSharedPreferences("geet.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String album_id = sp.getString("album_id", "");
        Log.d("notification", "album_id:-" + album_id);
        editor.putString("album_id", "");
        editor.commit();
        if (album_id != null || album_id.length() > 0) {
            album_id_not = album_id;
            if (nav_flag) {
                for (AlbumListBean album : main_album_list) {
                    if (album.getId().equals(album_id_not)) {
                        Intent i = new Intent(MainActivity.this, AlbumeSongs.class);
                        // String Singer_Song_path = catagory_array_listArray.get(3).getAlbumCover();
                        String SongImg = album.getAlbumCover();
                        String AlbumId = album.getId();
                        i.putExtra("Album_id", AlbumId);
                        i.putExtra("SongImage123", SongImg);
                        nav_flag = false;
                        startActivity(i);
                    }
                }
            }
        }


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            //When the broadcast received
            //We are sending the broadcast from GCMRegistrationIntentService

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {

                    String token = intent.getStringExtra("token");
                    Log.d("sunil", "Token:-" + token);
                    if (!AppConstant.getNotification(getApplicationContext())) {

                    }
//                    sendApiToken(token);
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (ConnectionResult.SUCCESS != resultCode) {

            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }
        } else {
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }

        try {
            boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_STORAGE);
            }
        } catch (Exception e) {

        }


        try {
            currentVersion = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        cd = new ConnectionDetector(getApplicationContext());

        edit_searchmain = (EditText) findViewById(R.id.edit_searchmain);
        btn_searchmain = (Button) findViewById(R.id.btn_searchmain);

        garwali_title = (TextView) findViewById(R.id.garwali_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        back_arrow.setVisibility(View.GONE);
        toolbar_main = (ImageView) findViewById(R.id.toolbar_main);
        toolbar_main.setVisibility(View.VISIBLE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        title_singer = (TextView) findViewById(R.id.title_singer);
        linear = (LinearLayout) findViewById(R.id.linear);

        //  searchArraylist = new ArrayList<String>();
        // savesearcharraylist = new ArrayList<>(searchArraylist);

        edit_searchmain = (EditText) findViewById(R.id.edit_searchmain);
        btn_searchmain = (Button) findViewById(R.id.btn_searchmain);

        list_search = (ListView) findViewById(R.id.list_search);

        catagoryAdapter = new CatagoryAdapter(MainActivity.this, R.layout.activity_main, add_allthree_arraylist);
        list_search.setAdapter(catagoryAdapter);


        final InputMethodManager imma = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        edit_searchmain.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edit_searchmain.setTextIsSelectable(true);

        btn_searchmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!boolSearch) {
                    boolSearch = true;
                    edit_searchmain.setVisibility(View.VISIBLE);
                    toolbar_main.setVisibility(View.GONE);
                    toolbar_title.setVisibility(View.GONE);
                    //list_search.setVisibility(View.VISIBLE);
                    edit_searchmain.requestFocus();
                    imma.showSoftInput(edit_searchmain, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    edit_searchmain.setText("");
                    boolSearch = false;
                    edit_searchmain.setVisibility(View.GONE);
                    toolbar_main.setVisibility(View.VISIBLE);
                    toolbar_title.setVisibility(View.VISIBLE);
                    list_search.setVisibility(View.GONE);
                    linear_full.setVisibility(View.VISIBLE);
                    imma.hideSoftInputFromWindow(edit_searchmain.getWindowToken(), 0);
                }

            }
        });

        edit_searchmain.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                list_search.setVisibility(View.VISIBLE);
                linear_full.setVisibility(View.GONE);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void afterTextChanged(Editable s) {
                String filtered = edit_searchmain.getText().toString().toLowerCase(Locale.getDefault());
                catagoryAdapter.filter(filtered);

            }
        });


/*VIEW PAGER CODE HERE AS MENTION */

        linear_full = (LinearLayout) findViewById(R.id.linear_full);
        media_player_play = (LinearLayout) findViewById(R.id.media_player_play);


        scrollMethod();
        pageSwitcher(3);
        String cat_name;
        catagory_see_all = (TextView) findViewById(R.id.catagory_see_all);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        see_all_singers = (TextView) findViewById(R.id.see_all_singers);
        see_all_album = (TextView) findViewById(R.id.see_all_album);
        toolbar_main = (ImageView) findViewById(R.id.toolbar_main);

        garwali_see_all = (TextView) findViewById(R.id.garwali_see_all);
        garwali_title = (TextView) findViewById(R.id.garwali_title);

        title_kumaoni = (TextView) findViewById(R.id.title_kumaoni);
        title_kumaoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(MainActivity.this, Demo_kumanuni_albums.class);
                startActivity(ii);
            }
        });

        title_jonsari = (TextView) findViewById(R.id.title_jonsari);
        title_jonsari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Demo_jonsari_albums.class);
                startActivity(i);
            }
        });


        latest_song_title_main = (TextView) findViewById(R.id.latest_song_title_main);


        garwali_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Garwali_Album_intent = new Intent(MainActivity.this, Demo_garwali_albums.class);
                startActivity(Garwali_Album_intent);
            }
        });
        title_singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Singer_See_all.class);
                startActivity(i);

            }
        });

//
        // Step1 : create the  RotateAnimation object
        RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        // Step 2:  Set the Animation properties
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);


        toolbar_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SampleActivity.class);
                startActivity(i);
            }
        });

        see_all_singers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("See all button clicked.." + "click tus");
                Intent i = new Intent(MainActivity.this, Singer_See_all.class);
                startActivity(i);
            }
        });


        if (!cd.isConnectingToInternet()) {
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//            finish();
        } else {
            checkPrefsongs();
            new GetVersionCode().execute();
            new Garwali_cat_Asyntask().execute();
            if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_SINGER_LIST, "").length() > 0) {
                new Singer_List_AsynTask(false).execute();
            } else {
                new Singer_List_AsynTask(true).execute();
            }

            if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_TASK, "").length() > 0) {
                new FetchingTask(false).execute();
            } else {
                new FetchingTask(true).execute();
            }
            new CatagoryUrlAsynTask().execute();
            new Kumauni_cat_Asyntask().execute();
            new junsari_cat_Asyntask().execute();
        }


        try {
            Intent in = getIntent();
            Uri data = in.getData();

            Log.d(TagUtils.getTag(), "data to get:-" + data.toString());
//            String url = "http://www.geetuttrakahand.com/album_id=133/image=singer_25_387893574.png";
            String url = data.toString();

            url = url.replace("http://www.geetuttrakahandpro.com/", "");
            String[] split = url.split("/");
            String a_id = split[0].split("=")[1];
            String img_name = split[1].split("=")[1];

            Intent i = new Intent(this, NewAlbumSongsActivity.class);
            i.putExtra("Album_id", a_id);
            i.putExtra("SongImage123", "http://23.22.9.33/Admin/GeetApp/includes/uploads/" + img_name);
            startActivity(i);

            Log.d(TagUtils.getTag(), "album_id:-" + a_id + ",image:-" + img_name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        garwali_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Garwali_Album_intent = new Intent(MainActivity.this, Demo_garwali_albums.class);
                startActivity(Garwali_Album_intent);
            }
        });

        catagory_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Demo_kumanuni_albums.class);
                startActivity(i);
            }
        });

        see_all_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Demo_jonsari_albums.class);
                startActivity(i);
            }
        });
    }

    public void checkPrefsongs() {

        attachGarwaliAdapter();
        attachKumauniAdapter();
        attachJaunsariAdapter();
        attachSingerList();

        if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_TASK, "").length() > 0) {
            parseBannerImages(Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_TASK, ""));
        }

        if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_GARWALI_SONGS, "").length() > 0) {
            parseGarwaliSongs(Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_GARWALI_SONGS, ""));
        }

        Log.d(TagUtils.getTag(), "singer list:-" + Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_SINGER_LIST, ""));

        if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_SINGER_LIST, "").length() > 0) {
            parseSingerList(Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_SINGER_LIST, ""));
        }
//
        if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_CATEGORY_DATA, "").length() > 0) {
            parseCategoryResponse(Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_CATEGORY_DATA, ""));
        }
//
        if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_KAMUANI_SONGS, "").length() > 0) {
            parseKumauniResponse(Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_KAMUANI_SONGS, ""));
        }
//
        if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_JAUNSARI_SONGS, "").length() > 0) {
            parseJaunsariResponse(Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_JAUNSARI_SONGS, ""));
        }

    }

    private void showInterstitial() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
    }

    private final String TAG = "notification";

    public void sendApiToken(final String token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://23.22.9.33/Admin/GeetApp/getapi/notification1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("sunil","response:-"+response);
//                        try {
//                            JSONObject jsonObject=new JSONObject(response);
//                            String success=jsonObject.optString("success");
//                            if(success.equals("true")){
////                                JSONObject result=""
//                                Toast.makeText(getApplicationContext(),"Gcm Registration Success",Toast.LENGTH_SHORT).show();
//                                AppConstant.setNotification(getApplicationContext(),true);
//                                //Log.d("sunil","success");
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(),"Gcm Registration Failed",Toast.LENGTH_SHORT).show();
//                                //Log.d("sunil","failed");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("sunil", "" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sp = getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<String, String>();
                params.put("apikey", "AIzaSyDnLuEw-_lUKETi-W0l425a-208yr6Zr2I");
                params.put("regtoken", token);
                params.put("message", "hey this is sunil");


                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
        super.onDestroy();
    }

    private long value(String string) {
        string = string.trim();
        if (string.contains(".")) {
            final int index = string.lastIndexOf(".");
            return value(string.substring(0, index)) * 100 + value(string.substring(index + 1));
        } else {
            return Long.valueOf(string);
        }
    }

//    private void showInterstitial(){
//        if(mInterstitialAd.isLoaded()){
//            mInterstitialAd.show();
//        }
//    }

    private void scrollMethod() {

    }

    private boolean checkInternetConenction() {


        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            //  Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
//            Intent i = new Intent(MainActivity.this, MainActivity.class);
////        oursong.stop();
//            startActivity(i);
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Intent i = new Intent(MainActivity.this, NetworkCheckClass.class);
            // oursong.stop();
            startActivity(i);
            // Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }


    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... params) {
            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    //show dialog
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("Update Available");
                    alertDialog.setMessage("Do you want to update it?");
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    });
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
//                                mInterstitialAd.loadAd(adRequest);
                        }
                    });
                    alertDialog.show();
                    //Log.d("update","yess");
                } else {
//                        mInterstitialAd.loadAd(adRequest);
                }
            }
            //Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
//            Toast.makeText(getApplicationContext(),"Current version " + currentVersion + "playstore version " + onlineVersion,Toast.LENGTH_LONG).show();
        }
    }


    public void pageSwitcher(int seconds) {

        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1500); // delay
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.menu_main, menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Log.e("item click for event", "hii tushar");
            // checkInternetConenction();
            Intent i = new Intent(getApplicationContext(), SampleActivity.class);

            i.putExtra(SampleActivity.ARG_LAYOUT, R.layout.sample);
            startActivity(i);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private class RemindTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 10) {
                        // In my case the number of pages are 5
                        timer = new Timer();
                    } else {
                        viewPager.setCurrentItem(page++, true);
                    }
                }
            });

        }
    }


    private class Garwali_cat_Asyntask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            String url = "http://23.22.9.33/SongApi/singer.php?action_type=Catagorydetails&id=2";
            String content = HttpULRConnect.getData(url);

            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TagUtils.getTag(), "garwali reponse:-" + s);
            if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_GARWALI_SONGS, "").length() == 0) {
                parseGarwaliSongs(s);
            }
            Pref.SetStringPref(getApplicationContext(), StringUtils.FETCH_GARWALI_SONGS, s);
        }

    }

    AlbumRecyclerAdapter garwaliAlbumAdapter;
    List<AlbumPOJO> garwaliAlbumPOJOS = new ArrayList<>();

    public void parseGarwaliSongs(final String s) {
        final TimingLogger timings = new TimingLogger(TagUtils.getTag(), "garwali song split");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                garwaliAlbumPOJOS.clear();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    JSONArray ar = new JSONArray(s);
                    int length = ar.length();
                    if (ar.length() > 4) {

                        for (int i = length - 1; i > length - 5; i--) {
                            Gson gson = new Gson();
                            AlbumPOJO albumPOJO = gson.fromJson(ar.optJSONObject(i).toString(), AlbumPOJO.class);
                            garwaliAlbumPOJOS.add(albumPOJO);
                        }
                    }


                    timings.addSplit("parsing");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                garwaliAlbumAdapter.notifyDataSetChanged();
                timings.addSplit("finished");
                timings.dumpToLog();
            }
        }.execute();
    }

    public void attachGarwaliAdapter() {
        garwaliAlbumAdapter = new AlbumRecyclerAdapter(this, null, garwaliAlbumPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        rv_garwali.setHasFixedSize(true);
        rv_garwali.setAdapter(garwaliAlbumAdapter);
        rv_garwali.setLayoutManager(layoutManager);
        rv_garwali.setItemAnimator(new DefaultItemAnimator());
    }


    private class FetchingTask extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog;
        boolean isDialog;

        public FetchingTask(boolean isDialog) {
            this.isDialog = isDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isDialog) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(true);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL("http://23.22.9.33/SongApi/singer.php?action_type=Latest");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder lineRead = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        lineRead.append(line);
                    }

                    String responseString = lineRead.toString();

                    return responseString;

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_TASK, "").length() == 0){
                parseBannerImages(s);
            }

            Pref.SetStringPref(getApplicationContext(), StringUtils.FETCH_TASK, s);


        }
    }

    public void parseBannerImages(final String s) {

        if (!s.isEmpty()) {
            new AsyncTask<Void, Void, Void>() {
                List<Catagory_calass_bean> itemInfos = new ArrayList<>();

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        JSONArray jsonObject = new JSONArray(s);

                        int length = jsonObject.length();
                        if (length > 5) {
                            length = 5;
                        }
                        for (int i = 0; i < length; i++) {
                            Catagory_calass_bean itemInfo = new Catagory_calass_bean();
                            JSONObject jsObject = jsonObject.getJSONObject(i);
                            itemInfo.setId(jsObject.getString("id"));
                            Latest_Image_Id = jsObject.getString("id");
                            itemInfo.setAlbumCover(jsObject.getString("AlbumCover"));
                            itemInfo.setAlbumName(jsObject.getString("AlbumName"));
                            Latest_Song_Name = jsObject.getString("AlbumName");
                            itemInfos.add(itemInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    itemInfoList.clear();
                    itemInfoList.addAll(itemInfos);
                    adapter = new ViewPagerAdapter(MainActivity.this, itemInfoList);
                    viewPager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }.execute();
        }

    }


    private class Kumauni_cat_Asyntask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = "http://23.22.9.33/SongApi/singer.php?action_type=Catagorydetails&id=1";
            String content = HttpULRConnect.getData(url);
            return content;
        }


        @Override
        protected void onPostExecute(String s) {
            if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_KAMUANI_SONGS, "").length() == 0) {
                parseKumauniResponse(s);
            }
            Pref.SetStringPref(getApplicationContext(), StringUtils.FETCH_KAMUANI_SONGS, s);
        }
    }

    AlbumRecyclerAdapter kumauniAlbumAdapter;
    List<AlbumPOJO> kumauniAlbumPOJOS = new ArrayList<>();

    public void parseKumauniResponse(final String s) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                kumauniAlbumPOJOS.clear();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    JSONArray ar = new JSONArray(s);
//                    JSONArray newJsonArray = new JSONArray();
//                    for (int i = ar.length()-1; i>=0; i--) {
//                        newJsonArray.put(ar.get(i));
//                    }
//                    System.out.println("Array response is ---" + newJsonArray);
//                    //Log.e("Array response ids---", String.valueOf(ar));
//                    int length = newJsonArray.length();
//                    if (newJsonArray.length() > 4) {
//                        length = 4;
//                    }

                    for (int i = ar.length() - 1; i > ar.length() - 5; i--) {
                        Gson gson = new Gson();
                        AlbumPOJO albumPOJO = gson.fromJson(ar.optJSONObject(i).toString(), AlbumPOJO.class);
                        kumauniAlbumPOJOS.add(albumPOJO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                kumauniAlbumAdapter.notifyDataSetChanged();
            }
        }.execute();


    }

    public void attachKumauniAdapter() {
        kumauniAlbumAdapter = new AlbumRecyclerAdapter(this, null, kumauniAlbumPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        rv_kumauni.setHasFixedSize(true);
        rv_kumauni.setAdapter(kumauniAlbumAdapter);
        rv_kumauni.setLayoutManager(layoutManager);
        rv_kumauni.setItemAnimator(new DefaultItemAnimator());
    }

    private class junsari_cat_Asyntask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = "http://23.22.9.33/SongApi/singer.php?action_type=Catagorydetails&id=3";
            String content = HttpULRConnect.getData(url);
            return content;
        }


        @Override
        protected void onPostExecute(String s) {
            if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_JAUNSARI_SONGS, "").length() == 0) {
                parseJaunsariResponse(s);
            }
            Pref.SetStringPref(getApplicationContext(), StringUtils.FETCH_JAUNSARI_SONGS, s);

        }
    }


    AlbumRecyclerAdapter jaunsariAlbumAdapter;
    List<AlbumPOJO> jaunsariAlbumPOJOS = new ArrayList<>();

    public void parseJaunsariResponse(final String s) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                jaunsariAlbumPOJOS.clear();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    JSONArray ar = new JSONArray(s);
//                    JSONArray newJsonArray = new JSONArray();
//                    for (int i = ar.length()-1; i>=0; i--) {
//                        newJsonArray.put(ar.get(i));
//                    }
//                    System.out.println("Array response is ---" + newJsonArray);
                    //Log.e("Array response ids---", String.valueOf(ar));
                    int length = ar.length();
                    if (ar.length() > 4) {
                        length = 4;
                    }

                    for (int i = length - 1; i > length - 5; i--) {
                        Gson gson = new Gson();
                        AlbumPOJO albumPOJO = gson.fromJson(ar.optJSONObject(i).toString(), AlbumPOJO.class);
                        jaunsariAlbumPOJOS.add(albumPOJO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                jaunsariAlbumAdapter.notifyDataSetChanged();
            }
        }.execute();


    }

    public void attachJaunsariAdapter() {
        jaunsariAlbumAdapter = new AlbumRecyclerAdapter(this, null, jaunsariAlbumPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        rv_jaunsari.setHasFixedSize(true);
        rv_jaunsari.setAdapter(jaunsariAlbumAdapter);
        rv_jaunsari.setLayoutManager(layoutManager);
        rv_jaunsari.setItemAnimator(new DefaultItemAnimator());
    }

    private class Singer_List_AsynTask extends AsyncTask<String, String, String> {

        boolean is_dialog;

        public Singer_List_AsynTask(boolean is_dialog) {
            this.is_dialog = is_dialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (is_dialog) {
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading .........");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpULRConnect.getData(Holder.SINGERS_LIST_URL);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                if (pDialog != null) {
                    pDialog.dismiss();
                }
            } catch (Exception e) {
            }
            if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_SINGER_LIST, "").length() == 0) {
                parseSingerList(s);
            }

            Pref.SetStringPref(getApplicationContext(), StringUtils.FETCH_SINGER_LIST, s);

        }
    }

    LatestSongAdapter latestSongAdapter;
    List<LatestSongPOJO> latestSongPOJOS = new ArrayList<>();

    public void parseSingerList(final String s) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                jaunsariAlbumPOJOS.clear();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    JSONArray ar = new JSONArray(s);
//                    JSONArray newJsonArray = new JSONArray();
//                    for (int i = ar.length()-1; i>=0; i--) {
//                        newJsonArray.put(ar.get(i));
//                    }
//                    System.out.println("Array response is ---" + newJsonArray);
                    //Log.e("Array response ids---", String.valueOf(ar));
                    int length = ar.length();
                    if (ar.length() > 4) {
                        length = 4;
                    }

                    for (int i = 0; i < length; i++) {
                        Gson gson = new Gson();
                        LatestSongPOJO latestSongPOJO = gson.fromJson(ar.optJSONObject(i).toString(), LatestSongPOJO.class);
                        latestSongPOJOS.add(latestSongPOJO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                latestSongAdapter.notifyDataSetChanged();
                getAlbums();
            }
        }.execute();

    }

    public void attachSingerList() {
        latestSongAdapter = new LatestSongAdapter(this, null, latestSongPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        rv_singers.setHasFixedSize(true);
        rv_singers.setAdapter(latestSongAdapter);
        rv_singers.setLayoutManager(layoutManager);
        rv_singers.setItemAnimator(new DefaultItemAnimator());
    }


    public void getAlbums() {
        for (Catagory_calass_bean s : add_allthree_arraylist) {
            ////Log.d("sun", s.getAlbumName());
            ////Log.d("sun", s.getAlbumCover());
            catagoryAdapter = new CatagoryAdapter(MainActivity.this, R.layout.catagory_class_layout, add_allthree_arraylist);
            list_search.setAdapter(catagoryAdapter);

            list_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                   /* String albumID12 = add_allthree_arraylist.get(position).getId();
                    String AlbumImage = add_allthree_arraylist.get(position).getAlbumCover();
                    String album_name= add_allthree_arraylist.get(position).getAlbumName();
                    Intent i = new Intent(MainActivity.this, AlbumeSongs.class);
                    i.putExtra("SongImage123", AlbumImage);
                    i.putExtra("album_name", album_name);
                    i.putExtra("Album_id", albumID12);

                    startActivity(i);
*/

                    String SingerId = add_allthree_arraylist.get(position).getId();
                    String singerImage = add_allthree_arraylist.get(position).getImage();
                    int songIndex = position;
                    FULL_NAME = Singer_First_Name + Singer_Last_Name;

                    if (singerImage != null) {
                        Intent i1 = new Intent(getApplicationContext(), Singers_Details_class.class);
                        i1.putExtra("songIndex", songIndex);
                        i1.putExtra("ImageIs", singerImage);
                        i1.putExtra("Singer_id_put", SingerId);
                        i1.putExtra("FULL_Singer__NAME", FULL_NAME);

                        startActivity(i1);
                    } else {
                        String albumID12 = add_allthree_arraylist.get(position).getId();
                        String AlbumImage = add_allthree_arraylist.get(position).getAlbumCover();
                        String album_name = add_allthree_arraylist.get(position).getAlbumName();
                        Intent i = new Intent(MainActivity.this, AlbumeSongs.class);
                        i.putExtra("SongImage123", AlbumImage);
                        i.putExtra("album_name", album_name);
                        i.putExtra("Album_id", albumID12);

                        startActivity(i);
                    }


                }
            });
        }
    }


    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
            String content = HttpULRConnect.getData(CATAGOARY_URL);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            if (Pref.GetStringPref(getApplicationContext(), StringUtils.FETCH_CATEGORY_DATA, "").length() == 0) {
                parseCategoryResponse(s);
            }
            Pref.SetStringPref(getApplicationContext(), StringUtils.FETCH_CATEGORY_DATA, s);
        }
    }


    public void parseCategoryResponse(String s) {
        try {
            JSONArray ar = new JSONArray(s);
            System.out.println("Array response is ---" + ar);
            for (int i = 0; i < ar.length(); i++) {
                System.out.println("Array response is ---" + ar.length());
                JSONObject jsonobject = ar.getJSONObject(ar.length() - 1 - i);

                CategoryGetSetUrl catagory_bean = new CategoryGetSetUrl();

                CatId = jsonobject.getString("id");
                catagory_bean.setId(CatId);

                System.out.println("Full URL is As iT is ---" + Detailes_caragory);
                CatagoryName = jsonobject.getString("CatagoryName");
                catagory_bean.setCatagoryName(CatagoryName);

                latest_song_title_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, See_all_catagory.class);
                        i.putExtra("ID", CatId);
                        i.putExtra("NAME", CatagoryName);
                        startActivity(i);
                    }
                });
                catagory_array_list1.add(catagory_bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {

        if (flag) {
            flag = false;
            new AlertDialog.Builder(this)
                    //.setTitle("")
                    .setCancelable(false)
                    .setMessage("Are you Sure you want to Exit?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();

        } else {
            flag = true;
            edit_searchmain.setVisibility(View.GONE);
            toolbar_main.setVisibility(View.VISIBLE);
            toolbar_title.setVisibility(View.VISIBLE);
            list_search.setVisibility(View.GONE);
            linear_full.setVisibility(View.VISIBLE);

        }
    }
    //  this.moveTaskToBack(true);


    @Override
    public void onPause() {
        super.onPause();
        if ((pDialog != null) && pDialog.isShowing())
            pDialog.dismiss();
        pDialog = null;
        Intent intent = new Intent(getApplicationContext(), BlurMediaPlayer.class);
        startService(intent);
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    int count = 0;

}






