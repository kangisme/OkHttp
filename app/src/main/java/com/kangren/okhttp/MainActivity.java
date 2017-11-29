package com.kangren.okhttp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kangren.okhttp.ui.ChannelCategoryActivity;
import com.kangren.okhttp.ui.CircleActivity;
import com.kangren.okhttp.ui.CustomActivity;
import com.kangren.okhttp.ui.EventBusActivity;
import com.kangren.okhttp.ui.OkhttpActivity;
import com.kangren.okhttp.ui.RxAndroidActivity;
import com.kangren.okhttp.ui.SerializableActivity;
import com.kangren.okhttp.ui.SystemInfoActivity;
import com.kangren.okhttp.ui.ViewDrawActivity;
import com.kangren.okhttp.ui.ViewGroupActivity;
import com.kangren.okhttp.view.MyAppWidgetProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Info.i = 2;
    }

    private void init()
    {
        findViewById(R.id.okhttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(OkhttpActivity.class);
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
        findViewById(R.id.serializable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item(2);
                Intent intent = new Intent(MainActivity.this, SerializableActivity.class);
                intent.putExtra("kang", item);
                startActivity(intent);
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
    }

    private void goActivity(Class activity)
    {
        startActivity(new Intent(MainActivity.this, activity));
    }
}
