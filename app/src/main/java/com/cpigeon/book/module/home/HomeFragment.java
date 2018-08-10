package com.cpigeon.book.module.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.BarUtils;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.adpter.HomeAdapter;
import com.cpigeon.book.base.BaseBookFragment;

/**
 * Created by Zhu TingYu on 2018/7/10.
 */

public class HomeFragment extends BaseBookFragment {

    RecyclerView mTopList;
    HomeAdapter mAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarNotBack();
        setImageTitle();
        setToolbarColor(R.color.white);
    }
}
