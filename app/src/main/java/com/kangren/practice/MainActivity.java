package com.kangren.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.kangren.practice.databinding.BindingActivity;
import com.kangren.practice.translation.BaiduTranslateActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * first activity in whole project Created by kangren on 2017/12/2.
 */

public class MainActivity extends Activity
{
    @BindView(R.id.custom_view)
    Button customView;

    @BindView(R.id.function_test)
    Button functionTest;

    @BindView(R.id.baidu_translate)
    Button baiduTranslate;

    @BindView(R.id.data_binding)
    Button dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.custom_view, R.id.function_test, R.id.baidu_translate, R.id.data_binding})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.custom_view:
                startActivity(new Intent(MainActivity.this, ViewActivity.class));
                break;
            case R.id.function_test:
                startActivity(new Intent(MainActivity.this, FunctionActivity.class));
                break;
            case R.id.baidu_translate:
                startActivity(new Intent(MainActivity.this, BaiduTranslateActivity.class));
                break;
            case R.id.data_binding:
                startActivity(new Intent(MainActivity.this, BindingActivity.class));
                break;
        }
    }
}
