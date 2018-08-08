package com.cpigeon.book.module.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * hl 设置
 * Created by Administrator on 2018/8/8.
 */

public class SettingFragment extends BaseBookFragment {

    @BindView(R.id.ll_current_version)
    RelativeLayout llCurrentVersion;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, SettingFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }


    @OnClick({R.id.ll_clear_cache, R.id.ll_applied_scores, R.id.ll_current_version, R.id.ll_push_set, R.id.out_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_clear_cache:
                //清除缓存

                break;
            case R.id.ll_applied_scores:
                //应用评分
                try {
                    String mAddress = "market://details?id=" + getActivity().getPackageName();
                    Intent marketIntent = new Intent("android.intent.action.VIEW");
                    marketIntent.setData(Uri.parse(mAddress));
                    startActivity(marketIntent);
                } catch (Exception e) {
                    error("未找到应用商城");
                }

                break;
            case R.id.ll_current_version:
                //当前版本

                break;
            case R.id.ll_push_set:
                //推送设置
                PushSetFragment.start(getActivity());
                break;
            case R.id.out_login:
                break;
        }
    }
}
