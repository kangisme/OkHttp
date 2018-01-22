package com.kangren.practice.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kangren.practice.R;

/**
 * Created by kangren on 2018/1/22.
 */

public class AnimationActivity extends Activity implements View.OnClickListener{

    private Button viewAnima;
    private Button reset;
    private Button valueAnima;
    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        initUI();
    }

    private void initUI(){
        viewAnima = (Button)findViewById(R.id.view_animation);
        viewAnima.setOnClickListener(this);
        valueAnima = (Button)findViewById(R.id.value_animation);
        valueAnima.setOnClickListener(this);
        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(this);
        text = (TextView)findViewById(R.id.item);
        text.setOnClickListener(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)text.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        params.setMargins(0, 0, 0, 0);
        text.setLayoutParams(params);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_animation:
                playAnim1();
                break;
            case R.id.value_animation:
                playAnim2();
                break;
            case R.id.reset:
                resetPos();
                break;
            case R.id.item:
                printParams();
                break;
            default:break;
        }
    }

    private void printParams(){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)text.getLayoutParams();
        if(params != null){
            String s =  "leftMargin = " + params.leftMargin + " rightMargin = " + params.rightMargin
                    + " getLeft = " + text.getLeft() + " getRight = " + text.getRight() + " getWidth = " + text.getWidth();
            int[] pos = new int[2];
            text.getLocationInWindow(pos);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }
    private void playAnim2(){
        int w = text.getWidth();
        int screenW = getResources().getDisplayMetrics().widthPixels;
        int transX = screenW - w;
        ObjectAnimator transAnim = ObjectAnimator.ofFloat(text, "translationX", 0, transX);
        transAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        transAnim.setDuration(3000);
        transAnim.start();;
    }

    private void playAnim1(){
        int w = text.getWidth();
        int screenW = getResources().getDisplayMetrics().widthPixels;
        int transX = screenW - w;
        TranslateAnimation transAnim = new TranslateAnimation(0, transX, 0, 0);
        transAnim.setDuration(3000);
        //设置动画结束后动画效果一直存在
//        transAnim.setFillAfter(true);
        text.startAnimation(transAnim);
    }

    private void resetPos(){
        text.setTranslationX(0);
    }
}
