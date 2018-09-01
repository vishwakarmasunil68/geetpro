package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import webuters.com.geet_uttarakhand20aprilpro.R;

/**
 * Created by Mukesh on 5/14/2016.
 */
public class Constants {


    public interface ACTION {
        public static String MAIN_ACTION = "23.22.9.33.geet_uttarakhand20april.MyNotification.action.main";
        public static String INIT_ACTION = "23.22.9.33.geet_uttarakhand20april.MyNotification.action.init";
        public static String PREV_ACTION = "23.22.9.33.geet_uttarakhand20april.MyNotification.action.prev";
        public static String PLAY_ACTION = "23.22.9.33.geet_uttarakhand20april.MyNotification.action.play";
        public static String NEXT_ACTION = "23.22.9.33.geet_uttarakhand20april.MyNotification.action.next";
        public static String STARTFOREGROUND_ACTION = "23.22.9.33.geet_uttarakhand20april.MyNotification.startforeground";
        public static String STOPFOREGROUND_ACTION = "23.22.9.33.geet_uttarakhand20april.MyNotification.stopforeground";

    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.finallogo, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }
}
