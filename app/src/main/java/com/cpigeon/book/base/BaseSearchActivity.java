package com.cpigeon.book.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseActivity;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.widget.SearchTextView;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public abstract class BaseSearchActivity extends BaseActivity {

    public static final int CODE_SEARCH = 0x987;

    protected XRecyclerView mRecyclerView;
    protected RecyclerView mRecyclerViewHistory;
    protected SearchTextView mSearchTextView;
    protected RelativeLayout mRlHistory;
    protected TextView mTvCleanHistory;

    protected SearchHistoryAdapter mSearchHistoryAdapter;

    public  static <A extends  BaseSearchActivity> void start(Activity activity, Class<A> aClass){
        Intent intent = new Intent();
        intent.setClass(activity, aClass);
        activity.startActivityForResult(intent, CODE_SEARCH);
        activity.overridePendingTransition(R.anim.bottom_out, R.anim.anim_no);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_base_search);

        mRecyclerView = findViewById(R.id.list);
        mSearchTextView = findViewById(R.id.key);
        mRlHistory = findViewById(R.id.rlHistory);
        mTvCleanHistory = findViewById(R.id.tvCleanHistory);
        mRecyclerViewHistory = findViewById(R.id.recyclerViewHistory);

        mSearchHistoryAdapter = new SearchHistoryAdapter();
        mRecyclerViewHistory.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerViewHistory.setAdapter(mSearchHistoryAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerView.setAdapter(getResultAdapter());
    }

    protected abstract BaseQuickAdapter getResultAdapter();

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_no, R.anim.bottom_in);
    }


}

