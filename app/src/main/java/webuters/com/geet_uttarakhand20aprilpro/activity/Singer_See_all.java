package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.adapter.Singer_Adapter;
import webuters.com.geet_uttarakhand20aprilpro.adapter.Singer_list_Adapter;
import webuters.com.geet_uttarakhand20aprilpro.bean.SingerlistBean;
import webuters.com.geet_uttarakhand20aprilpro.holder.Holder;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.StandardExceptionParser;


/**
 * Created by Tushar on 2/25/2016.
 */
public class Singer_See_all extends Activity {

    ListView singer_see_all_list;
    Singer_Adapter singer_list_adapter;
    String Singer_ID, Singer_First_Name, Singer_Last_Name, Singer_Image;
    ProgressDialog pDialog;
    String FULL_NAME;
    String SongImage;
    Toolbar toolbar_singer_share;
    TextView text_see_all_singers;
    ImageView image_share;
    FrameLayout blur_frame;
    Singer_list_Adapter singer_list_item;
    public ArrayList<SingerlistBean> array_SingerlistBean;
    EditText etSearch;
    Button btnSearch;
    Boolean boolSearch = false;

//    private EasyTracker easyTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.singer_see_all);

        toolbar_singer_share = (Toolbar) findViewById(R.id.toolbar_share);
        text_see_all_singers = (TextView) findViewById(R.id.text_see_all_singers);
        text_see_all_singers.setText("Singer");
        // setSupportActionBar(toolbar_singer_share);
        etSearch = (EditText) findViewById(R.id.etSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        etSearch.setRawInputType(InputType.TYPE_CLASS_TEXT);
        etSearch.setTextIsSelectable(true);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolSearch) {
                    text_see_all_singers.setVisibility(View.GONE);
                    etSearch.setVisibility(View.VISIBLE);
                    boolSearch = true;
                    etSearch.requestFocus();
                    imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    text_see_all_singers.setVisibility(View.VISIBLE);
                    etSearch.setVisibility(View.GONE);
                    etSearch.setText("");
                    boolSearch = false;
                    imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                }


            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //singer_list_adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String filtered = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                singer_list_adapter.filter(filtered);
            }
        });

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setFocusableInTouchMode(true);
                    v.requestFocus();
                } else
                    etSearch.setHint("Type here");
            }
        });


 /* Singer List AsynTask Call Here by tushar*/
        new Singer_List_AsynTask().execute();

    }

    private class Singer_List_AsynTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Singer_See_all.this);
            pDialog.setMessage("Loading .........");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpULRConnect.getData(Holder.SINGERS_LIST_URL);
//            if(content==""){
//                Intent i=new in
//            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            array_SingerlistBean = new ArrayList<SingerlistBean>();
            pDialog.dismiss();
            try {
//
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray ar = new JSONArray(s);
                System.out.println("Array response is ---" + ar);
                Log.e("Array response ids---", String.valueOf(ar));
                for (int i = 0; i < ar.length(); i++) {
                    System.out.println("Array response is ---" + ar.length());

                    JSONObject jsonobject = ar.getJSONObject(i);

                    SingerlistBean singerlistBean = new SingerlistBean();

                    Singer_ID = jsonobject.getString("Id");
                    singerlistBean.setId(Singer_ID);
                    //  Log.e("Singer ID -------- :::--------- >", Singer_ID);
                    SongImage = jsonobject.getString("Image");
                    singerlistBean.setImage(SongImage);

                    //Log.e("singer see all list Image  url is ----- >", SongImage);

                    Singer_First_Name = jsonobject.getString("FirstName");
                    singerlistBean.setFirstName(Singer_First_Name);
                    //Log.e("Singer_First_Name -------- :::--------- >", Singer_First_Name);

                    Singer_Last_Name = jsonobject.getString("LastName");
                    singerlistBean.setLastName(Singer_Last_Name);
                    //Log.e("Singer_Last_Name -------- :::--------- >", Singer_Last_Name);
                    array_SingerlistBean.add(singerlistBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            singer_list_adapter = new Singer_Adapter(Singer_See_all.this, R.layout.singer_see_all, array_SingerlistBean);
            singer_see_all_list = (ListView) findViewById(R.id.singer_see_all_list);
            singer_see_all_list.setTextFilterEnabled(true);

            //           Log.e("Adapter Set here ----->>>", "Adapter set ");
            singer_see_all_list.setAdapter(singer_list_adapter);


            singer_see_all_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Log.e("List Item Click -----tushar", "Clicked ");
                    String SingerId = array_SingerlistBean.get(position).getId();
                    String singerImage = array_SingerlistBean.get(position).getImage();
                    System.out.println("Singer seeall singer id get by position");
                    int songIndex = position;
                    FULL_NAME = Singer_First_Name + Singer_Last_Name;
                    Intent i = new Intent(getApplicationContext(), Singers_Details_class.class);
                    i.putExtra("songIndex", songIndex);
                    i.putExtra("ImageIs", singerImage);
                    //                  Log.e("<<<<<<<<<<<<<<<<<<<<<put SongImage>>>>>>>>>>>>>>>>>>>>>>>>>",singerImage);
                    //  Log.e("song index clicked -s ----->>>", String.valueOf(songIndex));
                    //                Log.e("song index clicked -s ----->>>", String.valueOf(songIndex));
                    i.putExtra("Singer_id_put", SingerId);
                    i.putExtra("FULL_Singer__NAME", FULL_NAME);
                    //              Log.e("Full Singer Name IS ---------------   :::: -------->>",FULL_NAME);
                    //            Log.e("Put singer id from Singer see all class TUSHAR ----------- ::: --------- >",SingerId);
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