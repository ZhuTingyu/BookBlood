package com.cpigeon.book.module.basepigeon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/10.
 */

public class BaseListFragment extends BaseBookFragment {


    @BindView(R.id.list)
    protected XRecyclerView list;
    @BindView(R.id.tvOk)
    protected TextView tvOk;
    @BindView(R.id.view_placeholder)
    protected View view_placeholder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
        return view;
    }

}
