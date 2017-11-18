package com.kangren.okhttp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.kangren.okhttp.Info;
import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/11/15.
 */

public class ViewDrawActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_draw);
        Log.d("kang", Info.i + "");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x;
        float y;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                int slop = ViewConfiguration.get(this).getScaledTouchSlop();
                Log.d("kang", slop + "");
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(event);
    }
}
