package com.kangren.practice.util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kangren on 2017/12/11.
 */

public class BaiduAPI {

    private final static String APP_ID = "20171209000103466";

    private final static String KEY = "hGN8BLRS7jziyJKFjVUj";

    private final static String GET_TRANSLATION = "http://api.fanyi.baidu.com/api/trans/vip/translate?" +
            "q=%s&from=%s&to=%s&appid=%s&salt=%s&sign=%s";

    public static String getTranslated(String query, String from, String to) throws IOException {
        int salt = 1435660288;
        String string = APP_ID + query + salt + KEY;
        String sign = BaiduTranslationUtil.stringToMD5(string);
        final String url = String.format(GET_TRANSLATION, query, from, to, APP_ID, salt, sign);
        OkHttpClient okHttpClient = new OkHttpClient();
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
