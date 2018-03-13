package com.kangren.practice.translation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    private final static int REQUEST_SETTING = 0;

    public final static int RESULT_FROM_SETTING = 1;

    private final static int MSG_TRANSLATION_RESULT = 0;

    private final static int MSG_TRANSLATION_FAIL = 1;

    /**
     * “中文”在数组中的位置
     */
    private final static int CHINESE_INDEX = 1;

    /**
     * “en”在数组中的位置
     */
    private final static int ENGLISH_INDEX = 2;

    private final static String AUTO = "auto";

    private final static String ENGLISH = "en";

    /**
     * 语言中文列表
     */
    private List<String> cnList;

    /**
     * 语言英文列表
     */
    private List<String> enList;

    private List<String> selectedList;

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
        cnList = new ArrayList<>();
        cnList = Arrays.asList(getResources().getStringArray(R.array.language_cn));
        enList = new ArrayList<>();
        enList = Arrays.asList(getResources().getStringArray(R.array.language_en));
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
        selectedList = new ArrayList<>();
        refreshList();
        findViewById(R.id.select_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = new SelectPopupWindow(BaiduTranslateActivity.this, selectedList, onItemClickListener);

                popupWindow.showAtLocation(BaiduTranslateActivity.this.findViewById(R.id.main_layout),
                        Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });
    }

    /**
     * 刷新已选中的语言
     */
    private void refreshList() {
        selectedList.clear();
        selectedList.add("中文");
        selectedList.add("英语");
        selectedList.add("日语");
        SharedPreferences sp = getSharedPreferences(SettingActivity.LANGUAGE_SELECTED, MODE_PRIVATE);
        for (int i = 0; i < cnList.size(); i++)
        {
            String language = cnList.get(i);
            if (sp.getBoolean(language,false))
            {
                selectedList.add(language);
            }
        }
    }

    /**
     * 处理popupWindow中的ListView点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (view instanceof TextView && translatedLanguage != null) {
                String language = ((TextView) view).getText().toString();
                translatedLanguage.setText(language);
                to = getShort(language);
            }
            if (popupWindow != null && popupWindow.isShowing())
            {
                popupWindow.dismiss();
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
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
                           final String result = BaiduAPI.getTranslation(query, from, to);
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
            translatedLanguage.setText(language);
        }
    }

    /**
     * 根据语种缩写获取其中文名
     * @param language 语种缩写
     * @return 语言中文名
     */
    private String getLanguage(String language)
    {
        //异常状态返回默认值
        if (cnList == null || enList == null || language == null || TextUtils.isEmpty(language))
        {
            return "中文";
        }

        //默认index，不存在该语言则返回cnList.get(1)
        int index = CHINESE_INDEX;
        for (int i = 0; index < enList.size(); i++)
        {
            if (language.equals(enList.get(i)))
            {
                index = i;
                break;
            }
        }
        return cnList.get(index);
    }

    /**
     * 根据语言获取其缩写
     * @param language 语言
     * @return 缩写
     */
    private String getShort(String language)
    {
        //异常状态返回默认值
        if (cnList == null || enList == null || language == null || TextUtils.isEmpty(language))
        {
            return ENGLISH;
        }

        //默认index，不存在该语言则返回cnList.get(2)
        int index = ENGLISH_INDEX;
        for (int i = 0; index < cnList.size(); i++)
        {
            if (language.equals(cnList.get(i)))
            {
                index = i;
                break;
            }
        }
        return enList.get(index);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.clear:
                originalLanguage.setText("自动检测");
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
                Intent intent = new Intent(this, SettingActivity.class);
                startActivityForResult(intent, REQUEST_SETTING);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_SETTING && resultCode == RESULT_FROM_SETTING)
        {
            refreshList();
        }
    }
}
