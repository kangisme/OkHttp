package com.kangren.okhttp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kangren.okhttp.R;
import com.kangren.okhttp.util.SystemUtil;

/**
 * Created by kangren on 2017/11/27.
 */

public class SystemInfoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systeminfo);
        init();
    }

    private void init()
    {
        showSystemParameter();
    }

    private void showSystemParameter() {
        String TAG = "系统参数：";
        Log.e(TAG, "手机厂商：" + SystemUtil.getDeviceBrand());
        Log.e(TAG, "手机型号：" + SystemUtil.getSystemModel());
        Log.e(TAG, "手机当前系统语言：" + SystemUtil.getSystemLanguage());
        Log.e(TAG, "Android系统版本号：" + SystemUtil.getSystemVersion());
        Log.e(TAG, "手机IMEI：" + SystemUtil.getIMEI(getApplicationContext()));
    }
}
