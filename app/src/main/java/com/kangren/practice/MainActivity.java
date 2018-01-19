package com.kangren.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kangren.practice.databinding.BindingActivity;
import com.kangren.practice.litepal.LitePalActivity;
import com.kangren.practice.translation.BaiduTranslateActivity;

/**
 * first activity in whole project
 * Created by kangren on 2017/12/2.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.custom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewActivity.class));
            }
        });
        findViewById(R.id.function_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FunctionActivity.class));
            }
        });
        findViewById(R.id.baidu_translate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BaiduTranslateActivity.class));
            }
        });
        findViewById(R.id.lite_pal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LitePalActivity.class));
            }
        });
        findViewById(R.id.data_binding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BindingActivity.class));
            }
        });
    }
}
