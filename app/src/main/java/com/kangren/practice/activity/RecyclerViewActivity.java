package com.kangren.practice.activity;

import java.util.ArrayList;
import java.util.List;

import com.kangren.practice.R;
import com.kangren.practice.model.Model;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kangren on 2017/10/9.
 */

public class RecyclerViewActivity extends Activity {
    private RecyclerView recyclerView;
    private List<Model> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        init();
    }

    private void init()
    {
        for (int i = 0; i < 30; i ++)
        {
            Model model = new Model();
            model.setPic(BitmapFactory.decodeResource(getResources(), R.drawable.xm_01 + i));
            model.setName("图片" + i);
            list.add(model);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {
        private List<Model> modelList;
        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView imageView;
            TextView textView;
            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }

        public MyAdapter(List<Model> modelList) {
            this.modelList = modelList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecyclerViewActivity.this, "you clicked image" + holder.textView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecyclerViewActivity.this, "you clicked TextView" + holder.textView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Model model = modelList.get(position);
            holder.textView.setText(model.getName());
            holder.imageView.setImageBitmap(model.getPic());
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }
    }
}
