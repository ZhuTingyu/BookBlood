package com.cpigeon.book.base;

import android.os.Bundle;
import android.view.View;

import com.base.BaseFragment;
import com.base.util.dialog.DialogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class BaseBookFragment extends BaseFragment {

    Unbinder unbinder;

    @Override
    public void error(int code, String error) {
        super.error(code, error);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

}
