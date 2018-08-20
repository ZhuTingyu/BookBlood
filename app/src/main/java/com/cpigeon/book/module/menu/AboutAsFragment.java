package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.base.BaseWebViewActivity;
import com.base.util.IntentBuilder;
import com.base.util.utility.PhoneUtils;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.util.KeywordUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * hl 关于我们
 * Created by Administrator on 2018/8/8.
 */

public class AboutAsFragment extends BaseBookFragment {

    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.tv_copyright)
    TextView tv_copyright;

    @BindView(R.id.str_about_as)
    TextView str_about_as; //关于我们文字


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AboutAsFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fratment_about_as, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("关于我们");

        tvVersionName.setText("V " + PhoneUtils.getVersionName(getActivity()));//设置版本号
        tv_copyright.setText(String.valueOf("Copyright © 2006 - " + TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYY) + " All rights reserved."));


        String[] bl = {"12", "100G", "云计算分析", "种：", "养：", "训：", "赛："};
        str_about_as.setText(KeywordUtil.matcherSearchTitle(Color.BLACK, getString(R.string.string_about_as_tv), bl));

    }

    @OnClick({R.id.tv_function_intro, R.id.tv_user_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_function_intro:
                //功能介绍
                Intent intent1 = new Intent(getActivity(), BaseWebViewActivity.class);
                intent1.putExtra(IntentBuilder.KEY_DATA, String.valueOf(getString(R.string.baseUrl) + getString(R.string.txgp_function_introduce)));
                startActivity(intent1);
                break;
            case R.id.tv_user_agreement:
                //用户协议
                Intent intent2 = new Intent(getActivity(), BaseWebViewActivity.class);
                intent2.putExtra(IntentBuilder.KEY_DATA, String.valueOf(getString(R.string.baseUrl) + getString(R.string.txgp_use_protocol)));
                startActivity(intent2);
                break;
        }
    }
}
