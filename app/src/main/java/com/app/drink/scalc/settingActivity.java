package com.app.drink.scalc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.asha.nightowllib.NightOwl;

public class settingActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    View vLight , vDark;
    Button buttonGo;
    String theme = "light";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.builder().defaultMode(0).create();
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        NightOwl.owlAfterCreate(this);
        SharedPreferences sharedPreferences = getSharedPreferences("appSettings" , Context.MODE_PRIVATE);
        vLight = findViewById(R.id.view);
        vDark = findViewById(R.id.view6);
        buttonGo = findViewById(R.id.buttonGo);


        vLight.setOnClickListener(v ->
        {
            if(theme.equals("dark"))
            {
                NightOwl.owlNewDress(this);
                setLightStatusBar(settingActivity.this);
                theme = "light";
            }
        });


        vDark.setOnClickListener(v -> {
            if(theme.equals("light"))
            {
                NightOwl.owlNewDress(this);
                clearLightStatusBar(settingActivity.this);
                theme = "dark";
            }
        });

        buttonGo.setOnClickListener(v -> {
            startActivity(new Intent(settingActivity.this , MainActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Theme" , theme);
        editor.apply();
    }

    public static void setLightStatusBar(Activity activity) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.parseColor("#FAFAFA")); // optional

        }
    }

    public static void clearLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.BLACK);
        }
    }



}
