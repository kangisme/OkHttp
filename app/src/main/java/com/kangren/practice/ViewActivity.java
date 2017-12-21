package com.kangren.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kangren.practice.activity.ChannelCategoryActivity;
import com.kangren.practice.activity.CircleActivity;
import com.kangren.practice.activity.CustomActivity;
import com.kangren.practice.activity.FloatingButtonActivity;
import com.kangren.practice.activity.Html5Activity;
import com.kangren.practice.activity.MyAssetActivity;
import com.kangren.practice.activity.TexiaotuActivity;
import com.kangren.practice.activity.ViewDrawActivity;
import com.kangren.practice.activity.ViewGroupActivity;

/**
 * custom view test index
 * Created by kangren on 2017/12/2.
 */

public class ViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        init();
    }

    private void init()
    {
        findViewById(R.id.custom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(CustomActivity.class);
            }
        });
        findViewById(R.id.custom_view_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ViewGroupActivity.class);
            }
        });
        findViewById(R.id.channel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ChannelCategoryActivity.class);
            }
        });
        findViewById(R.id.view_draw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ViewDrawActivity.class);
            }
        });
        findViewById(R.id.circle_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(CircleActivity.class);
            }
        });
        findViewById(R.id.recycle_property).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(MyAssetActivity.class);
            }
        });
        findViewById(R.id.floating_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(FloatingButtonActivity.class);
            }
        });
        findViewById(R.id.html5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(Html5Activity.class);
            }
        });
        findViewById(R.id.texiaotu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(TexiaotuActivity.class);
            }
        });
    }

    private void goActivity(Class activity)
    {
        startActivity(new Intent(ViewActivity.this, activity));
    }
}
