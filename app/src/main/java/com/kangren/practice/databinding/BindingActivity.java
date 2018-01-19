package com.kangren.practice.databinding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kangren.practice.R;
import com.kangren.practice.databinding.ActivityBindingBinding;

/**
 * Created by kangren on 2017/12/29.
 */

public class BindingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_binding);
        User user = new User("kang", "ren");
        binding.setUser(user);
        binding.setHandler(new MyHandler());
    }


}
