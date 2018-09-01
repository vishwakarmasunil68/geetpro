package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sunil on 31-08-2016.
 */
public class AppConstant {
//    public static Context context;
//    public AppConstant(Context context){
//        this.context=context;
//    }
    public static void SetSaveMusicpref(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("savesongplaying",value);
        editor.commit();
    }
    public static boolean getSaveMusicPref(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("savesongplaying",false);
    }

    public static void SetfavMusicPref(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("favmusicplaying",value);
        editor.commit();
    }
    public static boolean getFavMusicPref(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("favmusicplaying",false);
    }

    public static void SetmainMusicpref(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("mainsongplaying",value);
        editor.commit();
    }
    public static boolean getMainMusicPref(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("mainsongplaying",false);
    }
    public static boolean getCallStatus(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("incall",false);
    }
    public static void setCallStatus(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("incall",value);
        editor.commit();
    }

    public static void setSavedCallStatus(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("savedincall",value);
        editor.commit();
    }

    public static boolean getSavedCallStatus(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("savedincall",false);
    }

    public static void setMainCallStatus(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("mainincall",value);
        editor.commit();
    }

    public static boolean getMainCallStatus(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("mainincall",false);
    }

    public static boolean getRecievingPref(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("recievepref",false);
    }
    public static void setRecievePref(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("recievepref",value);
        editor.commit();
    }
    public static boolean getIdlePref(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("idlepref",false);
    }
    public static void setIdlePref(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("idlepref",value);
        editor.commit();
    }
    public static boolean getoffhookPref(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("offhook",false);
    }
    public static void setoffhookPref(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("offhook",value);
        editor.commit();
    }
    public static boolean getIsPlaying(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("isplaying",false);
    }
    public static void setIsPlaying(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isplaying",value);
        editor.commit();
    }

    public static void setsavedmusicplaying(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("savedmusicplaying",value);
        editor.commit();
    }

    public static boolean getsavedmusicplaying(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("savedmusicplaying",false);
    }
    public static void setmainmusicplaying(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("mainmusicplaying",value);
        editor.commit();
    }

    public static boolean getmainmusicplaying(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("mainmusicplaying",false);
    }

    public static void setfavmusicplaying(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("favmusicplaying",value);
        editor.commit();
    }

    public static boolean getfavmusicplaying(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("favmusicplaying",false);
    }

    public static void setNotification(Context context,boolean value){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("notify",value);
        editor.commit();
    }

    public static boolean getNotification(Context context){
        SharedPreferences sp=context.getSharedPreferences("geet.txt",Context.MODE_PRIVATE);
        return sp.getBoolean("notify",false);
    }

}
