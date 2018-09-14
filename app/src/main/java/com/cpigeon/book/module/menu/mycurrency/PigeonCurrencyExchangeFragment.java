package com.cpigeon.book.module.menu.mycurrency;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.module.menu.mycurrency.adapter.PigeonCurrencyExchangeAdapter;
import com.cpigeon.book.module.menu.mycurrency.viewmodel.PigeonCurrencyViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 鸽币兑换
 * Created by Zhu TingYu on 2018/8/24.
 */

public class PigeonCurrencyExchangeFragment extends BaseFragment {

    XRecyclerView mRecyclerView;
    PigeonCurrencyExchangeAdapter mAdapter;

    private PigeonCurrencyViewModel mPigeonCurrencyViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPigeonCurrencyViewModel = new PigeonCurrencyViewModel();
        initViewModels(mPigeonCurrencyViewModel);
    }

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PigeonCurrencyExchangeFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_currency_exchange);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new PigeonCurrencyExchangeAdapter();
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mPigeonCurrencyViewModel.ecchangePi = 1;
            mPigeonCurrencyViewModel.getTXGP_GeBi_DuiHuan_ListData();
        });

//        mAdapter.setOnLoadMoreListener(() -> {
//            mPigeonCurrencyViewModel.ecchangePi++;
//            mPigeonCurrencyViewModel.getTXGP_GeBi_DuiHuan_ListData();
//        }, mRecyclerView.getRecyclerView());
        setProgressVisible(true);
        mPigeonCurrencyViewModel.getTXGP_GeBi_DuiHuan_ListData();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPigeonCurrencyViewModel.mPigeonCurrencyExchangeData.observe(this, data -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, data);
        });

        mPigeonCurrencyViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
    }
}
