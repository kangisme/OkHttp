package com.kangren.practice.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kangren.practice.R;

/**
 * builder.setSpan(Object what, int start, int end, int flags)
 第1个参数，修改文本的成什么，是一个对象，有21种：
 1、BackgroundColorSpan 背景色
 2、ClickableSpan 文本可点击，有点击事件
 3、ForegroundColorSpan 文本颜色（前景色）
 4、MaskFilterSpan 修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
 5、MetricAffectingSpan 父类，一般不用
 6、RasterizerSpan 光栅效果
 7、StrikethroughSpan 删除线（中划线）
 8、SuggestionSpan 相当于占位符
 9、UnderlineSpan 下划线
 10、AbsoluteSizeSpan 绝对大小（文本字体）,单位为px
 11、DynamicDrawableSpan 设置图片，基于文本基线或底部对齐。
 12、ImageSpan 图片
 13、RelativeSizeSpan 相对大小（文本字体）,相对其它字体的大小比例，1为不变
 14、ReplacementSpan 父类，一般不用
 15、ScaleXSpan 基于x轴缩放
 16、StyleSpan 字体样式：粗体、斜体等
 17、SubscriptSpan 下标（数学公式会用到）
 18、SuperscriptSpan 上标（数学公式会用到）
 19、TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）
 20、TypefaceSpan 文本字体
 21、URLSpan 文本超链接
 第2,3个参数被操作的String的开始位置，结束位置；
 最后一个参数是需要指定的 flag，它是用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果。分别有：
 Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
 Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)
 Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)
 Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
 * Created by kangren on 2017/12/4.
 */

public class SpannableActivity extends Activity {
    private String text = "I am the bone of my sword \nSteel is my body&Fire is my blood \n" +
            "I have created over a thousand blades \nUnknown to Death,Nor known to Life \n" +
            "Have with stood pain to create many weapons \nYet,those hands will never hold anything \n" +
            "So as I pray, Unlimited Blade Works \n剑骨头 玻璃心 没人懂 不开心 中二一生 一事无成 故此 人至剑则无敌";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);

        initClickTextVIew();

        TextView spannableWords = (TextView) findViewById(R.id.spannable_words);
        spannableWords.setText(text, TextView.BufferType.SPANNABLE);
        //点击每个单词响应
        getEachWord(spannableWords);
        spannableWords.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void getEachWord(TextView textView){
        Spannable spans = (Spannable)textView.getText();
        Integer[] indices = getIndices(
                textView.getText().toString().trim(), ' ');
        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i <= indices.length; i++) {
            ClickableSpan clickSpan = getClickableSpan();
            // to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        //改变选中文本的高亮颜色
        textView.setHighlightColor(Color.BLUE);
    }
    private ClickableSpan getClickableSpan(){
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView tv = (TextView) widget;
                String s = tv
                        .getText()
                        .subSequence(tv.getSelectionStart(),
                                tv.getSelectionEnd()).toString();
                Log.d("tapped on:", s);
                Toast.makeText(SpannableActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

    public static Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return (Integer[]) indices.toArray(new Integer[0]);
    }

    private void initClickTextVIew() {
        TextView textView = (TextView) findViewById(R.id.text_view);
        String textStr = "1234567890";
        SpannableStringBuilder builder = new SpannableStringBuilder(textStr);

        builder.setSpan(new URLSpan("https://www.baidu.com"), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.colorAccent)), textStr.length()-5, textStr.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
        // 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
