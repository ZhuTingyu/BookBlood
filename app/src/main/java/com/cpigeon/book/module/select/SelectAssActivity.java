package com.cpigeon.book.module.select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.base.base.BaseActivity;
import com.base.base.pinyin.LetterSortModel;
import com.base.util.FragmentUtils;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AssEntity;
import com.cpigeon.book.module.select.adpter.SelectAssAdapter;
import com.cpigeon.book.module.select.viewmodel.SelectAssViewModel;
import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class SelectAssActivity extends BaseActivity {

    XRecyclerView mRecyclerView;
    SelectAssAdapter mAdapter;
    WaveSideBar mWaveSideBar;
    LetterSortModel<AssEntity> mModel = new LetterSortModel<>();
    SelectAssViewModel mViewModel;
    @Override
    protected void onResume() {
        super.onResume();
        mViewModel = new SelectAssViewModel();
        initViewModel(mViewModel);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ass);

        mViewModel.liveAss.observe(this, assEntities -> {
            mModel.setData(assEntities);

            mAdapter.initWave(mModel, mWaveSideBar);
            mAdapter.initHead(getBaseActivity());
            mAdapter.setNewData(mModel.getData());
        });

        mRecyclerView = findViewById(R.id.list);
        mWaveSideBar = findViewById(R.id.side_bar);


        mAdapter  = new SelectAssAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView.getRecyclerView());

        mViewModel.getAssList();


    }
}
