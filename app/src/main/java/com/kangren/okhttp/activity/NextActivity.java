package com.kangren.okhttp.activity;

import org.greenrobot.eventbus.EventBus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/11/23.
 */

public class NextActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        findViewById(R.id.send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("来自NextActivity的消息");
            }
        });
    }
}
