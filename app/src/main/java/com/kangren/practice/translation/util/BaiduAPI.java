package com.kangren.practice.translation.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.kangren.practice.translation.util.BaiduTranslationUtil;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 百度翻译API
 * Created by kangren on 2017/12/11.
 */

public class BaiduAPI {

    private final static String APP_ID = "20171209000103466";

    private final static String KEY = "hGN8BLRS7jziyJKFjVUj";

    //百度翻译API
    private final static String GET_TRANSLATION = "http://api.fanyi.baidu.com/api/trans/vip/translate?" +
            "q=%s&from=%s&to=%s&appid=%s&salt=%s&sign=%s";

    public static String getTranslated(String query, String from, String to) throws IOException {
        //salt为api请求随机数，这里使用当前日期
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateString = format.format(new Date());
        int salt = Integer.parseInt(dateString);
        String string = APP_ID + query + salt + KEY;
        String sign = BaiduTranslationUtil.stringToMD5(string);
        final String url = String.format(GET_TRANSLATION, query, from, to, APP_ID, salt, sign);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else
        {
            throw new IOException("error");
        }
    }
}
