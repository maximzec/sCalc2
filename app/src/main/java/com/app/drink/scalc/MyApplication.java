package com.app.drink.scalc;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.asha.nightowllib.NightOwl;
import com.asha.nightowllib.observer.IOwlObserver;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        SharedPreferences preferences = getSharedPreferences("appSettings",Activity.MODE_PRIVATE);
        int mode = preferences.getInt("mode",0);
        NightOwl.builder().subscribedBy(new SkinObserver()).defaultMode(mode).create();

    }


    public static class SkinObserver implements IOwlObserver
    {

        @Override
        public void onSkinChange(int mode, Activity activity) {
            SharedPreferences preferences = activity.getSharedPreferences("appSettings",
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("mode", mode);
            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        }
    }
}
