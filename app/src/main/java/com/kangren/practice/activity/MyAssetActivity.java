package com.kangren.practice.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kangren.practice.R;
import com.kangren.practice.util.GridSpacingItemDecoration;

/**
 * Created by kangren on 2017/12/4.
 */

public class MyAssetActivity extends Activity {

    private RecyclerView assetRecycler;

    private List<Drawable> drawables;

    /**
     * 当前点击的position
     */
    private int mPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myasset);
        init();
    }

    private void init()
    {
        drawables = new ArrayList<>();
        Drawable drawable = getResources().getDrawable(R.mipmap.asset_diamond);
        drawables.add(drawable);
        drawable = getResources().getDrawable(R.mipmap.asset_points);
        drawables.add(drawable);
        drawable = getResources().getDrawable(R.mipmap.asset_dragon);
        drawables.add(drawable);
        drawable = getResources().getDrawable(R.mipmap.asset_coupon);
        drawables.add(drawable);
        drawable = getResources().getDrawable(R.mipmap.asset_sports);
        drawables.add(drawable);
        drawable = getResources().getDrawable(R.mipmap.asset_movies);
        drawables.add(drawable);
        drawable = getResources().getDrawable(R.mipmap.asset_present_times);
        drawables.add(drawable);
        assetRecycler = (RecyclerView) findViewById(R.id.asset_recycler);
        GridLayoutManager  gridLayoutManager = new GridLayoutManager (this, 3, GridLayoutManager.VERTICAL, false);
        assetRecycler.setLayoutManager(gridLayoutManager);
        assetRecycler.addItemDecoration(new GridSpacingItemDecoration(3, getResources().getDimensionPixelOffset(R.dimen.property_item_gap), true));
        assetRecycler.setHasFixedSize(true);
        assetRecycler.setAdapter(new PropertyAdapter());
    }

    private class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder>
    {
        public PropertyAdapter() {
            super();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            private LinearLayout normalLayout;
            private LinearLayout clickedLayout;
            private ImageView assetIcon;
            private TextView assetText;
            private TextView useText;
            private TextView getText;
            private ImageView closedIcon;
            public ViewHolder(View itemView) {
                super(itemView);
                assetIcon = (ImageView) itemView.findViewById(R.id.asset_item_diamond);
                assetText = (TextView) itemView.findViewById(R.id.asset_item_text);
                useText = (TextView) itemView.findViewById(R.id.asset_item_use);
                getText = (TextView) itemView.findViewById(R.id.asset_item_get);
                closedIcon = (ImageView) itemView.findViewById(R.id.asset_closed);
                normalLayout = (LinearLayout) itemView.findViewById(R.id.asset_normal);
                clickedLayout = (LinearLayout) itemView.findViewById(R.id.asset_clicked);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.assetIcon.setImageDrawable(drawables.get(position));
            holder.normalLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosition = position;
                    notifyDataSetChanged();
                }
            });
            holder.clickedLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //事件不处理，覆盖normalLayout点击事件
                }
            });
            holder.useText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyAssetActivity.this, "use", Toast.LENGTH_SHORT).show();
                }
            });
            holder.getText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyAssetActivity.this, "get", Toast.LENGTH_SHORT).show();
                }
            });
            holder.closedIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消clickedLayout
                    mPosition = -1;
                    notifyDataSetChanged();
                }
            });
            if (position == mPosition)
            {
                Animation mShowAction = AnimationUtils.loadAnimation(MyAssetActivity.this, R.anim.property_item_click_show);
                holder.clickedLayout.startAnimation(mShowAction);
                holder.clickedLayout.setVisibility(View.VISIBLE);
                mShowAction = AnimationUtils.loadAnimation(MyAssetActivity.this, R.anim.property_item_close_show);
                holder.closedIcon.startAnimation(mShowAction);
                holder.closedIcon.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.clickedLayout.setVisibility(View.GONE);
                holder.closedIcon.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return drawables.size();
        }
    }
}
