package com.kangren.practice.translation;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kangren.practice.R;
import com.kangren.practice.translation.util.BaiduAPI;
import com.kangren.practice.util.NetworkUtils;

/**
 * Created by kangren on 2017/12/9.
 */

public class BaiduTranslateActivity extends Activity
        implements View.OnClickListener,PopupMenu.OnMenuItemClickListener{

    private final static int MSG_TRANSLATION_RESULT = 0;

    private final static int MSG_TRANSLATION_FAIL = 1;

    /**
     * 自动判断
     */
    private final static String AUTO = "auto";

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

    private ImageButton toolMenu;

    /**
     * 选择语言弹窗
     */
    private SelectPopupWindow popupWindow;

    private Gson gson;

    /**
     * 剪贴板
     */
    private ClipboardManager clipboardManager;

    private LinearLayout loadingView;

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
                        if (loadingView != null)
                        {
                            loadingView.setVisibility(View.GONE);
                        }
                        TransResultBean bean = resultBean.getTransResult().get(0);

                        if (translatedText != null)
                        {
                            translatedText.setText(bean.getDst());
                        }
                        if (originalLanguage != null)
                        {
                            originalLanguage.setText(getLanguage(resultBean.getFrom()));
                        }
                    }
                    break;
                case MSG_TRANSLATION_FAIL:
                    if (loadingView != null)
                    {
                        loadingView.setVisibility(View.GONE);
                    }
                    Toast.makeText(BaiduTranslateActivity.this, "失败了，再试一次吧！", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);
        initView();
        initPopupView();
    }

    private void initView() {
        from = AUTO;
        to = ENGLISH;
        gson = new Gson();
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        loadingView = (LinearLayout) findViewById(R.id.loading_view);
        loadingView.setOnClickListener(this);

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
        toolMenu = (ImageButton) findViewById(R.id.tool_menu);
        toolMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(BaiduTranslateActivity.this, toolMenu, Gravity.BOTTOM);
                menu.inflate(R.menu.menu_tool);
                menu.setOnMenuItemClickListener(BaiduTranslateActivity.this);
                menu.show();
            }
        });
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
                if (!NetworkUtils.isNetworkAvailable(this))
                {
                    Toast.makeText(this, "当前网络不可用，请检查网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }

                //显示正在翻译进度条
                loadingView.setVisibility(View.VISIBLE);

                //在子线程中处理网络请求
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
                            handler.sendEmptyMessage(MSG_TRANSLATION_FAIL);
                        }
                    }
                }).start();
                break;
            case R.id.select_language:
                break;
            case R.id.translated_text:
                break;
            case R.id.loading_view:
                //在翻译进行中时，不响应其它点击事件
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

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.clear:
                originalText.setText("");
                translatedText.setText("");
                break;
            case R.id.paste:
                ClipData clipData = clipboardManager.getPrimaryClip();
                if (clipData != null && clipData.getItemCount() > 0)
                {
                    ClipData.Item clipItem = clipData.getItemAt(0);
                    String existString = originalText.getText().toString();
                    originalText.setText(existString + clipItem.getText().toString());
                }
                else
                {
                    Toast.makeText(this, "The clipboard is empty!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.copy:
                String result = translatedText.getText().toString();
                if (TextUtils.isEmpty(result))
                {
                    Toast.makeText(this, "The translation is empty!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    clipboardManager.setText(result);
                    Toast.makeText(this, "Copyed to clipboard!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
