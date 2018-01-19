package com.kangren.practice.activity;

import com.kangren.practice.R;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by kangren on 2017/10/25.
 */

public class DrawableActivity extends Activity {
    private final int RED_COLOR = 0xffff0000;
    private final int GREEN_COLOR = 0xff00ff00;
    private final int BLUE_COLOR = 0xff0000ff;
    private int[] colors = {RED_COLOR, GREEN_COLOR, BLUE_COLOR};
    private int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        init();
    }

    private void init()
    {
        final Drawable drawable = getResources().getDrawable(R.drawable.danmu_user_bg);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageDrawable(drawable);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = i++ % 3;
                Drawable temp = tintDrawable(drawable, ColorStateList.valueOf(colors[id]));
                imageView.setImageDrawable(temp);
            }
        });
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
