package com.cpigeon.book.module.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.pigeonhouse.PigeonHouseInfoFragment;

import butterknife.OnClick;

/**
 * 我的信息
 * Created by Administrator on 2018/8/8.
 */

public class InfoDetailsFragment extends BaseBookFragment {

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, InfoDetailsFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_details, container, false);
        return view;
    }

    @OnClick({R.id.ll_loft_info, R.id.ll_account_security, R.id.ll_logbook, R.id.ll_about_us, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_loft_info:
                //鸽舍信息
                PigeonHouseInfoFragment.start(getActivity(),false);
                break;
            case R.id.ll_account_security:
                //账户安全
                AccountSecurityFragment.start(getActivity());
                break;
            case R.id.ll_logbook:
                //操作日志
                LogbookFragment.start(getActivity());
                break;
            case R.id.ll_about_us:
                //关于我们
                AboutAsFragment.start(getActivity());
                break;

            case R.id.ll_setting:
                SettingFragment.start(getActivity());
                break;
        }
    }
}
