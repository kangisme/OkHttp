package com.kangren.practice;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by kangren on 2017/11/15.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .tag("practice")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process: manager.getRunningAppProcesses()) {
            if(process.pid == pid)
            {
                processName = process.processName;
            }
        }
        Logger.d("application start, processName:" + processName);
    }
}
