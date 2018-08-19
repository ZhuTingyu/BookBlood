package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.menu.viewmodel.SignViewModel;

/**
 * 签到
 * Created by Administrator on 2018/8/9.
 */

public class SignFragment extends BaseBookFragment {

    private SignViewModel mSignViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, SignFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSignViewModel = new SignViewModel();
        initViewModel(mSignViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setTitle("签到");

        mSignViewModel.pigeonFriendMsgListData.observe(this, signRuleListEntities -> {
            //签到规则
            //SignRuleAdapter
        });

        mSignViewModel.getZGW_Users_SignGuiZeData();
    }
}
