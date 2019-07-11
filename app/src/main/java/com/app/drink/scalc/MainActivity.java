package com.app.drink.scalc;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asha.nightowllib.NightOwl;

public class MainActivity extends AppCompatActivity {
    ///анимации

    ValueAnimator animatorTV = ValueAnimator.ofFloat(86,43);


    ///вспомогательные переменные
    String theme = "light";


    ///картинка для смены темы и внешний контейнер
    ImageView themePicture;
    RelativeLayout relativeLayout;

    ///отображатели формул
    private TextView mainText ;
    private TextView subText;

    ///кнопки
    Button btnPlus;
    Button btnMinus ;
    Button btnDiv ;
    Button btnMult ;

    Button btnZero;
    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button btnFive;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine ;

    Button btnPercent;
    Button btnDot;

    Button btnRBracket;
    Button btnLBracket;

    Button cButton;
    Button rButton;


    ExpressionParser expressionParser = new ExpressionParser();
    HorizontalScrollView horizontalScrollView;
    public static void setLightStatusBar(Activity activity){


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
    private void AnimateButton(final Button b)
    {
        ValueAnimator animator = ValueAnimator.ofFloat(36,34);
        ValueAnimator animatorTop = ValueAnimator.ofFloat(34,38);
        ValueAnimator animatorBack = ValueAnimator.ofFloat(38,36);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                b.setTextSize((Float)animation.getAnimatedValue());
            }
        });


        animatorTop.setDuration(300);
        animatorTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                b.setTextSize((Float)animation.getAnimatedValue());
            }
        });


        animatorBack.setDuration(150);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                b.setTextSize((Float)animation.getAnimatedValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorBack).after(animatorTop).after(animator);
        animatorSet.start();

    }

    private Button getButtonbyId(int id)
    {
        Button b = null;
        switch(id)
        {
            case R.id.btnMinus:b =  btnMinus;break;
            case R.id.btnPlus:b =  btnPlus;break;
            case R.id.btnMult:b =  btnMult;break;
            case R.id.btnDiv:b = btnDiv;break;
            case R.id.ZeroBtn:b =  btnZero;break;
            case R.id.btnOne:b = btnOne;break;
            case R.id.btnTwo:b = btnTwo;break;
            case R.id.btnThree:b = btnThree;break;
            case R.id.btnFour:b = btnFour;break;
            case R.id.btnFive:b = btnFive;break;
            case R.id.btnSix:b = btnSix;break;
            case R.id.btnSeven:b = btnSeven;break;
            case R.id.btnEight:b = btnEight;break;
            case R.id.btnNine:b = btnNine;break;
            case R.id.btnRightBracket:b = btnRBracket;break;
            case R.id.btnLeftBracket:b = btnLBracket;break;
            case R.id.cButton:b = cButton;break;
            case R.id.resultBtn:b = rButton;break;
        }
    return b;
    }
    private void changeTextSize(final TextView tv)
    {

        animatorTV.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.setTextSize((Float)animation.getAnimatedValue());
            }
        });
        if (tv.getText().length() == 7)
        {
            animatorTV.start();

        }
    }



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v ) {
            String s = (String)mainText.getText();
            Button buttonClicked = getButtonbyId(v.getId());
            AnimateButton(buttonClicked);
            switch (buttonClicked.getId())
            {
                case R.id.cButton:
                {
                    s="";
                    mainText.setTextSize(86);
                    mainText.setText(s);
                    subText.setText(s);
                    break;
                }
                case R.id.resultBtn:
                {
                    mainText.setText(String.valueOf( expressionParser.calc(expressionParser.parse((String) mainText.getText()))));
                    break;
                }
                default:
                {
                    s+=buttonClicked.getText();
                    mainText.setText(s);
                    break;
                }
            }
            changeTextSize(mainText);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.builder().defaultMode(0).create();
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NightOwl.owlAfterCreate(this);
        mainText = findViewById(R.id.MainText);
        subText = findViewById(R.id.subText);
        horizontalScrollView = findViewById(R.id.MainHor);

        horizontalScrollView.setHorizontalScrollBarEnabled(false);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDiv = findViewById(R.id.btnDiv);
        btnMult = findViewById(R.id.btnMult);

        btnZero = findViewById(R.id.ZeroBtn);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);

        btnPercent = findViewById(R.id.btnPercent);
        btnDot = findViewById(R.id.btnDot);

        btnRBracket = findViewById(R.id.btnRightBracket);
        btnLBracket = findViewById(R.id.btnLeftBracket);

        cButton = findViewById(R.id.cButton);
        rButton = findViewById(R.id.resultBtn);
        relativeLayout = findViewById(R.id.RelativeLayout);
        themePicture = findViewById(R.id.ThemePicture);
        themePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              NightOwl.owlNewDress(MainActivity.this);
              if(theme.equals("light")) {
                  clearLightStatusBar(MainActivity.this);
                  theme = "dark";
              }else
              {
                  setLightStatusBar(MainActivity.this);
                  theme = "light";
              }

            }
        });
        btnDiv.setOnClickListener(onClickListener);
        btnMinus.setOnClickListener(onClickListener);
        btnMult.setOnClickListener(onClickListener);
        btnPlus.setOnClickListener(onClickListener);
        btnOne.setOnClickListener(onClickListener);
        btnTwo.setOnClickListener(onClickListener);
        btnThree.setOnClickListener(onClickListener);
        btnFour.setOnClickListener(onClickListener);
        btnFive.setOnClickListener(onClickListener);
        btnSix.setOnClickListener(onClickListener);
        btnSeven.setOnClickListener(onClickListener);
        btnEight.setOnClickListener(onClickListener);
        btnNine.setOnClickListener(onClickListener);
        btnZero.setOnClickListener(onClickListener);
        btnLBracket.setOnClickListener(onClickListener);
        btnRBracket.setOnClickListener(onClickListener);

        cButton.setOnClickListener(onClickListener);
        rButton.setOnClickListener(onClickListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }
}
