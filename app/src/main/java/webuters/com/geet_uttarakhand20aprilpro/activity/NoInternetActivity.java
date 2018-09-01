package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import webuters.com.geet_uttarakhand20aprilpro.R;

//
//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;

public class NoInternetActivity extends AppCompatActivity {
//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

//        easyTracker = EasyTracker.getInstance(NoInternetActivity.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(NoInternetActivity.this, null)
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
