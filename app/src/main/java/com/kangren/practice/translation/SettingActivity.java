package com.kangren.practice.translation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kangren.practice.R;

/**
 * 百度翻译——Setting
 * Created by kangren on 2017/12/26.
 */

public class SettingActivity extends Activity {

    public static final String LANGUAGE_SELECTED = "language_selected";

    /**
     * 标题栏
     */
    private TitleBar titleBar;

    private RecyclerView recyclerView;

    private LanguageAdapter adapter;

    private ItemTouchHelper itemTouchHelper;
    /**
     * 语言中文列表
     */
    private List<String> cnList;

    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        cnList = new ArrayList<>();
        cnList = Arrays.asList(getResources().getStringArray(R.array.language_cn));
        sp = getSharedPreferences(LANGUAGE_SELECTED, MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(1, intent);
        finish();
        super.onBackPressed();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_language);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new LanguageAdapter();
        recyclerView.setAdapter(adapter);
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView){

            //取消拖拽功能
//            @Override
//            public void onLongClick(RecyclerView.ViewHolder vh) {
//                if (vh.getLayoutPosition() != cnList.size() - 1) {
//                    itemTouchHelper.startDrag(vh);
//                }
//            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                if (position < 4)
                {
                    Toast.makeText(SettingActivity.this, "The language is blocked", Toast.LENGTH_SHORT).show();
                    return;
                }
                String s = cnList.get(position);
                SharedPreferences.Editor editor = sp.edit();
                if (sp.getBoolean(s, false))
                {
                    editor.putBoolean(s, false);
                }
                else
                {
                    editor.putBoolean(s, true);
                }
                editor.apply();
                adapter.notifyItemChanged(position);
            }
        });

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setTitle("Setting");
        titleBar.enableBack();
        titleBar.enableEdit("edit", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "edit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> implements MyItemTouchCallback.ItemTouchAdapter
    {

        public LanguageAdapter() {
            super();
        }

        @Override
        public void onMove(int fromPosition, int toPosition) {
            if (fromPosition==cnList.size()-1 || toPosition==cnList.size()-1){
                return;
            }
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(cnList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(cnList, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onSwiped(int position) {
            cnList.remove(position);
            notifyItemRemoved(position);
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView languageTv;
            ImageView selectedIv;

            public ViewHolder(View itemView) {
                super(itemView);
                languageTv = (TextView) itemView.findViewById(R.id.language);
                selectedIv = (ImageView) itemView.findViewById(R.id.selected);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_language_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.languageTv.setText(cnList.get(position));
            boolean isSelected = sp.getBoolean(cnList.get(position), false);
            //前4个语言固定选择
            if (position < 4)
            {
                isSelected = true;
            }
            if (isSelected)
            {
                holder.selectedIv.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.selectedIv.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return cnList.size();
        }
    }
}
