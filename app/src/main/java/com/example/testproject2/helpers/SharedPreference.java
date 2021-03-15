package com.example.testproject2.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testproject2.models.User;

public class SharedPreference {
    private static SharedPreference mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "salesZingSharedprefretrofit";

    private static final String KEY_USER_ID = "keyuserid";

    private static final String KEY_USER_EMAIL = "keyuseremail";


    private SharedPreference(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreference(context);
        }
        return mInstance;
    }

    public boolean userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, user.getToken());
        editor.putString(KEY_USER_EMAIL, user.getEmailId());
        editor.putString("pass", user.getPassword());

        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
            return true;
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USER_EMAIL, null),

                sharedPreferences.getString("pass", "pass"),

                sharedPreferences.getString(KEY_USER_ID, null)

        );
    }


    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
}
