package com.kangren.practice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kangren.practice.R;

/**
 * Created by kangren on 2017/11/1.
 */

public class ViewGroupActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
    }
}
