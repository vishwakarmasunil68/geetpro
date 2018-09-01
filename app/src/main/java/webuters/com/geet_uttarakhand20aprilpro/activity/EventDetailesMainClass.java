package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.bean.EventDetailesBean;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Tushar on 2/24/2016.
 */
public class EventDetailesMainClass extends Activity {

    ProgressDialog pDialog;
  //  ListView detailes_list;
    String currentSongIndex;
    String EVENT_ID_IS;
    String Full_Event_Detaile_Url;
    String Event_id_IS;
    String Full_Singer_Name;
    Button bt_location;
    String EVENT_LIST_INDEX,Tushar_Event_ID;
    ImageView detaile_cover_image;
    TextView txt_EventName_is,txt_StartDate_is,txt_Singer_Name_is,txt_Location_is,txt_Start_time_is,txt_Orgnigation_is;

    String FULL_EVENT_DETAILS_URL;
TextView event_name,event_address,event_start_time,singer_name_event;

    String id, EventName, EventCover, MusicDirector, Lyricist, StartDate;
    public static String  Location;
    String StartTime, EndDate, EndTime, Details, OrganizerName, CreatedDate, ModifiedDate, SingerId, FirstName, LastName;
    ArrayList<EventDetailesBean> event_array_list = new ArrayList<EventDetailesBean>();

//    private EasyTracker easyTracker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main_detaile);


//        easyTracker = EasyTracker.getInstance(EventDetailesMainClass.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(EventDetailesMainClass.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }


        bt_location=(Button)findViewById(R.id.bt_location);
        //event_name=(TextView)findViewById(R.id.event_name);
        //detailes_list=(ListView)findViewById(R.id.detailes_list);

        txt_EventName_is=(TextView)findViewById(R.id.txt_EventName_is);
        txt_StartDate_is=(TextView)findViewById(R.id.txt_StartDate_is);
        txt_Singer_Name_is=(TextView)findViewById(R.id.txt_Singer_Name_is);
        txt_Location_is=(TextView)findViewById(R.id.txt_Location_is);
        txt_Orgnigation_is=(TextView)findViewById(R.id.txt_Orgnigation_is);
        txt_Start_time_is=(TextView)findViewById(R.id.txt_Start_time_is);

        detaile_cover_image=(ImageView)findViewById(R.id.detaile_cover_image);

     //  Picasso.with(EventDetailesMainClass.this).load(EventCover).into(detaile_cover_image);


        Intent intent = getIntent();
        if (null != intent) {

            System.out.println("currentSongIndex Create callstart here"+ String.valueOf(currentSongIndex));
            Event_id_IS = intent.getStringExtra("Event_id_IS");
           Tushar_Event_ID =intent.getStringExtra("TUSHAR_EVENT_ID");
//            Toast.makeText(getApplicationContext(),"Event Id"+Tushar_Event_ID,Toast.LENGTH_LONG).show();
            System.out.println("Tushar Your Event ID  Recived====>>"+ Tushar_Event_ID);
             FULL_EVENT_DETAILS_URL= Holder.EVENT_DETAILES_URL+Tushar_Event_ID;
            System.out.println("FULL_EVENT_DETAILS_URL ====>>"+ FULL_EVENT_DETAILS_URL);

          System.out.println("Event_id_IS Recived====>>" + Event_id_IS);
            EVENT_LIST_INDEX = intent.getStringExtra("EVENT_LIST_INDEX");
            System.out.println("EVENT_LIST_INDEX Recived====>>"+ EVENT_LIST_INDEX);
            new Event_Detailes_Class_Asyntask().execute();
        }

        bt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EventDetailesMainClass.this,MAP.class);
//                i.putExtra("Location_ADDRESS",Location);
//                Toast.makeText(getApplicationContext(),"The location is : "+Location,Toast.LENGTH_SHORT).show();
//                Log.e("PUt LOCATION ADDRESS",""+Location);
                startActivity(i);
            }
        });
    }

    private class Event_Detailes_Class_Asyntask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(EventDetailesMainClass.this);
            pDialog.setMessage("Loading .........");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

    }
//

        @Override
        protected String doInBackground(String... params) {
            String Event_detailes__Response = HttpULRConnect.getData(Holder.EVENT_DETAILES_URL+Tushar_Event_ID);
            System.out.println("EVENT FULL Class URL is ------>>  "+ FULL_EVENT_DETAILS_URL);
        System.out.println("EVENT Detailes CLASS RESPONSE  ---->"+Event_detailes__Response);
            return Event_detailes__Response;
        }
        @Override
        protected void onPostExecute(String s) {
            event_array_list.clear();
                  pDialog.dismiss();
            try {
                JSONObject obj = new JSONObject(s);
               // Log.e("Post Method Call  here ....", "Method ...");
                JSONArray ar = obj.getJSONArray("Event");
                // JSONArray ar = new JSONArray(s);
                System.out.println("Array response is ---" + ar);
                Log.e("Array response ids---", String.valueOf(ar));
                for (int i = 0; i < ar.length(); i++) {

                    JSONObject jsonobject = ar.getJSONObject(i);

                    Log.e("Response in i ", String.valueOf(i));
                    EventDetailesBean eventDetailesBean = new EventDetailesBean();

                    id = jsonobject.getString("id");

                    eventDetailesBean.setId(id);
                 //   Log.e(" Event ID is =======================>>>>>", id);
                    EventName = jsonobject.getString("EventName");
                    eventDetailesBean.setEventName(EventName);

                  //  Log.e(" EventName is =======================>>>>>", EventName);

                    txt_EventName_is.setText(EventName);

                    Location = jsonobject.getString("Location");
                    if(Location==null || Location.equals("")){
                        Location = "INDIA";
                        Toast.makeText(getApplicationContext(),"Try Again or Location Undefined",Toast.LENGTH_LONG).show();
                    }
                    eventDetailesBean.setLocation(Location);
                 //   Log.e("Location Address As it IS ----- >>.", Location);


                    EventCover = jsonobject.getString("EventCover");
                    eventDetailesBean.setLocation(EventCover);
                 //   Log.e("EventCover    IS ----- >>.", EventCover);

                    if(EventCover==""){

                        Picasso.with(EventDetailesMainClass.this)
                                .load(jsonobject.getString(""))
                                .into(detaile_cover_image);
                    }
                    else {
                        Picasso.with(EventDetailesMainClass.this)
                                .load(jsonobject.getString("EventCover"))
                                .into(detaile_cover_image);
                    }

                   // Picasso.with(EventDetailesMainClass.this).load(EventCover).into(detaile_cover_image);

                    StartDate = jsonobject.getString("StartDate");
                    eventDetailesBean.setStartDate(StartDate);
                    Log.e("Start date is ---->> ", StartDate);
                    StartTime=jsonobject.getString("StartTime");
                    eventDetailesBean.setStartTime(StartTime);
                    Log.e("Start time is ---->> ", StartTime);
                    OrganizerName = jsonobject.getString("OrganizerName");
                    eventDetailesBean.setSingerId(OrganizerName);
                 //   Log.e("OrganizerName  is =======================>>>>>", OrganizerName);

                    MusicDirector=jsonobject.getString("MusicDirector");
                    eventDetailesBean.setMusicDirector(MusicDirector);
                 //   Log.e("Music Director is ------->>", MusicDirector);

                    Details=jsonobject.getString("Details");
                    eventDetailesBean.setDetails(Details);
                    Log.e("Detailes  is ------->>", Details);

                    txt_EventName_is.setText(EventName);
                    txt_StartDate_is.setText(StartDate);
                    txt_Start_time_is.setText(StartTime);
                    txt_Location_is.setText(Location);
                    txt_Orgnigation_is.setText(OrganizerName);

//         Picasso.with(EventDetailesMainClass.this).load("" + jsonobject.getString("EventCover")).fit().into(detaile_cover_image);

                 //   Log.e(" Event StartDate As it IS ----- >>.", StartDate);
                }

                JSONArray ar1 = obj.getJSONArray("EventSingers");
                Log.e("Array response ids---", String.valueOf(ar1));
                for (int i = 0; i < ar1.length(); i++) {
                    System.out.println("Array response is ---" + ar.length());
                    JSONObject jsonobject = ar1.getJSONObject(i);

                    Log.e("Response in i ", String.valueOf(i));
                    EventDetailesBean eventDetailesBean = new EventDetailesBean();
                    FirstName = jsonobject.getString("FirstName");
                    eventDetailesBean.setFirstName(FirstName);
                  //  Log.e("singer first name  is =======================>>>>>", FirstName);

                    LastName = jsonobject.getString("LastName");
                    eventDetailesBean.setFirstName(LastName);
                  //  Log.e("singer LastName  is =======================>>>>>", LastName);
                   Full_Singer_Name=FirstName+" "+LastName;
                  //  Log.e("Singer Full Name is ----",Full_Singer_Name);
                    SingerId = jsonobject.getString("SingerId");
                    eventDetailesBean.setSingerId(SingerId);
                 //   Log.e("SingerId  is =======================>>>>>", SingerId);

                    txt_Singer_Name_is.setText(Full_Singer_Name);
                    event_array_list.add(eventDetailesBean);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Event_Detailes_Adapter adapter = new Event_Detailes_Adapter(EventDetailesMainClass.this, R.layout.event_main_detaile, event_array_list);
//            detailes_list = (ListView) findViewById(R.id.event_list);
//            detailes_list.setAdapter(adapter);
        }


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