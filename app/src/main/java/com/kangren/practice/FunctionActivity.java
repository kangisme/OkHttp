package com.kangren.practice;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.kangren.practice.activity.FingerprintActivity;
import com.kangren.practice.activity.ListViewActivity;
import com.kangren.practice.activity.RecyclerViewActivity;
import com.kangren.practice.activity.SpannableActivity;
import com.kangren.practice.activity.EventBusActivity;
import com.kangren.practice.activity.OkhttpActivity;
import com.kangren.practice.activity.RxAndroidActivity;
import com.kangren.practice.activity.SharePreferencesActivity;
import com.kangren.practice.activity.SystemInfoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dalvik.system.DexClassLoader;

/**
 * Created by kangren on 2017/12/2.
 */

public class FunctionActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.list_view) void listView()
    {
        goActivity(ListViewActivity.class);
    }
    @OnClick(R.id.recycler_view) void recycle()
    {
        goActivity(RecyclerViewActivity.class);
    }
    @OnClick(R.id.okhttp) void okhttp()
    {
        goActivity(OkhttpActivity.class);
    }
    @OnClick(R.id.event_bus) void eventBus()
    {
        goActivity(EventBusActivity.class);
    }
    @OnClick(R.id.rx_android) void rxAndroid()
    {
        goActivity(RxAndroidActivity.class);
    }
    @OnClick(R.id.system_info) void systemInfo()
    {
        goActivity(SystemInfoActivity.class);
    }
    @OnClick(R.id.shared_preferences) void shared()
    {
        goActivity(SharePreferencesActivity.class);
    }
    @OnClick(R.id.spannable) void spannable(){
        goActivity(SpannableActivity.class);
    }
    @OnClick(R.id.fingerprint) void fingerprint()
    {
        goActivity(FingerprintActivity.class);
    }
    @OnClick(R.id.dexclassloader) void dexclassloader()
    {
        String apkPath = Environment.getExternalStorageDirectory() + "/hello.apk";
        loadApk(apkPath);
    }

    /**
     * DexClassLoader加载其它apk文件中的类
     * @param apkPath apk文件路径
     */
    private void loadApk(String apkPath) {
        File dexOpt = this.getDir("dexOpt", MODE_PRIVATE);
        final DexClassLoader classloader = new DexClassLoader(apkPath, dexOpt.getAbsolutePath(), null, this.getClassLoader());
        try {
            Class<?> classToLoad = (Class<?>)classloader.loadClass("com.kang.classtobeload.ClassToBeLoad");
            Object instance = classToLoad.newInstance();
            Method method = classToLoad.getMethod("hello");
            method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goActivity(Class activity)
    {
        startActivity(new Intent(FunctionActivity.this, activity));
    }
}
