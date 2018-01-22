package com.kangren.practice;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.kangren.practice.activity.AnimationActivity;
import com.kangren.practice.activity.ChannelCategoryActivity;
import com.kangren.practice.activity.CircleActivity;
import com.kangren.practice.activity.CustomActivity;
import com.kangren.practice.activity.DrawableActivity;
import com.kangren.practice.activity.FloatingButtonActivity;
import com.kangren.practice.activity.Html5Activity;
import com.kangren.practice.activity.MyAssetActivity;
import com.kangren.practice.activity.TexiaotuActivity;
import com.kangren.practice.activity.ViewDrawActivity;
import com.kangren.practice.activity.ViewGroupActivity;
import com.kangren.practice.activity.ZanPlayActivity;
import com.kangren.practice.databinding.BindingActivity;

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

    @Override
    protected void onResume() {
        super.onResume();
        //强制竖屏
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void init()
    {
        findViewById(R.id.drawable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(DrawableActivity.class);
            }
        });
        findViewById(R.id.danmu_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ZanPlayActivity.class);
            }
        });
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
        findViewById(R.id.animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(AnimationActivity.class);
            }
        });
    }

    private void goActivity(Class activity)
    {
        startActivity(new Intent(ViewActivity.this, activity));
    }
}
