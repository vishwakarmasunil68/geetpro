package webuters.com.geet_uttarakhand20aprilpro.activity;

/*
 * Copyright 2013 Csaba Szugyiczki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;
import com.szugyi.circlemenu.view.CircleLayout.OnCenterClickListener;
import com.szugyi.circlemenu.view.CircleLayout.OnItemClickListener;
import com.szugyi.circlemenu.view.CircleLayout.OnItemSelectedListener;
import com.szugyi.circlemenu.view.CircleLayout.OnRotationFinishedListener;

import webuters.com.geet_uttarakhand20aprilpro.R;

public class SampleActivity extends AppCompatActivity implements OnItemSelectedListener,
        OnItemClickListener, OnRotationFinishedListener, OnCenterClickListener {
    public static final String ARG_LAYOUT = "layout";

    protected CircleLayout circleLayout;
    protected TextView selectedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view by passed extra

//        int layoutId = extras.getInt(ARG_LAYOUT);



        setContentView(R.layout.sample);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            try {
                if (extras.getBoolean("backup")) {
                    Intent intent = new Intent(SampleActivity.this, ContactUs.class);
                    intent.putExtra("backup", true);
                    startActivity(intent);
                }
            } catch (Exception e) {

            }
        }
        if(extras!=null) {
            try {
                if (extras.getBoolean("backup1")) {
                    Intent intent = new Intent(SampleActivity.this, Setting_event.class);
                    intent.putExtra("backup1", true);
                    startActivity(intent);
                }
            } catch (Exception e) {

            }
        }
        // Set listeners
        circleLayout = (CircleLayout) findViewById(R.id.circle_layout);
        circleLayout.setOnItemSelectedListener(this);
        circleLayout.setOnItemClickListener(this);
        circleLayout.setOnRotationFinishedListener(this);
        circleLayout.setOnCenterClickListener(this);

        selectedTextView = (TextView) findViewById(R.id.selected_textView);

        String name = null;
        View view = circleLayout.getSelectedItem();
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        }
        selectedTextView.setText(name);
    }

    @Override
    public void onItemSelected(View view) {
        final String name;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        } else {
            name = null;
        }

        selectedTextView.setText(name);

        switch (view.getId()) {
            case R.id.main_calendar_image:
                // Handle calendar selection
                break;
            case R.id.main_cloud_image:
                // Handle cloud selection
                break;
            case R.id.main_events_image:
                // Handle key selection
                break;
            case R.id.main_contact_image:
                // Handle mail selection
                break;
            case R.id.main_help_image:
                // Handle profile selection
                break;
            case R.id.main_tap_image:
                // Handle tap selection
                break;
        }
    }

    @Override
    public void onItemClick(View view) {
        String name = null;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        }

       // String text = getResources().getString(R.string.start_app, name);
      //  Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.main_calendar_image:
                // Handle calendar click
                Intent i=new Intent(SampleActivity.this,AboutUs.class);
                startActivity(i);
                break;
            case R.id.main_cloud_image:
                // Handle cloud click
                Intent ii=new Intent(SampleActivity.this,Setting_event.class);
                startActivity(ii);
                break;
            case R.id.main_events_image:
                // Handle key click
                Intent i1=new Intent(SampleActivity.this,EventClass.class);
                startActivity(i1);
                break;

            case R.id.main_contact_image:
                Intent intent= new Intent(SampleActivity.this,MainActivity.class);
                startActivity(intent);
              break;

            case R.id.main_help_image:
                // Handle profile click
                Intent contact=new Intent(SampleActivity.this,ContactUs.class);
                startActivity(contact);
                break;

            case R.id.main_tap_image:
                // Handle profile click
                Intent rate = (new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=webuters.com.geet_uttarakhand20april")));
                startActivity(rate);
                break;


        }
    }

    @Override
    public void onRotationFinished(View view) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2, view.getHeight() / 2);
        animation.setDuration(250);
        view.startAnimation(animation);
    }

    @Override
    public void onCenterClick() {
        Toast.makeText(getApplicationContext(), R.string.center_click, Toast.LENGTH_SHORT).show();
    }

    public void onAddClick(View view) {
        CircleImageView newMenu = new CircleImageView(this);
        newMenu.setBackgroundResource(R.drawable.circle);
        newMenu.setImageResource(R.drawable.ic_voice);
        newMenu.setName(getString(R.string.voice_search));
        circleLayout.addView(newMenu);
    }

    public void onRemoveClick(View view) {
        if (circleLayout.getChildCount() > 0) {
            circleLayout.removeViewAt(circleLayout.getChildCount() - 1);
        }
    }
}
