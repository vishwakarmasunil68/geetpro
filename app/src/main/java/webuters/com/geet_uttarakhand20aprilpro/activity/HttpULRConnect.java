package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 2/8/2016.
 */
public class HttpULRConnect {


 public String Response_Data;
    public static String getData(String uri){

        BufferedReader reader = null;

        try {

            URL url = new URL(uri);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            Log.e("TEST API","Test data");

            String line;

            while ((line= reader.readLine())!=null) {

                sb.append(line+"\n");

            }

            Log.e("Response is -------->>>", sb.toString());
           String Response_Data=sb.toString();
            return sb.toString();

        } catch (Exception e) {

// TODO: handle exception

            e.printStackTrace();

            return null;

        }

        finally{



            if (reader!=null) {

                try {

                    reader.close();

                } catch (IOException e) {

// TODO Auto-generated catch block

                    e.printStackTrace();

                    return null;

                }



            }

        }}}

