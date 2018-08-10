package com.cpigeon.book.module.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.base.base.BaseActivity;
import com.base.base.pinyin.LetterSortModel;
import com.base.util.FragmentUtils;
import com.base.util.IntentBuilder;
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
    LinearLayout mLlSearchTextClick;

    SelectAssAdapter mAdapter;
    WaveSideBar mWaveSideBar;
    LetterSortModel<AssEntity> mModel = new LetterSortModel<>();
    SelectAssViewModel mViewModel;
    SearchAssFragment searchAssFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ass);
        setTitle(R.string.title_select_ass);
        mViewModel = new SelectAssViewModel();
        initViewModel(mViewModel);
        mViewModel.liveAss.observe(this, assEntities -> {
            setProgressVisible(false);
            mModel.setData(assEntities);
            mAdapter.initWave(mModel, mWaveSideBar);
            mAdapter.initHead(getBaseActivity());
            mAdapter.setNewData(mModel.getData());
        });

        mRecyclerView = findViewById(R.id.list);
        mWaveSideBar = findViewById(R.id.side_bar);
        mLlSearchTextClick = findViewById(R.id.llSearchTextClick);


        mAdapter  = new SelectAssAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView.getRecyclerView());

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent();
            intent.putExtra(IntentBuilder.KEY_DATA, mAdapter.getData().get(position).getISOCName());

        });

        setProgressVisible(true);
        mViewModel.getAssList();

        searchAssFragment = new SearchAssFragment();
        FragmentUtils.add(getSupportFragmentManager(), searchAssFragment ,R.id.rlRoot,false);
        hideSearch();

        mLlSearchTextClick.setOnClickListener(v -> {
            FragmentUtils.show(searchAssFragment);
        });

    }

    public void hideSearch(){
        FragmentUtils.hide(searchAssFragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
