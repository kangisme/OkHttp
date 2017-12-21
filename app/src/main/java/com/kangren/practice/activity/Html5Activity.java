package com.kangren.practice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.kangren.practice.R;

/**
 * Android与HTML5交互
 * Created by kangren on 2017/12/21.
 */

public class Html5Activity extends Activity {

    private WebView webView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html5);

        webView = (WebView) findViewById(R.id.web_view);

        webView.setWebViewClient(new WebViewClient());

        //必须设置，Javascript的alertMessage方法才能显示
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        //设置为可调用js方法
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/html5.html");

        webView.addJavascriptInterface(new JsCall(), "android");
    }

    public void onClick1(View v)
    {
        //直接访问H5里不带返回值的方法
        webView.loadUrl("JavaScript:show()");

        //访问H5里带参数不带返回值的方法，固定字符串采用单引号隔开
        webView.loadUrl("javascript:alertMessage('哈哈')");

        //当出入变量名时，需要用转义符隔开
        String content="9880";
        webView.loadUrl("javascript:alertMessage(\""   +content+   "\")"   );
    }

    public void onClick2(View v)
    {
        webView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(Html5Activity.this,"js返回的结果为=" + value,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class JsCall{

        //注解必须写
        @JavascriptInterface
        public String back()
        {

            return "Android return back";
        }
    }
}
