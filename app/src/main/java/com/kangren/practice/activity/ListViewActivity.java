package com.kangren.practice.activity;


import java.util.ArrayList;
import java.util.List;

import com.kangren.practice.R;
import com.kangren.practice.model.Model;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by kangren on 2017/10/9.
 */

public class ListViewActivity extends Activity {
    private List<Model> list = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        init();
    }

    private void init()
    {
        listView = (ListView) findViewById(R.id.list_view);
        for (int i = 0; i < 30; i ++)
        {
            Model model = new Model();
            model.setPic(BitmapFactory.decodeResource(getResources(), R.drawable.xm_01 + i));
            model.setName("图片" + i);
            list.add(model);
        }
        listView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
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
                convertView = LayoutInflater.from(ListViewActivity.this).inflate(R.layout.list_item, parent, false);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(list.get(position).getName());
            viewHolder.imageView.setImageBitmap(list.get(position).getPic());
            return convertView;
        }
        private class ViewHolder
        {
            ImageView imageView;
            TextView textView;
        }
    }
}
