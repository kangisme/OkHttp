package com.kangren.okhttp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.kangren.okhttp.Item;
import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/11/8.
 */

public class SerializableActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable);
        textView = (TextView) findViewById(R.id.result);
        Intent intent = getIntent();
        if (intent != null)
        {
            Item item = (Item) intent.getSerializableExtra("kang");
            textView.setText(item.j + "");
        }
    }
}
