package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * hl  账户余额
 * Created by Administrator on 2018/8/19.
 */

public class AccountBalanceFragment extends BaseBookFragment {

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AccountBalanceFragment.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_balance, container, false);
        return view;
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

                break;
            case R.id.tv_bottom_help:
                //不知如何操作？快快点击此处!

                break;
        }
    }
}
