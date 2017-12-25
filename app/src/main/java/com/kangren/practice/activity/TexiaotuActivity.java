package com.kangren.practice.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kangren.practice.R;

/**
 * 特效图——pp搜索特殊词特效需求
 * Created by kangren on 2017/12/21.
 */

public class TexiaotuActivity extends Activity {

    private WebView webView;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0)
            {
                webView.setVisibility(View.GONE);
            }
            return false;
        }
    });

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texiaotu);
        webView = (WebView) findViewById(R.id.web_view);

        webView.setWebViewClient(new WebViewClient());

        //必须设置，Javascript的alertMessage方法才能显示
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        //设置为可调用js方法
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/texiaotu.html");

        webView.addJavascriptInterface(new TexiaotuCall(), "android");

    }

    public class TexiaotuCall {
        @JavascriptInterface
        public void stop()
        {
            handler.sendEmptyMessage(0);
        }
    }
}
