package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import webuters.com.geet_uttarakhand20aprilpro.R;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;

/**
 * Created by Tushar on 4/3/2016.
 */

public class AboutUs extends Activity {
        TextView geet;
    TextView rachnamedia,geet_email,geet_phone;

//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_event);
        geet= (TextView) findViewById(R.id.geet);
        geet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.geetdigital.com/"));
                startActivity(browserIntent);
            }
        });
        rachnamedia= (TextView) findViewById(R.id.rachnamedia);
        rachnamedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rachnamedia.com/"));
                startActivity(browserIntent);
            }
        });
        geet_email= (TextView) findViewById(R.id.geet_email);
        geet_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "geetdigital@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);

            }
        });
        geet_phone= (TextView) findViewById(R.id.geet_phone);
        geet_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9810005126"));
                startActivity(intent);
            }
        });
//        easyTracker = EasyTracker.getInstance(AboutUs.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(AboutUs.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }


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
