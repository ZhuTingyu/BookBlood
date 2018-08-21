package com.cpigeon.book.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.entity.MultiSelectEntity;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.adpter.FiltrateItemAdapter;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class FiltrateItemView extends RelativeLayout {

    TextView mTvTitle;
    RecyclerView recyclerView;
    FiltrateItemAdapter mAdapter;

    public FiltrateItemView(Context context) {
        super(context);
        initView(context);
    }

    public FiltrateItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView(context);
    }

    public FiltrateItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView(context);
    }

    private void initAttrs(AttributeSet attrs) {
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_filtrate_item, this);

        mTvTitle = view.findViewById(R.id.tvTitle);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new FiltrateItemAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    public void setData(List<SelectTypeEntity> data){
        mAdapter.setNewData(data);
    }
}



