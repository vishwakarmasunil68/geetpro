package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import webuters.com.geet_uttarakhand20aprilpro.R;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;

/**
 * Created by Tushar on 2/25/2016.
 */
public class MAP extends Activity implements OnMapReadyCallback {
    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap=map;
        Log.d("sun",EventDetailesMainClass.Location);
        getLocationFromAddress(MAP.this, EventDetailesMainClass.Location);
//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(28.630407, 77.275868))
//                .title("Marker"));
    }
    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
           double latitude= location.getLatitude();
            double longitude=location.getLongitude();
            Log.d("sun",latitude+"  "+longitude);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title("Event"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(p1));
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}

//
//        extends FragmentActivity implements OnMapReadyCallback {
//
//    Button mBtnFind;
//    GoogleMap mMap;
//    EditText etPlace;
//    String Location_finder;
//
////    private EasyTracker easyTracker = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map);
//
//
////        easyTracker = EasyTracker.getInstance(this);
////        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
////
////        try {
////            int a[] = new int[2];
////            int num = a[4];
////        } catch (ArrayIndexOutOfBoundsException e) {
////            easyTracker.send(MapBuilder.createException(
////                    new StandardExceptionParser(this, null)
////                            .getDescription(Thread.currentThread().getName(), e), false).build());
////        }
//
//        Location_finder=EventDetailesMainClass.Location;
////
////        Intent intent = getIntent();
////        if (null != intent) {
////            Location_finder = intent.getStringExtra("Location_ADDRESS");
////            // Location_finder = String.valueOf(intent.getExtras().getInt("Location_ADDRESS"));
////
//////            Log.e("Location_finder  Location recived ====>>", Location_finder);
////
////        }
//
//
//
//
//
//        // Getting reference to the find button
////        mBtnFind = (Button) findViewById(R.id.btn_show);
//
//        // Getting reference to the SupportMapFragment
//
//        SupportMapFragment supportMapFragment =
//                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
//        mMap = supportMapFragment.getMap();
//
//
////        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
////
////        // Getting reference to the Google Map
////        mapFragment.getMapAsync(this);
////
////
////
////        MarkerOptions markerOptions = new MarkerOptions();
////
////        LatLng latLng = new LatLng(28.630407, 77.275868);
////
////        // Setting the position for the marker
////        markerOptions.position(latLng);
////
////        // Setting the title for the marker
////        markerOptions.title("location");
////
////        // Placing a marker on the touched position
////        mMap.addMarker(markerOptions);
//        // Getting reference to EditText
//     //   etPlace = (EditText) findViewById(R.id.et_place);
//
//        // Setting click event listener for the find button
////        mBtnFind.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View v) {
//                // Getting the place entered
////                String location = Location_finder;
////              Log.d("sun",location);
////
//////                if(location==null || location.equals("")){
//////                    Toast.makeText(getBaseContext(), "No Place is entered", Toast.LENGTH_SHORT).show();
//////                    return;
//////                }
////
////                String url = "https://maps.googleapis.com/maps/api/geocode/json?";
////
////                try {
////                    // encoding special characters like space in the user input place
////                    location = URLEncoder.encode(location, "utf-8");
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                }
////
////                String address = "address=" + location;
////
////                String sensor = "sensor=false";
////
////
////                // url , from where the geocoding data is fetched
////                url = url + address + "&" + sensor;
////
////                // Instantiating DownloadTask to get places from Google Geocoding service
////                // in a non-ui thread
////                DownloadTask downloadTask = new DownloadTask();
////
////                // Start downloading the geocoding places
////                downloadTask.execute(url);
//
//
////
////            }
////        });
//
//    }
//
//    private String downloadUrl(final String strUrl) throws IOException {
//        final String[] data1 = {""};
//        new AsyncTask<Void,Void,Void>(){
//            String data = "";
//            @Override
//            protected Void doInBackground(Void... params) {
//                InputStream iStream = null;
//                HttpURLConnection urlConnection = null;
//                try{
//                    URL url = new URL(strUrl);
//
//
//                    // Creating an http connection to communicate with url
//                    urlConnection = (HttpURLConnection) url.openConnection();
//
//                    // Connecting to url
//                    urlConnection.connect();
//
//                    // Reading data from url
//                    iStream = urlConnection.getInputStream();
//
//                    BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//                    StringBuffer sb  = new StringBuffer();
//
//                    String line = "";
//                    while( ( line = br.readLine())  != null){
//                        sb.append(line);
//                    }
//
//                    data = sb.toString();
//
//                    br.close();
//
//                }catch(Exception e){
//                    Log.d("Excpon whle downing url", e.toString());
//                }finally{
//                    try {
//                        iStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    urlConnection.disconnect();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                data1[0] =data;
//                Log.d("sun","data:-"+data);
//            }
//        }.execute();
//        return data1[0];
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//    }
//
//
//    /** A class, to download Places from Geocoding webservice */
//    private class DownloadTask extends AsyncTask<String, Integer, String>{
//
//        String data = null;
//
//        // Invoked by execute() method of this object
//        @Override
//        protected String doInBackground(String... url) {
//            try{
//                data = downloadUrl(url[0]);
//            }catch(Exception e){
//                Log.d("Background Task", e.toString());
//            }
//            return data;
//        }
//
//        // Executed after the complete execution of doInBackground() method
//        @Override
//        protected void onPostExecute(String result){
//            Log.d("sun","result:-"+result);
//            // Instantiating ParserTask which parses the json data from Geocoding webservice
//            // in a non-ui thread
//            ParserTask parserTask = new ParserTask();
//
//            // Start parsing the places in JSON format
//            // Invokes the "doInBackground()" method of the class ParseTask
//            parserTask.execute(result);
//        }
//
//    }
//
//    /** A class to parse the Geocoding Places in non-ui thread */
//    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {
//
//        JSONObject jObject;
//
//        // Invoked by execute() method of this object
//        @Override
//        protected List<HashMap<String,String>> doInBackground(String... jsonData) {
//
//            List<HashMap<String, String>> places = null;
//            GeocodeJSONParser parser = new GeocodeJSONParser();
//
//            try{
//                jObject = new JSONObject(jsonData[0]);
//
//                /** Getting the parsed data as a an ArrayList */
//                places = parser.parse(jObject);
//
//            }catch(Exception e){
//                Log.d("Exception",e.toString());
//            }
//            return places;
//        }
//
//        // Executed after the complete execution of doInBackground() method
//        @Override
//        protected void onPostExecute(List<HashMap<String,String>> list){
//
//            // Clears all the existing markers
//            if(mMap!=null) {
//                mMap.clear();
//            }
//
//            for(int i=0;i<list.size();i++){
//
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Getting a place from the places list
//                HashMap<String, String> hmPlace = list.get(i);
//
//                // Getting latitude of the place
//                double lat = Double.parseDouble(hmPlace.get("lat"));
//                Log.d("maps","lat = "+lat);
//
//                // Getting longitude of the place
//                double lng = Double.parseDouble(hmPlace.get("lng"));
//                Log.d("maps","lng = "+lng);
//                // Getting name
//                String name = hmPlace.get("formatted_address");
//
//                LatLng latLng = new LatLng(lat, lng);
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker
//                markerOptions.title(name);
//
//                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);
//
//                // Locate the first location
//                if(i==0)
//                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//            }
//        }
//    }
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
////    @Override
////    public void onStart() {
////        super.onStart();
////        EasyTracker.getInstance(this).activityStart(this);
////    }
////
////    @Override
////    public void onStop() {
////        super.onStop();
////        EasyTracker.getInstance(this).activityStop(this);
////    }
//}
//
