package com.cpigeon.book.module.menu.mycurrency;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.menu.mycurrency.adapter.MyPigeonCurrencyAdapter;
import com.cpigeon.book.widget.SimpleTitleView;

/**
 * hl  我的鸽币
 * Created by Administrator on 2018/8/19.
 */

public class MyPigeonCurrencyFragment extends BaseBookFragment {

    private TextView mTvCount;
    private SimpleTitleView mSTvCurrencyExchange;
    private SimpleTitleView mSTvCurrencyDetails;
    private RecyclerView mList;

    MyPigeonCurrencyAdapter mAdapter;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, MyPigeonCurrencyFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_pigeon_currency, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_my_currency);
        mTvCount = findViewById(R.id.tvCount);
        mSTvCurrencyExchange = findViewById(R.id.sTvCurrencyExchange);
        mSTvCurrencyDetails = findViewById(R.id.sTvCurrencyDetails);
        mList = findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        addItemDecorationLine(mList);
        mSTvCurrencyDetails.setOnClickListener(v -> {
            IntentBuilder.Builder().startParentActivity(getBaseActivity(), PigeonCurrencyDetailsFragment.class);
        });

        mAdapter = new MyPigeonCurrencyAdapter();
        mList.setAdapter(mAdapter);

        mAdapter.setNewData(Lists.newTestArrayList());
    }
}
