package com.kangren.okhttp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/11/21.
 */

public class CircleView extends AppCompatImageView {

    private Bitmap iconBitmap;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性值
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        BitmapDrawable drawable = (BitmapDrawable) array.getDrawable(R.styleable.CircleView_mBitmap);
        if (drawable != null) {
            iconBitmap = drawable.getBitmap();
        }
        array.recycle();
        Animation rotate = AnimationUtils.loadAnimation(context, R.anim.circle_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        setAnimation(rotate);
        startAnimation(rotate);
    }
}
