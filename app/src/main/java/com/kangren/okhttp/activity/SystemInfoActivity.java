package com.kangren.okhttp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

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
        String info = "系统参数\n" + "手机厂商：" + SystemUtil.getDeviceBrand() + "\n手机型号：" + SystemUtil.getSystemModel()
                + "\n手机当前系统语言：" + SystemUtil.getSystemLanguage() + "\nAndroid系统版本号：" + SystemUtil.getSystemVersion()
                + "\n手机IMEI：" + SystemUtil.getIMEI(getApplicationContext());
        ((TextView) findViewById(R.id.system_info_text)).setText(info);
    }

}
