package com.kangren.okhttp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

import com.kangren.okhttp.R;
import com.kangren.okhttp.view.SelectPopupWindow;

/**
 * Created by kangren on 2017/12/9.
 */

public class BaiduTranslateActivity extends Activity {

    private SelectPopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);
        findViewById(R.id.select_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = new SelectPopupWindow(BaiduTranslateActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                popupWindow.showAtLocation(BaiduTranslateActivity.this.findViewById(R.id.main_layout),
                        Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });
    }
}
