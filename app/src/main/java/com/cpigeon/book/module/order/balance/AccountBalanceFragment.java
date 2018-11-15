package com.cpigeon.book.module.order.balance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.WXPayEvent;
import com.cpigeon.book.module.order.viewmodel.AccountBalanceViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * hl  账户余额
 * Created by Administrator on 2018/8/19.
 */

public class AccountBalanceFragment extends BaseBookFragment {

    @BindView(R.id.tv_balance)
    TextView tvBalance;
    AccountBalanceViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AccountBalanceFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new AccountBalanceViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_balance, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("余额");

        setToolbarRight("明细", item -> {
            AccountBalanceDetailsFragment.start(getActivity());
            return true;
        });

        tvBalance.setTypeface(Typeface.createFromAsset(getBaseActivity().getAssets(), "SF-PRO-TEXT_REGULAR.TTF"));

        setProgressVisible(true);
        mViewModel.getBalance();
    }

    @Override
    protected void initObserve() {
        mViewModel.mDataBalance.observe(this, balanceEntity -> {
            setProgressVisible(false);
            tvBalance.setText(balanceEntity.getAb());
        });
    }

    @OnClick({R.id.balance_refill, R.id.balance_withdraw, R.id.tv_bottom_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.balance_refill:
                //充值
                BalanceRefillFragment.start(getActivity());
                break;
            case R.id.balance_withdraw:
                //提现

                ToastUtils.showLong(getBaseActivity(), "该功能暂未实现，敬请期待");

                break;
            case R.id.tv_bottom_help:
                //不知如何操作？快快点击此处!

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(WXPayEvent event) {
        mViewModel.getBalance();
    }
}
