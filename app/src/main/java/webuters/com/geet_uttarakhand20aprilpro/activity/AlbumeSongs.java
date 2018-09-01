package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;
import webuters.com.geet_uttarakhand20aprilpro.bean.AlbumSongsBean;
import webuters.com.geet_uttarakhand20aprilpro.bean.SongListBean;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by tushar on 2/9/2016.
 */
public class AlbumeSongs extends Activity implements View.OnClickListener, activitylistener{

    JSONArray user = null;
    private MediaPlayer mediaPlayer;
    ListView list_me;
    ImageView album_songs_cover;
    public static ProgressDialog pdd;
    String songp, AlbumCover, AlbumName, FirstName, SingerId, SongImg, SongName;
    public ListView album_songs_list;
    TextView title;
    //ImageView Album_image_baner;
    public static ImageView Album_image_baner;
    static ArrayList<AlbumSongsBean> AlBUM_LIST_ARRAY;

    public String FULL_URL_IS;
    public static String Album_ID;
    ProgressDialog pDialog;
    String ALBUM_DETAILE;
    String album_name1;
    String Albumid, Description, CategoryId, CreatedDate;
    String newString;
    static AlbumSongsBean albumSongsBean;
    private ConnectionDetector cd;
    static SongListBean songListBean = new SongListBean();
    Button downloadallsongs, share_arraysong;

    Boolean value1, value2;

    /*  private static String SERVICE_ID = "be0cab5709b03f596020db7c0ddc72c8";
      private static String APP_SECRET = "57004ade292ba870e9e4cb3a2fd1b126";*/
    private static String SERVICE_ID = "b3daee1913f41beca5e3185edb939349";
    private static String APP_SECRET = "54bc71ede855e69e9cf40d2ee1ea0f27";

    private File file;
    private List<String> myList;
    public static String Album_Cover_Image;
    Uri myUri;
    Bitmap b;
    public static File traceFile;
    private ProgressDialog mProgressDialog;
    public static String album_url;
    public static String singer_email_id = "";
    public static String album_email_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_songs_layout);

        cd = new ConnectionDetector(getApplicationContext());
        pdd = new ProgressDialog(this);
        pdd.setMessage("Loding Song...");

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(AlbumeSongs.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            new AlbumAsyTask().execute();
        }

        downloadallsongs = (Button) findViewById(R.id.downloadallsongs);
        share_arraysong = (Button) findViewById(R.id.share_arraysong);

        AlBUM_LIST_ARRAY = new ArrayList<AlbumSongsBean>();
        album_songs_list = (ListView) findViewById(R.id.album_songs_list);
        // title=(TextView)findViewById(R.id.album_songs_cover);
        Album_image_baner = (ImageView) findViewById(R.id.Album_image_baner);
        Intent intent = getIntent();
        if (null != intent) {
            Album_ID = intent.getStringExtra("Album_id");
//            album_name1=intent.getStringExtra("album_name");
            Album_Cover_Image = intent.getStringExtra("SongImage123");

            Log.d("sunil", "Album id:-" + Album_ID);
            getBitmapFromURL(Album_Cover_Image);
            Log.d("mukesh", Album_Cover_Image);
            System.out.println("Album_Cover_Image is AS IT iS ==========  >>>" + Album_Cover_Image);
            Picasso.with(AlbumeSongs.this)
                    .load(Album_Cover_Image)
                    .into(Album_image_baner);
            System.out.println("Recive Album id in Detailes Activity As it IS ----->>" + Album_ID);
            FULL_URL_IS = Holder.ALBUM_DETAILES + Album_ID;

        }

        downloadallsongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value2 = true;
                value1 = false;
            }
        });

        share_arraysong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TagUtils.getTag(), "Album_id:-" + Album_ID);
                String image_url = Album_Cover_Image.replace("http://23.22.9.33/Admin/GeetApp/includes/uploads/", "");
                Log.d(TagUtils.getTag(), "SongImage123:-" + image_url);
                String link = "http://www.geetuttrakahand.com/album_id=" + Album_ID + "/image=" + image_url;
                String arrays = songp;
                final String seprated = arrays.replace("/", "");

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
                Uri imageUri = Uri.parse(path);
//                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.putExtra(Intent.EXTRA_TEXT, link);
                Log.d(TagUtils.getTag(), songp);
                startActivity(Intent.createChooser(share, "Select"));

            }
        });

//        bp = new BillingProcessor(this, getResources().getString(R.string.google_licence_key), this);


    }

    @Override
    public void onClick(View v) {

    }

    String save_song_path = "";

    @Override
    public void yourDesiredMethod(String song_path) {
        save_song_path = song_path;
//        bp.purchase(AlbumeSongs.this, SONG_SKU);
    }

//    private static final String TESTING_PRODUCT_ID = "android.test.purchased";
//    private static final String SONG_SKU = "song_003";
//    BillingProcessor bp;
//
//    @Override
//    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
//        bp.consumePurchase(SONG_SKU);
//        saveSong(save_song_path, AlbumAdapter.S1 + ".mp3");
//    }
//
//    @Override
//    public void onPurchaseHistoryRestored() {
//
//    }
//
//    @Override
//    public void onBillingError(int errorCode, @Nullable Throwable error) {
//        Log.d(TagUtils.getTag(), "billing errror:-" + errorCode);
//        error.printStackTrace();
//    }
//
//    @Override
//    public void onBillingInitialized() {
//        Log.d(TagUtils.getTag(), "billing initialized");
//    }
//

    private class AlbumAsyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Log.e("Loading.......","loading");
            pDialog = new ProgressDialog(AlbumeSongs.this);
            pDialog.setMessage("Loading .........");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            System.out.println("Garwali Album songs Full Url is do in background -----------" + FULL_URL_IS);
            Log.d("sun", "Details:-" + Holder.ALBUM_DETAILES + Album_ID);
            album_url = Holder.ALBUM_DETAILES + Album_ID;
            Log.d("sun", "Full url:-" + FULL_URL_IS);
            String content = HttpULRConnect.getData(Holder.ALBUM_DETAILES + Album_ID);
            //Log.e("Latest Songs Response", String.valueOf(content));
            if (String.valueOf(content) == null) {
                Toast.makeText(AlbumeSongs.this, "Connection is Breaking ", Toast.LENGTH_SHORT).show();
            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            AlBUM_LIST_ARRAY.clear();

            try {
                try {
                    JSONObject obj = new JSONObject(s);
                    //Log.e("Post Method Call", "Method ...");
                    Log.d(TagUtils.getTag(), obj.toString());
                    JSONArray ar = obj.getJSONArray("Album");
                    //  Log.d(TagUtils.getTag(),ar.toString());
                    // JSONArray ar = new JSONArray(s);
                    System.out.println("Array response is ---" + ar);
                    //Log.e("Album Array response ids", String.valueOf(ar));
                    for (int i = 0; i < ar.length(); i++) {
                        System.out.println("Array response is ---" + ar.length());
                        JSONObject jsonobject = ar.getJSONObject(i);
//                    //Log.e("Response in i ", String.valueOf(i));
                        albumSongsBean = new AlbumSongsBean();
                        AlbumName = jsonobject.getString("AlbumName");
                        album_email_id = jsonobject.getString("AlbumEmail");
                        albumSongsBean.setAlbumName(AlbumName);
                        //Log.e("Album NAme is =======================>>>>>", AlbumName);
                        AlbumCover = jsonobject.getString("AlbumCover");
                        albumSongsBean.setAlbumCover(AlbumCover);
//                        Picasso.with(AlbumeSongs.this)
//                                .load(AlbumCover)
//                                .into(Album_image_baner);
                    }

                    JSONArray ar1 = obj.getJSONArray("singers");
                    //Log.e("Singer Array response ids---", String.valueOf(ar1));
                    for (int i = 0; i < ar1.length(); i++) {
                        System.out.println("Array response is ---" + ar1.length());
                        JSONObject jsonobject = ar1.getJSONObject(i);

                        //Log.e("Response in i ", String.valueOf(i));
                        AlbumSongsBean albumSongsBean = new AlbumSongsBean();
                        FirstName = jsonobject.getString("FirstName");

                        albumSongsBean.setFirstName(FirstName);
                        //Log.e("FirstName NAme is =======================>>>>>", FirstName);

                        SingerId = jsonobject.getString("SingerId");

                        singer_email_id = jsonobject.getString("Email");
                        Log.d("sunil", "singer_email:-" + singer_email_id);
                        albumSongsBean.setSingerId(SingerId);
                        //Log.e("SingerId  is =======================>>>>>", SingerId);
                        //AlBUM_LIST_ARRAY.add(albumSongsBean);
                        //   //Log.e("First array it is ------ >>", String.valueOf(AlBUM_LIST_ARRAY));
                    }

                    JSONArray ar3 = obj.getJSONArray("Songs");
                    //Log.e("Songs Array response ids---", String.valueOf(ar3));
                    for (int i = 0; i < ar3.length(); i++) {
                        Log.d(TagUtils.getTag(), ar3.toString());
                        //Log.e("Response for case  i ", String.valueOf(i));
                        System.out.println("Array response is ---" + ar3.length());
                        JSONObject jsonobject = ar3.getJSONObject(i);

                        //Log.e("Response in i ", String.valueOf(i));
                        AlbumSongsBean albumSongsBean = new AlbumSongsBean();
                        songp = jsonobject.getString("SongPath");

                        albumSongsBean.setSongPath(songp);
                        //Log.e("SongPath is =======================>>>>>", songp);

//                    SingerId = jsonobject.getString("SingerId");
//
//                    albumSongsBean.setSingerId(SingerId);
//                    //Log.e("SingerId  is =======================>>>>>", SingerId);

                        SongName = jsonobject.getString("SongName");
//                    newString=SongName.replace("%"," ");

                        albumSongsBean.setSongName(SongName);

                        //Log.e("SongName  is =======================>>>>>", SongName);

//                    Picasso.with(AlbumeSongs.this)
//                            .load(SongImg)
//                            .into(album_songs_cover);
                        SongImg = jsonobject.getString("SongImg");
                        albumSongsBean.setSongImg(AlbumCover);
                        //Log.e("SongImg  is =======================>>>>>", SongImg);

                        AlBUM_LIST_ARRAY.add(albumSongsBean);
                        //Log.e("Album Array list data tushar  ----- ", String.valueOf(AlBUM_LIST_ARRAY));

                    }
//                AlBUM_LIST_ARRAY.add(albumSongsBean);
//                //Log.e("Album mmanmnsankska list Array data is ----- ", String.valueOf(AlBUM_LIST_ARRAY));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            AlbumAdapter adapter = new AlbumAdapter(AlbumeSongs.this, R.layout.album_songs_layout, AlBUM_LIST_ARRAY);


            //Log.e("Album Adapter Set here", "Adapter set ");
            album_songs_list.setAdapter(adapter);

            if (pdd != null) {
                pdd.dismiss();
            }
            if (pDialog != null) {
                pDialog.dismiss();
                Log.d("progress", "executed");
            }

        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);


//            songListBean.setBitmapsongnam(bitmap);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    private static final int REQUEST_CODE = 1234; // Can be anything


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }

    public void SendEmail(String email_id, final String song_url) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog dialog;

            StringBuilder response = new StringBuilder();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                dialog  = ProgressDialog.show(AlbumeSongs.this, "", "Loading...", true);
//                dialog.setCancelable(false);
//                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params1) {
                try {
                    URL url = new URL("http://23.22.9.33/SongApi/donloadmail.php");
                    Map<String, Object> params = new LinkedHashMap<>();
//					params.put("method", "doLoginStandard");
                    params.put("singer_email", album_email_id);
                    params.put("song_path", song_url);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, Object> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String s = "";
                    while ((s = in.readLine()) != null) {
                        response.append(s);
                    }
                    Log.d("msg", response.toString());
//                       System.out.print((char) c);
                } catch (Exception e) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    Log.d("msg", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                if(dialog!=null) {
//                    dialog.dismiss();
//                }
                Log.d("sunil", response.toString());


            }
        }.execute();
    }

    public void SendSongPath(final String song_url) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog dialog;

            StringBuilder response = new StringBuilder();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                dialog  = ProgressDialog.show(AlbumeSongs.this, "", "Loading...", true);
//                dialog.setCancelable(false);
//                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params1) {
                try {
                    URL url = new URL("http://23.22.9.33/SongApi/donloadmailreciver.php");
                    Map<String, Object> params = new LinkedHashMap<>();
//					params.put("method", "doLoginStandard");
                    params.put("song_path", song_url);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, Object> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String s = "";
                    while ((s = in.readLine()) != null) {
                        response.append(s);
                    }
                    Log.d("msg", response.toString());
//                       System.out.print((char) c);
                } catch (Exception e) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    Log.d("msg", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                if(dialog!=null) {
//                    dialog.dismiss();
//                }
                Log.d("sunil", response.toString());


            }
        }.execute();
    }

    public void saveSong(final String song_url, final String song_name) {
        SendEmail(singer_email_id, song_url);
        SendSongPath(song_url);

        int requestID = (int) System.currentTimeMillis();
        Intent notificationIntent = new Intent(getApplicationContext(), SavedSongsMusicPlayer.class);
        notificationIntent.putExtra("song", true);

//            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent contentIntent = PendingIntent.getActivity(AlbumeSongs.this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        new AsyncTask<Void, Integer, Void>() {
            // ProgressDialog mProgressDialog;
            boolean bol = false;
            int id = 1;
            NotificationManager mNotifyManager;
            NotificationCompat.Builder mBuilder;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mNotifyManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(AlbumeSongs.this);
                mBuilder.setContentTitle("Downloading " + song_name)
                        .setContentText("Download in progress")
                        .setSmallIcon(R.mipmap.finallogo)
                        .setContentIntent(contentIntent);
            }

            @Override
            protected Void doInBackground(Void... params) {
                int count;
                try {
                    URL url = new URL(song_url);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    Log.d(TagUtils.getTag(), "url:-" + url);
                    int lenghtOfFile = conexion.getContentLength();
                    Log.d(TagUtils.getTag(), "Lenght of file: " + lenghtOfFile);
                    String root_sd = Environment.getExternalStorageDirectory().toString();
                    file = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "files");
                    file.mkdir();
                    File outputFile = new File(file.toString() + File.separator + song_name);
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    InputStream is = conexion.getInputStream();

                    int sentData = 0;
                    byte[] buffer = new byte[4096];
                    int len1 = 0;
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);
                        sentData += len1;
                        int progress = (int) ((sentData / (float) lenghtOfFile) * 100);
                        publishProgress(progress);
                    }

                    fos.close();
                    is.close();

                    Log.d(TagUtils.getTag(), "saved");


                } catch (IOException e) {
                    Log.d(TagUtils.getTag(), "Error:-" + e.toString());
                    bol = true;
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                mBuilder.setProgress(100, values[0], false);
                mBuilder.setContentText("Downloading\t\t" + values[0] + "%");
                mNotifyManager.notify(id, mBuilder.build());
                // Sleeps the thread, simulating an operation
                // that takes time
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (bol) {
                    Toast.makeText(getApplicationContext(), "Downloading Failed", Toast.LENGTH_LONG).show();
                    mBuilder.setContentText("Download Failed")
                            // Removes the progress bar
                            .setProgress(0, 0, false);
                    mNotifyManager.notify(id, mBuilder.build());
                } else {
                    mBuilder.setContentText("Download complete")
                            // Removes the progress bar
                            .setProgress(0, 0, false);
                    mNotifyManager.notify(id, mBuilder.build());
                    try {
                        ImageView img = new ImageView(AlbumeSongs.this);

                        Picasso.with(AlbumeSongs.this)
                                .load(AlbumAdapter.song_image)
                                .into(img);
                        String root_sd = Environment.getExternalStorageDirectory().toString();
                        file = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "images");
                        File outputFile = new File(file.toString() + File.separator + AlbumAdapter.S1 + ".jpg");
                        SaveImage(outputFile.toString(), ((BitmapDrawable) img.getDrawable()).getBitmap());
                        refreshList();
                    } catch (Exception e) {
                        Log.d("sunil", e.toString());
                    }
                }

            }
        }.execute();
    }

    public void refreshList() {
        try {

            String root_sd = Environment.getExternalStorageDirectory().toString();
            ContactUs.filepathlst.clear();
            ContactUs.song_image_list.clear();
            //file = new File(root_sd + "/Download");
            File file = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "files");
            file.mkdirs();
            File list[] = file.listFiles();

            for (int i = 0; i < list.length; i++) {
                ContactUs.myList.add(list[i].getName());
                ContactUs.filepathlst.add(list[i].toString());
                Log.d("sun", ContactUs.filepathlst.toString());

            }
            File file1 = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "images");
            file1.mkdirs();
            File list1[] = file1.listFiles();
            for (int i = 0; i < list1.length; i++) {
                ContactUs.song_image_list.add(list1[i].toString());

            }
            Log.d("sunil", ContactUs.song_image_list.toString());
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }

    public void SaveImage(String filename, Bitmap bmp) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromURL(final String imageUrl) {
        final Bitmap[] bmp = new Bitmap[1];
        new AsyncTask<Void, Void, Void>() {
            Bitmap bmp1;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();

                    // Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    b = Utils.decodeSampledBitmapFromStream(input, width, height);

                    Log.d(TagUtils.getTag(), b.toString());
//            UserObject.setFullBitmap(animalfacebitmap);
                    return null;


                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                bmp1=b;
                bmp[0] = b;
            }
        }.execute();
        return bmp[0];
    }

    @Override
    public void onPause() {
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
        super.onPause();

        if ((pDialog != null) && pDialog.isShowing())
            pDialog.dismiss();
        pDialog = null;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (pdd != null) {
            pdd.dismiss();
        }
    }


}






