package com.kangren.practice;

import org.litepal.LitePalApplication;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by kangren on 2017/11/15.
 */

public class MyApplication extends LitePalApplication {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process: manager.getRunningAppProcesses()) {
            if(process.pid == pid)
            {
                processName = process.processName;
            }
        }
        Log.d(TAG, "application start, processName:" + processName);
    }
}
