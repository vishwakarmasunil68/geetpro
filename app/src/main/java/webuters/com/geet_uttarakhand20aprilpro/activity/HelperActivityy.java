package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by emobi5 on 5/20/2016.
 */
public class HelperActivityy extends Activity {

    private HelperActivity ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        String action = (String) getIntent().getExtras().get("DO");
        if (action.equals("pause")) {
            //Your code
            Log.d("hell","pause button notification");
            if(BlurMediaPlayer.mediaPlayer!=null){
                if (BlurMediaPlayer.mediaPlayer.isPlaying())
                {
                    BlurMediaPlayer.mediaPlayer.pause();
                    BlurMediaPlayer.play_pause_button.setChecked(false);
                } else {
                    BlurMediaPlayer.mediaPlayer.start();
                    BlurMediaPlayer.play_pause_button.setChecked(true);
//
                }
            }
        } else if (action.equals("volume")) {
            //Your code
            Log.d("hell","okay");
            Toast.makeText(getApplicationContext(),"hhk",Toast.LENGTH_SHORT).show();
        } else if (action.equals("reboot")) {
            //Your code
            Log.d("hell","okay");
            Toast.makeText(getApplicationContext(),"hhk",Toast.LENGTH_SHORT).show();
        } else if (action.equals("top")) {
            //Your code

            Log.d("hell","okay");
            Toast.makeText(getApplicationContext(),"hhk",Toast.LENGTH_SHORT).show();
        } else if (action.equals("app")) {
            //Your code
            Log.d("hell","okay");
            Toast.makeText(getApplicationContext(),"hhk",Toast.LENGTH_SHORT).show();
        }

//        if (!action.equals("reboot"))
//            finish();
    }

}
