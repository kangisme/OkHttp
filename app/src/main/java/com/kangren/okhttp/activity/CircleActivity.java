package com.kangren.okhttp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.kangren.okhttp.R;
import com.kangren.okhttp.view.CircleView;

/**
 * Created by kangren on 2017/11/21.
 */

public class CircleActivity extends Activity {

    private boolean flag;
    private RotateDrawable rotateDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        CircleView circleView = (CircleView) findViewById(R.id.circle_view);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.alipay);
        Bitmap tempBitmap = bitmapScale(bitmap, 1.0f);
        Drawable drawable = new BitmapDrawable(bitmap);
        rotateDrawable = (RotateDrawable) getResources().getDrawable(R.drawable.rotate_drawable);
        rotateDrawable.setDrawable(drawable);
        seekBar.setThumb(rotateDrawable);
        flag = true;
        new MyThread().start();
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                while(true) {
                    Thread.sleep(50);
                    if(flag) {
                        //只有在flag==true的情况下才会对唱片进行旋转操作
                        handler.sendEmptyMessage(0);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int level = rotateDrawable.getLevel();
            level = level + 200;
            if(level > 10000) {
                level = level - 10000;
            }
            rotateDrawable.setLevel(level);
        }
    };

    /**
     * 根据scale生成一张图片  图片缩放：
     *
     * @param bitmap
     * @param scale  等比缩放值
     * @return
     */
    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

}
