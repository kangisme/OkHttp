package com.kangren.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kangren.practice.activity.BaiduTranslateActivity;
import com.kangren.practice.activity.SpannableActivity;
import com.kangren.practice.activity.EventBusActivity;
import com.kangren.practice.activity.OkhttpActivity;
import com.kangren.practice.activity.RxAndroidActivity;
import com.kangren.practice.activity.SharePreferencesActivity;
import com.kangren.practice.activity.SystemInfoActivity;

/**
 * Created by kangren on 2017/12/2.
 */

public class FunctionActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        init();
    }

    private void init() {
        findViewById(R.id.okhttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(OkhttpActivity.class);
            }
        });
        findViewById(R.id.event_bus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(EventBusActivity.class);
            }
        });
        findViewById(R.id.rx_android).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(RxAndroidActivity.class);
            }
        });
        findViewById(R.id.system_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(SystemInfoActivity.class);
            }
        });
        findViewById(R.id.shared_preferences).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(SharePreferencesActivity.class);
            }
        });
        findViewById(R.id.spannable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(SpannableActivity.class);
            }
        });
        findViewById(R.id.baidu_translate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(BaiduTranslateActivity.class);
            }
        });
    }

    private void goActivity(Class activity)
    {
        startActivity(new Intent(FunctionActivity.this, activity));
    }
}
