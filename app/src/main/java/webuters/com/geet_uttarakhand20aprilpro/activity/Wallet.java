package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by emobi8 on 7/30/2016.
 */
public class Wallet {

        private static final String PREFS = "com.fortumo.FortumoDemo.PREFS";
        private static final String CREDITS = "virtualcredits";

    public static int addCredits(Context context, int amount) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        int currentCredits = prefs.getInt(CREDITS, 0);

        SharedPreferences.Editor editor = prefs.edit();
        currentCredits += amount;
        editor.putInt(CREDITS, currentCredits);
        editor.commit();
        return currentCredits;
    }

    public static int getCredits(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(CREDITS, 0);
    }
}