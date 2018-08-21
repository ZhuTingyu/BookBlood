package com.cpigeon.book.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class FiltrateListView extends RelativeLayout {

    private LinearLayout mLlRoot;
    private TextView mTvReset;
    private TextView mTvSure;
    private List<List<SelectTypeEntity>> selectTypeList = Lists.newArrayList();

    public FiltrateListView(Context context) {
        super(context);
        initView(context);
    }

    public FiltrateListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView(context);
    }

    public FiltrateListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView(context);
    }

    private void initAttrs(AttributeSet attrs) {
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_filtrate_list, this);

        mLlRoot = view.findViewById(R.id.llRoot);
        mTvReset = view.findViewById(R.id.tvReset);
        mTvSure = view.findViewById(R.id.tvSure);


    }

    public void setData(boolean isHaveYear, List<SelectTypeEntity> data, List<String> titlse){
        if(isHaveYear){

        }

    }

    private void parseData(List<SelectTypeEntity> data, List<String> titlse){
        List<String> ids = Lists.newArrayList();

        for (int i = 0, len = data.size(); i < len; i++) {
            SelectTypeEntity entity = data.get(i);
            if(!ids.contains(entity.getWhichType())){
                ids.add(entity.getWhichType());
            }
        }
    }

}
