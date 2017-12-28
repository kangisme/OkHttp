package com.kangren.practice.translation;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangren.practice.R;

/**
 * 自定义标题栏
 * Created by kangren on 2017/12/26.
 */

public class TitleBar extends RelativeLayout {

    /**
     * 标题
     */
    private TextView titleTv;

    /**
     * 返回箭头
     */
    private ImageButton backIB;

    /**
     * 右侧编辑，默认隐藏，可扩展
     */
    private TextView editTv;


    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.titlebar_layout, this);
        titleTv = (TextView) findViewById(R.id.titlebar_title);
        backIB = (ImageButton) findViewById(R.id.titlebar_back);
        backIB.setVisibility(GONE);
        editTv = (TextView) findViewById(R.id.titlebar_edit);
        editTv.setVisibility(GONE);
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title)
    {
        if (titleTv != null)
        {
            titleTv.setText(title);
        }
    }

    /**
     * 开启回退按钮
     */
    public void enableBack()
    {
        if (backIB != null)
        {
            backIB.setVisibility(VISIBLE);
            backIB.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity)getContext()).onBackPressed();
                }
            });
        }
    }

    /**
     * 开启右侧扩展功能按钮
     * @param edit 按钮显示
     */
    public void enableEdit(String edit, OnClickListener listener)
    {
        if (editTv == null)
        {
            return;
        }
        editTv.setVisibility(VISIBLE);
        editTv.setOnClickListener(listener);
        if (edit != null && !TextUtils.isEmpty(edit))
        {
            editTv.setText(edit);
        }
    }
}
