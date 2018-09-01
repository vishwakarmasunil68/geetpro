package webuters.com.geet_uttarakhand20aprilpro.connection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.activity.MainActivity;


/**
 * Created by Admin on 2/11/2016.
 */
public class NetworkCheckClass extends Activity {
TextView text_connection_title;
    Button bt_retry_connection;
    Animation animRotate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_connection_layout);
        bt_retry_connection=(Button)findViewById(R.id.bt_retry_connection);
        bt_retry_connection.getBackground().setAlpha(0);
        bt_retry_connection.setBackgroundColor(Color.TRANSPARENT);
         animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

//        text_connection_title.setText("Connecting...");
        bt_retry_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animRotate);
                checkInternetConenction();
            }
        });
        }

    private boolean checkInternetConenction() {

        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Intent i=new Intent(NetworkCheckClass.this,MainActivity.class);
            startActivity(i);
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
//            Intent i = new Intent(this,NetworkCheckClass.class);
//            startActivity(i);
//            text_connection_title.setText("Network Issue");
            return false;
        }
        return false;
    }
}
