package com.kangren.okhttp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/11/1.
 */

public class CustomActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        init();
    }

    private void init()
    {

    }
}
