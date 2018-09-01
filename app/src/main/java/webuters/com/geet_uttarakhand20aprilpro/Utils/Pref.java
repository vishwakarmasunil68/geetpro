package webuters.com.geet_uttarakhand20aprilpro.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webuters.com.geet_uttarakhand20aprilpro.pojo.AlbumSongResultPOJO;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sunil on 26-05-2017.
 */

public class Pref {

    private static final String PrefDB = "geetapppro";


    public static final String FCM_REGISTRATION_TOKEN = "fcm_registration_token";


    public static void SetStringPref(Context context, String KEY, String Value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(KEY, Value);
            editor.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String GetStringPref(Context context, String KEY, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getString(KEY, defValue);
    }

    public static void SetBooleanPref(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }

    public static int GetIntPref(Context context, String KEY, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getInt(KEY, defValue);
    }

    public static void SetIntPref(Context context, String KEY, int Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY, Value);
        editor.commit();
    }

    public static boolean GetBooleanPref(Context context, String KEY, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getBoolean(KEY, defValue);
    }

    public static void clearSharedPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void SaveDeviceToken(Context context, String Value) {
        SharedPreferences sp = context.getSharedPreferences("momytdevicetoken.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("devicetoken", Value);
        editor.commit();
    }

    public static String GetDeviceToken(Context context, String defValue) {
        SharedPreferences sp = context.getSharedPreferences("momytdevicetoken.txt", MODE_PRIVATE);
        return sp.getString("devicetoken", defValue);
    }

    public static boolean saveSongs(Context context, AlbumSongResultPOJO albumSongResultPOJO) {
        SharedPreferences sp = context.getSharedPreferences("geetfavorite", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Set<String> songpojos = sp.getStringSet("favoriteSongSet", new HashSet<String>());
        String songpojo=new Gson().toJson(albumSongResultPOJO);
        Log.d(TagUtils.getTag(),"song pojo:-"+songpojo);
        boolean isAdded=false;
        if(songpojos.contains(songpojo)){
            songpojos.remove(songpojo);
            isAdded=false;

        }else{
            songpojos.add(songpojo);
            isAdded=true;
        }

        editor.putStringSet("favoriteSongSet", songpojos);
        editor.commit();

        Log.d(TagUtils.getTag(),"stored list:-"+getSavedStringSongs(context));
        return isAdded;
    }

    public static List<AlbumSongResultPOJO> getSavedSongs(Context context){
        SharedPreferences sp = context.getSharedPreferences("geetfavorite", MODE_PRIVATE);
        Set<String> songpojos = sp.getStringSet("favoriteSongSet", new HashSet<String>());
        Log.d(TagUtils.getTag(),"list length:-"+songpojos.size());
        List<AlbumSongResultPOJO> albumSongResultPOJOS=new ArrayList<>();
        for(String song:songpojos){
            albumSongResultPOJOS.add(new Gson().fromJson(song,AlbumSongResultPOJO.class));
        }
        return albumSongResultPOJOS;
    }

    public static List<String> getSavedStringSongs(Context context){
        SharedPreferences sp = context.getSharedPreferences("geetfavorite", MODE_PRIVATE);
        Set<String> songpojos = sp.getStringSet("favoriteSongSet", new HashSet<String>());
        List<String> list = new ArrayList<String>(songpojos);
        return list;
    }

    public static boolean saveSongPOJOS(Context context, AlbumSongResultPOJO albumSongResultPOJO){
        String albumString=new Gson().toJson(albumSongResultPOJO);

        String favSongString=GetStringPref(context,StringUtils.FAV_SONG,"");
        try {
            if (favSongString.equals("")) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject songObj = new JSONObject();
                songObj.put("song", albumString);
                jsonArray.put(songObj);

                jsonObject.put("data",jsonArray);
                SetStringPref(context,StringUtils.FAV_SONG,jsonObject.toString());
            } else {
                JSONObject jsonObject = new JSONObject(favSongString);
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                JSONObject songObj = new JSONObject();
                songObj.put("song", albumString);
                jsonArray.put(songObj);

                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("data",jsonArray);
                SetStringPref(context,StringUtils.FAV_SONG,jsonObject1.toString());
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static List<AlbumSongResultPOJO> getsaveSongPOJOS(Context context){
        List<AlbumSongResultPOJO> albumSongResultPOJOS=new ArrayList<>();
        String favSongString=GetStringPref(context,StringUtils.FAV_SONG,"");
        try {
            if (favSongString.equals("")) {
            } else {
                JSONObject jsonObject = new JSONObject(favSongString);
                JSONArray jsonArray = jsonObject.optJSONArray("data");

                for(int i=0;i<jsonArray.length();i++){
                    albumSongResultPOJOS.add(new Gson().fromJson(jsonArray.optJSONObject(i).optString("song").toString(),AlbumSongResultPOJO.class));
                }
            }
            return albumSongResultPOJOS;
        }catch (Exception e){
            e.printStackTrace();
            return albumSongResultPOJOS;
        }
    }

    public static List<String> getsaveSongSTRINGS(Context context){
        List<String> albumSongResultPOJOS=new ArrayList<>();
        String favSongString=GetStringPref(context,StringUtils.FAV_SONG,"");
        try {
            if (favSongString.equals("")) {
            } else {
                JSONObject jsonObject = new JSONObject(favSongString);
                JSONArray jsonArray = jsonObject.optJSONArray("data");

                for(int i=0;i<jsonArray.length();i++){
                    albumSongResultPOJOS.add(jsonArray.optJSONObject(i).optString("song").toString());
                }
            }
            return albumSongResultPOJOS;
        }catch (Exception e){
            e.printStackTrace();
            return albumSongResultPOJOS;
        }
    }

//    public static List<UserVideoPOJO> getVideos(Context context) {
//        String allVideos = Pref.GetStringPref(context, StringUtils.USER_VIDEOS, "");
//        if (allVideos.length() > 0) {
//            try {
//                List<UserVideoPOJO> userVideoPOJOS = new ArrayList<>();
//                List<String> userVideosString = new Gson().fromJson(allVideos, new TypeToken<List<String>>() {
//                }.getType());
//                for (String video : userVideosString) {
//                    Gson gson = new Gson();
//                    UserVideoPOJO userVideoPOJO = gson.fromJson(video, UserVideoPOJO.class);
//                    userVideoPOJOS.add(userVideoPOJO);
//                }
//
//                return userVideoPOJOS;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
}
