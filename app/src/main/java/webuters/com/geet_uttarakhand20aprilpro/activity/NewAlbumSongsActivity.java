package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;
import webuters.com.geet_uttarakhand20aprilpro.pojo.AlbumSongPOJO;
import webuters.com.geet_uttarakhand20aprilpro.pojo.AlbumSongResultPOJO;
import webuters.com.geet_uttarakhand20aprilpro.util.IabBroadcastReceiver;
import webuters.com.geet_uttarakhand20aprilpro.util.IabHelper;
import webuters.com.geet_uttarakhand20aprilpro.util.IabResult;
import webuters.com.geet_uttarakhand20aprilpro.util.Inventory;
import webuters.com.geet_uttarakhand20aprilpro.util.Purchase;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by tushar on 2/9/2016.
 */
public class NewAlbumSongsActivity extends Activity implements View.OnClickListener {

    private RecyclerView rv_songs_list;
    private TextView tv_album_name;
    public static ImageView iv_album_cover;
    Button share_arraysong;

    ConnectionDetector cd;

    String album_url = "";
    String album_id = "";
    String album_name = "";
    String album_cover_image = "";

    //    Bitmap b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_album_songs);
        rv_songs_list = findViewById(R.id.rv_songs_list);
        iv_album_cover = findViewById(R.id.iv_album_cover);
        share_arraysong = findViewById(R.id.share_arraysong);

        cd = new ConnectionDetector(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            album_id = bundle.getString("Album_id");
            album_cover_image = bundle.getString("SongImage123");
//            album_name = bundle.getString("album_name");
            Log.d(TagUtils.getTag(), "album id:-" + album_id);
            Log.d(TagUtils.getTag(), "album song image cover:-" + album_cover_image);
            Glide.with(NewAlbumSongsActivity.this)
                    .load(album_cover_image)
                    .into(iv_album_cover);

            album_url = Holder.ALBUM_DETAILES + album_id;
            Log.d(TagUtils.getTag(), "album url:-" + album_url);
            if (!cd.isConnectingToInternet()) {
                Toast.makeText(NewAlbumSongsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                new AlbumAsyTask().execute();
            }

        }


        share_arraysong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TagUtils.getTag(), "Album_id:-" + album_id);
                String image_url = album_cover_image.replace("http://23.22.9.33/Admin/GeetApp/includes/uploads/", "");
                Log.d(TagUtils.getTag(), "SongImage123:-" + image_url);
                String link = "http://www.geetuttrakahandpro.com/album_id=" + album_id + "/image=" + image_url;
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, link);
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);


                saveAndShare(album_cover_image, link);


            }
        });

        base64EncodedPublicKey = getResources().getString(R.string.google_licence_key);

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TagUtils.getTag(), "Problem setting up in-app billing: " + result);
                    return;
                }

                // Hooray, IAB is fully set up. Now, let's get an inventory of
                // stuff we own.
                Log.d(TagUtils.getTag(), "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });


//        bp = new BillingProcessor(this, getResources().getString(R.string.google_licence_key), this);


    }

    public void saveAndShare(final String image_url, final String link) {
        new AsyncTask<String, Void, String>() {
            String filepath = "";
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(NewAlbumSongsActivity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(image_url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);
                    urlConnection.connect();
                    File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
                    String filename = "downloadedFile.png";
                    Log.i("Local filename:", "" + filename);
                    File file = new File(SDCardRoot, filename);
                    if (file.createNewFile()) {
                        file.createNewFile();
                    }
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    InputStream inputStream = urlConnection.getInputStream();
                    int totalSize = urlConnection.getContentLength();
                    int downloadedSize = 0;
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        fileOutput.write(buffer, 0, bufferLength);
                        downloadedSize += bufferLength;
                        Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
                    }
                    fileOutput.close();
                    if (downloadedSize == totalSize) filepath = file.getPath();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    filepath = null;
                    e.printStackTrace();
                }
                Log.i("filepath:", " " + filepath);
                return filepath;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Log.d(TagUtils.getTag(), "file path:-" + s);

                if (new File(s).exists()) {
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.putExtra(Intent.EXTRA_TEXT, link);
//
//                    Uri uri = Uri.parse(s);
//
//                    intent.putExtra(Intent.EXTRA_STREAM, uri);
//                    intent.setType("image/*");
//                    startActivity(Intent.createChooser(intent, "Share image via..."));


                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");

                    File imageFileToShare = new File(s);

//                    Uri uri = Uri.fromFile(imageFileToShare);
//                    share.putExtra(Intent.EXTRA_STREAM, uri);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileProvider", imageFileToShare);
                        share.putExtra(Intent.EXTRA_STREAM, contentUri);
                    } else {
                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFileToShare));
                    }
                    share.putExtra(Intent.EXTRA_TEXT, link);

                    startActivity(Intent.createChooser(share, "Share Image!"));
                }
            }
        }.execute();
    }


    @Override
    public void onClick(View v) {

    }

    String save_song_path = "";
    String save_song_name = "";

    public void yourDesiredMethod(String song_path, String song_name) {
        save_song_path = song_path;
        this.save_song_name = song_name;
//        bp.purchase(NewAlbumSongsActivity.this, SONG_SKU);
        saveSong(save_song_path, save_song_name);
//        new NotificationTask(getApplicationContext(),"Downloading", (int) System.currentTimeMillis()).execute();
//        try {
//            mHelper.launchPurchaseFlow(NewAlbumSongsActivity.this, ITEM_SKU, 10001,
//                    mPurchaseFinishedListener, "mypurchasetoken");
//        } catch (IabHelper.IabAsyncInProgressException e) {
//            e.printStackTrace();
//        }
    }

    //    private static final String TESTING_PRODUCT_ID = "android.test.purchased";
    private static final String SONG_SKU = "song_10";
//    BillingProcessor bp;
//
//    @Override
//    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
//        bp.consumePurchase(SONG_SKU);
//        saveSong(save_song_path, save_song_name);
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
////        error.printStackTrace();
//    }
//
//    @Override
//    public void onBillingInitialized() {
//        Log.d(TagUtils.getTag(), "billing initialized");
//    }


    private class AlbumAsyTask extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        List<AlbumSongResultPOJO> albumSongResultPOJOS;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            albumSongResultPOJOS = new ArrayList<>();
            //Log.e("Loading.......","loading");
            pDialog = new ProgressDialog(NewAlbumSongsActivity.this);
            pDialog.setMessage("Loading .........");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TagUtils.getTag(), "album url:-" + album_url);
            String content = HttpULRConnect.getData(album_url);
            if (String.valueOf(content) == null) {
                Toast.makeText(NewAlbumSongsActivity.this, "Connection is Breaking ", Toast.LENGTH_SHORT).show();
            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TagUtils.getTag(), "album songs pojo:-" + s);
            try {
                JSONObject obj = new JSONObject(s);
                JSONArray ar = obj.getJSONArray("Songs");

                for (int i = 0; i < ar.length(); i++) {
//                    System.out.println("Array response is ---" + ar.length());
                    Gson gson = new Gson();
                    AlbumSongResultPOJO albumSongResultPOJO = gson.fromJson(ar.optJSONObject(i).toString(), AlbumSongResultPOJO.class);
                    Log.d(TagUtils.getTag(), "album song:-" + albumSongResultPOJO.toString());
                    albumSongResultPOJOS.add(albumSongResultPOJO);
                }
                Log.d(TagUtils.getTag(), "album songs:-" + albumSongResultPOJOS.toString());
                AlbumSongPOJO albumSongPOJO = new AlbumSongPOJO();
                albumSongPOJO.setAlbumSongResultPOJOS(albumSongResultPOJOS);
                NewAlbumAdapter newAlbumAdapter = new NewAlbumAdapter(NewAlbumSongsActivity.this, null, albumSongPOJO, album_cover_image);
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(NewAlbumSongsActivity.this, LinearLayoutManager.VERTICAL, false);
                rv_songs_list.setHasFixedSize(true);
                rv_songs_list.setAdapter(newAlbumAdapter);
                rv_songs_list.setLayoutManager(layoutManager);
                rv_songs_list.setItemAnimator(new DefaultItemAnimator());

            } catch (Exception e) {
                e.printStackTrace();
            }

//            AlbumAdapter adapter = new AlbumAdapter(NewAlbumSongsActivity.this, R.layout.album_songs_layout, AlBUM_LIST_ARRAY);

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


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    public void SendEmail(String email_id, final String song_url) {
//        new AsyncTask<Void, Void, Void>() {
//            ProgressDialog dialog;
//
//            StringBuilder response = new StringBuilder();
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
////                dialog  = ProgressDialog.show(AlbumeSongs.this, "", "Loading...", true);
////                dialog.setCancelable(false);
////                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////                dialog.show();
//            }
//
//            @Override
//            protected Void doInBackground(Void... params1) {
//                try {
//                    URL url = new URL("http://23.22.9.33/SongApi/donloadmail.php");
//                    Map<String, Object> params = new LinkedHashMap<>();
////					params.put("method", "doLoginStandard");
//                    params.put("singer_email", album_email_id);
//                    params.put("song_path", song_url);
//
//                    StringBuilder postData = new StringBuilder();
//                    for (Map.Entry<String, Object> param : params.entrySet()) {
//                        if (postData.length() != 0) postData.append('&');
//                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//                        postData.append('=');
//                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//                    }
//                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
//
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("POST");
//                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
//                    conn.setDoOutput(true);
//                    conn.getOutputStream().write(postDataBytes);
//
//                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                    String s = "";
//                    while ((s = in.readLine()) != null) {
//                        response.append(s);
//                    }
//                    Log.d("msg", response.toString());
////                       System.out.print((char) c);
//                } catch (Exception e) {
//                    if (dialog != null) {
//                        dialog.dismiss();
//                    }
//                    Log.d("msg", e.toString());
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
////                if(dialog!=null) {
////                    dialog.dismiss();
////                }
//                Log.d(TagUtils.getTag(), response.toString());
//
//
//            }
//        }.execute();
//    }

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
                Log.d(TagUtils.getTag(), response.toString());


            }
        }.execute();
    }

    File file;

    public void saveSong(final String song_url, final String song_name) {
//        SendEmail(singer_email_id, song_url);
//        SendSongPath(song_url);
        int requestID = (int) System.currentTimeMillis();
        Intent notificationIntent = new Intent(getApplicationContext(), SavedSongsMusicPlayer.class);
        notificationIntent.putExtra("song", true);

//            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent contentIntent = PendingIntent.getActivity(NewAlbumSongsActivity.this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        new AsyncTask<Void, Integer, Void>() {
            // ProgressDialog mProgressDialog;
            boolean bol = false;
            int id = 1;
            NotificationManager mNotifyManager;
            NotificationCompat.Builder mBuilder;
            String channelId = "geetProID";
            String channelName = "Geet Pro";
            int previous_progress = 0;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mNotifyManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    mNotifyManager.createNotificationChannel(mChannel);
                }
                mBuilder = new NotificationCompat.Builder(NewAlbumSongsActivity.this);
                mBuilder.setContentTitle("Downloading " + song_name)
                        .setContentText("Download in progress")
                        .setSmallIcon(R.mipmap.finallogo)
                        .setContentIntent(contentIntent)
                        .setChannelId(channelId);
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
                        if (len1 != 0) {
                            fos.write(buffer, 0, len1);
                            sentData += len1;
                            int progress = (int) ((sentData / (float) lenghtOfFile) * 100);
                            Log.d(TagUtils.getTag(), "download progress:-" + progress);

                            if (previous_progress != progress) {
                                publishProgress(progress);
                                previous_progress = progress;
                            } else {

                            }
//                            if(progress%1==0) {
//                                publishProgress((int)progress);
//                            }

                        } else {
                            Log.d(TagUtils.getTag(), "download progress111:-" + 100);
                            publishProgress(100);
                        }
                    }

                    fos.close();
                    is.close();

                    publishProgress(100);
                    Log.d(TagUtils.getTag(), "download progress222:-" + 100);
                    Log.d(TagUtils.getTag(), "saved");


                } catch (IOException e) {
                    e.printStackTrace();
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
                        ImageView img = new ImageView(NewAlbumSongsActivity.this);

                        Picasso.with(NewAlbumSongsActivity.this)
                                .load(album_cover_image)
                                .into(img);
                        String root_sd = Environment.getExternalStorageDirectory().toString();
                        file = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "images");
                        file.mkdirs();
                        File outputFile = new File(file.toString() + File.separator + AlbumAdapter.S1 + ".jpg");
                        SaveImage(outputFile.toString(), ((BitmapDrawable) img.getDrawable()).getBitmap());
                        refreshList();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TagUtils.getTag(), e.toString());
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
            Log.d(TagUtils.getTag(), ContactUs.song_image_list.toString());
        } catch (Exception e) {
            Log.d(TagUtils.getTag(), e.toString());
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

//    private Bitmap getBitmapFromURL(final String imageUrl) {
//        final Bitmap[] bmp = new Bitmap[1];
//        new AsyncTask<Void, Void, Void>() {
//            Bitmap bmp1;
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    URL url = new URL(imageUrl);
//                    HttpURLConnection connection = (HttpURLConnection) url
//                            .openConnection();
//                    connection.setDoInput(true);
//                    connection.connect();
//                    InputStream input = connection.getInputStream();
//
//                    // Bitmap myBitmap = BitmapFactory.decodeStream(input);
//                    DisplayMetrics metrics = getResources().getDisplayMetrics();
//                    int width = metrics.widthPixels;
//                    int height = metrics.heightPixels;
//                    b = Utils.decodeSampledBitmapFromStream(input, width, height);
//
//                    Log.d(TagUtils.getTag(), b.toString());
////            UserObject.setFullBitmap(animalfacebitmap);
//                    return null;
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
////                bmp1=b;
//                bmp[0] = b;
//            }
//        }.execute();
//        return bmp[0];
//    }


    @Override
    public void onPause() {
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    Button btn_download;
    String base64EncodedPublicKey;
    IabHelper mHelper;
    IabBroadcastReceiver mBroadcastReceiver;
    //    private final String ITEM_SKU = "android.test.purchased";
//    private final String ITEM_SKU = "song_10";
    private final String ITEM_SKU = "songten";
    static final int RC_REQUEST = 10001;


    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            Log.d(TagUtils.getTag(), "Query inventory finished.");
            if (result.isFailure()) {
                Log.d(TagUtils.getTag(), "Failed to query inventory: " + result);
                return;
            }

            Log.d(TagUtils.getTag(), "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // // Check for gas delivery -- if we own gas, we should fill up the
            // tank immediately
            Purchase gasPurchase = inventory.getPurchase(ITEM_SKU);
            if (gasPurchase != null) {
                Log.d(TagUtils.getTag(), "We have gas. Consuming it.");
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                            mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            Log.d(TagUtils.getTag(), "Purchase finished: " + result + ", purchase: "
                    + purchase);
            if (result.isFailure()) {
                Log.d(TagUtils.getTag(), "Error purchasing: " + result);
                return;
            }
//            if (!verifyDeveloperPayload(purchase)) {
//                complain("Error purchasing. Authenticity verification failed.");
//                return;
//            }

            Log.d(TagUtils.getTag(), "Purchase successful.");

            if (purchase.getSku().equals(ITEM_SKU)) {

                // remove query inventory method from here and put consumeAsync() directly
                try {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }

            }


        }
    };

    public void consumeItem() {
        try {
            mHelper.queryInventoryAsync(mReceivedInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {


            if (result.isFailure()) {
                // Handle failure
                Log.d(TagUtils.getTag(), "error in consuming product");
            } else {
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                            mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {

                        //final listener
                        Log.d(TagUtils.getTag(), "product consumed");
                        saveSong(save_song_path, save_song_name);
                    } else {
                        // handle error
                        Log.d(TagUtils.getTag(), "product consumed failed");
                    }
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }

}






