package com.cpigeon.book.widget;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.utility.StringUtil;
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
    public FiltrateItemAdapter mAdapter;

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
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new FiltrateItemAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    public void setData(List<SelectTypeEntity> data){

        for (int i = 0, len = data.size(); i < len; i++) {
            SelectTypeEntity entity  = data.get(i);
            if(StringUtil.isStringValid(entity.getTypeName())){
                entity.setType(SelectTypeEntity.TYPE_NORMAL);
            }else {
                //类型名字为空 第一个是全部，最后一个为自定义
                if(i == 0){
                    entity.setType(SelectTypeEntity.TYPE_NORMAL);
                }else {
                    entity.setType(SelectTypeEntity.TYPE_CUSTOM);
                }
            }
        }

        mAdapter.setNewData(data);
    }

    public void setTitle(String text){
        mTvTitle.setText(text);
    }
}



