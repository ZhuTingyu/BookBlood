package com.cpigeon.book.module.menu.mycurrency;

import android.app.Activity;
import android.content.Context;
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
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonCurrencyEntity;
import com.cpigeon.book.module.menu.mycurrency.adapter.MyPigeonCurrencyAdapter;
import com.cpigeon.book.module.menu.mycurrency.viewmodel.PigeonCurrencyViewModel;
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

    private PigeonCurrencyViewModel mPigeonCurrencyViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPigeonCurrencyViewModel = new PigeonCurrencyViewModel();
        initViewModels(mPigeonCurrencyViewModel);
    }

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

        mSTvCurrencyExchange.setOnClickListener(v -> {
            //鸽币兑换
            PigeonCurrencyExchangeFragment.start(getBaseActivity());
        });

        mSTvCurrencyDetails.setOnClickListener(v -> {
            //鸽币明细
            PigeonCurrencyDetailsFragment.start(getBaseActivity());
        });

        mAdapter = new MyPigeonCurrencyAdapter();

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonCurrencyEntity.GetgbBean item = (PigeonCurrencyEntity.GetgbBean) adapter.getData().get(position);

        });

        mList.setAdapter(mAdapter);
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mPigeonCurrencyViewModel.mGeBi.observe(this, data -> {
            setProgressVisible(false);
            mTvCount.setText(data.getGb());
            mAdapter.setNewData(data.getGetgb());
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        setProgressVisible(true);
        mPigeonCurrencyViewModel.getTXGP_Account_GeBi();//获取我的鸽币数量
    }
}
