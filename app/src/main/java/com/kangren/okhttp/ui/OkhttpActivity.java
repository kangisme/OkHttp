package com.kangren.okhttp.ui;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kangren.okhttp.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by kangren on 2017/10/31.
 */

public class OkhttpActivity extends Activity {
    private TextView textView;

    private Button syncButton;

    private Button asynButton;

    private Button asynPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        initView();
    }

    private void initView() {
        syncButton = (Button) findViewById(R.id.sync_http);
        asynButton = (Button) findViewById(R.id.asyn_http);
        textView = (TextView) findViewById(R.id.okhttp_text);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSync();
            }
        });
        asynButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAsyn();
            }
        });
        asynPost = (Button) findViewById(R.id.asyn_post);
        asynPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAsynHttp();
            }
        });
    }

    private void initSync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    final String result = getSyncHttp();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(result);
                        }
                    });
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void postAsynHttp() {
        OkHttpClient mOkHttpClient=new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = new Request.Builder()
                //png图
                .url("http://img.blog.csdn.net/20160420015144350")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(str);
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 同步请求
     * @return
     * @throws IOException
     */
    private String getSyncHttp() throws IOException{
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建请求Request
        final Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response mResponse=call.execute();
        if (mResponse.isSuccessful()) {
            return mResponse.body().string();
        } else {
            throw new IOException("Unexpected code " + mResponse);
        }
    }

    private void initAsyn()
    {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String htmlStr =  response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(htmlStr);
                    }
                });
            }
        });
    }
}
