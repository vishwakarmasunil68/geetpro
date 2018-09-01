package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Tushar on 3/1/2016.
 */
public class ContactUs extends Activity {
    //TextView txt_EventName;
    ListView listview;
    public static TextView text_songname;

    private File file;

    public static ArrayList<String> myList = new ArrayList<>();
    public static ArrayList<String> filepathlst = new ArrayList<>();
    public static ArrayList<String> song_image_list = new ArrayList<>();

    public static MediaPlayer mediaPlayer;
    public static int pos;
    public static boolean music_flag = true;


    public static ImageView imgplay, imgpause;

    SeekBar seekBar;

    TextView remaingTxt, Totaltxt;


    ImageView next;

    public static boolean reset_player = false;

    private ConnectionDetector cd;

    public static String song_path="";

    public static Handler myHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_layout);


        listview = (ListView) findViewById(R.id.mymusic_songlist);
        text_songname = (TextView) findViewById(R.id.text_songname);
        imgplay = (ImageView) findViewById(R.id.img_playmusic);
        imgpause = (ImageView) findViewById(R.id.img_pausemusic);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        remaingTxt = (TextView) findViewById(R.id.remaining_songtime_left_text1);
        Totaltxt = (TextView) findViewById(R.id.total_song_time_text1);
        seekBar.setClickable(false);

        next = (ImageView) findViewById(R.id.btnNext);
        cd = new ConnectionDetector(getApplicationContext());

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                seekChange(v);
                return false;
            }
        });
        try {
            myList = new ArrayList<String>();
            String root_sd = Environment.getExternalStorageDirectory().toString();
            //file = new File(root_sd + "/Download");
            file = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "files");
            file.mkdirs();
            File list[] = file.listFiles();

            for (int i = 0; i < list.length; i++) {
                myList.add(list[i].getName());
                filepathlst.add(list[i].toString());
                Log.d("sun", filepathlst.toString());

            }
            File file1 = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "images");
            file1.mkdirs();
            File list1[] = file1.listFiles();
            for(int i=0;i<list1.length;i++){
                song_image_list.add(list1[i].toString());

            }
            Log.d("sunil", song_image_list.toString());
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
        }

        ArrayAdapter<String> adapter = (new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myList));
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppConstant.setsavedmusicplaying(getApplicationContext(),false);
                AppConstant.setmainmusicplaying(getApplicationContext(),false);
                AppConstant.setfavmusicplaying(getApplicationContext(),false);
                pos=i;
//                song_path=filepathlst.get(i);
                Intent intent=new Intent(ContactUs.this,SavedSongsMusicPlayer.class);
                intent.putExtra("back",true);
                startActivity(intent);
//                startActivityForResult(intent,1);
            }
        });

    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if(resultCode == Activity.RESULT_OK){
//                String result=data.getStringExtra("result");
//            }
//            if (resultCode == Activity.RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
//        }
//    }
}