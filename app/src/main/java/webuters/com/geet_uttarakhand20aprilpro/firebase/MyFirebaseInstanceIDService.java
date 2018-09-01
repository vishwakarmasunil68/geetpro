package webuters.com.geet_uttarakhand20aprilpro.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import webuters.com.geet_uttarakhand20aprilpro.activity.AppConstant;

/**
 * Created by emobi8 on 11/23/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        sendToken(refreshedToken);
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);


    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
    public void sendToken(final String token){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://23.22.9.33/Admin/GeetApp/getapi/ursereg.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.optString("success");
                            if(success.equals("true")){
//                                JSONObject result=""
                                Toast.makeText(getApplicationContext(), "Gcm Registration Success", Toast.LENGTH_SHORT).show();
                                AppConstant.setNotification(getApplicationContext(), true);
                                //Log.d("sunil","success");
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Gcm Registration Failed",Toast.LENGTH_SHORT).show();
                                //Log.d("sunil","failed");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("sunil", "" + error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", String.valueOf(System.currentTimeMillis()));
                params.put("useremail", "");
                params.put("device_token", token);


                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
