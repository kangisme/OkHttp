package com.kangren.practice.activity;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kangren.practice.R;
import com.kangren.practice.model.ResultBean;
import com.kangren.practice.model.TransResultBean;
import com.kangren.practice.util.BaiduAPI;
import com.kangren.practice.view.SelectPopupWindow;

/**
 * Created by kangren on 2017/12/9.
 */

public class BaiduTranslateActivity extends Activity implements View.OnClickListener{

    private final static int MSG_TRANSLATION_RESULT = 0;

    private final static String CHINESE = "zh";

    private final static String ENGLISH = "en";

    private final static String JAPANESE = "jp";

    /**
     * 默认源语言
     */
    private String from;

    /**
     * 默认翻译语言
     */
    private String to;

    private TextView originalText;

    private TextView originalLanguage;

    private TextView translatedText;

    private TextView translatedLanguage;

    private TextView translate;

    /**
     * 选择语言弹窗
     */
    private SelectPopupWindow popupWindow;

    private Gson gson;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG_TRANSLATION_RESULT:
                    String result = (String) msg.obj;
                    ResultBean resultBean = gson.fromJson(result, ResultBean.class);
                    if (resultBean != null && resultBean.getTransResult() != null && resultBean.getTransResult().size() > 0)
                    {
                        TransResultBean bean = resultBean.getTransResult().get(0);
                        //防止翻译过程中退出Activity，translatedText被释放
                        if (translatedText != null)
                        {
                            translatedText.setText(bean.getDst());
                        }
                        if (originalLanguage != null)
                        {
                            originalLanguage.setText(getLanguage(resultBean.getFrom()));
                        }
                    }
            }
            return true;
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);
        from = "auto";
        to = ENGLISH;
        gson = new Gson();
        initView();
        initPopupView();
    }

    private void initView() {
        originalLanguage = (TextView) findViewById(R.id.original_text_kind);
        originalText = (TextView) findViewById(R.id.original_text);
        translatedLanguage = (TextView) findViewById(R.id.select_language);
        translatedText = (TextView) findViewById(R.id.translated_text);
        translate = (TextView) findViewById(R.id.translate);
        originalLanguage.setOnClickListener(this);
        originalText.setOnClickListener(this);
        translate.setOnClickListener(this);
        translatedText.setOnClickListener(this);
        translatedLanguage.setOnClickListener(this);
    }

    private void initPopupView() {
        findViewById(R.id.select_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = new SelectPopupWindow(BaiduTranslateActivity.this, BaiduTranslateActivity.this);
                popupWindow.showAtLocation(BaiduTranslateActivity.this.findViewById(R.id.main_layout),
                        Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.chinese:
                showTranslatedLanguage(CHINESE);
                break;
            case R.id.english:
                showTranslatedLanguage(ENGLISH);
                break;
            case R.id.japanese:
                showTranslatedLanguage(JAPANESE);
                break;
            case R.id.original_text_kind:
                break;
            case R.id.original_text:
                break;
            case R.id.translate:
                final String query = originalText.getText().toString();
                if (TextUtils.isEmpty(query))
                {
                    Toast.makeText(this, "请输入待翻译的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           final String result = BaiduAPI.getTranslated(query, from, to);
                           Message message = new Message();
                           message.what = MSG_TRANSLATION_RESULT;
                           message.obj = result;
                           handler.sendMessage(message);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.select_language:
                break;
            case R.id.translated_text:
                break;
            default:
                break;
        }
    }



    private void showTranslatedLanguage(String language) {
        if (language == null)
        {
            return;
        }
        to = language;
        if (popupWindow != null && popupWindow.isShowing())
        {
            popupWindow.dismiss();
        }
        if (translatedLanguage != null)
        {
            translatedLanguage.setText(getLanguage(language));
        }
    }

    /**
     * 根据语种缩写获取其中文名
     * @param language 语种缩写
     * @return 语言中文名
     */
    private String getLanguage(String language)
    {
        String languageString = "中文";
        switch (language)
        {
            case CHINESE:
                break;
            case ENGLISH:
                languageString = "英语";
                break;
            case JAPANESE:
                languageString = "日语";
                break;
            default:
                Log.e("tan90", "it can not happen!");
        }
        return languageString;
    }
}
