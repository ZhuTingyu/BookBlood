package com.cpigeon.book.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/23.
 */

public class TestActivity extends BaseBookActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_fragment;
    }

    @OnClick({R.id.ac_btns3, R.id.ac_btns4, R.id.ac_btns5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_btns3:
                ToastUtils.showLong(TestActivity.this, "点击");
                break;
            case R.id.ac_btns4:

                break;
            case R.id.ac_btns5:
                break;
        }
    }
}
