package com.cpigeon.book.module.menu;

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
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * hl 推送设置
 * Created by Administrator on 2018/8/8.
 */

public class PushSetFragment extends BaseBookFragment {


    @BindView(R.id.sb_push_info)
    SwitchButton sb_push_info;
    @BindView(R.id.sb_voice_hint)
    SwitchButton sb_voice_hint;//声音提示

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PushSetFragment.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_set, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("推送设置");
    }


    @OnClick({R.id.sb_push_info, R.id.sb_voice_hint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sb_push_info:
                //推送消息

                if (sb_push_info.isChecked()) {
                    sb_push_info.setBackColorRes(getResources().getColor(R.color.colorPrimary));
                } else {
                    sb_push_info.setBackColorRes(getResources().getColor(R.color.gray));
                }

                break;
            case R.id.sb_voice_hint:
                //声音提示
                if (sb_voice_hint.isChecked()) {
                    sb_voice_hint.setBackColorRes(getResources().getColor(R.color.colorPrimary));
                } else {
                    sb_voice_hint.setBackColorRes(getResources().getColor(R.color.gray));
                }
                break;
        }
    }
}
