package com.cpigeon.book.test;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.adpter.DemoAdapter;

/**
 * Created by Zhu TingYu on 2018/3/1.
 */

public class NumberListFragment extends BaseFragment{

    NumberListViewModel viewModel;
    XRecyclerView recyclerView;
    DemoAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(getBaseActivity()).get(NumberListViewModel.class);
        initViewModel(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("列表");

        setToolbarRight("测试", menuItem -> {
            viewModel.add();
            return false;
        });

        viewModel.number.observe(this, list -> {
            if(list.isEmpty()){
                adapter.setLoadMore(true);
            }else {
                adapter.setLoadMore(false);
                adapter.addData(list);
            }
        });

        recyclerView = findViewById(R.id.list);
        adapter = new DemoAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(() -> {
            viewModel.page ++;
            viewModel.loadNumber();
        },recyclerView.getRecyclerView());

        viewModel.loadNumber();

    }
}
