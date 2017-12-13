package com.kangren.okhttp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/12/13.
 */

public class FloatingButtonActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button);
        findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingButtonActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
