package com.kangren.practice.translation;

import java.util.List;

import com.kangren.practice.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 自定义PopupWindow——选择语言弹窗
 * Created by kangren on 2017/12/9.
 */

public class SelectPopupWindow extends PopupWindow {

    private ListView listView;

    /**
     * 语言中文列表
     */
    private List<String> cnList;

    private Context mContext;

    public SelectPopupWindow(Context context,List<String> selectedList, AdapterView.OnItemClickListener onItemClickListener) {
        super(context);
        mContext = context;
        cnList = selectedList;
        final View view = View.inflate(context, R.layout.select_language_popup, null);
        listView = (ListView) view.findViewById(R.id.list_language);
        listView.setAdapter(new ListAdapter());
        listView.setOnItemClickListener(onItemClickListener);

        this.setContentView(view);

        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.anim.show_from_bottom);

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xBB000000);

        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.popup_layout).getTop();
                int y = (int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y < height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private class ListAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return cnList.size();
        }

        @Override
        public Object getItem(int position) {
            return cnList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null)
            {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.list_language_item, null);
                viewHolder.language = (TextView) convertView.findViewById(R.id.language);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.language.setText(cnList.get(position));
            return convertView;
        }

        class ViewHolder{
            TextView language;
        }
    }
}
