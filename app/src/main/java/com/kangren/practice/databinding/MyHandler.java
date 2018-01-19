package com.kangren.practice.databinding;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by kangren on 2018/1/2.
 */

public class MyHandler {
    public void onClickOne(View view)
    {
        Log.d("kang", "binding test!!!");
        Toast.makeText(view.getContext(), "Click", Toast.LENGTH_SHORT).show();
    }
}
