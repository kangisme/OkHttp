package com.kangren.okhttp.activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/11/23.
 */

public class EventBusActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        //注册EventBus
        EventBus.getDefault().register(this);
        findViewById(R.id.next_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventBusActivity.this, NextActivity.class));
            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvetMaind(String event) {

        String msg = event;
        TextView textView = (TextView) findViewById(R.id.event_bus_result);
        textView.setText(event);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }
}
