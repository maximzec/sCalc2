package com.app.drink.scalc;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.widget.Button;

import static android.animation.ValueAnimator.ofFloat;

public class AnimationFunctions {


    static void AnimateDownButton(final Button b) {
        ValueAnimator animator = ofFloat(36, 30);
        animator.setDuration(125);
        animator.addUpdateListener(animation -> b.setTextSize((Float) animation.getAnimatedValue()));
        animator.start();


    }


    static void AnimateUpButton(final Button b) {
        ValueAnimator animatorTop;
        animatorTop = ofFloat(30, 40);
        ValueAnimator animatorBack;
        animatorBack = ofFloat(42, 36);
        animatorTop.setDuration(175);
        animatorTop.addUpdateListener(animation -> b.setTextSize((Float) animation.getAnimatedValue()));
        animatorBack.setDuration(125);
        animatorBack.addUpdateListener(animation -> b.setTextSize((Float) animation.getAnimatedValue()));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorBack).after(animatorTop);
        animatorSet.start();
    }

}
