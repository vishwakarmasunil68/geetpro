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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.adapter.See_all_catagory_Adapter;
import webuters.com.geet_uttarakhand20aprilpro.bean.CategoryGetSetUrl;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Tushar on 2/28/2016.
 */
public class See_all_catagory extends Activity {
    ListView see_all_cat_list;
    ProgressDialog pDialog;
    String id, CatagoryName;
    TextView cat_text;

    ArrayList<CategoryGetSetUrl> catagory_array_list = new ArrayList<CategoryGetSetUrl>();

//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_catagory);


//        easyTracker = EasyTracker.getInstance(See_all_catagory.this);
//        easyTracker.send(MapBuilder.createEvent("your_action", "envet_name", "button_name/id", null).build());
//
//        try {
//            int a[] = new int[2];
//            int num = a[4];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            easyTracker.send(MapBuilder.createException(
//                    new StandardExceptionParser(See_all_catagory.this, null)
//                            .getDescription(Thread.currentThread().getName(), e), false).build());
//        }


        cat_text=(TextView)findViewById(R.id.cat_text);
        cat_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(See_all_catagory.this,AlbumList.class);
                startActivity(i);
            }
        });

        new CatagoryUrlAsynTask().execute();
    }

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            String content = HttpULRConnect.getData(Holder.SINGERS_DETAILES);
            Log.e("Category Url Response ---->", String.valueOf(content));

            return content;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(See_all_catagory.this);
            pDialog.setMessage("Loading profile ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected void onPostExecute(String s) {
            catagory_array_list.clear();
            try {
                pDialog.dismiss();
                Log.e("Post Method Call ....", "Method ...");
                JSONArray ar = new JSONArray(s);
                System.out.println("Array response is ---" + ar);
                Log.e("Array response ids---", String.valueOf(ar));
                for (int i = 0; i < ar.length(); i++) {
                    System.out.println("Array response is ---" + ar.length());
                    JSONObject jsonobject = ar.getJSONObject(i);

                    CategoryGetSetUrl categoryGetSetUrl = new CategoryGetSetUrl();

                    id = jsonobject.getString("id");
                    categoryGetSetUrl.setId(id);
                    Log.e("cat id ====================>>>>>", id);
                    CatagoryName = jsonobject.getString("CatagoryName");
                    categoryGetSetUrl.setCatagoryName(CatagoryName);
                    Log.e("CatagoryName is ======================>>>>>", CatagoryName);


                    catagory_array_list.add(categoryGetSetUrl);
                    Log.e("catagory array is -----+", String.valueOf(catagory_array_list));

                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            See_all_catagory_Adapter catagoryAdapter = new See_all_catagory_Adapter(See_all_catagory.this, R.layout.see_all_catagory, catagory_array_list);

            Log.e("Adapter Set here ----->>>", "Adapter set ");

            see_all_cat_list = (ListView) findViewById(R.id.see_all_cat_list);
            see_all_cat_list.setAdapter(catagoryAdapter);

            see_all_cat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.e("catagory lis", "item clicked");
                    Intent i = new Intent(See_all_catagory.this, CatagoryClass.class);
                    String ID = catagory_array_list.get(position).getId();
                    i.putExtra("ID", ID);
                    Log.e("CAt id put ",ID);
                    String CAT_Name=catagory_array_list.get(position).getCatagoryName();

                    i.putExtra("CAT_Name",CAT_Name);
                    Log.e("CAT_Name ----put ",CAT_Name);
//                    i.putExtra("ID",id);
//                    Log.e("put cat id -----", String.valueOf(id));
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