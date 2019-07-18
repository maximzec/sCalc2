package com.app.drink.scalc;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asha.nightowllib.NightOwl;



import static android.animation.ValueAnimator.*;

public class MainActivity extends AppCompatActivity {
    ///анимации

    ValueAnimator animatorTV = ofFloat(86, 43);


    ///вспомогательные переменные
    String theme = "light";
    int[] btnIdArray = {  R.id.btnPlus , R.id.btnMinus , R.id.btnDiv  ,R.id.btnMult, R.id.ZeroBtn ,
                          R.id.btnOne ,R.id.btnTwo  , R.id.btnThree , R.id.btnFour , R.id.btnFive ,
                          R.id.btnSix ,R.id.btnSeven, R.id.btnEight , R.id.btnNine , R.id.btnPercent ,
                           R.id.btnDot ,R.id.btnRightBracket , R.id.btnLeftBracket ,R.id.cButton , R.id.resultBtn
                          };

    SparseArray<Button> btnArray = new SparseArray<>();



    ///картинка для смены темы и внешний контейнер
    ImageView themePicture;
    RelativeLayout relativeLayout;

    ///отображатели формул
    private TextView mainText;

    HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.builder().defaultMode(0).create();
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NightOwl.owlAfterCreate(this);
        mainText = findViewById(R.id.MainText);

        for (int i1 : btnIdArray) btnArray.put(i1, findViewById(i1));

        for(int i = 0 ; i < btnArray.size() ; i++) btnArray.get(btnArray.keyAt(i)).setOnTouchListener(onTouchListener);


        horizontalScrollView = findViewById(R.id.MainHor);
        relativeLayout = findViewById(R.id.RelativeLayout);
        themePicture = findViewById(R.id.ThemePicture);
        themePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NightOwl.owlNewDress(MainActivity.this);
                if (theme.equals("light")) {
                    clearLightStatusBar(MainActivity.this);
                    theme = "dark";
                } else {
                    setLightStatusBar(MainActivity.this);
                    theme = "light";
                }

            }
        });
        horizontalScrollView.setHorizontalScrollBarEnabled(false);

    }


    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
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

    private void changeTextSize(final TextView tv) {

        animatorTV.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.setTextSize((Float) animation.getAnimatedValue());
            }
        });
        if (tv.getText().length() == 7) {
            animatorTV.start();

        }
    }


    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {


        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            String s = (String) mainText.getText();
            Button buttonClicked = getButtonbyId(v.getId());

            switch (e.getAction())
            {




                 case MotionEvent.ACTION_DOWN:
                     {
                    AnimationFunctions.AnimateDownButton(buttonClicked);
                    switch (buttonClicked.getId()) {


                        case R.id.cButton:
                            {
                                s = "";
                                mainText.setTextSize(86);
                                mainText.setText(s);
                                break;
                            }

                        case R.id.resultBtn:
                            {

                                if (s.length() != 0) {
                                    double result = ExpressionParser.calc(ExpressionParser.parse((String) mainText.getText()));
                                    double fractionPart = result % Math.floor(result);
                                    if (fractionPart == 0) {
                                        mainText.setText(String.format("%.0f", result));
                                    } else {
                                        mainText.setText(String.valueOf(result));
                                    }

                                } else {
                                    s += '0';
                                }
                                break;
                            }

                        default:
                            {
                                s += buttonClicked.getText();
                                mainText.setText(s);
                                break;
                            }
                    }
                    changeTextSize(mainText);
                    break;
                }


                case MotionEvent.ACTION_UP: {
                    AnimationFunctions.AnimateUpButton(buttonClicked);
                    break;
                }
            }

            return false;
        }


    };
}
