package com.cpigeon.book.module.foot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 测试
 * Created by Administrator on 2018/8/7.
 */

public class MyActivity extends AppCompatActivity {

    private String TAG = "mmmy";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_home_fragment);
        ButterKnife.bind(this);

        findViewById(R.id.ac_btns2).setOnClickListener(v -> {
            Log.d(TAG, "onViewClicked: 3");
            ToastUtils.showLong(this, "点击测试2");
        });
    }

    @OnClick({R.id.ac_btns, R.id.ac_btns2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_btns:
                Log.d(TAG, "onViewClicked: 1");
                ToastUtils.showLong(this, "点击测试");
                break;
            case R.id.ac_btns2:
                Log.d(TAG, "onViewClicked: 2");
                break;
        }
    }
}
