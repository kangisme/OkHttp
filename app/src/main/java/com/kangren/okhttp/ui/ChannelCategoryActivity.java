package com.kangren.okhttp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/11/8.
 */

public class ChannelCategoryActivity extends Activity {
    private FrameLayout layout;
    private boolean isPlaying;
    View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        layout = (FrameLayout) findViewById(R.id.paly_content);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                Log.d("kang", "x:" + x + "y:" + y);
                createView(x, y, x, y + 800);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void createView(float x, float y, float ex, float ey)
    {
        if (!isPlaying)
        {
            isPlaying = true;
            view = View.inflate(this, R.layout.drag_view_item, null);
            layout.addView(view);
            TranslateAnimation animation = new TranslateAnimation(x, ex, y, ey);
            animation.setDuration(1000);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layout.removeView(view);
                    view = null;
                    isPlaying = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.setAnimation(animation);
            animation.startNow();
        }
    }
}
