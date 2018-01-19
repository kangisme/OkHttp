package com.kangren.practice;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 弹幕点赞动画
 * Created by kangren on 2017/10/18.
 */

public class DanmuZanLayout extends FrameLayout {
    /**
     * 动画播放时间
     */
    private final int PLAY_TIME = 2000;

    /**
     * 贝塞尔曲线控制点距离
     */
    private int BEZIER_LENGTH = 300;

    private float width;
    private float height;

//    /**
//     * 播放起点
//     * 控制点1
//     * 控制点2
//     * 结束点
//     */
//    private PointF startPoint;
//    private PointF controlPoint1;
//    private PointF controlPoint2;
//    private PointF endPoint;

    private Context mContext;

    private Drawable[] loveIcons;

    // 用于实现随机颜色功能
    private Random random = new Random();

    public DanmuZanLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    //初始化显示的图片
    private void init()
    {
        loveIcons = new Drawable[7];
        Drawable red = getResources().getDrawable(R.drawable.danmu_zan_play);
        Drawable yellow = getResources().getDrawable(R.drawable.danmu_zan_play);
        Drawable blue = getResources().getDrawable(R.drawable.danmu_zan_play);
        Drawable green = getResources().getDrawable(R.drawable.danmu_zan_play);
        Drawable orange = getResources().getDrawable(R.drawable.danmu_zan_play);
        Drawable purple = getResources().getDrawable(R.drawable.danmu_zan_play);
        Drawable pink = getResources().getDrawable(R.drawable.danmu_zan_play);
        loveIcons[0] = red;
        loveIcons[1] = yellow;
        loveIcons[2] = blue;
        loveIcons[3] = green;
        loveIcons[4] = orange;
        loveIcons[5] = purple;
        loveIcons[6] = pink;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    /**
     * 播放动画
     * @param start 起始点坐标
     */
    public void play(PointF start)
    {
        PointF startPoint;
        PointF controlPoint1;
        PointF controlPoint2;
        PointF endPoint;
        startPoint = new PointF();
        startPoint.x = start.x;
        startPoint.y = start.y;
        //修正比例
        float scaleX = (width / 2 - startPoint.x) / width;
        float scaleY = (height / 2 - startPoint.y) / height;
        controlPoint1 = new PointF();
        controlPoint1.x = startPoint.x + (random.nextInt(BEZIER_LENGTH) - BEZIER_LENGTH / 2 + BEZIER_LENGTH * scaleX);
        controlPoint1.y = startPoint.y + (random.nextInt(BEZIER_LENGTH) - BEZIER_LENGTH / 2 + BEZIER_LENGTH * scaleY);
        Log.d("kang", controlPoint1.x + "    " + controlPoint1.y);
        controlPoint2 = new PointF();
        controlPoint2.x = controlPoint1.x + (random.nextInt(BEZIER_LENGTH) - BEZIER_LENGTH / 2);
        controlPoint2.y = controlPoint1.y + (random.nextInt(BEZIER_LENGTH) - BEZIER_LENGTH / 2);
        endPoint = new PointF();
        endPoint.x = controlPoint2.x + (random.nextInt(BEZIER_LENGTH) - BEZIER_LENGTH / 2);
        endPoint.y = controlPoint2.y + (random.nextInt(BEZIER_LENGTH) - BEZIER_LENGTH / 2);
        final ImageView starView = new ImageView(mContext);
        starView.setImageDrawable(loveIcons[random.nextInt(7)]);
        starView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(starView);
        BezierTypeEvaluator bezierTypeEvaluator = new BezierTypeEvaluator(controlPoint1, controlPoint2);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierTypeEvaluator, startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                starView.setX(pointF.x);
                starView.setY(pointF.y);
            }
        });
        valueAnimator.addListener(new AnimEndListener(starView));
        valueAnimator.setDuration(PLAY_TIME);
        valueAnimator.start();
    }


    private class AnimEndListener extends AnimatorListenerAdapter
    {
        private View target;

        public AnimEndListener(View target)
        {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation)
        {
            super.onAnimationEnd(animation);
            //动画结束时移除图片
            removeView((target));
        }
    }

    //二阶贝塞尔曲线
    private class BezierTypeEvaluator implements TypeEvaluator<PointF> {
        private PointF mControllPoint1, mControllPoint2;

        public BezierTypeEvaluator(PointF mControllPointOne, PointF mControllPointTwo) {
            mControllPoint1 = mControllPointOne;
            mControllPoint2 = mControllPointTwo;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF pointCur = new PointF();
            // 实时计算最新的点X坐标
            pointCur.x = startValue.x * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint1.x * fraction * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint2.x * (1 - fraction) * fraction * fraction + endValue.x * fraction * fraction * fraction;
            // 实时计算最新的点Y坐标
            pointCur.y = startValue.y * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint1.y * fraction * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint2.y * (1 - fraction) * fraction * fraction + endValue.y * fraction * fraction * fraction;
            return pointCur;
        }
    }
}
