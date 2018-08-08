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

/**
 * hl  账户安全
 * Created by Administrator on 2018/8/8.
 */

public class AccountSecurityFragment extends BaseBookFragment {

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AccountSecurityFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_security, container, false);
        return view;
    }

}
