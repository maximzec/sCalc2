package com.app.drink.scalc;


import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asha.nightowllib.NightOwl;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static android.animation.ValueAnimator.*;

public class MainActivity extends AppCompatActivity {
    ///анимации

    ValueAnimator animatorTV = ofFloat(86, 43);
    StringValidator stringValidator = new StringValidator();
    boolean checkTheme = true;
    ///вспомогательные переменные
    String theme;
    int[] btnIdArray = {  R.id.btnPlus , R.id.btnMinus , R.id.btnDiv  ,R.id.btnMult, R.id.ZeroBtn ,
                          R.id.btnOne ,R.id.btnTwo  , R.id.btnThree , R.id.btnFour , R.id.btnFive ,
                          R.id.btnSix ,R.id.btnSeven, R.id.btnEight , R.id.btnNine , R.id.btnPercent ,
                           R.id.btnDot ,R.id.btnRightBracket , R.id.btnLeftBracket , R.id.resultBtn ,R.id.cButton
                          };
    SharedPreferences sharedPreferences;

    SparseArray<Button> btnArray = new SparseArray<>();



    ///картинка для смены темы и внешний контейнер
    ImageView themePicture;

    ///отображатели формул
    private RightAlignedEditText mainText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.builder().defaultMode(0).create();
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NightOwl.owlAfterCreate(this);

        mainText = findViewById(R.id.MainText);
        mainText.setEnabled(true);
        mainText.setKeyListener(null);


        for (int i1 : btnIdArray) btnArray.put(i1, findViewById(i1));

        for(int i = 0 ; i < btnArray.size() ; i++) btnArray.get(btnArray.keyAt(i)).setOnTouchListener(onTouchListener);

        sharedPreferences = getSharedPreferences("appSettings" , Context.MODE_PRIVATE);

        theme = sharedPreferences.getString("Theme" , "light");


        themePicture = findViewById(R.id.ThemePicture);
        themePicture.setOnClickListener(v -> {

            NightOwl.owlNewDress(MainActivity.this);
            if (theme.equals("light")) {
                clearLightStatusBar(MainActivity.this);
                mainText.setHintTextColor(Color.parseColor("#E0E0E0"));
                theme = "dark";
            } else {
                setLightStatusBar(MainActivity.this);
                mainText.setHintTextColor(Color.parseColor("#263238"));
                theme = "light";
            }

        });

        /*
        if(checkTheme)
        {
            if(theme.equals("dark"))
            {
                themePicture.performClick();
            }
        }
        */
    }


    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Theme" , theme);
        editor.apply();
        super.onDestroy();
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

    private Button getButtonbyId(int id) {
        return btnArray.get(id);
    }


    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {


        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            String s = "" + mainText.getText();
            Button buttonClicked = getButtonbyId(v.getId());
            switch (e.getAction())
            {




                 case MotionEvent.ACTION_DOWN:
                     {

                    AnimationFunctions.AnimateDownButton(buttonClicked);
                    switch (buttonClicked.getId()) {




                        case R.id.resultBtn:
                            {

                                if (s.length() != 0) {
                                    if(s.contains(","))
                                    {
                                        s = s.replace(',', '.');
                                    }
                                    double result = ExpressionParser.calc(ExpressionParser.parse(s));
                                    double fractionPart = result % Math.floor(result);
                                    if (fractionPart == 0) {
                                        mainText.setText(String.format("%.0f", result));
                                    } else {
                                        s = String.valueOf(result);
                                        s = s.replace('.' , ',');
                                        mainText.setText(s);
                                    }

                                } else {
                                    s += '0';
                                }
                                s = s.replace('.' , ',');
                                break;
                            }
                        case R.id.btnPercent:
                        {
                            if(s.length()!= 0)
                            {
                                s = s.replace(',' , '.');
                                double result = ExpressionParser.calc(ExpressionParser.parse(s)) / 100;
                                double fractionPart = result % Math.floor(result);
                                if(fractionPart == 0) mainText.setText(String.format("%0.f", result));
                                else
                                {
                                    s = String.valueOf(result);
                                    s = s.replace('.' , ',');
                                    mainText.setText(s);

                                }

                            }
                            break;
                        }

                        case R.id.btnLeftBracket:
                        {
                            if(stringValidator.isLeftBracketAvaible(s))
                            {
                                s+=buttonClicked.getText();
                                mainText.setText(s);
                            }
                            break;
                        }

                        case R.id.btnRightBracket:
                        {
                            if(stringValidator.isRightBracketAvaible(s))
                            {
                                s+=buttonClicked.getText();
                                mainText.setText(s);
                            }
                            break;
                        }
                        case R.id.btnDot:
                        {
                            if(s.length()!=0)
                            {
                                s+=buttonClicked.getText();
                                if(s.charAt(s.length()-1) == ',') s = stringValidator.checkStringByDot(s);
                                mainText.setText(s);
                            }
                            else {
                                s+='0';
                                s+=',';
                                mainText.setText(s);
                            }
                            break;
                        }

                        default:
                            {
                                if(buttonClicked.getId() != R.id.cButton) {
                                    s += buttonClicked.getText();
                                    if(stringValidator.getActionTokens().contains(s.charAt(s.length()-1))) s = stringValidator.checkStringByActions(s);
                                    mainText.setText(s);

                                }
                                break;

                            }
                    }

                    break;
                }


                case MotionEvent.ACTION_UP: {
                {
                    if(buttonClicked.getId() == R.id.cButton) {
                        if(e.getEventTime() - e.getDownTime() > 1000) {
                            s = "";
                            mainText.setTextSize(86);
                            mainText.setText(s);

                        } else
                        {
                            if(s.length()!=0) {
                                s = s.substring(0, s.length() - 1);
                                mainText.setText(s);
                            }
                        }
                    }
                }
                    AnimationFunctions.AnimateUpButton(buttonClicked);
                    break;
                }
            }

            return false;
        }


    };



}
