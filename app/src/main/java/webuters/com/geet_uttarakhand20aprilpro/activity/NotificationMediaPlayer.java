package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;

public class NotificationMediaPlayer extends AppCompatActivity {
    ListView listview;
    File file;
    public static List<String> myList=new ArrayList<>();
    public static List<String> filepathlst=new ArrayList<>();
    NotificationPanel nPanel;
    public static MediaPlayer mediaPlayer;
    public static int pos;
    public static boolean music_flag=true;

//    private EasyTracker easyTracker = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_media_player);

//        easyTracker = EasyTracker.getInstance(NotificationMediaPlayer.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(NotificationMediaPlayer.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }


        listview = (ListView)findViewById(R.id.mymusic_songlist);


        file = new File( Environment.getExternalStorageDirectory().toString() + File.separator+"Android"+File.separator+"data"+File.separator+"webuters.com.geet_uttarakhand20aprilpro"+File.separator+"files") ;
        file.mkdir();
        File list[] = file.listFiles();

        for (int i = 0; i < list.length; i++) {
            myList.add(list[i].getName());
            filepathlst.add(list[i].toString());
            Log.d("sun", filepathlst.toString());

        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,myList);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(nPanel!=null){
                    nPanel.notificationCancel();
                }
                nPanel = new NotificationPanel(NotificationMediaPlayer.this);
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                }
                try {
                    pos=position;
                    music_flag=false;
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(filepathlst.get(position));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
                catch (Exception e){
                    Log.d(TagUtils.getTag(),e.toString());
                }
            }
        });
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
