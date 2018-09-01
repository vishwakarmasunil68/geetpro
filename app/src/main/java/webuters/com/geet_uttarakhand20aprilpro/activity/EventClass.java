package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.adapter.EventClassAdapter;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Tushar on 2/24/2016.
 */
public class EventClass extends Activity {
    public ArrayList<EventClassBean> array_event_calss_bean = new ArrayList<EventClassBean>();

    private ListView event_list;
    ProgressDialog pDialog;

//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_class_layout);


//        easyTracker = EasyTracker.getInstance(EventClass.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(EventClass.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }


        event_list = (ListView) findViewById(R.id.event_list);
        new EventClassAsyntask().execute();


    }


    private class EventClassAsyntask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EventClass.this);
            pDialog.setMessage("Loading .........");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            String Event_Response = HttpULRConnect.getData(Holder.EVENT_CALSS_URL);
            Log.e("EVENT Class URL is ------>>  ", Holder.EVENT_CALSS_URL);
            Log.e("EVENT CLASS RESPONSE  ---->", String.valueOf(Event_Response));
            return Event_Response;
        }

        @Override
        protected void onPostExecute(String s) {

            pDialog.dismiss();
            try {

                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray ar = new JSONArray(s);
                System.out.println("Array response is ---" + ar);
                Log.e("Event class Array response ids---", String.valueOf(ar));
                for (int i = 0; i < ar.length(); i++) {
                    System.out.println("Array response is ---" + ar.length());

                    JSONObject jsonobject = ar.getJSONObject(i);

                    EventClassBean event_class_bean = new EventClassBean();

                    String EVENT_ID, EVENTE_NAME, EVENT_COVER, EVENT_START_DATE;

                    EVENT_ID = jsonobject.getString("id");

                    event_class_bean.setId(EVENT_ID);
                    Log.e("EVENT ID  -------------->> ", EVENT_ID);

                    // mediaplayer.setDataSource(songPath);
                    EVENTE_NAME = jsonobject.getString("EventName");
                    event_class_bean.setEventName(EVENTE_NAME);
                    Log.e("Event NAME is ----------------------->>>", EVENTE_NAME);
                    EVENT_COVER = jsonobject.getString("EventCover");
                    event_class_bean.setEventCover(EVENT_COVER);
                    Log.e("EVENT COVER IMAGE------->>>", EVENT_COVER);

                    EVENT_START_DATE = jsonobject.getString("StartDate");
                    event_class_bean.setStartDate(EVENT_START_DATE);
                    Log.e("EVENT START DATE===========>>>", EVENT_START_DATE);


                    array_event_calss_bean.add(event_class_bean);
                    Log.e("Event list Array data is ----- ", String.valueOf(array_event_calss_bean));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            EventClassAdapter adapter = new EventClassAdapter(EventClass.this, R.layout.recent_class_layout, array_event_calss_bean);
            event_list = (ListView) findViewById(R.id.event_list);

            Log.e("Adapter Set here ----->>>", "Adapter set ");
            event_list.setAdapter(adapter);


            event_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Log.e("List Item Click -----tushar", "Clicked ");

                    int EVENT_LIST_INDEX = position;

                    Intent i = new Intent(EventClass.this, EventDetailesMainClass.class);
                    i.putExtra("EVENT_LIST_INDEX", EVENT_LIST_INDEX);
                    String EVENT_ID_IS = array_event_calss_bean.get(position).getId();
                    i.putExtra("TUSHAR_EVENT_ID", EVENT_ID_IS);
                    Log.e("TUSHAR_EVENT_ID ----->>>>>>>>>", EVENT_ID_IS);

                    Log.e("put  EVENT_LIST_INDEX Activity --->", String.valueOf(EVENT_LIST_INDEX));
                    startActivity(i);
                }
            });
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