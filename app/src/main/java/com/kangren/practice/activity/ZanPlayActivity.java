package com.kangren.practice.activity;

import com.kangren.practice.DanmuZanLayout;
import com.kangren.practice.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by kangren on 2017/10/26.
 */

public class ZanPlayActivity extends Activity {
    private DanmuZanLayout zanLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //强制横屏
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_zanplay);
        init();
    }
    private void init()
    {
        zanLayout = (DanmuZanLayout) findViewById(R.id.zan_play);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN)
//        {
            float x = event.getX();
            float y = event.getY();
            PointF xy = new PointF(x, y);
            zanLayout.play(xy);
//        }
        return super.onTouchEvent(event);
    }
}
