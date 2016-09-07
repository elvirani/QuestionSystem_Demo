package utils;

import android.content.Context;
import android.content.SharedPreferences;

import pojo.User;

/**
 * Created by yanni on 2016/9/4.
 */
public class SharedPreferencesUtils {
    public static final String TAG = "namepass";

    public static boolean judgeState(Context context) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        if (sp.getBoolean("isSave", true))
            if (!sp.getString("username", "").equals("")) return true;
        return false;
    }

    public static void saveNamePass(User user, Context context) {

        SharedPreferences preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putString("nickname", user.getNickname());
        editor.putBoolean("isSave", true);
        editor.commit();
    }

    public static boolean getState(Context context) {
        SharedPreferences sp = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        boolean state = sp.getBoolean("isSave",true);
        return state;
    }
    public static boolean getImgState(Context context) {
        SharedPreferences sp = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        boolean state = sp.getBoolean("isSaveImg",true);
        return state;
    }
    public static User getInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        String nickname = sp.getString("nickname", "");
        return new User(username, password, nickname);
    }

    public static Boolean setName(Context context, String name) {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putString("saveName", name).commit();
    }

    public static String getName(Context context, String nickname) {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getString("saveName", nickname);
    }

    public static void setState(Context context, boolean state) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        sp.edit().putBoolean("isSave", state).commit();
    }
    public static void setImgState(Context context, boolean state) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        sp.edit().putBoolean("isSaveImg", state).commit();
    }
}
